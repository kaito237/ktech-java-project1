import java.time.LocalDate;

public class Todo {
    private String title;
    private LocalDate until;
    private boolean done;

    public Todo(String title, LocalDate until) {
        this.title = title;
        this.until = until;
        this.done = false;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getUntil() {
        return until;
    }

    public boolean isDone() {
        return done;
    }

    public void markAsDone() {
        this.done = true;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUntil(LocalDate until) {
        this.until = until;
    }

    @Override
    public String toString() {
//        String status = (done) ? " (Done)" : "";
        return title + " - " + until ;
    }
}

