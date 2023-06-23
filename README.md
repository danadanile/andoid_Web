# andoid_Web

In this repository, we have created an Android client for a chat system. The application contains the following screens:
- Login screen
- Registration screen
- contacts screen
- Chat screen
- Added a new contact
- settings

## General Information
The main page displays a simple login screen where the user is required to enter a username and password or navigate to the registration screen.
On the registration screen, there is validation for the input fields with the following requirements:
    All fields are mandatory.
    The password must have at least 8 characters.
    The username must be unique.

After registration, the user can log in to the application by entering their username and password. If the details are correct, the user will be
redirected to the contacts screen where all their contacts are displayed. The user can click on a contact to move to the chat screen with the selected contact.

In the login page and contacts page, there is also a settings option where the user can choose to change the server address or the color theme of the app.

In our android app we save a local copy of the chats and messages .
When the application is opened, the information is extracted from the local sqlite DB with the help of
Room. 

The app also includes notification functionality using Firebase. The server sends push notifications to clients,
which will display appropriate notifications when a user sends a message.

## Server side:



## Installation
