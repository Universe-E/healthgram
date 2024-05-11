# Instructions
- One member of your group should `fork` this repository, add all the members to the repository and set it as private.
  > The forked repository will be where you continuously update your progress, and
  submit the major part of the project to.
  Include the link to your repository in group registration on Wattle.
- Some template files are provided in the `items` [folder](./items). Please place all your documents including report, meeting minutes, your APK, etc. in this folder, while
- your Android project should be in another folder with proper folder structures and naming.

Please refer to the `assignment specification` for detailed instructions.
- you are encouraged to update [report.md](./items/report.md) incrementally as the project progress. 
- You may also use [checklist.md](./items/checklist.md) for a Quick check.
- You must ensure the link to your repo is correct on the `Wattle Group Registration Page`.





# Healthgram

Healthgram is a social media application specifically designed for healthcare workers and patients. It provides a platform for users to share and access the latest medical posts, get professional advice, and engage in discussions related to healthcare issues.



# Features

- User authentication and account management
- Create, edit, and delete posts
- Follow and unfollow other users
- Receive notifications for new posts from followed users
- Search for posts and users
- Privacy settings for posts (public, restricted, private)
- User profile customization



# Getting Started

## 1. Prerequisites

- Android Studio
- Java SDK

## 2. Installation

- Clone the repository:

- ```
  git clone https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1
  ```

- Open the project in Android Studio.
- Build and run the application on an emulator or physical device.

## 3. Usage

1. Launch the Healthgram application on your Android device.
2. Sign up for a new account or log in with your existing credentials.
3. Explore the main feed to view posts from other users.
4. Create a new post by clicking on the "New Post" button.
5. Search for specific posts or users using the search functionality.
6. Follow interesting users to receive their updates in your feed.
7. Manage your profile and privacy settings in the "Settings" section.



# UML Diagram

![Search](./items/media/_examples/Search.png)

# Change Log

## 2024.05.11

---

Xingchen Zhang

- Fixed bugs on search page close

Han Bao

- Refactored back-end recall methods
- Refactored Toast popup logics

Yulong Chen

- Refactored adapter on-call problem

Zehua Kong

- Implemented B-Tree structure in search

------

## 2024.05.08

---

### Finished Checkpoint 2

Zehua Kong

- Implemented User and Notification classes
- Fixed bugs on search bar

Han Bao

- Added reflection methods for Fragments
- Added recall method

Yulong Chen

- Fixed bugs on search bar dropdown
- Fixed search lists incomplete problem

Tianci Li

- Provided icon and app name
- Modify Searching logics

Xingchen Zhang

- Fixed bugs on friend list
- Fixed bugs on keyboard dropdown

------

## 2024.05.06

---

Han Bao

- Modify database back-end methods

- Fixed java redirect bugs
- Added back-end adaptor for method call in Android UI

Zehua Kong

- Implemented User and Notification classes
- Modify Database for data retrieving

Xingchen Zhang

- Fixed bugs on search bar dropdown
- Fixed search lists incomplete problem

Yulong Chen

- Completed notification and privacy set
- Fixed redirect bugs on logout

Tianci Li

- Wrote Parser and Tokenizer
- Modify fragment for better adapt data present

------

## 2024.05.04

---

Han Bao

- Complete util methods

- Implemented authentication

Zehua Kong

- Implemented get id methods in database
- Completed database back-end methods

Tianci Li

- Added new features on home data present
- give post structures
  

------

## 2024.05.01

---

Xingchen Zhang

- Wrote rough search bar

- Initialize lists in search results

Yulong Chen

- Wrote rough setting page

- fixed redirect bugs on clicking bottom icon buttons

------

## 2024.04.29

---

Zehua Kong

- Connect to firebase

- Test login page and initialize accounts
- modify signup page

Yulong Chen

- Add front-end privacy management

------

## 2024.04.27

---

Tianci Li

- Added main page

- Test notification page

## 2024.04.25

---

Han Bao

- Design rough back-end methods

## 2024.04.22

---

### Finished Checkpoint1

Han Bao

- Drafted structures for data layer

  Design initial test cases

---

## 2024.04.21

---

Han Bao

- New Main Activity

  Now New main activity has following logics:
  - Check if user already signed in
    - If not active original main activity (may change it to sign in activity?)
    - else remain in this activity

- Create Account Activity

  Now click create account button in original main activity actives sign up activity

---

## 2024.04.18

---

Yulong Chen

- Add environment and dependency
- Add login activity

---

## 2024.04.15

---

Zehua Kong

- Add structures: 
  - Data Item
  - Database management: SQLite Database
  - B-Tree structure for data storage

---

# TODO

- Come up a TODO list for the whole project and assign specific tasks to team members.
- More detail about back-end implementations

# Notice
## Third-party usage
- Amazon AWS
  - root user: Han
    - If you need access to AWS please feel free to contact me!
    - How to use AWS within jetBrain products: [Doc](https://docs.aws.amazon.com/toolkit-for-jetbrains/latest/userguide/welcome.html)
    - How to set up AWS environment: [Doc](https://aws.amazon.com/getting-started/guides/setup-environment/?ref=gsrchandson)
  - cognito: for sign up and sign in

- Material 3
  - Enrached UI

# Questions

- Can we use LiveData? or we need to implement our own Observer base class?

- Can we use interface to simulate the network API or we need to implement some network policies to prevent lose marks?



# Team Members

- Han Bao (u7752342)
- Tianci Li (u7773219)
- Xingchen Zhang (u7670173)
- Yulong Chen (u7756137)
- Zehua Kong (u7693498)



# Acknowledgements

- [Material 3](https://m3.material.io/) - UI design library
- [Firebase](https://firebase.google.com/) - Backend and authentication services