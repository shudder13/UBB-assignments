import dataStore from 'nedb-promise';

export class TaskStore {
  constructor({ filename, autoload }) {
    this.store = dataStore({ filename, autoload });
  }
  
  async find(props) {
    return this.store.find(props);
  }
  
  async findOne(props) {
    return this.store.findOne(props);
  }
  
  async insert(task) {
    let taskText = task.text;
    if (!taskText) { // validation
      throw new Error('Missing text property')
    }
    if (!task.date)
      task.date = Date.now();
    if (task.active === undefined)
      task.active = true;
    return this.store.insert(task);
  };
  
  async update(props, task) {
    return this.store.update(props, task);
  }
  
  async remove(props) {
    return this.store.remove(props);
  }
}

export default new TaskStore({ filename: './db/tasks.json', autoload: true });
