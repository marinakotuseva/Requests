package ru.ocrv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ocrv.entity.Comment;
import ru.ocrv.entity.Request;
import ru.ocrv.entity.Status;
import ru.ocrv.exc.IncorrectDescription;
import ru.ocrv.exc.RecordNotFoundException;
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
    @GetMapping("/request/{id}")
    Request findByNum(@PathVariable Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Поменять статус
    @PutMapping("/request/{id}")
    Request setStatus(@RequestBody Status status, @PathVariable Long id) {
        Request request = repository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        request.setStatus(status);
        repository.save(request);
        return request;
    }

    // Добавить комментарий
    @PostMapping("/request/{id}")
    Request addComment(@RequestBody String comment, @PathVariable Long id) {
        Request request = repository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        request.addComment(new Comment(comment));
        return request;
    }

    // Delete
    @DeleteMapping("/request/{id}")
    public void delete(@PathVariable Long id) {
        Request request = repository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        repository.deleteById(id);
    }
}
