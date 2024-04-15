package data.item;

import java.io.Serializable;

/**
 * @desc user data
 * @author Zehua Kong
 */
public class UserItem implements Serializable {
    private String UserId;
    private String channelId;
    private String nick;
    private int headIcon;
    private int group;

    public UserItem(String UserId, String channelId, String nick, int headIcon,
                    int group) {
        this.UserId = UserId;
        this.channelId = channelId;
        this.nick = nick;
        this.headIcon = headIcon;
        this.group = group;
    }

    public UserItem() {

    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(int headIcon) {
        this.headIcon = headIcon;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "User [UserId=" + UserId + ", channelId=" + channelId
                + ", nick=" + nick + ", headIcon=" + headIcon + ", group="
                + group + "]";
    }

}
