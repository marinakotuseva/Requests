package ru.ocrv.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class IncorrectText extends Throwable {
    public IncorrectText(){
        super("Передан пустой текст");
    }
}
