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

login:

![image](https://github.com/danadanile/andoid_Web/assets/117977429/f720ca00-5dbf-46ad-932e-6b6b4c75c81b)

register:

![image](https://github.com/danadanile/andoid_Web/assets/117977429/2bb48b65-e1cf-44f3-a68a-9248c772677d)

contacts:

![image](https://github.com/danadanile/andoid_Web/assets/117977429/075c9cdb-c5f4-4eac-9ac2-835db38cf994)

chat page:

![image](https://github.com/danadanile/andoid_Web/assets/117977429/7ad8809a-40b4-4d0a-9be5-5801e98d9b86)

setting:

![image](https://github.com/danadanile/andoid_Web/assets/117977429/54dc321d-8b08-4045-957c-42a1fcef2f9a)

add contact:

![image](https://github.com/danadanile/andoid_Web/assets/117977429/46ca340b-a83c-4b7c-bd0e-4f96b7a75d4e)








## Server side:



## Installation
