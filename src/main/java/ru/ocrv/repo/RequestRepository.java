package ru.ocrv.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ocrv.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
