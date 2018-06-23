package cz.filipklimes.edu.datastructure.exception;

public class EmptyHeapException extends RuntimeException
{

    public EmptyHeapException()
    {
        super("No elements present in the heap.");
    }

}
