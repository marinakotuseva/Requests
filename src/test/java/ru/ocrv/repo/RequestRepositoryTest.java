package ru.ocrv.repo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ocrv.entity.Request;
import ru.ocrv.entity.Status;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@DataJpaTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RequestRepositoryTest {

    @Autowired
    private RequestRepository repository;

    @Test
    public void testReadRequests() {
        String existingDescr = "Описание 1";
        List<Request> requests = repository.findAll();
        assertThat(requests.size(), equalTo(3));
        assertThat(requests.get(0).getDescription(), equalTo(existingDescr));
    }

    @Test
    public void testSaveRequest() {
        // Get amount
        List<Request> initialRequests = repository.findAll();
        int initialSize = initialRequests.size();

        // Create new
        String newDescr = "Описание 4";
        Status newStatus = Status.NEW;

        Request r = new Request(newDescr);
        repository.save(r);

        List<Request> requests = repository.findAll();

        assertThat(requests.size(), equalTo(initialSize+1));
        assertThat(requests.get(initialSize).getDescription(), equalTo(newDescr));
        assertThat(requests.get(initialSize).getStatus(), equalTo(newStatus));
    }

    @Test
    public void testFindByNum() {
        long id = 1;
        List<Request> requests = repository.findById(id);

        assertThat(requests.size(), equalTo(1));
        assertThat(requests.get(0).getDescription(), equalTo("Описание 1"));

    }

    @Test
    public void testDeleteRequest() {

        List<Request> requests = repository.findAll();
        int existsRequests = requests.size();

        repository.delete(repository.findAll().get(existsRequests-1));

        List<Request> requestsAfterDeletion = repository.findAll();
        assertThat(requestsAfterDeletion.size(), equalTo(existsRequests-1));

    }
}
