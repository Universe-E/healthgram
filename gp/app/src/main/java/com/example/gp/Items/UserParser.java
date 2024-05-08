package com.example.gp.Items;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;

public class UserParser {
    public static void parseToJSON(Context context, User user, String fileName) throws JSONException, IllegalAccessException, IOException {
        JSONObject userJson = new JSONObject();

        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            userJson.put(field.getName(), field.get(user));
        }

        File externalDir = context.getExternalFilesDir(null);
        File file = new File(externalDir, fileName);
        Log.d("UserParser",file.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(userJson.toString().getBytes());
        fos.close();
    }

    public static void parseToXML(Context context, User user, String fileName) throws IllegalAccessException, IOException {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.startTag("", "user");

        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            xmlSerializer.startTag("", field.getName());
            xmlSerializer.text(String.valueOf(field.get(user)));
            xmlSerializer.endTag("", field.getName());
        }

        xmlSerializer.endTag("", "user");
        xmlSerializer.endDocument();

        File externalDir = context.getExternalFilesDir(null);
        File file = new File(externalDir, fileName);
        Log.d("UserParser",file.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(writer.toString().getBytes());
        fos.close();
    }
}