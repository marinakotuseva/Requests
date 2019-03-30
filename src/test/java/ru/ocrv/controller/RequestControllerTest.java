package ru.ocrv.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ocrv.entity.Comment;
import ru.ocrv.entity.Request;
import ru.ocrv.entity.Status;

import java.lang.reflect.Type;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.lang.reflect.Type;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RequestControllerTest {

    private String site = "http://localhost:8080";

    @Autowired
    private TestRestTemplate testRestTemplate;

    // Read
    @Test
    public void testReadRequests(){

        ResponseEntity<String> response = testRestTemplate.getForEntity("/request", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        Type listType = new TypeToken<ArrayList<Request>>() {
        }.getType();
        List<Request> requestList = new Gson().fromJson(response.getBody(), listType);

        assertThat(requestList, hasSize(3));

        assertThat(requestList.get(0).getDescription(), equalTo("Описание 1"));
        assertThat(requestList.get(1).getDescription(), equalTo("Описание 2"));
    }

    // Create new
    @Test
    public void testCreateRequest(){

        String newDescr = "Описание новой заявки";
        Status newStatus = Status.NEW;

        // Check amount before creation
        ResponseEntity<String> response = testRestTemplate.getForEntity("/request", String.class);
        Type listType = new TypeToken<ArrayList<Request>>() {
        }.getType();
        List<Request> requestList = new Gson().fromJson(response.getBody(), listType);
        int beforeAmount = requestList.size();

        // Create new
        Request request = new Request(newDescr);
        ResponseEntity<String> responseAfterCreation = testRestTemplate.postForEntity("/request", request, String.class);
        assertThat(responseAfterCreation.getStatusCode(), equalTo(HttpStatus.OK));

        Type type = new TypeToken<Request>() {
        }.getType();
        Request createdRequest = new Gson().fromJson(responseAfterCreation.getBody(), type);
        assertThat(createdRequest.getDescription(), equalTo(newDescr));
        assertThat(createdRequest.getStatus(), equalTo(newStatus));

        // Check amount after
        ResponseEntity<String> responseAmountAfterCreation = testRestTemplate.getForEntity("/request", String.class);
        Type listType2 = new TypeToken<ArrayList<Request>>() {
        }.getType();
        List<Request> requestListAfterCreation = new Gson().fromJson(responseAmountAfterCreation.getBody(), listType2);
        assertThat(requestListAfterCreation, hasSize(beforeAmount+1));

    }
    @Test
    public void testCantCreateEmptyRequest() {

        String newDescr = "";
        Status newStatus = Status.NEW;

        // Create new
        Request request = new Request(newDescr);
        ResponseEntity<String> responseAfterCreation = testRestTemplate.postForEntity("/request", request, String.class);
        assertThat(responseAfterCreation.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

    }


    // Set status
    @Test
    public void testSetStatus() {

        Status newStatus = Status.DONE;
        long num = 1;
        String url = site + "/request/"+num;

        // Check that request exists
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        // Set status
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Status> newStatusEntity = new HttpEntity<>(newStatus, headers);

        ResponseEntity<String> responsePut = testRestTemplate.exchange(url, HttpMethod.PUT, newStatusEntity, String.class);

        System.out.println(responsePut);
        assertThat(responsePut.getStatusCode(), equalTo(HttpStatus.OK));

        // Check new status
        Type type = new TypeToken<Request>() {
        }.getType();
        Request changedRequest = new Gson().fromJson(responsePut.getBody(), type);

        assertThat(changedRequest.getStatus(), equalTo(newStatus));

    }

    // Add comment
    @Test
    public void testAddComment() {

        String newComment = "Комментарий 1";
        long num = 1;
        String url = site + "/request/"+num;

        // Check that request exists and get request
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        Type type = new TypeToken<Request>() {
        }.getType();
        Request foundRequest = new Gson().fromJson(response.getBody(), type);
        int commentAmount = foundRequest.getComments().size();

        // Add comment
        ResponseEntity<String> responseAfterAddingComment = testRestTemplate.postForEntity("/request/"+num, newComment, String.class);
        assertThat(responseAfterAddingComment.getStatusCode(), equalTo(HttpStatus.OK));
        Type type2 = new TypeToken<Request>() {
        }.getType();
        Request requestAfterAddingComment = new Gson().fromJson(responseAfterAddingComment.getBody(), type2);

        int commentAmountAfterAdding = requestAfterAddingComment.getComments().size();

        assertThat(commentAmountAfterAdding, equalTo(commentAmount+1));

    }
    @Test
    public void testCantAddEmptyComment() {

        Comment newComment = new Comment("");
        long num = 1;
        String url = site + "/request/"+num;


        // Add comment
        ResponseEntity<String> responseAfterAddindComment = testRestTemplate.postForEntity("/request/"+num, newComment, String.class);
        assertThat(responseAfterAddindComment.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

    }


}
