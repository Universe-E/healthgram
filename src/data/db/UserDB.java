package data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import data.item.UserItem;

import java.util.LinkedList;
import java.util.List;
/**
 * @desc User database
 * @author Zehua Kong
 */
public class UserDB {
	private UserDBHelper helper;

	public UserDB(Context context) {
		helper = new UserDBHelper(context);
	}

	public UserItem selectInfo(String userId) {
		UserItem u = new UserItem();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from user where userId=?",
				new String[] { userId + "" });
		if (c.moveToFirst()) {
			u.setHeadIcon(c.getInt(c.getColumnIndex("img")));
			u.setNick(c.getString(c.getColumnIndex("nick")));
			u.setChannelId(c.getString(c.getColumnIndex("channelId")));
			u.setGroup(c.getInt(c.getColumnIndex("_group")));
		} else {
			return null;
		}
		return u;
	}

	public void addUser(List<UserItem> list) {
		SQLiteDatabase db = helper.getWritableDatabase();
		for (UserItem u : list) {
			db.execSQL(
					"insert into user (userId,nick,img,channelId,_group) values(?,?,?,?,?)",
					new Object[] { u.getUserId(), u.getNick(), u.getHeadIcon(),
							u.getChannelId(), u.getGroup() });
		}
		db.close();
	}

	public void addUser(UserItem u) {
		if (selectInfo(u.getUserId()) != null) {
			update(u);
			return;
		}
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"insert into user (userId,nick,img,channelId,_group) values(?,?,?,?,?)",
				new Object[] { u.getUserId(), u.getNick(), u.getHeadIcon(),
						u.getChannelId(), u.getGroup() });
		db.close();

	}

	public UserItem getUser(String userId) {
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor c = db.rawQuery("select * from user where userId=?",
				new String[] { userId });
		UserItem u = new UserItem();
		if (c.moveToNext()) {
			u.setUserId(c.getString(c.getColumnIndex("userId")));
			u.setNick(c.getString(c.getColumnIndex("nick")));
			u.setHeadIcon(c.getInt(c.getColumnIndex("img")));
			u.setChannelId(c.getString(c.getColumnIndex("channelId")));
			u.setGroup(c.getInt(c.getColumnIndex("_group")));
		}
		return u;
	}

	public void updateUser(List<UserItem> list) {
		if (list.size() > 0) {
			delete();
			addUser(list);
		}
	}

	public List<UserItem> getUser() {
		SQLiteDatabase db = helper.getWritableDatabase();
		List<UserItem> list = new LinkedList<>();
		Cursor c = db.rawQuery("select * from user", null);
		while (c.moveToNext()) {
			UserItem u = new UserItem();
			u.setUserId(c.getString(c.getColumnIndex("userId")));
			u.setNick(c.getString(c.getColumnIndex("nick")));
			u.setHeadIcon(c.getInt(c.getColumnIndex("img")));
			u.setChannelId(c.getString(c.getColumnIndex("channelId")));
			u.setGroup(c.getInt(c.getColumnIndex("_group")));
			list.add(u);
		}
		c.close();
		db.close();
		return list;
	}

	public void update(UserItem u) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"update user set nick=?,img=?,_group=? where userId=?",
				new Object[] { u.getNick(), u.getHeadIcon(), u.getGroup(),
						u.getUserId() });
		db.close();
	}

	public UserItem getLastUser() {
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor c = db.rawQuery("select * from user", null);
		UserItem u = new UserItem();
		while (c.moveToLast()) {
			u.setUserId(c.getString(c.getColumnIndex("userId")));
			u.setNick(c.getString(c.getColumnIndex("nick")));
			u.setHeadIcon(c.getInt(c.getColumnIndex("img")));
			u.setChannelId(c.getString(c.getColumnIndex("channelId")));
			u.setGroup(c.getInt(c.getColumnIndex("_group")));
		}
		c.close();
		db.close();
		return u;
	}

	public void delUser(UserItem u) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from user where userId=?",
				new Object[] { u.getUserId() });
		db.close();
	}

	public void delete() {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from user");
		db.close();
	}
}
