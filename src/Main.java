import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class NotificationDeliveryException extends Exception {
    public NotificationDeliveryException(String message) {
        super(message);
    }
}

// Уведомления
abstract class Notification {
    private String title;

    public Notification(String title) {
        this.title = title;
    }

    public String format() {
        return title + ": " + formatBody();
    }

    protected abstract String formatBody();
}

class ClassroomChangeNotification extends Notification {
    private int roomNumber;

    public ClassroomChangeNotification(String title, int roomNumber) {
        super(title);
        this.roomNumber = roomNumber;
    }

    @Override
    protected String formatBody() {
        return "Пара перенесена в аудиторию " + roomNumber;
    }
}

class DeadlineReminderNotification extends Notification {
    private String taskName;
    private String dueDate;

    public DeadlineReminderNotification(String title, String taskName, String dueDate) {
        super(title);
        this.taskName = taskName;
        this.dueDate = dueDate;
    }

    @Override
    protected String formatBody() {
        return "Срок сдачи работы „" + taskName + "\" - " + dueDate;
    }
}

// Пользователь
class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Set<String> allowedChannels;

    public User(String id, String name, String email, String phone, Set<String> allowedChannels) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.allowedChannels = allowedChannels;
    }

    public boolean isNotificationEnabled(String channelId) {
        return allowedChannels != null && allowedChannels.contains(channelId);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}

// Каналы доставки
interface NotificationChannel {
    String id();
    void send(User user, String message) throws NotificationDeliveryException;
}

class EmailChannel implements NotificationChannel {
    @Override
    public String id() { return "Email"; }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new NotificationDeliveryException("ошибка доставки из-за отсутствующего email");
        }
        System.out.println(user.getName() + ", Email - доставлено. (" + message + ")");
    }
}

class SmsChannel implements NotificationChannel {
    @Override
    public String id() { return "SMS"; }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            throw new NotificationDeliveryException("ошибка доставки из-за отсутствующего номера");
        }
        System.out.println(user.getName() + ", SMS - доставлено. (" + message + ")");
    }
}

class PushChannel implements NotificationChannel {
    @Override
    public String id() { return "Push"; }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getId() == null || user.getId().isEmpty()) {
            throw new NotificationDeliveryException("ошибка доставки из-за отсутствующего ID пользователя");
        }
        System.out.println(user.getName() + ", Push - доставлено. (" + message + ")");
    }
}

// Сервис рассылки
class NotificationService {
    public void broadcast(List<User> users, Notification notification, List<NotificationChannel> channels) {
        // Текст формируется один раз
        String messageText = notification.format();

        for (User user : users) {
            for (NotificationChannel channel : channels) {
                if (!user.isNotificationEnabled(channel.id())) {
                    System.out.println(user.getName() + ", " + channel.id() + " - пропущено.");
                } else {
                    try {
                        channel.send(user, messageText);
                    } catch (NotificationDeliveryException e) {
                        System.out.println(user.getName() + ", " + channel.id() + " - " + e.getMessage() + ".");
                    }
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {

        User anna = new User("U001", "Анна", "anna@example.com", null, new HashSet<>(Arrays.asList("Email", "SMS")));
        User boris = new User("U002", "Борис", "boris@example.com", "+79991234567", new HashSet<>(Arrays.asList("SMS", "Push")));
        List<User> users = Arrays.asList(anna, boris);


        NotificationChannel emailChannel = new EmailChannel();
        NotificationChannel smsChannel = new SmsChannel();
        NotificationChannel pushChannel = new PushChannel();

        NotificationService service = new NotificationService();

        System.out.println("Первый запуск");
        Notification changeNotification = new ClassroomChangeNotification("Изменение расписания", 304);
        List<NotificationChannel> run1Channels = Arrays.asList(emailChannel, smsChannel);
        service.broadcast(users, changeNotification, run1Channels);

        System.out.println("\nВторой запуск");
        Notification deadlineNotification = new DeadlineReminderNotification("Дедлайн", "Лабораторная № 5", "13 ноября");
        List<NotificationChannel> run2Channels = Arrays.asList(emailChannel, smsChannel, pushChannel);
        service.broadcast(users, deadlineNotification, run2Channels);
    }
}