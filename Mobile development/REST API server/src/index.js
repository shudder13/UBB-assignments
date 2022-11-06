const Koa = require('koa');
const app = new Koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({ server });
const Router = require('koa-router');
const cors = require('koa-cors');
const bodyparser = require('koa-bodyparser');

app.use(bodyparser());
app.use(cors());
app.use(async (ctx, next) => {
  const start = new Date();
  await next();
  const ms = new Date() - start;
  console.log(`${ctx.method} ${ctx.url} ${ctx.response.status} - ${ms}ms`);
});

app.use(async (ctx, next) => {
  await new Promise(resolve => setTimeout(resolve, 2000));
  await next();
});

app.use(async (ctx, next) => {
  try {
    await next();
  } catch (err) {
    ctx.response.body = { issue: [{ error: err.message || 'Unexpected error' }] };
    ctx.response.status = 500;
  }
});

class Task {
  constructor({ id, text, date, active }) {
    this.id = id;
    this.text = text;
    this.date = date;
    this.active = active;
  }
}

const tasks = [];
for (let i = 0; i < 3; i++) {
  tasks.push(new Task({ id: `${i}`, text: `Task ${i}`, date: new Date(Date.now() + i), active: true }));
}
let lastUpdated = tasks[tasks.length - 1].date;
let lastId = tasks[tasks.length - 1].id;
const pageSize = 10;

const broadcast = data =>
  wss.clients.forEach(client => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(data));
    }
  });

const router = new Router();

router.get('/task', ctx => {
  ctx.response.body = tasks;
  ctx.response.status = 200;
});

router.get('/task/:id', async (ctx) => {
  const taskId = ctx.request.params.id;
  const task = tasks.find(task => taskId === task.id);
  if (task) {
    ctx.response.body = task;
    ctx.response.status = 200; // ok
  } else {
    ctx.response.body = { issue: [{ warning: `Task with id ${taskId} not found` }] };
    ctx.response.status = 404; // NOT FOUND (if you know the resource was deleted, then return 410 GONE)
  }
});

const createTask = async (ctx) => {
  const task = ctx.request.body;
  if (!task.text) { // validation
    ctx.response.body = { issue: [{ error: 'Text is missing' }] };
    ctx.response.status = 400; //  BAD REQUEST
    return;
  }
  task.id = `${parseInt(lastId) + 1}`;
  lastId = task.id;

  tasks.push(task);
  ctx.response.body = task;
  ctx.response.status = 201; // CREATED
  broadcast({ event: 'created', payload: { task } });
};

router.post('/task', async (ctx) => {
  await createTask(ctx);
});

router.put('/task/:id', async (ctx) => {
  const id = ctx.params.id;
  const task = ctx.request.body;
  task.date = new Date();
  const taskId = task.id;
  if (taskId && id !== task.id) {
    ctx.response.body = { issue: [{ error: `Param id and body id should be the same` }] };
    ctx.response.status = 400; // BAD REQUEST
    return;
  }
  if (!taskId) {
    await createTask(ctx);
    return;
  }
  const index = tasks.findIndex(task => task.id === id);
  if (index === -1) {
    ctx.response.body = { issue: [{ error: `task with id ${id} not found` }] };
    ctx.response.status = 400; // BAD REQUEST
    return;
  }
  const taskVersion = parseInt(ctx.request.get('ETag')) || task.version;
  if (taskVersion < tasks[index].version) {
    ctx.response.body = { issue: [{ error: `Version conflict` }] };
    ctx.response.status = 409; // CONFLICT
    return;
  }
  task.version++;
  tasks[index] = task;
  lastUpdated = new Date();
  ctx.response.body = task;
  ctx.response.status = 200; // OK
  broadcast({ event: 'updated', payload: { task } });
});

router.del('/task/:id', ctx => {
  const id = ctx.params.id;
  const index = tasks.findIndex(task => id === task.id);
  if (index !== -1) {
    const task = tasks[index];
    tasks.splice(index, 1);
    lastUpdated = new Date();
    broadcast({ event: 'deleted', payload: { task } });
  }
  ctx.response.status = 204; // no content
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
