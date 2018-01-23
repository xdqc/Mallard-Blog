# Meta Data #
## user.gender
0-- Female
1-- Male
2-- Other
## user.system_role
0-- Administrator
1-- Public
## attachment.attach_type
A-- Article id
C-- Comment id
U-- User id
## attachment.mine
Picture-- .jpg .jpn .bpm
Audio-- .wmv .mp3 .wav
Media-- .mov .mp4
 


# README #

This is a personal blogging system.  

## Team members
- Matthew Hu
- Nishant Rana
- Samuel Ding
- Tonny Li

## Important Dates
- Monday January 22nd: Project introduction
- Monday February 5th: Presentation day
- Wednesday February 7th: Source code submission
- Thursday February 8th: Final report submission

## Introduction ##
In this assignment you will develop a blogging website using a combination of the
programming skills you have learnt through the Programming for Industry and Programming
with Web Technology courses. The snapshot shown above is taken from a previous PGCert
IT student group’s submission.
Through the web site, users can register for an account, which is needed to be able to post
articles and to leave comments on others. When logged in, they have full control of the
content they have authored: creating, updating and deleting their content and comments.
The functionality you need to provide to implement the blogging system, which is detailed
below, has been divided into two sections: basic functionality, which is compulsory, and
advanced features. For advanced features, there are a range of features to choose
from—you do not have to implement them all.
This project is a team project - each of you will be working in groups which will be assigned
by your lecturer. Make sure to name your team something memorable!

## Team Deliverables
| Deliverable | Marks |
| -------- | -------- |
| Implementation of compulsory features | 32 marks  |
| Implementation of advanced features | 48 marks |
| Code style | 10 marks |
| Daily stand-ups | 10 marks |
| Presentation & demonstration | 0 marks (but compulsory) |

### Implementation of compulsory features (32 marks)
There are a set of compulsory features which comprise the basic functionality for a blogging
system. These are detailed in the “compulsory features” section below. Your team must
implement all of these features adequately to receive full marks for this section.

### Implementation of advanced features ( 48 marks)
There are a wide range of additional features which could be implemented to improve the
functionality and user experience of your team’s blogging system. These are organized into
feature packs which are detailed in the “advanced features” section below. Your team must
select and implement three of these feature packs, for 16 marks each.

### Code style (10 marks)
Your code must be easily understandable by third parties, and conform to best practices.
This includes use of appropriate variable names, sufficient commenting, and adherence to
applicable patterns such as DAO and Web-MVC, amongst other considerations.

### Daily stand-ups (10 marks)
Each weekday during the project (other than public holidays), your team will be required to
report progress to the lecturer. Each team member must be present at every meeting.
Further details are available in the Project Management section below. The meetings will be
short - approximately five minutes per team per meeting. Evidence of good teamwork is
required at these meetings - your lecturer should be able to see and agree upon a fair
workload allocation for each team member.

## Presentation & demonstration
On Monday, February 5 th , each team will get the opportunity to present their project to the
rest of the class, and other staff and guests. This is your chance to show off all your hard
work and should be a fun session. The format of this presentation is a secret - you will find
out the exact details on the day!
Following the presentations, all attendees will get the opportunity to try out each team’s
project. This means that, while the source code is technically due on the 7 th , it is highly
recommended to be functionally complete before the 5 th , and just set aside the last two days
for fixing any bugs identified during the demos.



## Individual Reflective Report (worth 10% overall)
In addition to the team component, you should submit an individual reflective report (each
team member must write their own), which comprises 10% of your final grade for both the
Industry and Web sections of the course. This is due on Thursday , February 8 th . The
report should cover the following topics:
- In your own words, explain how the system as a whole has been designed;
- Detail your particular contributions to the team and project;
- Detail which topics, taught in class, have been used within the project, and where;
- Detail any topics or technologies, which were not taught in class, that you have used,
and where;
- Describe the lessons you have learned from working in a team. What are the
benefits, and are there any drawbacks or difficulties? How have those been
overcome in your team?

The length of the report should be approximately four – six pages , or around 1500 - 2000
words.

----

## Compulsory Features
Your team must implement the following compulsory features, which comprise the basic
functionality of a blogging system:
1. Users must be able to create new accounts. Each new user should be able to choose
a username (which must be unique) and a password. At minimum, a user’s real
name, date of birth, and country should also be recorded, along with a brief
description about themselves.
2. When creating an account, users must be able to choose from amongst a set of
predefined “avatar” icons to represent themselves. Users must also be able to upload
an image from their computer, from which a thumbnail can be generated for use as a
custom avatar.
3. Once a user has created an account, they must be able to log in and log out.
4. Users must be able to browse a list of all articles, regardless of whether they are
logged in or not. If logged in, they should additionally be able to browse a list of their
own articles.
5. When logged in, users must be able to add new articles, and edit or delete existing
articles.
6. When logged in, users must be able to comment on articles. Users must also be able
to delete any comments they have written, as well as any comments on articles
which they have authored.
7. Users must be able to edit any of their account information, and also be able to
delete their account.
8. The website must have a consistent look and feel, and must be responsive.

Using default Bootstrap, w3.css or similar is ok, but do not use any commercial or freely
available 'website templates'. If in doubt, check with your lecturer first .