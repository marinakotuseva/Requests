package ru.ocrv.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class IncorrectDescription extends Throwable {
    public IncorrectDescription(){
        super("Передано пустое описание");
    }
}
