# What is PASITHEA ?
Pasithea is a framework created by Pasithea Software. This framework helps developpers to add vocal functions in their own Android native applications.\
PASITHEA provides a set of functions to do voice integration in an app. These functions cover the speech recognition and the speech synthesis. 

# PASITHEA documentation
The javadoc for each classes and interfaces are available [online](http://logicielpasithea.fr/Pasithea/framework/documentation/)

# The sample code
This sample code will provide you a basic single activity application to demonstrate the usage of the question/answer function of PASITHEA.\
At runtime, the app will start an activity with textviews to display the question, the answer detected  and the action triggered by the answer. The button is used to restart the same question.\
Depending on the answer detected the app wil change the image on top of the screen and put a happy face (answer is yes) or a sad face (answer is no). at the same time it will say a short message.

### How to use this demo ?
Clone this repository locally and open the project in your IDE. This demo is provided with a test version of the framework which stop working after December 31st 2019. [Contact us](contact@logicielpasithea.fr) if you want to extend your test period. You can modify the code and you can create your own app to test PASITHEA.

Refer to the [documentation](http://logicielpasithea.fr/Pasithea/framework/documentation/) but basically the app will initialize the framework with a builder.
  ![PASITHEA initialization](http://logicielpasithea.fr/img/initialization.PNG)

It uses a listener to configure an action to trigger once the initialization is done. The use of this listener is optional.
Once the framework is configured, the app calls the question/answer function

  ![startQuestionAnswer() usage](http://logicielpasithea.fr//img/startanswer.PNG)

Before the initialization, the app must check the AUDIO_RECORD and READ_EXTERNAL_STORAGE permissions.\
If one permission is not granted the initialization will continue but the use of the speech recognition will not be possible.

### Build your own testing app
If you want to build your own app with the framework, follow the procedure below:

* Open the gradle.properties file and add the credentials and the URL of the framework

    ![gradle.properties screenshot](http://logicielpasithea.fr/img/Gradle.properties.PNG)
    
Befor you sync your gradle configuration, make two other changes.

* Open the gradle.build.<project> file and add the repository information

    ![gradle.build.<project> screenshot](http://logicielpasithea.fr/img/Gradle.build.project.PNG)

* Finally open the gradle.build.app file and add the aar and the localbroadcastmanager dependencies

   ![gradle.build.app screenshot](http://logicielpasithea.fr/img/Gradle.build.app.PNG)
   
Now you can sync your gradle configuration. Once it is done you'll have access to the framework.

# Contributors
We are looking for contributors to help us to create amazing vocal modules and apps but also people who want to be involved in the development of the core framework.\
[Contact us](contact@logicielpasithea.fr) if you're interested.






