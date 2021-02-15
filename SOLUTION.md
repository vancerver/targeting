 THE SOLUTION
==================

To solve this problem I realized I need to figure out how to make Spring Boot act as a 
console application which is something I have not done before but I quickly learned
I could just overwrite Spring Boot's start process to instead make it act as a console
application using a second custom service class that I created.

I started with some unit tests for how I want the methods in this class to behave. 

I then implemented the process of reading from the campaigns file and creating Campaign 
objects from the data in that file. 

I then implemented reading input from the user. I had not yet developed an app that needs to continuously ask the user for new
input without any end so I just did this with an infinite loop. It felt like there could
be a better solution but I could not quickly find one so instead made a note to come back to 
this if I still had time.

I first did a non optimized approach to this solution where for each user entry, it would
go through each campaign and calculate a relevance score for that campaign. At then end,
the program would return the campaign with the highest relevance score. If there were
multiple campaigns with the highest score,  I choose one of them randomly.

I knew this algorithm could be faster though if I use the campaign data to create a map 
where the segments are the key and a collection of all the campaigns that have that segment
in them would be the value. I would then be able to quickly get a list of all the 
campaigns linked to all the user's segments and would only need to next determine 
which of the campaigns turned up the most often. 

Unfortunately I was ultimately not able to work out a working implementation of this 
quicker algorithm using the campaign lookup map before my time to complete this 
assignment was over.

