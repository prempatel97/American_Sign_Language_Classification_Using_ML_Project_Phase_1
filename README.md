# CSE 535 - Android app for downloading, recording and then uploading American Sign Language gestures to cloud

This is an android app that lets users download American Sign Language (ASL) demo videos as well as upload their own recorded gesture videos to Firebase cloud storage. 

The mobile application has three screens:

**Screen 1**: Shows a drop down menu of 20 different gestures

Gesture list: {gift, car, pay, pet, sell, explain, that, book, now, work, total, trip, future, good, thank you, learn, agent, should, like, movie}

On selection of a single gesture the user is taken to screen 2

<img src="https://github.com/i-am-SR/CSE_535_Mobile_Computing/blob/master/Images/Screen_1.jpg" width="200" height="350">

**Screen 2**: Here the downlaoded video of an expert performing the gesture is shown

The video will have to be downloaded on the phone from the SignSavvy ASL gesture repository

https://www.signingsavvy.com/

Screen 2 has another button that says “RECORD”. On pressing this button, the user is taken to Screen 3.

<img src="https://github.com/i-am-SR/CSE_535_Mobile_Computing/blob/master/Images/Screen_2.jpg" width="200" height="350">

**Screen 3**: In this screen the camera interface is opened for the user to record the practice gesture. 

On pressing the UPLOAD button, the user should be able to upload the gesture to the Firebase cloud storage. Moreover, clicking this 
button will also take back the user to screen 1.

<img src="https://github.com/i-am-SR/CSE_535_Mobile_Computing/blob/master/Images/Screen_3.jpg" width="200" height="350">

![Firebase](Images/firebase.png)

#Instructions to run the code:

- Download the zipped code files and unzip them.
- Open the project in Android Studio.
- Build the project (Ctrl+F9).
- Connect your android device if you want to run the app on your phone or use the emulator. Then press run.
- When the application starts, go to settings and provide the app with the permission to use Camera and Storage of your device.
- Home screen:
	In the three text fields, enter your lastname, ID and group number respectively. Please make sure that these details are correct as they will dictate the directory structure on the cloud storage.
	Press the submit button once you have entered the details to go to the next screen.
- Screen 1:
	Choose a gesture to learn from the dropdown list and click the download button to download the selected video.
- Screen 2:
	The VideoViewer will display the video once it is downloaded. Learn the gesture from the video and press record to start recording your gesture video.
	On pressing record, the camera app will open which you can use to record. The camera app will record the video for 5 seconds by default.
	Once you agree to save the video and the save is successful, the app will navigate to screen 2 again and the upload button will be activated to upload the captured video to cloud.
	The videos will be saved in a folder heirarchy [ Group Number -> ID -> Signs -> videos ]. These details are taken from the home screen.
	If the folder heirarchy doesnt exist, it will be created. The file is saved as [ GESTUREName_(Practice Number)_USERLASTNAME.mp4 ] on the cloud.
	The app navigates back to Screen 1 once the upload is successful.
	
	
Created by Sumit Rawat, Prem Patel, Dhruv Patel and Kum Hee Choy

The second phase of this project that classifies ASL gestures using a RESTful API is located at : https://github.com/prempatel97/American_Sign_Language_Classification_Using_ML_Project_Phase_2


