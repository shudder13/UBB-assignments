import { IonItem, IonLabel } from "@ionic/react";
import React, { useCallback } from "react";
import { TaskProps } from "./TaskProps";

interface TaskPropsExt extends TaskProps {
    onEdit: (id?: string) => void;
}

const Task: React.FC<TaskPropsExt> = ({id, text, date, active, onEdit }) => {
    const handleEdit = useCallback(() => onEdit(id), [id, onEdit]);
    return (
        <IonItem onClick={handleEdit}>
            <IonLabel>Task: {text}, Date: {date.toString()}, active: {String(active)}</IonLabel>
        </IonItem>
    );
};

export default Task;
