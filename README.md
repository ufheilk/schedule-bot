# schedule-bot

## Overview of Project
The initial idea for the project was, as can be seen from the questions, a text-based service which could inform people about the wait times for the various on-campus dining locations so that they would be better able to chose the dining location which fits into their preferences / needs at the time. These wait times would be crowdsourced from the userbase of the serivce, so that the times would be current and accurate. Long wait times are a major issue that many students have with the current dining system so it seemed that this might be a useful tool for a large group of people.

The students who were interviewed (only students were interviewed) seemed in general very receptive to the idea of this service. Wait times were an important factor in the decision making process for all those interviewed, especially during the lunch period as time constraints are greater. There is some variation in exactly what is desired from the application: e.g. the third student interviewed would want a much smaller margin of error for expected and actual wait times. In order to reach as large an audience as possible, the service would have to meet the higher standards such as those of the third student interviewed. Additionally, the first student interviewed wanted more information than the other two (the food options at the dining locations), implying that many other students may want some sort of additional information provided besides the wait times. 

The above taken into account, the fleshed-out idea for the service is as follows:
  1. A user can query the service via text to obtain current wait times for locations on campus (as reported by other students), and optionally for additional information (e.g. menu) depending on the user's preferences by adding additional keywords to the text.
  2. A user partaking in the service can self-report the time they waited at any of the on-campus dining locations by texting the service the name of the location followed by the time they waited. 
  3. The waiting time for each location will be continuously updated to reflect incoming reports from students and the fact and to decrease the impact of older reports (as wait times might fluctuate rapidly), as well as filtering out extreme outliers or multiple reports from the same user in a short period of time in order to protect the integrity of the information the service gives out.
  



# Questions
1. Do you eat on campus for lunch?  If so, where?
2. How do you usually choose your campus dining location for lunch?
3 .Do you feel that long wait times hinder your ability to make it to get lunch between classes, especially Tuesday/Thursday (where the gap between classes is shorter)?  How do you usually overcome this hurdle?
4. If you knew a dining location you do not usually frequent had a very short waiting time, would you be inclined to go there?  Why/why not?
5. Would you be interested in a service that suggested where you should eat, based on waiting times on campus?
6. Would you be willing to report to the program how long you waited for food on campus?
7. Do you think that Vanderbilt students would be interested in a service such as this?  Why/why not?
8. Do you trust that other people partaking in this service would accurately report their waiting time data?
9. What information about dining locations during the lunch rush would you want to receive, if you could choose?
10. What would be an acceptable margin of error for such a service? (ex, the texting service says wait will be 10 minutes and wait is actually 15 - is this acceptable? What is the acceptable error?)

# Answers

## Question 1: 
  1. I usually eat at Rand. I get a Randwich: salami and American cheese on a pretzel roll, with tomato, sweet peppers, carrot, and a little bit of mayo. I get yogurt, a pear, and chocolate milk as sides.
  2. Yes, usually Rand.
  3. Yes. EBI TR/Rand MWF

## Question 2:
  1. My options are limited by the ~45 minute window I have for lunch. I pretty much only ever get Rand because of the time pressure.
  2. Food variety and how long it takes
  3. Proximity to last class/next class, quality
  
## Question 3:
  1. Wait times are the bane of my lunchtime experience. I usually suck it up and wait in line, but I make myself feel better by complaining to my friends.
  2. Yes, I’ve been late to class several times. I normally go to get lunch in the last 20 minutes of the dining
period for shorter lines and eat quickly outside of my classroom
  3. Not as much because I eat fast
  
## Question 4:
  1. Absolutely. I will eat anything, and shorter wait times are a much bigger priority for me than food taste or quality.
  2. Yes, if the walk is reasonable! 
  3. Yes, if the quality is up to par and it is at a convenient location
  
## Question 5:
  1. Yes. This sounds like a great idea.
  2. Yes
  3. Yes, especially if these times were accurate
  
## Question 6: 
  1. Not every day, but for an initial trial period I would consider it.
  2. Yes
  3. Absolutely
  
## Question 7:
  1. I think they would be interested, especially because every complains so much about the incredible lines at lunchtime
  2. Yes, I think a lot of students face this problem.
  3. Yes, there are a decent number of places to eat and many times the wait time can play a significant role in the decision-making process. 
  
## Question 8: 
  1. Yes. I don't think they would have a reason to lie, and obvious trolls would probably be easy to spot.
  2. Yes
  3. There shouldn't be any reason to under-report wait times. They might be slightly over-reported, but in this case that is preferable and once acknowledged, won't matter much. 
  
