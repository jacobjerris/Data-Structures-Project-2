# Data-Structures-Project-2
In this project we took parts of our first project but incorporated two new classes. 
<h3>Stack Class</h3>
<p>In this class I created a constructor and created different methods that allowed us to push items onto a stack, as well as pop them off if needed. We also incorporated methods to check if the stack was full, or if it was empty. Which came in handy when pushing objects from the Queue onto the final stack.</p>
<h3>Queue Class</h3>
<p>In this class I created a constructor as well as different methods that allowed us to insert items into a certain queue which was determined by set values in the project. From there a counter was used to determine the length of the five queues that we were required to create. After they are created I looped through the Country array which holds the results of the parsed CSV file provided and insert each object into its respective queue.</p>
<h3>Country Class</h3>
<p>This class was the base class that was started in the first project. This class takes in the parsed info from the CSV file and creates Country objects using the information given. This allows the program to continue with its creation of the queues because they rely on the information from the Country class.</p>
