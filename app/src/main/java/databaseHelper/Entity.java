package databaseHelper;

public class Entity {
    public Entity(String name, String password, String sex, String type, String uri) {
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.type = type;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String name;
    public String password;
    public String sex;
    public String type;
    public String uri;

}