## Question 9:
  1. Wait time and food options (especially for places that have a rotation or new food options each day).
  2. Just wait times would be fine!
  3. Just average time to be served since joining the line
  
## Question 10:
  1. I would be okay with a normal 5 minute discrepancy. A 10 minute discrepancy would be acceptable if it was rare. 
  2. I think within 10 minutes of the estimated time is acceptable.
  3. <5 min
  
# Requirements
The entirety of the service, and hence its utility to students and others who often get food on campus, depends on:
  1. The reactivity of the service, i.e. how quickly it responds to user queries or reports on wait times
  2. The accuracy of the wait times given to students by the service
These two items therefore make up the two most important requirements that must be considered as the process is being designed, implemented, and maintained. 

Because the service will be run through a combination of Twilio / AWS, which are generally reliable, this requirement should be fulfilled automatically provided that the implementation of the service's logic is not faulty, which will be one of the goals of the design process. 

The other major requirement is not so easily fulfilled and can be stated more accurately as two separate requirements:
  1. There is a simple protocol made known to all users for how to report times (e.g. report the time you waited in line as soon as you receive your food, not when you check out)
  2. Incoming reports from users for various dining locations are processed in a sensible way that will give incoming students a good estimate on the various wait times. 
  
The only other outstanding requirement which has not been covered is that the wait time information held by the service is delivered to users (upon request) in a form that will be useful for them in deciding on where they would like to eat. This requirement effectively consists of the user's ability to see the top locations with the shortest waiting time, with the option to remove (or re-add) certain dining locations from appearing so that they can tailor their results to their liking.

Therefore the main requirements of the service are:
  1. The service responds and processes incoming reports quickly
  2. The service communicates a uniform protocol for reporting wait times to users.
  3. The service processes incoming reports intelligently to provide as accurate of a wait time as possible to users.
  4. The service displays the locations with the shortest lines to users upon request
  5. The service allows users to customize their results by omitting certain locations (which can later be re-added)

# Development Process
The area of greatest risk for this service likely lies in some combination of acquiring a large enough userbase that new wait times can consistently be reported and whether users would accurately report or remember to report their wait times. An inquiry into this risk can be seen in Question 8. While students believe that their fellow students who use the service would report accurately, the necessity of acquiring a large userbase still exists (of course, other methods for acquiring wait times exist, such as using a camera system to estimate how many people are at a dining location; however, these methods were deemed infeasible). 

The only way to mitigate the above risk would be to poll a large audience to acquire a sizeable userbase, which will be done.

Reviewing the requirements, the second greatest risk is 4/5: an acceptable way to display wait times is very subjective, and it may be difficult to know whether the chosen way to display results will appease all users. Hence the first step of the development process will be to create mock-up texts of what the results may actually look like and show a broader audience than those interviewed (as well as including those interviewed) to determine if they would use this service if they received results like these combined with the possibility of filtering certain locations they did not like.

Based on the interviews it would seem that such a design would be acceptable, and hopefully the broader audience would accept this. Otherwise requirement 5 would have to be revised. Changes could be gathered from the audience, filtered for what would be technically feasible (e.g. the first interviewee in question 9 requested displaying changing menus, which was determined to be infeasible), and the above could be repeated. This would quickly converge to an acceptable front-end for the users. Assuming a large enough group of people was asked, this step could be completed in two days.

It will remain to create the back-end for this system. This will include much less risk, as there will be an accepted front-end and the back-end need only implement it. In order to further mititgate risk, the spiral model can be used. Of the remaining requirements, the one with the greatest risk is 1, as incorrectly designing the architecture of the program may cause it to crash, denying the service to all users. The base of the service will be very similar to what was done for assignment 3/4. A prototype will be built off a model version of the code for assignment 3/4 which allows users to text the service the wait time for a location or receive the shortest waiting times. For the prototype user reports on wait times will simply overwrite the current entry for the wait time for a given establishment. While this is not necessarily an accurate estimate of the wait time, it will allow for an easy way to test the prototype within a group of test users. Ideally this will allow any architectural bugs to be identified and eliminated. Assuming the initial prototype functions correctly, two days of testing should be sufficient. Any bugs would require an additional day of testing, but no more testing should be necessary as the initial architecture will be thought about thoroughly.

Once the prototype above works properly, a better way to determine wait times based off of the most recent user reports can be designed, plugged into the working prototype and tested with the test group to ensure that it provides accurate wait times. If not, it can be revised. This should require at most two days of testing.

Once all of the above is working, the final version of the service can be created, with requirement 2 being satisfied by sending every new user who signs up an initial text specifying the protocol used to report times (this protocol will be determined in the above step).

At this point, the service should satisfy all requirements and have been thoroughly tested.
