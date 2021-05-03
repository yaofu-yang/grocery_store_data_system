The GroceryFrontEnd project folder is one of the three projects required in the
grocery traffic data simulator system.

Purpose: This project launches two user interfaces with their respective functions:
    1. A /new user interface that allows the user to add a new visit into the database.
    2. A /show user interface that allows the user to navigate a calendar to run queries on existing
    data inside the specified data ranges and additional parameters. The output data is represented
    by both a diagram and a table with flippable pages.
   
Building and running the Project:
 - Import all necessary changes into the text editor.
 - Run 'npm install --save' inside the project folder. 
 - Run 'npm start' to initialize the landing page for the user interface. This will be blank.
 - Add '/new' to the landing page http extension to navigate to the UI that adds a new visit.
 - Add '/show' to the landing page http extension to navigate to the UI that displays query data.
 - In the event of an out-of-memory error, run 'export NODE_OPTIONS=--max_old_space_size=4096'
 on the command line/terminal and then run 'npm start' once more.
 
 Input:
  - None. The input queries will be handled on the UI pages.
  
 Output:
  - Launches a new browser tab to an empty landing page.
  - Both UI pages will display output differentially, depending on user input.