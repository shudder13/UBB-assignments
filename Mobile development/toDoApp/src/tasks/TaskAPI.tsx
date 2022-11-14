import axios from 'axios';
import { authConfig, baseURL, getLogger, withLogs } from '../core';
import { TaskProps } from "./TaskProps";

const taskURL = `http://${baseURL}/api/task`;

export const getTasks: (token: string) => Promise<TaskProps[]> = token => {
    return withLogs(axios.get(taskURL, authConfig(token)), 'getTasks');
}

export const createTask: (token: string, task: TaskProps) => Promise<TaskProps[]> = (token, task) => {
    return withLogs(axios.post(taskURL, task, authConfig(token)), 'createTask');
}

export const updateTask: (token: string, task: TaskProps) => Promise<TaskProps[]> = (token, task) => {
    return withLogs(axios.put(`${taskURL}/${task._id}`, task, authConfig(token)), 'updateTask');
}

interface MessageData {
    type: string;
    payload: TaskProps;
}

const log = getLogger('ws');

export const newWebSocket = (token: string, onMessage: (data: MessageData) => void) => {
    const ws = new WebSocket(`ws://${baseURL}`);
    ws.onopen = () => {
        log('web socket onopen');
        ws.send(JSON.stringify({ type: 'authorization', payload: { token }}));
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
