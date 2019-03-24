package ru.ocrv.exc;

public class NotFoundException extends RuntimeException{
    public NotFoundException(long id){
        super("Не найдена запись с ID " + id);
    }
}
