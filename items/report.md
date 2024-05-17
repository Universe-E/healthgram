# [G04] Report

The following is a report template to help your team successfully provide all the details necessary for your report in a structured and organised manner. Please give a straightforward and concise report that best demonstrates your project. Note that a good report will give a better impression of your project to the reviewers.

Note that you should have removed ALL TEMPLATE/INSTRUCTION textes in your submission (like the current sentence), otherwise it hampers the professionality in your documentation.

*Here are some tips to write a good report:*

* `Bullet points` are allowed and strongly encouraged for this report. Try to summarise and list the highlights of your project (rather than give long paragraphs).*

* *Try to create `diagrams` for parts that could greatly benefit from it.*

* *Try to make your report `well structured`, which is easier for the reviewers to capture the necessary information.*

*We give instructions enclosed in square brackets [...] and examples for each sections to demonstrate what are expected for your project report. Note that they only provide part of the skeleton and your description should be more content-rich. Quick references about markdown by [CommonMark](https://commonmark.org/help/)*

## Table of Contents

1. [Team Members and Roles](#team-members-and-roles)
2. [Summary of Individual Contributions](#summary-of-individual-contributions)
3. [Application Description](#application-description)
4. [Application UML](#application-uml)
5. [Application Design and Decisions](#application-design-and-decisions)
6. [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
7. [Testing Summary](#testing-summary)
8. [Implemented Features](#implemented-features)
9. [Team Meetings](#team-meetings)
10. [Conflict Resolution Protocol](#conflict-resolution-protocol)

## Administrative
- Firebase Repository Link: <https://console.firebase.google.com/u/1/project/gp24-s1>
   - Confirm: I have already added comp21006442@gmail.com as a Developer to the Firebase project prior to due date.
- Two user accounts for markers' access are usable on the app's APK (do not change the username and password unless there are exceptional circumstances. Note that they are not real e-mail addresses in use):
   - Username: comp2100@anu.edu.au	Password: comp2100
   - Username: comp6442@anu.edu.au	Password: comp6442

## Team Members and Roles
The key area(s) of responsibilities for each member

| UID      |      Name      |                          Role |
| :------- | :------------: | ----------------------------: |
| u7693498 |   Zehua Kong   |          Back-end Programming |
| u7752342 |    Han Bao     |          Back-end Programming |
| u7670173 | Xingchen Zhang |    Android UI Design and Test |
| u7773219 |   Tianci Li    | Front-end Page Implementation |
| u7756137 |  Yulong Chen   | Front-end Page Implementation |


## Summary of Individual Contributions

Specific details of individual contribution of each member to the project.

Each team member is responsible for writing **their own subsection**.

A generic summary will not be acceptable and may result in a significant lose of marks.

*[Summarise the contributions made by each member to the project, e.g. code implementation, code design, UI design, report writing, etc.]*

*[Code Implementation. Which features did you implement? Which classes or methods was each member involved in? Provide an approximate proportion in pecentage of the contribution of each member to the whole code implementation, e.g. 30%.]*

*you should ALSO provide links to the specified classes and/or functions*
Note that the core criteria of contribution is based on `code contribution` (the technical developing of the App).

*Here is an example: (Note that you should remove the entire section (e.g. "others") if it is not applicable)*

1. **u7693498, Zehua Kong**  I have 19% contribution, as follows: 
  - **Code Contribution in the final App**
       - Feature
         - [Data-Profile]:  [User.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/User.java?ref_type=heads), [Post.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Post.java?ref_type=heads), [Notification.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Notification.java?ref_type=heads)
         - \[Search-Filter]: [Fragment_home.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/Fragment_home.java), [SearchPostsActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SearchPostsActivity.java)
     - \[FB-Auth]: [AuthUtil.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Utils/AuthUtil.java?ref_type=heads), [UserDB.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java)
    - Tokenizer and Parser (see more in Tokenizers and Parsers)
      - [Tokenizer.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Tokenizer.java?ref_type=heads)
      - [Parser.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Parser.java?ref_type=heads)
    - Tests
      - [FriendTest.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/test/java/com/example/gp/FriendTest.java?ref_type=heads), [NotificationTest.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/test/java/com/example/gp/NotificationTest.java?ref_type=heads), [ParserTest.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/test/java/com/example/gp/ParserTest.java?ref_type=heads), [PostTest.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/test/java/com/example/gp/PostTest.java?ref_type=heads), [TokenizerTest.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/test/java/com/example/gp/TokenizerTest.java?ref_type=heads), [UserTest.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/test/java/com/example/gp/UserTest.java?ref_type=heads), [UtilTest.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/test/java/com/example/gp/UtilTest.java?ref_type=heads)
- **Code and App Design**
  - State Pattern
    - [AuthUtil.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Utils/AuthUtil.java?ref_type=heads), [SignUpActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SignUpActivity.java?ref_type=heads) ,[signInWithEmail](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L688-702), [signInWithUsername](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L712-742) are used to authorize users, validate fields: name, email, and password
  - Data Structure: 
    - Implemented [BTree.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/BTree.java?ref_type=heads), used in [SearchPostsActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SearchPostsActivity.java)
    - ArrayList
    - Map
  - Tests
    - All UI tests are hold in my Android device, find and fix bugs
    - Write back-end logic Tests for all data profiles, tokenizer and parser
  - Write reports, record videos

2. **u7752342, Han Bao** I have 24% contribution, as follows:  

- **Code Contribution in the final App**

	    - Feature
	- **Firebase Integration**:
		- \[FB-Auth]: [SignUp Backend](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L344), [SignUpBackend Part 2](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L753) [SignIn Backend](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L344), [SignIn Backend for Email](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L688), [SignIn Backend for Username](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L712), [SignOut](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L267)
		- \[FB-Persist-extension]: [All the setter and getter in Database.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/Database.java?ref_type=heads) that for easy references, and their detailed implements: [UserDB.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads), [PostDB.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads), [FileDB](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/FileDB.java?ref_type=heads)
	- **User Interactivity**
		These are in the Firebase and happen in real-time so that other users on other devices can receive the updates
		- \[Interaction-Micro]: [Like backend](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L262)
		- \[Interact-Follow]: [Follow Backend](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L376), [Unfollow Backend](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L402)
  	- \[Interact-Noti]: Backend: [Notifications](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/Database.java?ref_type=heads#L183), implementations: [Get 1](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L72), [Get 2](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L517), [Set 1](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L434), [Set 2](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L619), [Set 3](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L654), [Set 4](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L374)
	 - **Privacy**
	    Private posts are only visible to the OP's followings, so when a user encounters a private post, they can send a request to the OP to let them add the user as his following.
	  - \[Privacy-Request]: Backend: [Request](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L434), [Accept or Deny](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L588)
	  - \[Privacy-Visibility]: Backend: [Change visibility](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L242), [Make it visible for certain users](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L419)
- **Code and App Design**
	
	- Singleton
		- [UserDB](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L60), that is for Manage current user's info and status. As only one user and login on one device at a time, the instance that represents the user must be unique and can't coexist.
		- [PostRepository](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/PostRepository.java?ref_type=heads), I firstly drafted it as PostsData, to simply hold the post retrieved from the firebase, and my team member Tianci, he found his UI was doing too much work regarding the data operations. Therefore he moved some code that deals with the post data into this class and renamed it PostRepository.
	
	

3. **u7670173, Xingchen Zhang**  I have 19% contribution, as follows: <br>

  - **Code Contribution in the final App**
    - Feature
      - UI-Layout: [Fragment_home.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/Fragment_home.java)，[HomeFragment.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/home/HomeFragment.java), [HomeViewModel.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/home/HomeViewModel.java), [FriendFragment.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/Friend/FriendFragment.java)
      - Search: [SearchActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SearchActivity.java),[SearchPostsActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SearchPostsActivity.java)
      - Notification: [NotificationsFragment.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/notifications/NotificationsFragment.java), [FollowerNotificationFragment.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/notifications/FollowerNotificationFragment.java),[FriendNotificationFragment.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/notifications/FriendNotificationFragment.java),[NotificationsViewModel.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/notifications/NotificationsViewModel.java),[NotificationsPagerAdapter.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Adapter/NotificationsPagerAdapter.java),[FollowerFragmentViewAdapter.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Adapter/FollowerFragmentViewAdapter.java),[FriendFragmentViewAdapter.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Adapter/FriendFragmentViewAdapter.java), [Notification.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Notification.java), [NotificationFactory.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/NotificationFactory.java)
      
    
  - **Code and App Design** 
    
    - Design Pattern
      - Singleton Pattern:  [fireAuthCreate](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L753-777), [savePost](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L71-116), [notifyNewPostToFollowers](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L374-409), [isAuthorMyself](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/interaction/PostDetailActivity.java?ref_type=heads#L179-183), [SettingActivity.onCreate](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/setting/SettingActivity.java?ref_type=heads#L51),  [getFollowList](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L495), [isAuthorFollowed](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/interaction/PostDetailActivity.java?ref_type=heads#L175-178)
    - Data Structures
      - Map
      - ArrayList
    - UI Design
      - Search Bar
      - Home Page 
    
    
    

4. **u7773219, Tianci Li**  I have 19% contribution, as follows: <br>

  - **Code Contribution in the final App**
    - Feature
      - User-Login: [LoginActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/LoginActivity.java), [SignUpActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SignUpActivity.java)
      - DataStream: [HomeFragment.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/home/HomeFragment.java), [PostDetailActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/interaction/PostDetailActivity.java)
      - User-Interact: [FriendFragment.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/Friend/FriendFragment.java), [SettingActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/setting/SettingActivity.java)
      - Privacy Visiblity: [PostVisibilityActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/setting/PostVisibilityActivity.java)
      - Privacy Block: [FriendDetailActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/setting/FriendDetailActivity.java)
      - LoadShowData: [User.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/User.java ), [Post.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Post.java), [Friend.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Friend.java), [Notification.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Notification.java)
      
    - Design Pattern
    
      - Singleton Pattern: [FriendsData.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/FriendsData.java?ref_type=heads#L21-26)
        , [PostRepository.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/PostRepository.java?ref_type=heads#L31-36), [HomeFragment](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/home/HomeFragment.java?ref_type=heads#L59), [PostRepository](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/interaction/PostDetailActivity.java?ref_type=heads#L44)
      - Observer Pattern: [setOnAvatarUUIDChangedListener](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L191-194)
        [savePost](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L71-116), [addNote](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/UserData.java?ref_type=heads#L85-93), [deleteNote](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/UserData.java?ref_type=heads#L95-103), [notifyAvatarUUIDChanged()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L197-201)
    
      
    
  - **Code and App Design** 
    
    - Design Pattern
  - Singleton Pattern
    - Data Structures
      - Map
      - ArrayList
    - UI Design
      - Search Bar
      - Privacy Page
    
    
    

5. **u7756137, Yulong Chen**  I have 19% contribution, as follows: <br>

  - **Code Contribution in the final App**
    - Feature
      - User-Login: [LoginActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/LoginActivity.java), [SignUpActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SignUpActivity.java)
      - Interaction-Micro: [PostDetailActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/interaction/PostDetailActivity.java)
      - Privacy-Request: [RequestAdapter.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/dev/gp/app/src/main/java/com/example/gp/setting/Adapter/RequestAdapter.java), [RequestActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/dev/gp/app/src/main/java/com/example/gp/setting/RequestActivity.java)
      - Privacy Visiblity: [PostAdapter.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/dev/gp/app/src/main/java/com/example/gp/setting/Adapter/PostAdapter.java), [PostVisibilityActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/dev/gp/app/src/main/java/com/example/gp/setting/PostVisibilityActivity.java)
      - Privacy Block: [FriendDetailActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/setting/FriendDetailActivity.java)
- **Code and App Design** 
     - Design Pattern
       - Singleton Pattern: [getNewPostsByTime](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L148-168), [getPreviousPostsByTime](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L178-198), [SearchPostsActivity](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SearchPostsActivity.java?ref_type=heads#L44), [HomeFragment](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/home/HomeFragment.java?ref_type=heads#L59), [PostRepository](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/interaction/PostDetailActivity.java?ref_type=heads#L44)
       - Observer Pattern: [setOnAvatarUUIDChangedListener](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L191-194)
         [savePost](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L71-116), [addNote](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/UserData.java?ref_type=heads#L85-93), [deleteNote](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/UserData.java?ref_type=heads#L95-103), [notifyAvatarUUIDChanged()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L197-201)
     - Data Structures
       - Map
       - ArrayList
     - UI Design
       - Privacy Page
       - Home Page
       - Setting Page
       - Avatar Page



## Application Description

*[What is your application, what does it do? Include photos or diagrams if necessary]*

*Healthgram is a social media application specifically targeting healthcare workers and patients, it provides latest medicare posts for all users, give information and professional advice for special groups, such as medical treatments, recent progress on bio-sciences, and latest news on healthcare policies. You can also use this application to post your ideas on healthcare issues.*

### Application Use Cases and or Examples

*[Provide use cases and examples of people using your application. Who are the target users of your application? How do the users use your application?]*

A patient names Alice wants to know something about his illnesses, and see a public post by physician Bob on the Healthgram app

1. Alice found that the medicare advice from Bob is useful
2. The post is edited by Bob with the content is mainly about adjust your daily diet to keep away from diseases
3. Alice followed doctor Bob, each time when Bob sends new posts, Alice will receive new notification from him
4. Alice want to inquiry her conditions in privacy with Bob, she set some of her posts private, so that no other people can see these posts
5. After Alice's illnesses were cured, she unfollowed doctor Bob, this operation will let her not to receive notification from doctor Bob

Here is a map navigation application example

*Targets Users: Patients*

* Patients can use it to search for useful posts by keywords
* Patients can add their own posts to share recent progress on their conditions
* Patients can learn the knowledge from public posts
* Patients can set visibility of posts to private to protect their privacy 
* Patients can follow other patients, or doctors to trace their status

*Target Users: Doctors*

* Doctors can publish professional suggestions
* Doctors can follow other doctors and patients for better medical treatment inquiry
* Doctors can set privacy status due to privacy protection

*List all the use cases in text descriptions or create use case diagrams. Please refer to https://www.visual-paradigm.com/guide/uml-unified-modeling-language/what-is-use-case-diagram/ for use case diagram.*

<hr> 

### Application UML

![ClassDiagramExample](media/_examples/ClassDiagramExample.png) <br>
*[Replace the above with a class diagram. You can look at how we have linked an image here as an example of how you can do it too.]*

<hr>

## Code Design and Decisions

This is an important section of your report and should include all technical decisions made. Well-written justifications will increase your marks for both the report as well as for the relevant parts (e.g., data structure). This includes, for example,

- Details about the parser (describe the formal grammar and language used)
- **Data Structure used:** 
  - Choose **Map** and **ArrayList** to store posts and friend lists to allow fast search of friends and posts associated with a particular user. **Map** and **ArrayList** are useful for operations that require sequential access, such as displaying a user's timeline or list of followers. 
  - Choose **B-Tree** for temporary data storage is efficient for searching, since the return type is a list of results. 
- **Design Patterns used:**
  - **Singleton Pattern**: Useful to initialize instances (User and Post) from firebase, avoid multiple initialized instances that may reduce the performance
  - **Observer Pattern**: Maintains a list of its observers, and notifies them of any state changes. In our application, the observers are **followers**, when user publishes their posts (not in privacy), all the other followers can receive **notifications** from this user.
  - **State Pattern**: Define a state to use based on user's authentication. They provide methods to solve the security and privacy problem, and connect the interface of one class to another interface so that front-end Android UI can work normally just using state mechanism (**authorize**), without modifying source code.

*Please give clear and concise descriptions for each subsections of this part. It would be better to list all the concrete items for each subsection and give no more than `5` concise, crucial reasons of your design.

<hr>

### Data Structures

*[What data structures did your team utilise? Where and why?]*

Here is a partial (short) example for the subsection `Data Structures`:*

*I used the following data structures in my project:*

1. ArrayList
   * *Objective: used for storing users and posts for **Data-Profile** feature.*
   * *Code Locations: defined in [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
   * *Reasons:*
      * Provides dynamic resizing, allowing easy addition and removal of elements as needed for managing user posts
      * ArrayList offers random access elements, enabling efficient search and display specific posts
      * For the list of users, we got a list as the receiving parameter from firebase, and uses in searching matched users or posts when handling authentication or get id methods
2. Map
   * *Objective: used for storing followers and posts for **Interact-Noti** feature.*
   * *Code Locations: defined in [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
   * * Map provides key-value pairs, where user data needs to be organized and accessed based on a specific key
     * In Hashmap, the time complexity of accessing specific key is O(1), for followers and posts,  we receive the map id (String) first, and search the corresponding follower and post in a high efficiency
     * Keys in map are unique, this feature prevents duplicate data when storing followers and posts by unique id
3. B-Tree
   * *Objective: used for storing temperary users and posts for **Search-Filter** feature.*
   * *Code Locations: defined in [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
   * * B-Tree provides efficient search and retrieval operations, we search results in database and stored temporarily in B-Tree structure
     * B-Tree maintains a balanced tree structure, for large data sets of users and posts, the balanced structure reduces the risk of performance degradation
     * B-Tree allows for range queries and partial key searches, making search filter working in a high efficiency

<hr>

### Design Patterns
*[What design patterns did your team utilise? Where and why?]*

1. *Singleton Pattern*
   * Objective: used for initialize Parser
   * Code Locations: 
      * [UserDB.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L218-223)
      * [FriendsData.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/FriendsData.java?ref_type=heads#L21-26)
      * [PostRepository.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/PostRepository.java?ref_type=heads#L31-36)
      * [Parser.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Parser.java?ref_type=heads#L16-21)
      * [FileDB.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/FileDB.java?ref_type=heads#L30-35)
   * Code usages:
      *  [UserDB.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L218-223) processed in [fireAuthCreate](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L753-777), [savePost](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L71-116), [notifyNewPostToFollowers](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L374-409), [isAuthorMyself](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/interaction/PostDetailActivity.java?ref_type=heads#L179-183), [SettingActivity.onCreate](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/setting/SettingActivity.java?ref_type=heads#L51)
      * [FriendsData.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/FriendsData.java?ref_type=heads#L21-26) processed in [getFollowList](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L495), [isAuthorFollowed](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/interaction/PostDetailActivity.java?ref_type=heads#L175-178)
      * [PostRepository.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/PostRepository.java?ref_type=heads#L31-36) processed in [getNewPostsByTime](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L148-168), [getPreviousPostsByTime](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L178-198), [SearchPostsActivity](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SearchPostsActivity.java?ref_type=heads#L44), [HomeFragment](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/home/ui/home/HomeFragment.java?ref_type=heads#L59), [PostRepository](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/interaction/PostDetailActivity.java?ref_type=heads#L44)
      * [Parser.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Parser.java?ref_type=heads#L16-21) processed in [performSearch](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SearchPostsActivity.java?ref_type=heads#L79-142)
      * [FileDB.getInstance()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/FileDB.java?ref_type=heads#L30-35) processed in [getImages](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/FileDB.java?ref_type=heads#L73-101), [newSaveImage](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/FileDB.java?ref_type=heads#L110-139)
   * *Reasons:*
     *  Ensure that only one instance of the Parser class is created, preventing multiple connections to Firebase and reducing redundant performance consumption.
      * Singleton Pattern helps maintain data consistency by ensuring all parts of the application use the same instance of the Database class.
     
   
2. *Observer Pattern*
   * Objective: used for observe the change of status, and notify corresponding users by using notify methods
   * Code Locations: 
     * [UserData.notifyObserver()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/UserData.java?ref_type=heads#L34-39)
     * [PostDB.notifyNewPostToFollowers()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L374-409)
     * [notifyAvatarUUIDChanged()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L197-201)
   * Code Usages:
     * [UserData.notifyObserver()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/UserData.java?ref_type=heads#L34-39) processed in [addNote](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/UserData.java?ref_type=heads#L85-93), [deleteNote](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/UserData.java?ref_type=heads#L95-103)
     * [PostDB.notifyNewPostToFollowers()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L374-409) processed in [savePost](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L71-116)
     * [notifyAvatarUUIDChanged()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L197-201) processed in [setOnAvatarUUIDChangedListener](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L191-194)
   * *Reasons:*
     * Observer pattern provides a solution for handling notifications of users.
     * Observer pattern allows publishers and observers interact normally, making app more flexible and maintainable.
3. *State Pattern*
   * *Objective: used for login and check the state of user.*
   * Code Locations: 
     * [AuthUtil](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Utils/AuthUtil.java?ref_type=heads)
     * [Database.signIn()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/Database.java?ref_type=heads#L60-62)
   * Code Usages:
     * [AuthUtil](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Utils/AuthUtil.java?ref_type=heads) processed in [SignUpActivity](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SignUpActivity.java?ref_type=heads), [UserDB.signIn()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L250-258)
     * [Database.signIn()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/Database.java?ref_type=heads#L60-62) processed in [UserDB.signIn()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L250-258), [signInWithEmail](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L688-702), [signInWithUsername](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L712-742)
     * [PostDB.notifyNewPostToFollowers()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L374-409) processed in [savePost](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads#L71-116)
     * [notifyAvatarUUIDChanged()](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L197-201) processed in [setOnAvatarUUIDChangedListener](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java?ref_type=heads#L191-194)
   * *Reasons:*
     * State Pattern allows the application to alter its login status based on user's authentication, this will simplify the manage and control access to different features and resources.
     * The State Pattern makes code more readable and maintainable, and eliminates the need for complex conditional statements to handle different authentication.
4. *Factory Pattern*
   * *Objective: used for login and check the state of user.*
   * Code Locations: 
      * [Notification](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/Notification.java?ref_type=heads)
      * [NotificationFactory](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/NotificationFactory.java?ref_type=heads)
   * Code Usages:
      * [createFollowNotification](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/NotificationFactory.java?ref_type=heads#L17-19)
      * [createFriendNotification](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Items/NotificationFactory.java?ref_type=heads#L29-31)
   * *Reasons:*
     * Factory Pattern centralizes the creation logic of 'Notification' objects, ensuring consistency and reducing the risk of errors.
     * The Factory Pattern allows easy modification and extension of the creation process without impacting the client code.
<hr>

### Parser

### <u>Grammar(s)</u>
*[How do you design the grammar? What are the advantages of your designs?]*
*If there are several grammars, list them all under this section and what they relate to.*

Parser class supports multiple grammars for different types of queries:

1. Mention Grammar:

   ```
   Copy code<mention> ::= '@' + <name>
   <name> ::= <char> + <char>
   <char> ::= any alphanumeric character | empty character
   ```

2. Title and User Grammar:

   ```
   Copy code<title> ::= ('title:' | 'user') + <name>
   <name> ::= <char> + <char>
   <char> ::= any alphanumeric character | empty character
   ```

3. Public Grammar:

   ```
   Copy code<public> ::= 'public:' + <bool>
   <bool> ::= 'true' | 'false'
   ```

This grammar is designed to be simple and flexible to handle username formats, the advantages of this grammar design are:

1. Simplicity: This grammar consists of at most three production rules, which is easy to understand and implement.
2. Flexibility: Name contains any character except whitespace, the grammar can handle a wide range of name formats, including alphanumeric characters and other special characters commonly used in names, to determine titles or public status.
3. Reusability: The `` <name> `` and ``<char>`` production rules are shared among the Mention and Title grammars, promoting reusability.

### <u>Tokenizers and Parsers</u>

*[Where do you use tokenisers and parsers? How are they built? What are the advantages of the designs?]*

Tokenizer:

1. The Tokenizer class is used to break down the input text into a sequence of tokens.
2. It recognizes five types of tokens: AT, NAME, TITLE, PUBLIC, and EOF.
3. The tokenization process is implemented using specific rules that translate the current character to the defined token types.
4. The advantages of this tokenizer design are:
   - Efficient: The tokenizer reads the input text only once, minimized the cost of time.
   - Understandable: The grammar is easy to understand and implement.

Parser:

1. The Parser class uses Tokenizer class to analyze the structure of the input text according to the defined grammars.
2. Each method for different parsing tasks, such as handling name, title, and public status.
3. It consumes tokens from the Tokenizer and applies the corresponding grammar rules to extract the desired information.
4. The advantages of this parser design are:
   - Each parsing method focuses on a specific task, make it easy to maintain the code framework.
   - The parser can handle different query types by following the appropriate grammar rules.
   - Detect error easily: Detect and handle invalid or incomplete input to avoid parsing problems.
   - Easy-to-call: Use Singleton Pattern to initialize Parser, and the parser relies on the Tokenizer for input processing, this will promote code reuse and separation of concerns.

<hr>

### Others

*[What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*

- Zehua Kong:
  - More validate algorithms can be added in the future version (strong password, phone number, verification code, etc.)
  - After the assessment, we will further develop this project, use advanced databases and implemented other features, including local storage, direct message, share posts, security validations, etc.
- Han Bao:
  - I helped my teammates to debug a lot.

  - We separated the App into two parts Frontend (UI) and [Backend](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/Database.java?ref_type=heads) (Data), I'm in charge of the backend, specifically, the part that save and retrieve data from the firebase. And my teammate, Zehua, helps me to deal with the data, applying appropriate algorithms to them.
  - I used reflection to manage the asynchronism, which in the beginning we didn't take into consideration as we drafted read and write from local storage. Once we switched to Firebase, all our get functions can only return nulls. It was a mess, especially with the UI and interaction done, we need an easy way to adjust our app to remote access storage. Therefore I did some research and found reflection is a possible way. I came up with an idea and implemented [MethodUtil](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/Utils/MethodUtil.java?ref_type=heads), to allow my teammates to only cut and paste their code into a different form to receive the data properly. ([Example](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SignUpActivity.java?ref_type=heads#L55)). In this way, they don't even have to know that reflection is. However, it wasn't that easy. Problems related to reflections kept popping out. We spent a lot of time and energy to debug them. But at the end of the day I'm glad that we did it and made it -- despite the challenges, we finished 15+ features! We figured out all the already-known bugs' causes, only if we have more time, we believe we can deliver it in a better form.
  - The backend I wrote consists of [Database.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/Database.java?ref_type=heads), an easy to read and implement Utility class for frontend to read and use. The underlying implements are separated into the [database](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/tree/main/gp/app/src/main/java/com/example/gp/data/database?ref_type=heads) package, and also uses Firestore's custom object functions using [models](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/tree/main/gp/app/src/main/java/com/example/gp/data/database/model?ref_type=heads) to swiftly change update the data structure on the go.

<hr>

## Implemented Features
*[What features have you implemented? where, how, and why?]* <br>
*List all features you have completed in their separate categories with their featureId. THe features must be one of the basic/custom features, or an approved feature from Voice Four Feature.*

### Basic Features
1. [LogIn]. User can login with their username and password (if account created)
   * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
   * Description of feature: ... <br>
   * Description of your implementation: ... <br>
2. [DataFiles]. Store user data, all info from users
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...
3. [LoadShowData]. Load and display data instances from firebase
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...
4. [DataStream]. Create Data by using methods in Database.java, and refresh data in firebase with the interaction of users
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...
5. [Search]. Search posts and users from app
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...

### Custom Features
Feature Category: Search-related features<br>
1. [Search-Filter] search with keywords, return a sorted and filtered list of results
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...



Feature Category: UI Design And Testing<br>

2. [UI-Layout] suitable layout adjustments in the UI components when size of screen changes

   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...

   

Greater Data Usage, Handling and Sophistication

3. [Data-Formats] read data from json and xml formats, as well as firebase
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...

4. [Data-Profile] Profile page to present users with avatar as media file (avatar is jpg format)
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...



Feature Category: Firebase Integration <br>
5. [FB-Auth] Implement authentication by firebase
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...

7. [FB-Persist] Firebase to persist all data used in app

   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...

   

Feature Category: User Interactivity

7. [Interact-Follow] Firebase to persist all data used in app

   - Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...

   - Link to the Firebase repo: ...

8. [Interact-Noti] Send notifications to all followers when user publish a new post

   - Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...

   - Link to the Firebase repo: ...



Feature Category: User Interactivity

9. [Privacy-Request] Send requests to view certain contents, in our case, non-public posts

   - Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...

   - Link to the Firebase repo: ...



Privacy

10. [Privacy-Visibility] We give privacy states: public, restricted, private

    - Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...

    - Link to the Firebase repo: ...



<hr>

### Surprise Features

- If implemented, explain how your solution addresses the task (any detail requirements will be released with the surprise feature specifications).

- State that "Surprised feature is not implemented" otherwise.

  

(i) Existing Code Smells (before May 1st, 2024):

1. Code Smell 1: Data items is not formalized, not use method invocation in back-end modules, not integrated in firebase
   - git commits: [2024-04-15 Add: data items, database management, BTree](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/commit/08525fff05def26958ed3f36ed1391156fe4789a)
   - files: [UserDB.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/UserDB.java)



2. Code Smell 2: User data is not formalized, some fields are invalid

   - git commits: [2024-04-19 Added userData for Login.](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/commit/e1f9ad2decdbe7b6a7ef717bd12d3b4acdaeb47b)

   - files: [PostDB.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads), [FileDB.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/FileDB.java?ref_type=heads)

     

3. Code Smell 3: Signup page have problem to create account page

   - git commits: [2024-04-20 Added signup layout.](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/commit/89fec6216be93056623ecdffa8028a800ecbf223)
   - files: [SignUpActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SignUpActivity.java?ref_type=heads) , [LoginActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/LoginActivity.java)
   

   
4. Code Smell 4: The app will crash after clicking on create account button

   - git commits: [2024-04-20 Added onClick to create account button](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/commit/0e98484e2aef746f5179994975cb9e63a1220af2)
   - files: [SignUpActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SignUpActivity.java)



(ii) Corrections Made (after May 1st, 2024):

1. Correction for Code Smell : Refactored UserDB and PostDB
   - Description of the correction: Refactored UserDB and PostDB, use the reflection to invoke java method instead of calling directly
   - Relevant git commits, files, and line numbers: 
     
     - git commit: [2024-05-05 Refactored UserDB and PostDB as standalone classes](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/commit/af1385d7dfcd8573bbcd5221ad7c95601caf4448)
     
     - files: [PostDB.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/PostDB.java?ref_type=heads), [FileDB.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/data/database/FileDB.java?ref_type=heads)
     
       
2. Correction for Code Smell : Fixed crash and redirect bugs in Create Account page
   - Description of the correction: Fixed crash and redirect bugs in Create Account page, write a new logic to validate input
   - Relevant git commits, files, and line numbers:
     - git commit: [2024-05-05 Debug: fixed signup problem, rewrite asynchronous validate methods](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/commit/0192163c2750bf03df34cd50b4b43445627e28ed)
     
     - files: [SignUpActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/SignUpActivity.java), [LoginActivity.java](https://gitlab.cecs.anu.edu.au/u7693498/gp-24s1/-/blob/main/gp/app/src/main/java/com/example/gp/LoginActivity.java)
     
       

(iii) Detailed Description:

In the period developing code framework before May 1st, 2024, the codebase suffered from several architectural and implementation problems. The data layer lacked proper structure,  data items are not formalized, the backend modules lacking integration with Firebase and proper use of method invocations. User data also had structural issues.

From a user-facing perspective, the signup flow was problematic, with the create account page not functioning properly. Attempting to create a new account would lead to the application crashing.

The correction includes significant refactoring efforts were undertaken after May 1st, 2024. The data layer was improved by rewriting the UserDB and PostDB components as self-contained classes. Java's reflection capabilities are now available.

We also solved crashes in account creation, and redirect bugs in the create account page. The input validation logic was redesigned to utilize asynchronous validation methods.



## Summary of Known Errors and Bugs

*[Where are the known errors and bugs? What consequences might they lead to?]*
*List all the known errors and bugs here. If we find bugs/errors that your team does not know of, it shows that your testing is not thorough.*



- Home Page
  - Bug
  
    - Loading home pages may costs several seconds, if page contains pages with large pictures
    - Toasts keep popping up when clicking previous page and next page multiple times
  
  - Error
  
    - In a very rare situation, when user return to home page from other page, the page may crash because of the loading overflow
  
    
  
- Send Post Page
  
  - Bug
  
    - In the "New Post" page, the Line "Who Can See This Post" is incomplete, it will be our future feature after the assessment.
    - After sending post, it may take times (several seconds) to load all the new posts, if the home page contains too many pictures
    - In some cases, Post cannot save the authorid if user send their **first** post
  
  - Error
  
    - In some situation, some picture from local storage cannot be updated, or may trigger crash, because we set the max size of picture to 5 MB
    - In a very rare situation, the 
  
    
  
- Settings Page
  
  - Bug
  
    - The button "Change nick name", "Change email", and "Change password" are incomplete, they will be our future features after the assessment.
    - In the "Settings-Friend Request" page, After clicking "Accept"or "Reject", the request is not removed and still in this page 
  
  - Error
  
    - In a very rare situation, after user clicking "Notifications-Change Avatar" or "Notifications-Friend Request", the page may crash, the possible reason may be the new user's avatar and friend request list is not initialized. 
    - If a user have published too many posts, it will take time to load the "Settings-Post Visibility" Page, and may trigger the shutdown of firebase, because the daily read times in firebase is limited.
  
    
  
- Notifications Page
  
  - Bug
    - Top bar "Follow Updates" is incomplete, it should represent the new follow notifications, it will be our new feature
    - After handling Friend requests in "Settings-Friend Requests" page, the notification in top bar "Notifications-Friend Requests" page is not removed
    - No response when user click the corresponding "Friend Requests"
  - Error
    - In a very rare situation, after other user send follow requests to you, the page may crash
    - 
  
- Firebase

  - Bug
    - Firebase cannot response quickly since we set the database located in United States, far away from Australia
    - The daily read times in firebase is limited, firebase may shutdown 
  - Error
    - In a test development, we use batch() to add multiple posts into firebase, the fire

- Friends Page

  - Bug
    - The top search bar will cover the whole page
    - After searching the result, user cannot return to the previous friend list page, unless user click other pages, and click "Friends" again
    - The page will redirect to "Friend" page **Only** when click the avatar in the friend list, no response if click title or name
    - "Direct Message" in "Friend" page is incomplete, it will be our future feature after assessment
  - Error
    - In a very rare situation, if friends list contains too many elements, the friends page may crash
    - In some situation, if search bar contains some characters, the friends page may crash

- Login Page

  - Bug
    - If user deliberately input the wrong password several times, the Toast on the bottom will keep on popping up
  - Error
    - In a very rare situation, after user logout and redirect to Login Page, the app may crash
  
- Create Account Page

  - Bug
    - If user deliberately input the existing email, the Toast on the bottom will keep on popping up
    - Cannot scroll down the keyboard, user can only click "next" to enter the "repeat password"
  - Error
    - In a very rare situation, After user create account, the page will crash, it is because the home page is no response



 <hr>


## Testing Summary

*[What features have you tested? What is your testing coverage?]*
*Please provide some screenshots of your testing summary, showing the achieved testing coverage. Feel free to provide further details on your tests.*

*Here is an example:*

1. Tests for User Management
   - Code: UserTest.java, covering the User class
   - Number of test cases: 3
   - Code coverage: The tests cover the User class constructors and setter/getter methods
   - Types of tests created and descriptions:
  - testUser(): Tests the setter and getter methods of the User class
     - testUserConstructorWithFullParams(): Tests the User class constructor with all parameters
     - testUserConstructorWithRequiredParams(): Tests the User class constructor with required parameters only
2. Tests for Post Management
   - Code: PostTest.java, covering the Post class
   - Number of test cases: 3
   - Code coverage: The tests cover the Post class constructors and setter/getter methods
   - Types of tests created and descriptions:
     - testPost(): Tests the setter and getter methods of the Post class
     - testPostConstructorWithContent(): Tests the Post class constructor with content, title, and isPublic parameters
     - testPostSetters(): Tests all the setter methods of the Post class
3. Tests for Friend Management
   - Code: FriendTest.java, covering the Friend and FriendRequest classes
   - Number of test cases: 4
   - Code coverage: The tests cover the Friend and FriendRequest class constructors and setter/getter methods
   - Types of tests created and descriptions:
     - testFriendRequest(): Tests the setter and getter methods of the FriendRequest class
     - testFriendConstructor(): Tests the Friend class constructor
     - testFriendSetters(): Tests the setter methods of the Friend class
     - testFriendGetters(): Tests the getter methods of the Friend class
4. Tests for Notification Management
   - Code: NotificationTest.java, covering the Notification class
   - Number of test cases: 2
   - Code coverage: The tests cover the Notification class constructor and setter/getter methods
   - Types of tests created and descriptions:
     - testNotificationConstructor(): Tests the Notification class constructor
     - testNotificationSetters(): Tests the setter methods of the Notification class
5. Tests for Utility Classes
   - Code: UtilTest.java, covering the AuthUtil and TimeUtil classes
   - Number of test cases: 7
   - Code coverage: The tests cover various methods of the AuthUtil and TimeUtil classes
   - Types of tests created and descriptions:
     - testAuthUtil(): Tests the isValidEmail() and isValidUsername() methods of the AuthUtil class
     - testAuthUtil_EmailVariations(): Tests different variations of email formats using the isValidEmail() method
     - testAuthUtil_UsernameVariations(): Tests different variations of username formats using the isValidUsername() method
     - testAuthUtil_EmailSubDomain(): Tests email formats with subdomains using the isValidEmail() method
     - testAuthUtil_isEmail(): Tests the isEmail() method of the AuthUtil class
     - testTimeUtil_getTimestamp(): Tests the getTimestamp() method of the TimeUtil class
6. Tests for Tokenizer
   - Code: TokenizerTest.java, covering the Tokenizer class
   - Number of test cases: 10
   - Code coverage: The tests cover various scenarios and edge cases for the nextToken() method of the Tokenizer class
   - Types of tests created and descriptions:
     - Tests for different token types
     - Tests for handling whitespace
     - Tests for handling empty input
     - Tests for handling multiple tokens in a single input string
7. Tests for Parser
   - Code: ParserTest.java, covering the Parser class
   - Number of test cases: 10
   - Code coverage: The tests cover various methods of the Parser class
   - Types of tests created and descriptions:
     - Tests for the isMentioningSomeone() method with different inputs
     - Tests for the parseUserName() method with different inputs
     - Tests for the parseTitle() method with different inputs
     - Tests for the parsePublic() method with different inputs

...

<br> <hr>


## Team Management

### Meetings Records
* Link to the minutes of your meetings like above. There must be at least 4 team meetings.
  (each commited within 2 days after the meeting)
* Your meetings should also have a reasonable date spanning across Week 6 to 11.*


- *[Team Meeting 1](meeting1.md)*
- *[Team Meeting 2](meeting2.md)*
- *[Team Meeting 3](meeting3.md)*
- *[Team Meeting 4](meeting4.md)*
- ... (Add any descriptions if needed) ...

<hr>

### Conflict Resolution Protocol
*[Write a well defined protocol your team can use to handle conflicts. That is, if your group has problems, what is the procedure for reaching consensus or solving a problem?
(If you choose to make this an external document, link to it here)]*

This shall include an agreed procedure for situations including (but not limited to):
- if a member fails to meet the initial plan and/or deadlines, we will decrease his contribution, generally this not happens in our team
- if your group has issues, we will have on-site meeting, to solve issues as soon as possible
- if a member gets sick, we will assign tasks to him and continue to proceed, generally this not happens in our team
- If we have merge conflict, we will send direct message to each other, or have on-site meeting, to solve conflicts as soon as possible
