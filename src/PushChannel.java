public class PushChannel implements NotificationChannel {
    @Override
    public String id() {
        return "Push";
    }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getId() == null || user.getId().isEmpty()) {
            throw new NotificationDeliveryException("ошибка доставки из-за отсутствующего ID пользователя");
        }
        System.out.println(user.getName() + ", Push - доставлено. (" + message + ")");
    }
}