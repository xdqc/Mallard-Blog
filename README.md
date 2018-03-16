
# About
## Introduction ##
This a blogging website. Through the web site, users can register for an account, be able to post
articles and to leave comments on others. When logged in, they have full control of the
content they have authored: creating, updating and deleting their content and comments.

## Features
Basic functionality of this blogging system:
1. Users are able to create new accounts. Each new user can choose
a username (which must be unique) and a password. User’s
name, date of birth, and country can be recorded, along with a brief
description about themselves.
2. When creating an account, users are able to choose from amongst a set of
predefined “avatar” icons to represent themselves. Users can upload
an image from their devices, from which a thumbnail can be generated for use as a
custom avatar.
3. Once a user has created an account, they are able to log in and log out.
4. Users are able to browse a list of all articles, regardless of whether they are
logged in or not. If logged in, they can additionally be able to browse a list of their
own articles.
5. When logged in, users are able to add new articles, and edit or delete existing
articles.
6. When logged in, users are able to comment on articles. Users are also able
to delete any comments they have written, as well as any comments on articles
which they have authored.
7. Users are able to edit any of their account information, and also be able to
delete their account.
8. The website is responsive.

**Features Pack One: Administrative interface

An administrative interface are implemented using Swing Web
technologies that allows an administrative user to:
1. Add and remove users
2. Reset user passwords, by sending the corresponding user an email with a link to
reset their password
3. Show and hide comments and articles

**Features Pack Two: User interface

The blogging system provides the following improvements to the website’s user
experience:
1. TODO: WYSIWYG support for authoring articles
2. Informing new users whether or not their chosen username is already taken - without
the user having to submit a form to find out.

**Features Pack Three: Feature enhancements

The blogging system provides the following extra features to users:
1. When creating new accounts, users are verified as human using a Google’s
reCAPTCHA.
2. Users are able to enter a date when authoring an article. The date is
default to the current day, but the user can change this to any valid date (past or
future) they wish. If the user enters a future date, the article cannot be visible until
that date.
3. Users are able to search and sort article lists by article title , username , and
date .

**Features Pack Four: Multimedia

The website incorporates the following support for multimedia:
1. Users are able to upload any number of images to an article. Multiple images
per article is supported, and these images can be added and
removed when editing existing articles, as well as when authoring new ones. 
2. Users can upload audio and video files to an article. 
3. Users can embed YouTube videos within an article. These embedded videos can be placed
anywhere within the article’s content.
4. Users can browse a multimedia gallery showing all multimedia content
that’s been uploaded to any article. Users can switch between looking
at just their own multimedia, or content from all users. When not logged in, users
can still be able to browse all content.

**Features Pack Five: Robustness & security

The website implements the following features to make it more resilient to attacks and
unexpected user input:
1. Passwords are appropriately hashed /
salted. TODO: investigate and use a 3rd-party authentication system
(SSO), such as “log in with Facebook”, or “log in with Google”, etc.
2. TODO: comprehensively check all input, and provide appropriate non-default
error responses when required. What happens when users browse to non-existent
pages? What happens when users bypass your HTML5 / JavaScript form validation
and submit incorrect values (this is very easy to do with appropriate tools)? What
happens if a malicious user attempts SQL injection? These questions and more
should be considered!
3. TODO: Any other items / checks from the OWASP Top 10 which may be applicable.

----

## Meta Data #
### user.gender
* 0-- Female
* 1-- Male
* 2-- Other
### user.system_role
* 0-- Administrator
* 1-- Public
### attachment.attach_type
* A-- Article id
* C-- Comment id
* U-- User id
### attachment.mine
* Picture-- .jpg .png .bmp
* Audio-- .wmv .mp3 .wav
* Video-- .mov .mp4 .ogg

****

# Project Report #

##	Designing of The Blog System

The structure of our blog system follows MVC design pattern. 

### Model

