import Router from 'koa-router';
import taskStore from './store';
import { broadcast } from "../utils";

export const router = new Router();

router.get('/', async (ctx) => { // response: all tasks of user
  const response = ctx.response;
  const userId = ctx.state.user._id;
  response.body = await taskStore.find({ userId: userId });
  response.status = 200; // ok
});

router.get('/:id', async (ctx) => { // response: specific task of user (by ID)
  const userId = ctx.state.user._id;
  const task = await taskStore.findOne({ _id: ctx.params.id });
  const response = ctx.response;
  if (task) {
    if (task.userId === userId) {
      response.body = task;
      response.status = 200; // ok
    } else {
      response.status = 403; // forbidden
    }
  } else {
    response.status = 404; // not found
  }
});

const createTask = async (ctx, task, response) => {  // get task text, store task, respond with it and broadcast
  try {
    const userId = ctx.state.user._id;
    task.userId = userId;
    response.body = await taskStore.insert(task);
    response.status = 201; // created
    broadcast(userId, { type: 'created', payload: task });
  } catch (err) {
    response.body = { message: err.message };
    response.status = 400; // bad request
  }
};

router.post('/', async ctx => await createTask(ctx, ctx.request.body, ctx.response));

router.put('/:id', async (ctx) => {
  const task = ctx.request.body;
  const paramId = ctx.params.id;
  const taskId = task._id;
  const response = ctx.response;
  if (taskId && taskId !== paramId) {
    response.body = { message: 'Param id and body _id should be the same' };
    response.status = 400; // bad request
    return;
  }
  if (!taskId) {
    await createTask(ctx, task, response);
  } else {
    const updatedCount = await taskStore.update({ _id: paramId }, task);
    if (updatedCount === 1) {
      response.body = task;
      response.status = 200; // ok
      const userId = ctx.state.user._id;
      broadcast(userId, { type: 'updated', payload: task });
    } else {
      response.body = { message: 'Resource no longer exists' };
      response.status = 405; // method not allowed
    }
  }
});

router.del('/:id', async (ctx) => {
  const userId = ctx.state.user._id;
  const task = await taskStore.findOne({ _id: ctx.params.id });
  if (task && userId !== task.userId) {
    ctx.response.status = 403; // forbidden
  } else {
    await taskStore.remove({ _id: ctx.params.id });
    ctx.response.status = 204; // no content
  }
});
