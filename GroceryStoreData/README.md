The GroceryStoreData project folder is one of three required projects
in the grocery store traffic simulator system.

Purpose:
 - Generate simulated shopping traffic data for a given calendar month for an assumed grocery store.
 The distribution of shoppers on a particular weekday or weekend was inspired by
 the observed distribution pattern of large-scale centers like Target and Costco.

Accepted Modifications:
 - May change the requested month inside the PilotSim class, which requires
 concurrent modification of the number of days in the month.
 - May change the lunch and/or dinner peak hour time.
 - May change the senior discount day.
 - May change the senior discount start and end time.
 
 Input:
  - Any modifications as stated above. Default parameters are already specified.
  - Run the PilotSim() main method to create output for the month.
  
  Output:
  - A products.csv file which contains a table-view of the data.
  - Data exported to a NoSQL database (MongoDB) that is accessible from a server.
  - Server may be launched from the GrocerySpringBackend Project.
 
