package in.dhaivathlal.dsshmanager.models;


public class Machine {

    private Integer id;
    private String name;
    private String ip;
    private String password;
    private String username;
    private Integer groupId;

    public Machine() {

    }
    public Machine(String name, String ip, String username, String password, Integer groupId) {
        this.name = name;
        this.ip = ip;
        this.password = password;
        this.username = username;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
