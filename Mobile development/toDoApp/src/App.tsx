import { Redirect, Route } from 'react-router-dom';
import { IonApp, IonRouterOutlet, setupIonicReact } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/* Theme variables */
import './theme/variables.css';
import { TaskList, TaskEdit } from './tasks';
import { TaskProvider } from './tasks/TaskProvider';

setupIonicReact();

const App: React.FC = () => (
  <IonApp>
    <TaskProvider>
      <IonReactRouter>
        <IonRouterOutlet>
          <Route path="/tasks" component={TaskList} exact={true} />
          <Route path="/task" component={TaskEdit} exact={true}/>
          <Route path="/task/:id" component={TaskEdit} exact={true}/>
          <Route exact path="/" render={() => <Redirect to="/tasks" />} />
        </IonRouterOutlet>
      </IonReactRouter>
    </TaskProvider>
  </IonApp>
);

export default App;
