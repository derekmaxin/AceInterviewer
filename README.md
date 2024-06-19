# AceInterviewer
Both students and professionals have limited access to valuable feedback on their responses to typical interview questions in their field, resulting in inadequate preparation and missed opportunities. Our application bridges this gap by offering a platform where users can engage with typical interview questions relevant to their industry and publicly post their answers to receive insightful feedback from a community of peers. This interactive forum not only enhances preparation but also fosters a supportive environment for career development.

### Demo:

https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/2e6002a9-2cc6-4c92-a649-3f55b11eda82

(The above is heavily compressed. See high quality version [here](https://drive.google.com/drive/folders/1df4Uqqx2KcWLQjGhdKKC23v_FGc0OZ36?usp=sharing) )

### Contributors: 
* Ryan Maxin [Email](mailto:rsmaxin@uwaterloo.ca)
* Derek Maxin [Email](mailto:dmaxin@uwaterloo.ca)
* Marcus Puntillo [Email](mailto:mapuntil@uwaterloo.ca)
* Jia Wen Li [Email](mailto:jw24li@uwaterloo.ca)

### Installation Guide

To download our app, please download the APK [here](https://drive.google.com/file/d/158b0aNngybd9aHsYIUKVHJ2VuB7w46Pl/view?usp=sharing). Then simply open the APK file on your physical device (must be an Android 12.0 and up, recommended Android 13.0 and up). If using an emulator, drag and drop the APK file into the emulator.

### Design Documentation
**[Design Documentation](https://github.com/Ryanmaxin/AceInterviewer/wiki/Design-Documentation)**

### Reflection
Our team worked together to build the app 'AceInterviewer'. The largest 'best practice' we used was MVVM architecture. We have one Firebase database that is referenced only from our models. We have two models: One authentication model and one main model. The authentication model deals with everything to do with authentication such as data coming in from the login and register reviews, while the main model deals with the bulk of the app functionality. We separate each screen by giving it a view, and each view generally has a viewModel if computations need to be done. We also have controllers that the views can use to request data from the model to update. Using MVVM was very effective for our group. Our team adapted as our project progressed by speeding up our work as the sprints moved along. The first sprint was largely all of our team members working with Kotlin and compose for the first time and our output was a few screen and some basic architecture, but as we progressed we started having a lot more meetings and integrating everyone's code together in a cohesive way. We also took all the suggestions the TA made during our demos to progress our app. In our next project, we would have the experience to know what it takes to build a high quality app, and would be able to budget our resources more effectively. The design/solution we are particularity proud of is integrating audio recording and playback into the app. It came with many challenges like asking for user microphone permissions, getting the audio to record correctly, storing the audio in blob storage and referencing it from our question data, and getting the audio to play back where the user has full control over moving the slider to different start points of the audio. It took everyone on the team to make it happen and in the end our solution was very elegant. Our most difficult technical challenge was bringing all of our components together as a team. For example, while someone was working on a view and another was working on the model and database, we had to communicate what data was needed to be fetched and what was needed to be updated, so we had to keep straight what data was being sent where and in what form we were sending the data and this was quite challenging with all the moving parts. In the end, we brought every part of our app together elegantly and extensively tested our app to create a great product.

## User Guide

### Login

When the user first opens the app, it should take the user to the login page. There, the user will have the option to sign in with an existing account's email and password, recover an existing account's password, or create a new account. Click "Register" to make a new account.
<br></br>
![login](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/130ac628-e5e3-44e9-8153-30498955ce68)


### Registration

Fill out the fields as prompted. Note that the user must be over the age of 16, must select at least one (maximum of three) Fields of Interests.
<br></br>
![registration](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/8d06b6ff-9cd7-4873-aff9-d01db17a3d10)


### Home

The home screen (accessible through the house icon on the bottom navigation bar) will have a recommended question at the top and a list of your answered questions at the bottom. The Question of the day is clickable.
<br></br>
![home](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/291b7845-1550-4a64-9763-8f9c8f9277f7)


### Answer Question

Clicking on a question (whether from the search or Home screen) will take you to a screen where you input your answer. Click "Start Recording" to start your recording and that button will change to "Stop Recording". Click that when you are finished speaking. Repeat if you want to change your answer. Click "Submit Answer" when finished.
<br></br>
![answerquestion](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/3033c07e-1cf1-48e5-aa09-37474b96c3e7)


### Search

Clicking the magnifying glass will prefix search through all the created questions. If nothing is entered in the search bar, all questions will be shown. Filtering by tag will show any question with any of the tags included in the filter. Checking the completed box will make it so only questions that you have completed will appear. Completed questions are outlined in blue. If you want to add another question you can press 'Add new question' to take you to the create question screen. Clicking on one of the questions in the search results will take you to the answer question screen.
<br></br>
![search](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/2a0fef17-7ff1-42f0-ac47-62f77e2cdc26)


### Add Question

On the Add Question screen, you can type the question you want to make in the text box. At least one tag must be selected. You have the option to discard your draft by clicking the red "Discard Draft" button, or Submit Question.
<br></br>
![addquestion](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/0ae7ed81-9ccb-487a-9714-f48be3b8dada)


### Profile

The profile screen (reachable with the person icon on the bottom navigation bar) has your username, email, birthday and a profile picture. You can upload a new profile picture by clicking the profile picture.
<br></br>
![profile](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/04a6d2b1-1b5a-483c-a17a-ee3d0c7549b2)


There is a logout button and your badges will be featured. Click "Expand" for more information on the badges. The badges have animations once unlocked!
<br></br>
![badges](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/c7369564-372e-441b-9b3e-a72ca9d7916b)

<br></br>
Your selected Fields of Interest will also appear here.
<br></br>
There is a button to take you to the Leaderboard screen.
<br></br>
The bottommost section is where you can see your answers and the aggregated feedback they have received. You can view them by month (dates are the date answered)
<br></br>
![profile2](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/ac4c5fd4-c4af-4312-956e-226fc6ab499f)



### Leaderboard

The leaderboard shows the users that have the most number of questions answered. This page is accessible through the profile screen.
<br></br>
![leaderboard](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/98fa45c8-85bc-400b-907f-0e1395540679)


### Notifications

The notification screen (reachable by the bell icon on the bottom navigation bar) contains notifications for when your answers receive reviews/feedback.
<br></br>
![notifications](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/b501bc71-9617-4cbe-a4b8-81ebdf8faceb)



### Review

The review screen is where you can review other users' answers to questions that are related to your fields of interests. The user who's answer you review will get a notification once you submit your review.
<br></br>

<img src="https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/7fc4518a-10ea-402b-9aea-a08214ab9e99" width="368">


### See Your Review

The see your review screen is reached when you press a notification of a review you received on one of your answers. You can see how the user rated your completeness and clarity, as well as a text review of your answer.
<br></br>
![seereview](https://github.com/Ryanmaxin/AceInterviewer/assets/90675771/d93f8974-88d8-4274-b320-1b8c9423406f)
