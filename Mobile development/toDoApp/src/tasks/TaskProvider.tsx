import React, { useCallback, useContext, useEffect, useReducer } from "react";
import PropTypes from 'prop-types';
import { getLogger } from "../core";
import { TaskProps } from "./TaskProps";
import { createTask, getTasks, newWebSocket, updateTask } from "./TaskAPI";
import { AuthContext } from "../auth";

const log = getLogger('TaskProvider');

type SaveTaskFn = (task: TaskProps) => Promise<any>;

export interface TasksState {
    tasks?: TaskProps[],
    fetching: boolean,
    fetchingError?: Error | null,
    saving: boolean,
    savingError?: Error | null,
    saveTask?: SaveTaskFn,
}

interface ActionProps {
    type: string,
    payload?: any,
}

const initialState: TasksState = {
    fetching: false,
    saving: false,
}

const FETCH_TASKS_STARTED = 'FETCH_TASKS_STARTED';
const FETCH_TASKS_SUCCEEDED = 'FETCH_TASKS_SUCCEEDED';
const FETCH_TASKS_FAILED = 'FETCH_TASKS_FAILED';
const SAVE_TASK_STARTED = 'SAVE_TASK_STARTED';
const SAVE_TASK_SUCCEEDED = 'SAVE_TASK_SUCCEEDED';
const SAVE_TASK_FAILED = 'SAVE_TASK_FAILED';

const reducer: (state: TasksState, action: ActionProps) => TasksState =
    (state, { type, payload }) => {
        switch(type) {
            case FETCH_TASKS_STARTED:
                return { ...state, fetching: true, fetchingError: null };
            case FETCH_TASKS_SUCCEEDED:
                return { ...state, tasks: payload.tasks, fetching: false };
            case FETCH_TASKS_FAILED:
                return { ...state, fetchingError: payload.error, fetching: false };
            case SAVE_TASK_STARTED:
                return { ...state, savingError: null, saving: true };
            case SAVE_TASK_SUCCEEDED:
                const tasks = [...(state.tasks || [])];
                const task = payload.task;
                const index = tasks.findIndex(tsk => tsk._id === task.id);
                if (index === -1)
                    tasks.splice(0, 0, task);
                else
                    tasks[index] = task;
                return { ...state, tasks, saving: false };
            case SAVE_TASK_FAILED:
                return { ...state, savingError: payload.error, saving: false };
            default:
                return state;
        }
    };

export const TaskContext = React.createContext<TasksState>(initialState);

interface TaskProviderProps {
    children: PropTypes.ReactNodeLike
}

export const TaskProvider: React.FC<TaskProviderProps> = ({ children }) => {
    const { token } = useContext(AuthContext);
    const [state, dispatch] = useReducer(reducer, initialState);
    const { tasks, fetching, fetchingError, saving, savingError } = state;
    useEffect(getTasksEffect, [token]);
    useEffect(wsEffect, [token]);
    const saveTask = useCallback<SaveTaskFn>(saveTaskCallback, [token]);
    const value = { tasks, fetching, fetchingError, saving, savingError, saveTask };
    log('returns');
    return (
        <TaskContext.Provider value={value}>
            {children}
        </TaskContext.Provider>
    );

    function getTasksEffect() {
        let cancelled = false;
        fetchTasks();
        return () => {
            cancelled = true;
        }

        async function fetchTasks() {
            if (!token?.trim()) {
                return;
            }
            try {
                log('fetchTasks started');
                dispatch( {type: FETCH_TASKS_STARTED});
                const tasks = await getTasks(token);
                log('fetchTasks succeded');
                if (!cancelled) {
                    dispatch({ type: FETCH_TASKS_SUCCEEDED, payload: { tasks }});
                }
            } catch (error) {
                log('fetchTasks failed');
                dispatch({ type: FETCH_TASKS_FAILED, payload: { error } });
            }
        }
    }

    async function saveTaskCallback(task: TaskProps) {
        try {
            log('saveTask started');
            dispatch({ type: SAVE_TASK_STARTED });
            const savedTask = await (task._id ? updateTask(token, task) : createTask(token, task));
            log('saveTask succeeded');
            dispatch({ type: SAVE_TASK_SUCCEEDED, payload: { task: savedTask }});
        } catch (error) {
            log('saveTask failed');
            dispatch({ type: SAVE_TASK_FAILED, payload: { error }});
        }
    }

    function wsEffect() {
        let cancelled = false;
        log('wsEffect connecting');
        let closeWebSocket: () => void;
        if (token?.trim()) {
            closeWebSocket = newWebSocket(token, message => {
                if (cancelled) {
                  return;
                }
                const { type, payload: item } = message;
                log(`ws message, task ${type}`);
                if (type === 'created' || type === 'updated') {
                  dispatch({ type: SAVE_TASK_SUCCEEDED, payload: { item } });
                }
              });
        }
        return () => {
            log('wsEffect disconnecting');
            cancelled = true;
            closeWebSocket?.();
        }
    }
};
