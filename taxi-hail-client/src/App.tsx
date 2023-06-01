import React from 'react';
import { AppRouter } from './components/router/AppRouter';
import {LocationUpdate} from "./components/driver/LocationUpdate";

const App: React.FC = () => {

    return (
        <>
            <LocationUpdate />
            <AppRouter />
        </>
    );
};

export default App;