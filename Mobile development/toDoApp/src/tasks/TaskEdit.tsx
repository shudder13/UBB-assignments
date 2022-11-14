import { IonButton, IonButtons, IonContent, IonHeader, IonInput, IonLoading, IonPage, IonTitle, IonToolbar } from "@ionic/react";
import { useCallback, useContext, useEffect, useState } from "react";
import { RouteComponentProps } from "react-router";
import { getLogger } from "../core";
import { TaskProps } from "./TaskProps";
import { TaskContext } from "./TaskProvider";

const log = getLogger('TaskEdit');

interface TaskEditProps extends RouteComponentProps<{
    id?: string;
}> {}

const TaskEdit: React.FC<TaskEditProps> = ({ history, match }) => {
    const { tasks, saving, savingError, saveTask } = useContext(TaskContext);
    const [text, setText] = useState('');
    const [date, setDate] = useState<Date>(new Date('2022-11-02'));
    const [active, setActive] = useState<Boolean>(true);
    const [task, setTask] = useState<TaskProps>();
    useEffect(() => {
        log('useEffect');
        const routeId = match.params.id || '';
        const task = tasks?.find(task => task._id === routeId);
        setTask(task);
        if (task) {
            setText(task.text);
            setDate(task.date);
            setActive(task.active);
        }
    }, [match.params.id, tasks]);
    const handleSave = useCallback(() => {
        const editedTask = task ? { ...task, text, date, active } : { text, date, active };
        saveTask && saveTask(editedTask).then(() => history.goBack());
    }, [task, saveTask, text, date, active, history]);
    log('render');
    return (
        <IonPage>
          <IonHeader>
            <IonToolbar>
              <IonTitle>Edit</IonTitle>
              <IonButtons slot="end">
                <IonButton onClick={handleSave}>
                  Save
                </IonButton>
              </IonButtons>
            </IonToolbar>
          </IonHeader>
          <IonContent>
            <IonInput value={text} onIonChange={e => setText(e.detail.value || '')} />
            <IonInput value={date.toString()} onIonInput={(e: any) => setDate(e.target.value)} />
            <IonInput value={active.toString()} onIonInput={(e: any) => setActive(e.target.value)} />
            
            <IonLoading isOpen={saving} />
            {savingError && (
              <div>{savingError.message || 'Failed to save task'}</div>
            )}
          </IonContent>
        </IonPage>
      );
};

export default TaskEdit;
