-Running the Project-
 - set correct sdk if using intellij, program uses switch expression
 - 'source activate python38' in terminal
 - set working directory to 440A2 

-src folder details-
5A
	This process is done using ManagerA as the backend running with python interface Implementation.
	Details are as follows : Each Action and Sensor reading are inserted into their own queues, labeled Action and Sensor queues.
	The State filtering estimate is stored in a 2d array held by the StateEstimate class wrapper.
	Each StateEstimate is maintained and persisted continuously while the program runs,
	and they are held in list order by the List data structure of the SEStorage class.
	This class creates the filtering estimate by taking the end of the list state estimate to create the following one
	in the sequence following the given rules. See functions GENERATESTATE() and ACTIONCALC() within SEStorage
	to review probability assignment for states. ManagerA generally creates the answers.
	Grids are generated in order with just their probabilities.

5B
	This process is handled by the ManagerB, creating a ResultB.txt file that holds a record of a grid’s row and columns,
	as well as the type of cell, and then has 10 unique paths with their truth states, actions,
	and sensor readings according to the assignment rules. One example file is included for reference.
	The ManagerB can be run to generate a new grid as well as 10 unique paths each time it is called.

5C
	The ManagerC creates two separate files. ResultC.txt and Result2C.txt are included as examples.
	ResultC.txt is in the same format as ResultA.txt but at scale of 100x50.
	These grids are separated by the Grid keyword and represent all 100 states after every ACTION and SENSOR pair are executed.
	 The Result2C.txt has one line representing the distance between maximum probability point and the true point,
	 while the line below has the probability value of the true point. Refer to demonstration videos for action of the program.

-running displays-
The video demo folder shows the program running in the ilabs

Before running displays, run the respective Manager in the src folder. Then in the terminal
run the respective display e.g. "python 5Adisplay.py" for managerA.
For 5B there are 0-9 paths indexed into the result. 
To run the display for 5B "python 5Bdisplay.py 0"
5A/5C there is a sleep function to decrease or increase the amount of seconds between each
state displayed on the gui. The percentile increases from green to red.
