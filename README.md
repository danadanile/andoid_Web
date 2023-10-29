# andoid_Web

In this repository, we have created an Android app for a chat system. This is the client side. You can find the server side [here](https://github.com/adi-ben-yehuda/Server)

The application contains the following screens:
- Login screen
- Registration screen
- contacts screen
- Chat screen
- Added a new contact
- settings

## General Information
The main page displays a simple login screen where the user is required to enter a username and password or navigate to the registration screen.

<img src="https://github.com/danadanile/andoid_Web/assets/117977429/f720ca00-5dbf-46ad-932e-6b6b4c75c81b" alt="Login" width="200">

![image](https://github.com/danadanile/andoid_Web/assets/75027826/f01427c7-e75e-4874-aab6-1d93c3f14cc4)


On the registration screen, the customer needs to enter their username, password, display name, and upload a photo. The input fields are validated with the following requirements: all fields are mandatory, the password must have at least 8 characters and the username must be unique.

<img src="https://github.com/danadanile/andoid_Web/assets/117977429/2bb48b65-e1cf-44f3-a68a-9248c772677d" alt="Register" width="200">

After registration, the user can log in to the application by entering his username and password. If the details are correct, the user will be redirected to the contacts screen. 

On the Contacts screen, all of the user's contacts are displayed. The user can click on a specific contact and the chat with him will be displayed.

<img src="https://github.com/danadanile/andoid_Web/assets/117977429/075c9cdb-c5f4-4eac-9ac2-835db38cf994" alt="Contacts" width="200">

On the chat page, the user can see all the message history with the specific contact. The user can also send him a new message.

<img src="https://github.com/danadanile/andoid_Web/assets/117977429/7ad8809a-40b4-4d0a-9be5-5801e98d9b86" alt="Chat page" width="200">

From the Contacts page, the user can add a new contact using the Add button and navigate to the Add Contact page. On this page, the user enters the username they want to add and clicks Add. On the Contacts page, the new contact will be displayed.

<img src="https://github.com/danadanile/andoid_Web/assets/117977429/46ca340b-a83c-4b7c-bd0e-4f96b7a75d4e" alt="image" width="200">

There is also a settings option on the login page and the contacts page. On the settings page, the user can change the address of the server to which the application is connected or the color theme of the application.

<img src="https://github.com/danadanile/andoid_Web/assets/117977429/54dc321d-8b08-4045-957c-42a1fcef2f9a" alt="Setting" width="200">

In our Android app, we keep a local copy of the chats and messages.
When the application is opened, the information is pulled from the local SQLite DB using Room. After the server returns the updated information to us via the API, the local DB is updated.

The app also includes notification functionality via Firebase. After the user is logged in and another user sends a message to him, a notification will be displayed. The notification includes the name of the sender and the content of the message.
If the user is not currently active, the notification will be displayed the next time the application is launched.

## Installation

Before installing this project, you need to install on your computer:

- Git
- Android Studio

Then open a terminal and clone the project:
```
git clone https://github.com/danadanile/andoid_Web.git
```

And run the app.
