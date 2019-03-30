package ru.ocrv.entity;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RequestTest {

    @Test
    public void testAddComments(){
        Request request = new Request("Some descr");
        request.addComment(new Comment("Comment 1"));
        request.addComment(new Comment("Comment 2"));


        assertThat(request.getComments().size(), equalTo(2));
        assertThat(request.getComments().toString(), equalTo("[Comment{id=1, text='Comment 1', request=null}, Comment{id=2, text='Comment 2', request=null}]"));
    }
}
