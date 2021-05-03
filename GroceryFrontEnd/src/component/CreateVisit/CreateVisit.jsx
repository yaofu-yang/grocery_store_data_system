import React from "react";
import Form from 'react-bootstrap/Form';
import VisitService from "../../service/VisitService";
import "./CreateVisit.css";

class CreateVisit extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            service:VisitService.getInstance(),
            visitID:null,
            entryDate:null,
            entryTime:null,
            leaveTime:null,
            holiday:null,
            dayOfWeek:null,
            reload:false
        };
    }

    updateForm = event => {
        // console.log(event.target.value);
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    // calculate minute difference
    minDiff = (a, b) => {
        let hourD = b.split(":")[0] - a.split(":")[0];
        let minD = b.split(":")[1] - a.split(":")[1];
        return hourD*60 + minD;
    };

    createVisit = (state) => {
        for (let key in this.state) {
            if (this.state[key] === null){
                alert("information is incomplete!");
                this.setState({reload:true});
                return;
            }
        }
        this.state.service
            .createVisit({
                visitID:this.state.visitID,
                entryTime:this.state.entryDate + "T" + this.state.entryTime + "Z",
                leaveTime:this.state.entryDate + "T" + this.state.leaveTime + "Z",
                duration:this.minDiff(this.state.entryTime, this.state.leaveTime),
                holiday:this.state.holiday,
                dayOfWeek:this.state.dayOfWeek
            })
            .then(visit => {
                console.log(visit);
                this.props.history.push("/show");
            });
    };

    render() {
        if (this.state.reload === true) {
            window.location.reload();
        }
        return (
            <div className="container">
                <div className="block">
                    <label htmlFor="visitID">Visit ID:</label>
                    <Form.Control
                        type="text"
                        name="visitID"
                        value={this.state.visitID}
                        onChange={this.updateForm}
                    />
                </div>

                <div className="block">
                    <label htmlFor="entryDate">Visit Date:</label>
                    <Form.Control
                        type="date"
                        name="entryDate"
                        value={this.state.entryDate}
                        onChange={this.updateForm}
                    />
                </div>

                <div className="block">
                    <label htmlFor="entryTime">Entry Time:</label>
                    <Form.Control
                        type="time"
                        name="entryTime"
                        value={this.state.entryTime}
                        onChange={this.updateForm}
                    />
                </div>

                <div className="block">
                    <label htmlFor="leaveTime">Leave Time:</label>
                    <Form.Control
                        type="time"
                        name="leaveTime"
                        value={this.state.leaveTime}
                        onChange={this.updateForm}
                    />
                </div>

                {/*<div className="block">*/}
                {/*    <label htmlFor="holiday">holiday</label>*/}
                {/*    <Form.Control*/}
                {/*        type="text"*/}
                {/*        name="holiday"*/}
                {/*        value={this.state.holiday}*/}
                {/*        onChange={this.updateForm}*/}
                {/*    />*/}
                {/*</div>*/}

                {/*<div className="block">*/}
                {/*    <label htmlFor="dayOfWeek">dayOfWeek</label>*/}
                {/*    <Form.Control*/}
                {/*        type="text"*/}
                {/*        name="dayOfWeek"*/}
                {/*        value={this.state.dayOfWeek}*/}
                {/*        onChange={this.updateForm}*/}
                {/*    />*/}
                {/*</div>*/}

                <div className="block-button">
                    <button className="btn btn-lg btn-primary btn-block" onClick={() => this.createVisit(this.state)}>submit</button>
                </div>

            </div>
        );
    }
}

export default CreateVisit;
