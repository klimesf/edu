package cz.filipklimes.edu.scheduling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HornsAlgorithmTest
{

    @Test
    void testScheduleSimpleInstance()
    {
        int[] processingTimes = new int[]{3, 2, 3, 4};
        int[] releaseTimes = new int[]{0, 4, 2, 0};
        int[] dueDates = new int[]{13, 8, 11, 16};

        HornsAlgorithm.Schedule schedule = HornsAlgorithm.schedule(4, releaseTimes, processingTimes, dueDates);
        assertEquals(-5 - 2 - 4 - 4, schedule.getLateness());

        HornsAlgorithm.TaskSchedule[] schedules = schedule.getSchedules();

        // Task 1
        assertEquals(2, schedules[0].getIntervals().size());
        assertEquals(0, schedules[0].getIntervals().get(0).getStart());
        assertEquals(2, schedules[0].getIntervals().get(0).getEnd());
        assertEquals(7, schedules[0].getIntervals().get(1).getStart());
        assertEquals(8, schedules[0].getIntervals().get(1).getEnd());

        // Task 2
        assertEquals(1, schedules[1].getIntervals().size());
        assertEquals(4, schedules[1].getIntervals().get(0).getStart());
        assertEquals(6, schedules[1].getIntervals().get(0).getEnd());

        // Task 3
        assertEquals(2, schedules[2].getIntervals().size());
        assertEquals(2, schedules[2].getIntervals().get(0).getStart());
        assertEquals(4, schedules[2].getIntervals().get(0).getEnd());
        assertEquals(6, schedules[2].getIntervals().get(1).getStart());
        assertEquals(7, schedules[2].getIntervals().get(1).getEnd());

        // Task 4
        assertEquals(1, schedules[3].getIntervals().size());
        assertEquals(8, schedules[3].getIntervals().get(0).getStart());
        assertEquals(12, schedules[3].getIntervals().get(0).getEnd());

        System.out.println(schedule.visualize());
    }

}
