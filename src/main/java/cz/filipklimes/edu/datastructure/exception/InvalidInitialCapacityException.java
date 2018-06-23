package cz.filipklimes.edu.datastructure.exception;

public class InvalidInitialCapacityException extends RuntimeException
{

    public InvalidInitialCapacityException()
    {
        super("Initial capacity of the heap must be at least 1.");
    }

}
