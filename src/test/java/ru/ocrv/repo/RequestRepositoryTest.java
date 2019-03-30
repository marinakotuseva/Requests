package ru.ocrv.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ocrv.entity.Request;
import ru.ocrv.exc.RecordNotFoundException;
import ru.ocrv.repo.RequestRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RequestRepositoryTest {

    @Autowired
    private RequestRepository repository;

    @Test
    public void testCanReadRequests() {
        String existingDescr = "Описание 1";
        List<Request> requests = repository.findAll();
        assertThat(requests.size(), equalTo(3));
        assertThat(requests.get(0).getDescription(), equalTo(existingDescr));
    }

    @Test
    public void testCanSaveRequest() {
        String newDescr = "Some descr";
        Request r = new Request(newDescr);
        repository.save(r);

        List<Request> requests = repository.findAll();

        assertThat(requests.size(), equalTo(4));
        assertThat(requests.get(3).getDescription(), equalTo(newDescr));
    }


    @Test
    public void testCanFindByNum() {
        long num = 1;
        List<Request> requests = repository.findByNum(num);

        assertThat(requests.size(), equalTo(1));
        assertThat(requests.get(0).getDescription(), equalTo("Описание 1"));

    }
}
