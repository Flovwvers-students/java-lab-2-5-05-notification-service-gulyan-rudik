public class SmsChannel implements NotificationChannel {
    @Override
    public String id() {
        return "SMS";
    }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            throw new NotificationDeliveryException("ошибка доставки из-за отсутствующего номера");
        }
        System.out.println(user.getName() + ", SMS - доставлено. (" + message + ")");
    }
}