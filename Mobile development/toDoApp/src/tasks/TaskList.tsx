import { IonContent, IonFab, IonFabButton, IonHeader, IonIcon, IonList, IonLoading, IonPage, IonTitle, IonToolbar } from "@ionic/react";
import React, { useContext } from "react";
import { getLogger } from "../core";
import Task from "./Task";
import { add } from 'ionicons/icons';
import { RouteComponentProps } from "react-router";
import { TaskContext } from "./TaskProvider";

const log = getLogger('TaskList');

const TaskList: React.FC<RouteComponentProps> = ({ history }) => {
    const { tasks, fetching, fetchingError} = useContext(TaskContext);
    log('render');
    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>To Do App</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent>
                <IonLoading isOpen={fetching} message="Fetching tasks" />
                {tasks && (
                    <IonList>
                        {tasks.map(({ _id, text, date, active }) =>
                        <Task key={_id} _id={_id} text={text} date={date} active={active} onEdit={id => history.push(`/task/${id}`)} />)}
                    </IonList>
                )}
                {fetchingError && (
                    <div>{fetchingError.message || 'Failed to fetch tasks'}</div>
                )}
                
                <IonFab vertical="bottom" horizontal="end" slot="fixed">
                    <IonFabButton onClick={() => history.push('/task')}>
                        <IonIcon icon={add} />
                    </IonFabButton>
                </IonFab>
            </IonContent>
        </IonPage>
    );
};

export default TaskList;
