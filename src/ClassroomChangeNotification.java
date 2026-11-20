public class ClassroomChangeNotification extends Notification {
    private final int roomNumber;

    public ClassroomChangeNotification(String title, int roomNumber) {
        super(title);
        this.roomNumber = roomNumber;
    }

    @Override
    protected String formatBody() {
        return "Пара перенесена в аудиторию " + roomNumber;
    }
}