![ER diagram](https://raw.githubusercontent.com/xdqc/Mallard-Blog/master/ER_model.png "ER diagram")

The ER diagram is shown above. There are four entities: `User`, `Article`, `Comment` and `Attachment`. User to Article, User to Comment, Article to Comment are 1:n relations, so we have foreign keys for each of them. To accomplish nested comments feature, the Comment has foreign key that references to itself.  To accomplish multimedia pack, i.e. each User (avatars), Article (gallery) or Comment (gallery) could have many files associated with it (1:n relation), we designed Attachment entity to represent files stored on sever, which has foreign keys reference to other entities. We also planned to have a feature that allows users to follow each other, i.e. an n:n relation, so we added another table called follow-relation. 

Thanks to JOOQ tools, we generated the ORM and DAOs with little efforts, after spending much time and discussions with lots of dedication and determination designing data model.

### View

We write HTML with JSP technology, which contains a bit of JAVA code to carry view-data from controllers. There are two pages, home_page.jsp and personal_blog.jsp, represents the core business of our website – displaying blog articles with comments, as well as showing user profiles. We also have `login.jsp`, `sign_up.jsp`, `reset_password.jsp` and `edit_profile.jsp` to composite the View of user account management and authentication subsystem. For advanced feature packs, we have `admin.jsp` and `search.jsp`. 

For code reusing and conciseness, we created several partial pages in WEB-INF directory, which include `header`, `footer`, `menu navigation`, `personal profile panel`, `article panel`, `multimedia gallery`, `file uploading`, `avatar chooser` and so on. 

Additionally, we have `error` and `contact` pages to make our website looks more professional.

### Controller

Controller is basically an abstract class extends HttpServlet, which contains common functionalities of controllers. Each of View has an associated Controller to handle Http requests and do the routing work. 

The DbConnector class does the job of connecting Controller and Model, thus no SQL need to be written in servlet controller classes. 
To make our coding life easier, we made a utilities package consists classes such as `Tree`, `Tuple` data structures; `Blog`, `Comments` composite DAOs; `File utilities`; and `Passwords` class from lab exercise. 
<br>

## My Contributions to The Team Project

In the ***planning stage*** of our project, I took part in the discussion actively, told some user stories, proposed to simplify our ER model with foreign keys to replace extra tables.

In the ***execution stage***, my coding works were both in back and front-end. For back-end, I wrote the `Controller` class, and `PersonalBlog`, `Search`, `Admin`, `ResetPassword`, `Contact`, `Error` servlets, as well as part of `HomePage`, `Login`, `SignUp` servlet. For front-end, I wrote the jsp files corresponds to those controllers above, as well as `homepage.js`, `personal_blog.js`, `admin.js` and some css. The following chart shows my git commit punch card during the two weeks.

![git punchcard](https://raw.githubusercontent.com/xdqc/Mallard-Blog/master/git_contributions.PNG "Git punch card")

The __highlight__ of my work was the extensively usage of AJAX in our blog website. There are massive jQuery.ajax() calls in `personal_blog.js` to load whole article, load edit article area, load nested comments, do post/edit/delete actions of articles and comments. I also used event delegates to make sure the buttons and anchors in ajax loaded part of the web page can be triggered correctly. 

The most __challenging__ part of my work was the implementation of nested comments feature. The first step was building a tree data structure, of which each node can have n children, with recursive algorithms to traverse the tree and find specific node. At the starting point, I tried to make recursive queries to the database to fetch nested comments data. The poor time efficiency can be imagined. Then I figured out to make a Blog class, which implements `Map.Entry<Tuple<UserRecord, ArticleRecord>, Tree<CommentRecord>>`. The reason of creating this class is to minimize total database connect, thus enhance the performance of the web app (localhost debug stage). This is also helpful to integrate our data model, with one database connection to query all comments under an article and process the list of comments to tree of comments. Similar reason to create Comment class in utilities package. 

The next task is to display nested comments nice and fast. Firstly, I tried to use jsp, and it was quite hard to deal with tree structure, even not able to display dynamically (hard to have synergy with ajax). Then I tried to pack the comment tree into json, and it worked quite well. I was using json array to keep the order of comments correct. In javascript, I made another recursive function, which was an exploitation of “Var Hijacking”. Without var, it would be pretty hard to write that function. At that moment, I started to really appreciate the creator of javascript made a such weirdly useful feature. It was the only place that I used `var` keyword in 1k+ lines of js in our project(mostly `const`, several `let`). Moreover, in the ajax load comment tree complete part, I tried to jquery the comment node, and set margins of comments correspond to their width, so that infinitely deep comments would be display normally. 

In ***project close stage*** , I refactored code of whole project, deployed the production version to tomcat server, and made a favicon for our mallard blog.
<br>

## Project and Class

###	Usage of topics learned in class

Most of knowledges and skills we learned from class are used in our project, except for Java Swing. The backbone of our project is Java Servlet technology, which controls the flow of http requests and responses, as well as sessions. 

For the industry classes, all basics of java language can be found in our code, such different data types, control flows, OOP concepts of encapsulation, inheritance, polymorphism, arrays, generic collections, IO, exceptions, concurrency collections, template design pattern, especially for event design pattern, widely used in the project.

For the web classes, we used git version control to collaborate. Almost every aspect of html, css, javascript taught in class are used, including responsive webpage, form validation, ajax, etc. We also hashed our user’s passwords with salt to make our website more secure.

In a word, the content of PGC teaching materials are very useful and practical for real world programming tasks and requirements.

###	Topics not learn in class

Java 8 lambda, stream. 

ECMA6 const arrow function, use function as a variable in javascript; Event delegate in js.

Regular expressions, which I suggest we may cover a little bit in class, give students some learning material, and introduce some use cases.

I suggest we could have more lab practices on json or with the usage of json object, json array. It is a very useful data structure, we need deeper understanding and should use it more fluently and naturally.

We also used some third-party libraries in the project. 

## Teamwork

We had a great team work and fair task allocation. The following chart shows a glimpse of our git history. We were planning to rotate scrum master between team members, but during most of the time in past two weeks, I was the de facto organizer and coordinator of the team. As a scrum master, I tried my best to bear in mind of each team member's coding works, and make sure each one's work could be properly integrated into the whole system. I was quite enjoying working with our mallard team and had a nice relation and communication with our team members.

![git teamwork](https://raw.githubusercontent.com/xdqc/Mallard-Blog/master/git_teamwork.PNG "Git teamwork")

*Tonny* is the oldest member in our team and has most experiences in web development. I learned a lot from him, especially in the planning stage of our project. He is very good at requirement analysis and prioritizing task list. He took the most difficult part of our project, multimedia gallery implementation. Although we were working on different part, we collaborated very efficiently by presenting interface to each other to use each one’s code. We made our team project with high coherence, low coupling.

*Nishant* is the one I spend most of the time with in our team. He is full of momentum to move forward and eager to learn more. We did 40+ hours pair programming, several whole nights in the lab together to conquer tasks of the team project. During that time, we were free to communicate without worrying about disturbing others. Our discussions not only limited to the specific issues of the project, but encompass programming languages, web technologies, IT trends and so on. For most of the times I was the navigator, and Nishant was driver. “Docendo discimus”, we both improved our programming skills in this process.
 
I also communicate with *Matthew* a lot. At the beginning, he was not crystal clear about how servlet works with http request and response. I spent an hour to tell him an analogous story of a custom going to restaurants to ordering food, how custom, server, cooker act at that scenario, and how menu, order sheet, plates do their roles. By analogy, he understood the flow of servlet much clearer, and then he finished his part of work.
 
All our team members are very appreciated to have a chance working together to accomplish something for real. Special thanks to our lecture *Dr. Cameron Grout* for giving us valuable help during the project.

