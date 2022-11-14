import { IonItem, IonLabel } from "@ionic/react";
import React, { useCallback } from "react";
import { TaskProps } from "./TaskProps";

interface TaskPropsExt extends TaskProps {
    onEdit: (_id?: string) => void;
}

const Task: React.FC<TaskPropsExt> = ({_id, text, date, active, onEdit }) => {
    const handleEdit = useCallback(() => onEdit(_id), [_id, onEdit]);
    return (
        <IonItem onClick={handleEdit}>
            <IonLabel>Task: {text}, Date: {Date().toString()}, active: {String(active)}</IonLabel>
        </IonItem>
    );
};

export default Task;
