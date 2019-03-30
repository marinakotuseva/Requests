package ru.ocrv.entity;

import org.junit.Test;

public class RequestTest {

    @Test
    public void testAddComment(){
        Request r = new Request("Req1");
        r.addComment(new Comment("Comment1"));
        r.addComment(new Comment("Comment2"));
        System.out.println(r);
    }

}
