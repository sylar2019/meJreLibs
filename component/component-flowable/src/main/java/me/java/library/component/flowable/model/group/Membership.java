package me.java.library.component.flowable.model.group;


/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public class Membership {

    private String userId;
    private String url;
    private String groupId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
