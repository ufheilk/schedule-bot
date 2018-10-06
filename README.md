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
  2. Incoming reports for users for various dining locations are processed in a sensible way that will give incoming students a good estimate on the various wait times. 
