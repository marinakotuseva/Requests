package ru.ocrv.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RequestControllerTest {

    private String site = "http://localhost:8080";

    @Autowired
    private TestRestTemplate testRestTemplate;


    // Add comment
    @Test
    public void testAddComment() {

        String newComment = "Комментарий 1";
        long id = 1;
        String url = site + "/request/" + id;

        // Check that request exists and get request
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        Type type = new TypeToken<Request>() {
        }.getType();
        Request foundRequest = new Gson().fromJson(response.getBody(), type);
        int commentAmount = foundRequest.getComments().size();

        // Add comment
        ResponseEntity<String> responseAfterAddingComment = testRestTemplate.postForEntity("/request/" + id, newComment, String.class);
        assertThat(responseAfterAddingComment.getStatusCode(), equalTo(HttpStatus.OK));
        Type type2 = new TypeToken<Request>() {
        }.getType();
        Request requestAfterAddingComment = new Gson().fromJson(responseAfterAddingComment.getBody(), type2);

        int commentAmountAfterAdding = requestAfterAddingComment.getComments().size();

        assertThat(commentAmountAfterAdding, equalTo(commentAmount + 1));

    }

    @Test
    public void testCantCreateEmptyRequest() {

        String newDescr = "";

        // Create new
        Request request = new Request(newDescr);
        ResponseEntity<String> responseAfterCreation = testRestTemplate.postForEntity("/request", request, String.class);
        assertThat(responseAfterCreation.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

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
    public void testDeleteRequest() {

        long id = 4;
        int size;

        // Check that country exists
        ResponseEntity<String> response = testRestTemplate.getForEntity("/request/"+ id, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        Type listType = new TypeToken<Request>() {
        }.getType();
        System.out.println(response.getBody());
        Request request = new Gson().fromJson(response.getBody(), listType);
        assertThat(request.getId(), equalTo(id));

        // Check amount
        ResponseEntity<String> responseAmount = testRestTemplate.getForEntity("/request", String.class);
        assertThat(responseAmount.getStatusCode(), equalTo(HttpStatus.OK));
        Type listType2 = new TypeToken<ArrayList<Request>>() {
        }.getType();
        List<Request> requestList = new Gson().fromJson(responseAmount.getBody(), listType2);
        size = requestList.size();


        // Delete country
        ResponseEntity<Request> responseDelete = testRestTemplate.exchange("/request/"+ id,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Request.class);

        assertThat(responseDelete.getStatusCode(), equalTo(HttpStatus.OK));

        // Check new amount
        ResponseEntity<String> responseAfterDeletion = testRestTemplate.getForEntity("/request", String.class);

        assertThat(responseAfterDeletion.getStatusCode(), equalTo(HttpStatus.OK));

        Type listType3 = new TypeToken<ArrayList<Request>>() {
        }.getType();
        List<Request> requestListAfterDeletion = new Gson().fromJson(responseAfterDeletion.getBody(), listType3);

        assertThat(requestListAfterDeletion, hasSize(size-1));

    }

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



    // Set status
    @Test
    public void testSetStatus() {

        Status newStatus = Status.DONE;
        long id = 1;
        String url = site + "/request/"+id;

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




}
