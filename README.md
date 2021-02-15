In online advertising, a valuable technology is "targeting". Targeting allows advertisers to deliver advertising content
specific to certain user attributes such as geographic location, age or which websites the user has visited. One method
of storing these user attributes is through segments. A segment is an identifier (in our case, a number) to a specific
attribute. For example, the segment 1 could be used to identify a user who is male while the segment 2 could be used to
identify users who are female.

# Specification

The program will take a single argument, a file containing a list of campaigns. This file will consist of several rows,
each with a variable number of columns. The first column is the unique identifier of a campaign (a single word). The
columns after the first are the segments that the campaign is targeting. Duplicate columns and/or rows are valid and to
be ignored.

Once the program has loaded the data file, it will be ready to accept input from users. Lines of data will be read from
standard input where each line consists of a list of segments some user is associated with. It is not guaranteed that a
segment a user belongs to has any campaigns associated with it. Given this list of segments, a campaign containing the
largest set of common segments is to be selected. Once a campaign has been selected, the name of the campaign is to be
printed on a single line. If no eligible campaign is found, "no campaign" is to be printed on a single line.

    Example data file (example_data.txt):
    ,----------------------------------------,
    | campaign_a 3 4 10 2                    |
    | campaign_b 9 14 15 21 3                |
    | campaign_c 12 1024 200 3 9 4           |
    '----------------------------------------'


    Example usage for the above data file:
    ,----------------------------------------,
    | shell$ ./campaign example_data.txt     |
    | 3 4 5 10 2 200                         |<- 1 input
    | campaign_a                             |-> 1 output
    | 3                                      |<- 2 input
    | campaign_a                             |-> 2 output
    | 3                                      |<- 3 input
    | campaign_c                             |-> 3 output
    | 4 10 15                                |<- 4 input
    | campaign_a                             |-> 4 output
    | 1024 15 200 21 9 14 15                 |<- 5 input
    | campaign_c                             |-> 5 output
    | 9000 29833 65000                       |<- 6 input
    | no campaign                            |-> 6 output
    '----------------------------------------'

In the first line of input, the user belonged to one segment campaign_b was targeting, four segments that campaign_a was
targeting and three segments that campaign_b was targeting. campaign_a was selected since it was targeting the largest
number of segments associated with the user. In the second line of input, the user belonged to one segment in each
campaign. Due to this, any of the campaigns are eligible for selection. In the fourth line of input, the user belongs to
two segments in campaign_a and one segment in campaign_b. Since the user belonged to more segments from campaign_a,
campaign_a was selected. In the fifth line of input, the user belongs to three segments that campaign_c is targeting and
two segments that campaign_b was targeting. Since the user belonged to more segments from campaign_c, campaign_c was
selected. In the sixth line of input, no campaign targeted any of the segments the user was associated with, so "no
campaign" was provided as output.

# Additional Information

There are only 65536 segments from 0 to 65535. This application will process many users a second, so it is important
that it is fast. The input and the campaign list we will be using are in the "examples"
directory (`input.txt` and `campaign.txt` respectively).

# Solution

Please use Spring Boot for your solution. Please do use tools such as [Spring Initializr](https://start.spring.io) to
help maximize the impact of your solution for the time you have to put in on the project.

# Extra Credit

**Option 1 - Packaging:** Standard input/output is great for a prototype, but not production. How might this be
improved?

**Option 2 - Algorithms:** How would you decrease the probability of starving a specific campaign? For example, let us
say that campaign_a and campaign_b were to target the same segments. How would you distribute your selection of the
campaigns?

# Submission Instructions

Use `git archive master | gzip > submission.tgz` and mail it back to us. 
