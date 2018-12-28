package app.entity.personcenter;

//用户相关兴趣标签
public class UserTags {
    private String id;
    private String tagName;
    private int parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "UserTags{" +
                "id='" + id +
                ", tagName='" + tagName + '\'' +
                ", parentId='" + parentId +
                '}';
    }
}
