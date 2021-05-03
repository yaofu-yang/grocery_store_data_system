The GrocerySpringBackend project folder is one of the three required
project in the grocery store traffic simulator system/

Purpose: Run a web server application to accept queries to access
data inside a dataset inside a specific MongoDB port.

Input:
 - Run the GroceryVisitsApplication main method to initialize the server.
 - Use the following URI's via Postman, curl, or online search engine to make queries.

1. Accessing the data in the visits collection:
    a. all visits: localhost:8080/visits/all/unordered
    b. all visits, ordered by entry time: localhost:8080/visits/all/ordered/entry
    c. a single visit by visit ID: localhost:8080/visits/single/{visitID}
    d. visits for specified event and entry time periods: localhost:8080/visits/partial/prefix/{idPrefix}in{start}to{end}
        prefix: N: normal daily visit entries
        H: holiday related visit entries
        W: weather related visit entries
        M: meal (lunch / dinner) rush time related visit entries
        S: senior discount event related visit entries
        entry time in date/time interval: formatting example as “2020-07-08T06:00”
    e. visits with an entry time in the interval: localhost:8080/visits/partial/entry/interval/{start}to{end}
    f. visits with a leave time in the interval: localhost:8080/visits/partial/leave/interval/{start}to{end}
    g. visits with a duration in the interval: localhost:8080/visits/partial/duration/interval/{min}to{max}
    h. visits with a duration greater than equal to the min: localhost:8080/visits/partial/duration/LTE/{max}
    i. visits with a duration less than equal to the max: localhost:8080/visits/partial/duration/GTE/{min}
    
2. Insert visit to data:
    localhost:8080/visits/add/single?visitID={param}&entryTime={param}&leaveTime={para
    m}&duration={param}&holiday={param}&dayOfWeek={param}
    
Output:
 - The server itself will not product any output.
 - Successful queries will return the subset of data requested.