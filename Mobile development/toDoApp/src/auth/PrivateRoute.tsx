import { getLogger } from "../core";
import { useContext } from "react";
import { Redirect, Route, RouteComponentProps } from "react-router";
import { AuthContext, AuthState } from "./AuthProvider";

const log = getLogger('Login');

export interface PrivateRouteProps {
    component: React.FC<RouteComponentProps>;
    path: string;
    exact?: boolean;
}

export const PrivateRoute: React.FC<PrivateRouteProps> = ({ component: Component, ...rest}) => {
    const { isAuthenticated } = useContext<AuthState>(AuthContext);
    log('render, isAuthenticated', isAuthenticated);
    return (
        <Route {...rest} render={props => {
            if (isAuthenticated) {
                // @ts-ignore
                return <Component {...props} />;
            }
            return <Redirect to={{ pathname: '/login' }} />
        }}/>
    );
}
