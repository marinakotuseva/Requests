package ru.ocrv.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ocrv.entity.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findById(long id);
    List<Request> deleteById(long id);

}
