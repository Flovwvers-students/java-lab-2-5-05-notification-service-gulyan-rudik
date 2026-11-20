public class DeadlineReminderNotification extends Notification {
    private final String taskName;
    private final String dueDate;

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