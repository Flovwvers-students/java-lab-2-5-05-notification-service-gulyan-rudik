public class EmailChannel implements NotificationChannel {
    @Override
    public String id() {
        return "Email";
    }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new NotificationDeliveryException("ошибка доставки из-за отсутствующего email");
        }
        System.out.println(user.getName() + ", Email - доставлено. (" + message + ")");
    }
}