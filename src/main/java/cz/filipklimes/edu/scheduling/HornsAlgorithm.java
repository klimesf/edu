package cz.filipklimes.edu.scheduling;

import sun.jvm.hotspot.utilities.Interval;

import java.util.*;

/**
 * Implementation of Horn's algorithm for one-processor scheduling with release
 * times, due dates and preemption, while minimizing lateness (1|r,d|Lmax).
 */
public class HornsAlgorithm
{

    public static Schedule schedule(final int numberOfTasks, final int[] releaseTimes, final int[] processingTimes, final int[] dueDates)
    {
        TaskSchedule[] schedules = new TaskSchedule[numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            schedules[i] = new TaskSchedule();
        }
        int[] remainingTime = Arrays.copyOf(processingTimes, processingTimes.length);

        // Build queue
        Queue<TaskProperty> releaseTimeQueue = new PriorityQueue<>(numberOfTasks);
        Queue<TaskProperty> dueDateQueue = new PriorityQueue<>(numberOfTasks);
        for (int i = 0; i < numberOfTasks; i++) {
            releaseTimeQueue.add(new TaskProperty(i, releaseTimes[i]));
        }

        int time = 0;
        int lateness = 0;
        while (!dueDateQueue.isEmpty() || !releaseTimeQueue.isEmpty()) {
            if (dueDateQueue.isEmpty() && !releaseTimeQueue.isEmpty()) {
                time = releaseTimeQueue.peek().property;
            }

            // Mark all available tasks
            while (!releaseTimeQueue.isEmpty() && releaseTimeQueue.peek().property <= time) {
                int id = releaseTimeQueue.poll().id;
                dueDateQueue.add(new TaskProperty(id, dueDates[id]));
            }

            // Select available task with earliest due date
            TaskProperty task = dueDateQueue.poll();

            // Schedule the task until next task becomes available or until it's finished
            int nextReleaseDate = Integer.MAX_VALUE;
            for (TaskProperty otherTask : releaseTimeQueue) {
                int releaseTime = otherTask.property;
                if (releaseTime > time && releaseTime < nextReleaseDate) {
                    nextReleaseDate = releaseTime;
                }
            }

            int stepSize = remainingTime[task.id];
            if (time + stepSize > nextReleaseDate) {
                stepSize = nextReleaseDate - time;
                dueDateQueue.add(task); // Requeue
            } else {
                lateness += (time + stepSize) - dueDates[task.id];
            }
            remainingTime[task.id] -= stepSize;
            schedules[task.id].addInterval(Interval.of(time, time + stepSize));
            time += stepSize;
        }

        return new Schedule(
            numberOfTasks,
            releaseTimes,
            processingTimes,
            dueDates,
            lateness,
            schedules
        );
    }

    public static final class Schedule
    {

        private final int numberOfTasks;
        private final int[] releaseTimes;
        private final int[] processingTimes;
        private final int[] dueDates;
        private final int lateness;
        private final TaskSchedule[] schedules;

        public Schedule(final int numberOfTasks, final int[] releaseTimes, final int[] processingTimes, final int[] dueDates, final int lateness, final TaskSchedule[] schedules)
        {
            this.numberOfTasks = numberOfTasks;
            this.releaseTimes = releaseTimes;
            this.processingTimes = processingTimes;
            this.dueDates = dueDates;
            this.lateness = lateness;
            this.schedules = schedules;
        }

        public int getNumberOfTasks()
        {
            return numberOfTasks;
        }

        public int[] getReleaseTimes()
        {
            return releaseTimes;
        }

        public int[] getProcessingTimes()
        {
            return processingTimes;
        }

        public int[] getDueDates()
        {
            return dueDates;
        }

        public int getLateness()
        {
            return lateness;
        }

        public TaskSchedule[] getSchedules()
        {
            return schedules;
        }

        public String visualize()
        {
            StringBuilder sb = new StringBuilder();

            int maxTime = 0;
            for (int i = 0; i < numberOfTasks; i++) {
                if (maxTime < dueDates[i]) {
                    maxTime = dueDates[i];
                }
                for (Interval interval : schedules[i].getIntervals()) {
                    if (maxTime < interval.getEnd()) {
                        maxTime = interval.getEnd();
                    }
                }
            }

            for (int i = 0; i < numberOfTasks; i++) {
                sb.append(String.format("T%d: ", i + 1));
                for (int time = 0; time < maxTime; time++) {
                    if (time < releaseTimes[i]) {
                        sb.append(" ");
                        continue;
                    }

                    boolean done = false;
                    for (Interval interval : schedules[i].getIntervals()) {
                        if (time >= interval.getStart() && time < interval.getEnd()) {
                            sb.append("▤");
                            done = true;
                            break;
                        }
                    }
                    if (done) {
                        continue;
                    }

                    if (time > dueDates[i]) {
                        sb.append(" ");
                        continue;
                    }

                    sb.append("-");
                }
                sb.append("\n");
            }

            return sb.toString();
        }

    }

    public static final class TaskSchedule
    {

        private final List<Interval> intervals = new ArrayList<>();

        public void addInterval(final Interval interval)
        {
            intervals.add(interval);
        }

        public List<Interval> getIntervals()
        {
            return Collections.unmodifiableList(intervals);
        }

    }

    public static final class Interval
    {

        private int start, end;

        private Interval(final int start, final int end)
        {
            this.start = start;
            this.end = end;
        }

        public int getStart()
        {
            return start;
        }

        public int getEnd()
        {
            return end;
        }

        public static Interval of(final int start, final int end)
        {
            return new Interval(start, end);
        }

    }

    private static final class TaskProperty implements Comparable<TaskProperty>
    {

        private final int id;
        private final int property;

        private TaskProperty(final int id, final int property)
        {
            this.id = id;
            this.property = property;
        }

        @Override
        public int compareTo(final TaskProperty o)
        {
            return Integer.compare(property, o.property);
        }

    }

}
