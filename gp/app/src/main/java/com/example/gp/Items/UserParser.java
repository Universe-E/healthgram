package com.example.gp.Items;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * User parser class
 * parse user to json or xml files, store in local
 * @author Zehua Kong
 * @since 2024-05-07
 */
public class UserParser {
    public static void parseToJSON(User user, String fileName) throws IOException, IllegalAccessException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> userMap = new HashMap<>();

        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            userMap.put(field.getName(), field.get(user));
        }

        objectMapper.writeValue(new File(fileName), userMap);
    }

    public static void parseToXML(User user, String fileName) throws IOException, IllegalAccessException {
        XmlMapper xmlMapper = new XmlMapper();
        Map<String, Object> userMap = new HashMap<>();

        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            userMap.put(field.getName(), field.get(user));
        }

        xmlMapper.writeValue(new File(fileName), userMap);
    }
}