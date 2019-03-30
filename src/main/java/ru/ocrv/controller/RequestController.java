package ru.ocrv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ocrv.entity.Comment;
import ru.ocrv.entity.Request;
import ru.ocrv.entity.Status;
import ru.ocrv.exc.RecordNotFoundException;
import ru.ocrv.repo.RequestRepository;

import java.util.List;

//Сервис должен поддерживать следующие операции:
//
//        1. Создание новой заявки, при создании передается только поле
//
//        "Описание", операция возвращает вновь созданную сущность с присвоенным
//
//        номером в статусе "Новая".
//
//        2. Обновление поля "Статус".
//
//        3. Добавление комментария к заявке.
//
//        4. Удаление заявки.

@RestController
public class RequestController {

    @Autowired
    private RequestRepository repository;


    @GetMapping(path="/request",
                consumes={MediaType.APPLICATION_JSON_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE})
    List<Request> findAll() {
        return repository.findAll();
    }

    @PostMapping("/request")
    Request create(@RequestBody Request request) {
        return repository.save(request);
    }

    @PutMapping("/request/{id}")
    Request setStatus(@RequestBody Status status, @PathVariable Long id) {
        Request request = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        request.setStatus(status);
        repository.save(request);
        return request;
    }

    @PostMapping("/request/{id}")
    Request addComment(@RequestBody Comment comment, @PathVariable Long id) {
        Request request = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        request.addComment(comment);
        return request;
    }
}
