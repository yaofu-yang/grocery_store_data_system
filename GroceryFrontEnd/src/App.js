import React from "react";
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import CreateVisit from "./component/CreateVisit/CreateVisit";
import ShowVisits from "./component/ShowVisits/ShowVisits";

class App extends React.Component{
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <Router>
                <div className="App">
                    <Switch>
                        <Route exact path="/" component={ShowVisits}/>
                        <Route path="/new" component={CreateVisit}/>
                        <Route path="/show" component={ShowVisits}/>
                    </Switch>
                </div>
            </Router>
        )
    }
}

export default App;
