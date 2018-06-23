package cz.filipklimes.edu.scheduling.exception;

public class InfeasibleScheduleException extends RuntimeException
{

    public InfeasibleScheduleException()
    {
        super("Infeasible schedule.");
    }

}
