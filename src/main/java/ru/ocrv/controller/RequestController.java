package ru.ocrv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ocrv.entity.Comment;
import ru.ocrv.entity.Request;
import ru.ocrv.entity.Status;
import ru.ocrv.exc.IncorrectDescription;
import ru.ocrv.exc.IncorrectText;
import ru.ocrv.repo.RequestRepository;

import java.util.List;

@RestController
public class RequestController {

    @Autowired
    private RequestRepository repository;

    // Считать все
    @GetMapping(path="/request")
    List<Request> findAll() {
        return repository.findAll();
    }

    // Создать новую
    @PostMapping("/request")
    Request create(@RequestBody Request request) throws IncorrectDescription {
        if (request.getDescription() == "" || request.getDescription() == null){
            throw new IncorrectDescription();
        }
        return repository.save(request);
    }

    // Найти заявку по номеру
    @GetMapping("/request/{num}")
    Request findByNum(@PathVariable Long num) {
        Request request = repository.findByNum(num).get(0);
        return request;
    }

    // Поменять статус
    @PutMapping("/request/{num}")
    Request setStatus(@RequestBody Status status, @PathVariable Long num) {
        Request request = repository.findByNum(num).get(0);
        request.setStatus(status);
        repository.save(request);
        return request;
    }

    // Добавить комментарий
    @PostMapping("/request/{num}")
    Request addComment(@RequestBody String comment, @PathVariable Long num) throws IncorrectText {
        if (comment == "" || comment == null){
            throw new IncorrectText();
        }
        Request request = repository.findByNum(num).get(0);
        request.addComment(new Comment(comment));
        return request;
    }

    // Delete
    @DeleteMapping("/request/{num}")
    public void delete(@PathVariable Long num) {
        Request existingRequest = repository.findByNum(num).get(0);;

        repository.deleteByNum(num);
    }
}
