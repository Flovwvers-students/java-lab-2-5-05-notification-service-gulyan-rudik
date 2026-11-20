import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String annaId = "U001";
        String annaName = "Анна";
        String annaEmail = "anna@example.com";
        String annaPhone = null;
        Set<String> annaChannels = new HashSet<>(Arrays.asList("Email", "SMS"));
        User anna = new User(annaId, annaName, annaEmail, annaPhone, annaChannels);

        String borisId = "U002";
        String borisName = "Борис";
        String borisEmail = "boris@example.com";
        String borisPhone = "+79991234567";
        Set<String> borisChannels = new HashSet<>(Arrays.asList("SMS", "Push"));
        User boris = new User(borisId, borisName, borisEmail, borisPhone, borisChannels);

        List<User> users = Arrays.asList(anna, boris);

        NotificationChannel emailChannel = new EmailChannel();
        NotificationChannel smsChannel = new SmsChannel();
        NotificationChannel pushChannel = new PushChannel();
        NotificationService service = new NotificationService();

        System.out.println("1.");
        String scheduleTitle = "Изменение расписания";
        int newRoomNumber = 304;
        Notification changeNotification =
                new ClassroomChangeNotification(scheduleTitle, newRoomNumber);
        List<NotificationChannel> run1Channels = Arrays.asList(emailChannel, smsChannel);
        service.broadcast(users, changeNotification, run1Channels);

        System.out.println("\n2.");
        String deadlineTitle = "Дедлайн";
        String labTaskName = "Лабораторная № 5";
        String labDueDate = "13 ноября";
        Notification deadlineNotification =
                new DeadlineReminderNotification(deadlineTitle, labTaskName, labDueDate);
        List<NotificationChannel> run2Channels =
                Arrays.asList(emailChannel, smsChannel, pushChannel);
        service.broadcast(users, deadlineNotification, run2Channels);
    }
}