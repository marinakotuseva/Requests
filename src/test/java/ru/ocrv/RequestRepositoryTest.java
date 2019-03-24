package ru.ocrv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ocrv.entity.Request;
import ru.ocrv.repo.RequestRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RequestRepositoryTest {
    @Autowired
    private RequestRepository repository;

    @Test
    public void canSaveRequest() {
        Request r = new Request("Some descr");
        repository.save(r);


        List<Request> requests = repository.findAll();

        assertEquals(1, requests.size());
        assertEquals("Some descr", requests.get(0).getDescription());
    }
}
