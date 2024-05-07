# Example for Data Directory Structure

```plain text
data/
├─ local/
│ ├─ entities/
│ │ ├─ AuthorEntity
│ ├─ dao/
│ ├─ NiADatabase
├─ network/
│ ├─ NiANetwork
│ ├─ models/
│ │ ├─ NetworkAuthor
├─ model/
│ ├─ Author
├─ repository/
```

# More About

Please read [Data Storage](https://developer.android.com/training/data-storage).
Please read [Android Doc](https://developer.android.com/topic/architecture/data-layer/offline-first) on how to structure the data layer. (In this Doc, it is using flow (a kotlin feature), we should use liveData (or if it will make us lose mark, we can implement our own observer base class))

# Demands

sendRequest(User user) send the request to certain user
getRequests() get all requests which are sent to current user


# Update on Backend API Data Return Format

## Changes

When returning data, the backend API will return an object containing two parameters:

1. `isSuccess` (boolean): Indicates whether the cloud request was successful or not.
2. `data` (generic object): The required object data or an error message string.

The specific format is as follows:

- If the request is successful, return `{ isSuccess: true, data: required object }`
- If the request fails, return `{ isSuccess: false, data: error message string }`

## Purpose

This update aims to improve the flexibility and user-friendliness of error handling, allowing the front-end to better capture and handle error messages while maintaining the original data retrieval method and ensuring good compatibility.

## Adjustments to Front-end UpdateUI Method

To accommodate this update, the front-end needs to check the value of `isSuccess` when calling the UpdateUI method and decide whether to display the data or the error message.

If a parameter is already available in the class, it can be directly accessed through the getter in UpdateUI, eliminating the need to pass the parameter repeatedly, further streamlining the interface.

### Example of UpdateUI Method

```java
private void updateUI(boolean isSuccess, Object arg) {
    if (isSuccess) {
        T theObjectNeeded = (T) arg;
        // Use theObject0Needed for UI update
        ...
    } else {
        if (arg != null) {
            String errorMsg = (String) arg;
            // Handle error message
            ... 
        }
    }
}
```

### Example of using callback to update UI

```java
    public void createUserAccount(View view) {
        // Initialize views
        EditText usernameEditText = findViewById(R.id.user_name_text);
        EditText emailEditText = findViewById(R.id.email_text);
        EditText pwdEditText = findViewById(R.id.pwd_text);
        EditText reapeatPwdEditText = findViewById(R.id.repeat_pwd_text);

        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = pwdEditText.getText().toString().trim();
        String repeat_password = reapeatPwdEditText.getText().toString().trim();

        if (validateForm(username, email, password, repeat_password)) {
            // Create account
            Database.UserDB.signUp(username, email, password, this, "updateUI");
        }

    }

    public void updateUI(Boolean isSuccess, Object arg) {
        if (isSuccess) {
            Intent intent = new Intent(this, Fragment_home.class);
            startActivity(intent);
        } else {
            if (arg != null) {
                String errorMsg = (String) arg;
                ToastUtil.showLong(this, "Create account failed: " + errorMsg);
            } else {
                ToastUtil.showLong(this, "Create account failed");
            }
        }
    }
```
