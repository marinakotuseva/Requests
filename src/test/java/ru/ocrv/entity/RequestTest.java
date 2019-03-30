package ru.ocrv.entity;

import org.junit.Test;

public class RequestTest {
    @Test
    public void testCreateRequest(){
        Request request = new Request("Some descr");
        System.out.println(request);
    }
}
