package com.example.gp.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import data.item.MessageItem;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * @desc Recent Message database
 * @author Zehua Kong
 */
public class MessageDB {
    public static final String MSG_DBNAME = "message.db";
    private SQLiteDatabase db;

    public MessageDB(Context context) {
        db = context.openOrCreateDatabase(MSG_DBNAME, Context.MODE_PRIVATE,
                null);
    }

    public void saveMsg(String id, MessageItem entity) {
        db.execSQL("CREATE table IF NOT EXISTS _"
                + id
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,messagetype INTEGER,name TEXT, img TEXT,date TEXT,isCome TEXT,message TEXT,isNew TEXT,voiceTime INTEGER)");
        int isCome = 0;
        if (entity.isComMeg()) {// 如果是收到的消息，保存在数据库的值为1
            isCome = 1;
        }
        db.execSQL(
                "insert into _"
                        + id
                        + " (messagetype,name,img,date,isCome,message,isNew,voiceTime) values(?,?,?,?,?,?,?,?)",
                new Object[] { entity.getMsgType(), entity.getName(),
                        entity.getHeadImg(), entity.getDate(), isCome,
                        entity.getMessage(), entity.getIsNew(),
                        entity.getVoiceTime() });
    }

    public List<MessageItem> getMsg(String id, int pager) {
        List<MessageItem> list = new LinkedList<>();
        int num = 10 * (pager + 1);
        db.execSQL("CREATE table IF NOT EXISTS _"
                + id
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,messagetype INTEGER,name TEXT, img TEXT,date TEXT,isCome TEXT,message TEXT,isNew TEXT,voiceTime INTEGER)");
        Cursor c = db.rawQuery("SELECT * from _" + id
                + " ORDER BY _id DESC LIMIT " + num, null);
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex("name"));
            int img = c.getInt(c.getColumnIndex("img"));
            long date = c.getLong(c.getColumnIndex("date"));
            int isCome = c.getInt(c.getColumnIndex("isCome"));
            String message = c.getString(c.getColumnIndex("message"));
            int isNew = c.getInt(c.getColumnIndex("isNew"));
            int msgType = c.getInt(c.getColumnIndex("messagetype"));
            int voiceTime = c.getInt(c.getColumnIndex("voiceTime"));
            boolean isComMsg = isCome == 1;
            MessageItem entity = new MessageItem(msgType, name, date, message,
                    img, isComMsg, isNew, voiceTime);
            list.add(entity);
        }
        c.close();
        Collections.reverse(list);//reverse list order
        return list;
    }

    public int getNewCount(String id) {
        db.execSQL("CREATE table IF NOT EXISTS _"
                + id
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, img TEXT,date TEXT,isCome TEXT,message TEXT,isNew TEXT,voiceTime INTEGER)");
        Cursor c = db.rawQuery("SELECT isNew from _" + id + " where isNew=1",
                null);
        int count = c.getCount();
        // L.i("new message num = " + count);
        c.close();
        return count;
    }

    public void clearNewCount(String id) {
        db.execSQL("CREATE table IF NOT EXISTS _"
                + id
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, img TEXT,date TEXT,isCome TEXT,message TEXT,isNew TEXT,voiceTime INTEGER)");
        db.execSQL("update _" + id + " set isNew=0 where isNew=1");
    }

    public void close() {
        if (db != null)
            db.close();
    }
}
