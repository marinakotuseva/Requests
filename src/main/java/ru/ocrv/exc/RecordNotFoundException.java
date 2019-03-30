package ru.ocrv.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(long id){
        super("Не найдена запись с номером " + id);
    }
}
