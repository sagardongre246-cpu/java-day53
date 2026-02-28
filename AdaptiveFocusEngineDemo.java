import java.util.*;

class TaskPerformance {
    private String taskName;
    private int difficulty; // 1 to 10
    private long completionTime; // in seconds

    public TaskPerformance(String taskName, int difficulty, long completionTime) {
        this.taskName = taskName;
        this.difficulty = difficulty;
        this.completionTime = completionTime;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public String getTaskName() {
        return taskName;
    }
}

class AdaptiveFocusEngine {
    private List<TaskPerformance> history = new ArrayList<>();

    public void addTask(TaskPerformance task) {
        history.add(task);
    }

    // Moving average of completion time
    public double calculateMovingAverage() {
        if (history.isEmpty()) return 0;

        double total = 0;
        for (TaskPerformance t : history) {
            total += t.getCompletionTime();
        }
        return total / history.size();
    }

    // Performance momentum score
    public double calculateMomentum() {
        if (history.size() < 2) return 0;

        TaskPerformance last = history.get(history.size() - 1);
        TaskPerformance prev = history.get(history.size() - 2);

        return prev.getCompletionTime() - last.getCompletionTime();
    }

    // Burnout risk detection
    public boolean isBurnoutRisk() {
        double avg = calculateMovingAverage();
        long lastTime = history.get(history.size() - 1).getCompletionTime();

        return lastTime > avg * 1.5;
    }

    // Suggest next difficulty dynamically
    public int suggestNextDifficulty() {
        if (history.isEmpty()) return 5;

        TaskPerformance last = history.get(history.size() - 1);
        int lastDifficulty = last.getDifficulty();

        double momentum = calculateMomentum();

        if (isBurnoutRisk()) {
            return Math.max(1, lastDifficulty - 2);
        }

        if (momentum > 10) {
            return Math.min(10, lastDifficulty + 1);
        } else if (momentum < -10) {
            return Math.max(1, lastDifficulty - 1);
        } else {
            return lastDifficulty;
        }
    }
}

public class AdaptiveFocusEngineDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdaptiveFocusEngine engine = new AdaptiveFocusEngine();

        System.out.println("=== Adaptive Focus Engine ===");
        System.out.println("Track your productivity intelligently.\n");

        for (int i = 0; i < 5; i++) {
            System.out.print("Enter Task Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Difficulty (1-10): ");
            int difficulty = scanner.nextInt();

            System.out.print("Enter Completion Time (seconds): ");
            long time = scanner.nextLong();
            scanner.nextLine();

            engine.addTask(new TaskPerformance(name, difficulty, time));

            System.out.println("Current Moving Average: " + engine.calculateMovingAverage());
            System.out.println("Momentum Score: " + engine.calculateMomentum());
            System.out.println("Burnout Risk: " + engine.isBurnoutRisk());
            System.out.println("Suggested Next Difficulty: " + engine.suggestNextDifficulty());
            System.out.println("-----------------------------------");
        }

        scanner.close();
    }
}