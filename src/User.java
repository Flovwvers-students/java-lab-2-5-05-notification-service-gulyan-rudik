import java.util.Set;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private final String phone;
    private final Set<String> allowedChannels;

    public User(String id, String name, String email, String phone, Set<String> allowedChannels) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.allowedChannels = allowedChannels;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isNotificationEnabled(String channelId) {
        return allowedChannels != null && allowedChannels.contains(channelId);
    }
}