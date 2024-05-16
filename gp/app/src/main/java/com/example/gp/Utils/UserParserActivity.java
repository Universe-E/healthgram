package com.example.gp.Utils;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.Items.Friend;
import com.example.gp.Items.Notification;
import com.example.gp.Items.Post;
import com.example.gp.Items.User;
import com.example.gp.Items.UserParser;
import com.example.gp.R;
import com.google.firebase.Timestamp;

import org.json.JSONException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Parser user in Android UI pages
 * @author Zehua Kong
 */
public class UserParserActivity extends AppCompatActivity {

    private static Timestamp currentDate = Timestamp.now();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_parser);

        User user = getCurrentUser();

        try {
            //Parse current user to json and xml file
            UserParser.parseToJSON(getApplicationContext(), user, "user.json");
            UserParser.parseToXML(getApplicationContext(), user, "user.xml");

            Toast.makeText(this, "User parsed successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException | IllegalAccessException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occurred while parsing user", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Create a user to parse
     * @return current user
     */
    @NonNull
    private static User getCurrentUser() {
        User user = new User("u1", "John", "john@example.com");
        user.setDescription("Sample description");
        user.setAvatar(1);
        HashMap<String, Friend> friends = new HashMap<>();
        Friend f1 = new Friend("f1","Alice",1);
        Friend f2 = new Friend("f2","Bob",2);
        friends.put("f1",f1);
        friends.put("f2",f2);
        user.setFriendMap(friends);
        Map<String, Post> posts = new HashMap<>();
        Post p1 = new Post("p1","a1",true);
        Post p2 = new Post("p2","a2",false);
        posts.put("p1",p1);
        posts.put("p2",p2);
        Map<String, Notification> notis = new HashMap<>();
        Notification n1 = new Notification("Vacation","Go to italy",currentDate, Notification.NotificationType.FOLLOW,"123");
        Notification n2 = new Notification("Vacation","Go to italy",currentDate, Notification.NotificationType.FOLLOW,"123");
        notis.put("n1",n1);
        notis.put("n2",n2);
        user.setNotificationMap(notis);
        return user;
    }
}