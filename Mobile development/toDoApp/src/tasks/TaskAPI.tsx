import axios from 'axios';
import { getLogger } from "../core";
import { TaskProps } from "./TaskProps";

const log = getLogger('TaskAPI');
const baseURL = 'localhost:3000';
const taskURL = `http://${baseURL}/task`;

interface ResponseProps<T> {
    data: T;
}

function withLogs<T>(promise: Promise<ResponseProps<T>>, fnName: string): Promise<T> {
    log(`${fnName} started`);
    return promise
        .then(res => {
            log(`${fnName} succeeded`);
            return Promise.resolve(res.data);
        })
        .catch(err => {
            log(`${fnName} failed`);
            return Promise.reject(err);
        });
}

const config = {
    headers: {
        'Content-Type': 'application/json'
    }
};

export const getTasks: () => Promise<TaskProps[]> = () => {
    return withLogs(axios.get(taskURL, config), 'getTasks');
}

export const createTask: (task: TaskProps) => Promise<TaskProps[]> = task => {
    return withLogs(axios.post(taskURL, task, config), 'createTask');
}

export const updateTask: (task: TaskProps) => Promise<TaskProps[]> = task => {
    return withLogs(axios.put(`${taskURL}/${task.id}`, task, config), 'updateTask');
}

interface MessageData {
    event: string;
    payload: {
        task: TaskProps;
    };
}

export const newWebSocket = (onMessage: (data: MessageData) => void) => {
    const ws = new WebSocket(`ws://${baseURL}`)
    ws.onopen = () => {
        log('web socket onopen');
    };
    ws.onclose = () => {
        log('web socket onclose');
    };
    ws.onerror = error => {
        log('web socket onerror', error);
    };
    ws.onmessage = messageEvent => {
        log('web socket onmessage');
        onMessage(JSON.parse(messageEvent.data));
    }
    return () => {
        ws.close();
    }
}
