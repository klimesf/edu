package cz.filipklimes.edu.scheduling;

/**
 * This class represents a Task for scheduling problems.
 * Task can be calculated by at most one processor at given point in time,
 * one processor cannot calculate more than one task at given point in time.
 */
public class Task
{

    /**
     * Time required to finish the task.
     */
    private final int processingTime;

    /**
     * Earliest point in time when the task is available for scheduling.
     */
    private final int releaseTime;

    /**
     * Latest point in time when the task should be calculated.
     */
    private final int dueDate;

    /**
     * Latest point in time when the task can be calculated.
     */
    private final int deadline;

    public Task(final int processingTime, final int releaseTime, final int dueDate, final int deadline)
    {
        if ((releaseTime + processingTime) > dueDate || (releaseTime + processingTime) > deadline) {
            throw new RuntimeException(String.format("Infeasible task, release time must be before due date and deadline"));
        }

        this.processingTime = processingTime;
        this.releaseTime = releaseTime;
        this.dueDate = dueDate;
        this.deadline = deadline;
    }

    public Task(final int processingTime, final int releaseTime, final int dueDate)
    {
        this(processingTime, releaseTime, dueDate, -1);
    }

    public int getProcessingTime()
    {
        return processingTime;
    }

    public int getReleaseTime()
    {
        return releaseTime;
    }

    public int getDueDate()
    {
        return dueDate;
    }

    public int getDeadline()
    {
        return deadline;
    }

}
