# Team 101-5

# Contributers

Ryan Maxin, Derek Maxin, Marcus Puntillo, Jia Wen Li

# Conventions
**Branching**: [issue-number]-[description] <br>
**Example**: 56-question-deletion

## Project Proposal

Name: AceInterviewer
Type: Mobile App

Click [here](https://git.uwaterloo.ca/kotlin-gang/team-101-5/-/wikis/Project-Proposal) for our Project Proposal. 

# Release 4.0.0
_April 5, 2024_
* Added the ability to upload profile photos to the profile.
* Added the ability to leave a review on a question response.
* Added the answer question page with the ability to record, and store audio. 
* Added the ability to see the questions you've answered by month on the profile.
* Implemented audio playback and the ability to hear audio responses.
* Added locked badges to the profile screen.
* Added birthdays to profile.
* Added logout to profile.
* Added additional search filter fields. 
* Made the search screen scrollable. 
* Added the ability to scroll through answers on the review.
* Added the requirement to aqcuire user's audio permissions.
* Added an age requirement to registration.
* Replaced default app icon.
* Removed redundant features.

# Release 3.0.0
_March 22, 2024_
* Added the screen meant for reviewing.
* Added the components associated with reviewing (Star selection).
* Added profile history chart.
* Added badge feature with animations and expanded view to profile.
* Added various scroll bars on necessary screens.
* Added sample questions to database.
* Questions users have authored are now attached to their profile.
* Locked the screen orientation to portrait mode. 
* Connected profile screen to database.
* Connected leaderboards screen to database.
* Added the option to filter search items by tag.
* Added a reccomended question to the home page.
* Refactored architecture

# Release 2.0.0
_March 8, 2024_
* Added Search Screen and associated functionality
* Added Profile Screen
* Added the Make Question Screen
* Added Notifications Screen
* Added Leaderboard Screen 
* Added reset password functionality
* Created the Review Screen
* Added the ability to retrieve question data from the database
* Authentication login errors now display to the user
* Created review component to display scores associated with a criteria. To be displayed when a user reads reviews on their answers. 
* Added coroutines to the backend, replacing callbacks
* Added divider between fields in the registration page
* Created data structures for database storage
* Obfuscated password when typing on login screen
* Fixed an issue where the pause button wouldn't display on the `PlayBar` component. 
* Fixed an issue where pressing enter on text fields wouldn't unfocus it.
* Updated the project directory structure to be more readable
* Setup funding for Google Cloud Storage

# Release 1.0.0
_February 16, 2024_
* Added Home Page
* Added Login Screen
* Added Registration Screen
* Added Birthday Selector for Registration
* Added Interest Selection for Registration
* Added Question UI 
* Added Simple Audio Playback Interface
* Added Navigation Between Homescreen, Login Screen and Registration Screen
* Setup Database
* Setup Codebase for MVVM Architecture
* Setup and Integrated User Authentication
* Created channels for communication between the User Interface and the backend