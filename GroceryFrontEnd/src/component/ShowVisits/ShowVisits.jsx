import React from "react";
import VisitService from "../../service/VisitService";
import "./ShowVisits.css";
import Plot from 'react-plotly.js';
import {InputGroup, FormControl, DropdownButton, Dropdown} from "react-bootstrap";

class ShowVisits extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            service: VisitService.getInstance(),
            visits:[],
            isLoading:true,
            sortType:"Unsorted",
            minDuration:null,
            maxDuration:null,
            startDate:null,
            endDate:null,
            Xs:[],
            Ys:[],
            page:0,
            capacity:5
        };
    }

    fetchAllVisits = async () => {
        await this.state.service.findAllVisits().then(results => {this.setState({visits:results, isLoading:false})});
    };

    sortByEntryTime = () => {
        this.setState({sortType:"SortByEntryTime", visit:this.state.visits.sort(function (a, b) {
                return new Date(a.entryTime) - new Date(b.entryTime);
            })});
    };

    updateForm = async (event) => {
        await this.setState({
            [event.target.name]: event.target.value
        });
    };

    updateCapacity = async (event) => {
        await this.setState({
            [event.target.name]: event.target.value
        });
        await this.setState({page : 0});
    };


    nextPage = () => {
        console.log("jump to the next page!");
        if(this.state.page < Math.floor(this.state.visits.length / this.state.capacity)) {
            this.setState({page:this.state.page+1});
        }
    };

    prevPage = () => {
        console.log("jump to the previous page!");
        if(this.state.page > 0) {
            this.setState({page:this.state.page-1});
        }
    };

    applyFilter = async () => {

        await this.fetchAllVisits();
        let results = [];
        let startDate = new Date(this.state.startDate+"T00:00:00");
        let endDate = new Date(this.state.endDate+"T23:59:59");

        for(let i=0; i < this.state.visits.length; i++) {
            let visit = this.state.visits[i];
            if(this.state.minDuration !== null && visit.duration < this.state.minDuration) {
                continue;
            }
            if(this.state.maxDuration !== null && visit.duration > this.state.maxDuration) {
                continue;
            }
            if(this.state.startDate !== null && new Date(visit.entryTime) < startDate) {
                continue;
            }
            if(this.state.startDate !== null && new Date(visit.entryTime) > endDate) {
                continue;
            }

            results.push(visit);

        }
        await this.setState({visits:results, page:0});
        this.countVisits();
    };

    countVisits = () => {
        let Xs = [];
        let Ys = [];
        let map = [];
        for(let i=0; i < 121; i++) {
            map.push(0);
        }
        for(let i=0; i < this.state.visits.length; i++) {
            let visit = this.state.visits[i];
            let duration = parseInt(visit.duration);
            if(duration < 0 || duration > 120) {
                continue;
            }
            map[duration] += 1;
        }
        // console.log(map);
        for(let i=0; i < map.length; i++) {
            Xs.push(i);
            Ys.push(map[i]);
        }
        this.setState({Xs:Xs, Ys:Ys});
    };

    componentDidMount = () => {
        this.fetchAllVisits().then(this.countVisits);
    };

    renderVisits() {
        return this.state.visits.slice(this.state.capacity*this.state.page, this.state.capacity*(this.state.page+1)).map((visit) => {
            return (
                <tr key={visit.visitID}>
                    <td>{visit.visitID}</td>
                    <td>{visit.entryTime.split(":00.000Z")[0].replace("T", " ")}</td>
                    <td>{visit.leaveTime.split(":00.000Z")[0].replace("T", " ")}</td>
                    <td>{visit.duration}</td>
                    <td>{visit.holiday}</td>
                    <td>{visit.dayOfWeek}</td>
                </tr>
            )
        })
    }

    renderHeader() {
        // console.log(this.state.visits);
        let header = Object.keys(this.state.visits[0]).slice(1,7);
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    render() {
        if(!this.state.isLoading) {
            return (
                <div>
                    <h1 id="title">All Visits</h1>
                    <div id="filter">
                        <InputGroup>
                            <DropdownButton
                                as={InputGroup.Prepend}
                                variant="outline-secondary"
                                title={this.state.sortType}
                                id="input-group-dropdown-1"
                            >
                                <Dropdown.Item onClick={this.sortByEntryTime}>SortByEntryTime</Dropdown.Item>
                            </DropdownButton>
                            <FormControl name="minDuration" onChange={this.updateForm} value={this.state.minDuration} placeholder="min duration"/>
                            <FormControl name="maxDuration" onChange={this.updateForm} value={this.state.maxDuration} placeholder="max duration"/>
                            <FormControl name="startDate" type="date" onChange={this.updateForm} value={this.state.startDate} placeholder="start date"/>
                            <FormControl name="endDate" type="date" onChange={this.updateForm} value={this.state.endDate} placeholder="end date"/>
                            <button
                                type="button"
                                className="btn btn-outline-dark"
                                onClick={() => this.applyFilter()}>
                                Apply
                            </button>
                        </InputGroup>
                    </div>
                    {this.state.visits.length > 0 && <table id="visits">
                        <tbody>
                        <tr>{this.renderHeader()}</tr>
                        {this.renderVisits()}
                        </tbody>
                    </table>}
                    <div className="row">
                        <div className="col-sm">
                            <div
                                style={{
                                    padding: "2.25em",
                                    float: "left",
                                    justifyContent: "center",
                                    verticalAlign: "middle",
                                    alignItems: "center",
                                    textAlign: "center",
                                    display: "flex",
                                    flex: 1,
                                    marginBottom: "1em"}}
                            >
                                <div className="input-group mb-3">
                                    <div className="input-group-prepend">
                                        <label className="input-group-text" htmlFor="inputGroupSelect01">Entry Number: </label>
                                    </div>
                                    <select name="capacity"
                                            className="custom-select"
                                            id="inputGroupSelect01"
                                            onChange={this.updateCapacity.bind(this)}
                                            value={this.state.capacity}>
                                        <option value="5">5</option>
                                        <option value="10">10</option>
                                        <option value="15">15</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div className="col-sm">
                            <div
                                style={{
                                    float: "right",
                                    justifyContent: "center",
                                    verticalAlign: "middle",
                                    alignItems: "center",
                                    textAlign: "center",
                                    display: "flex",
                                    flex:1,
                                    marginBottom: "1em"}}
                            >
                                {(this.state.page > 0) &&
                                <button
                                    type="button"
                                    style={{ marginTop: "2em", color: "#4CAF50"}}
                                    className="btn btn-link"
                                    onClick={this.prevPage}
                                >
                                    <strong>prev</strong>
                                </button>}
                                <button type="button" style={{ marginTop: "2em" }} disabled>
                                    {"Page " + (this.state.page + 1).toString()}
                                </button>
                                {(this.state.page < Math.floor(this.state.visits.length / this.state.capacity)) &&
                                <button
                                    type="button"
                                    style={{ marginTop: "2em", color: "#4CAF50"}}
                                    className="btn btn-link"
                                    onClick={this.nextPage}
                                >
                                    <strong>next</strong>
                                </button>}
                            </div>
                        </div>
                    </div>
                    <div id="chart">
                        <Plot
                            data={[
                                {
                                    x: this.state.Xs,
                                    y: this.state.Ys,
                                    type: 'scatter',
                                    // mode: 'lines+markers',
                                    marker: {color: 'green'},
                                }
                            ]}
                            layout={{ width: 720, height: 480, title: 'Duration Distribution'}}
                        />
                    </div>
                </div>
            )
        } else {
            return <div>Loading...</div>
        }
    }
}

export default ShowVisits;
