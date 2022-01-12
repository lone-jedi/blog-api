package com.yarkin.blogapi.web;

import java.time.LocalDateTime;
import java.util.List;
import com.yarkin.blogapi.entity.Comment;
import com.yarkin.blogapi.entity.Post;
import com.yarkin.blogapi.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(value = CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    private List<Comment> commentDb = List.of(
            new Comment(1, "Nice!",  LocalDateTime.of(
                    2020, 10, 21, 22, 0, 22, 32), 1),
            new Comment(2, "Cool!",  LocalDateTime.of(
                    2020, 10, 21, 22, 0, 22, 32), 1),
            new Comment(3, "jhfjkahkf!",  LocalDateTime.of(
                    2020, 10, 21, 22, 0, 22, 32), 2),
            new Comment(4, "Link...",  LocalDateTime.of(
                    2020, 10, 21, 22, 0, 22, 32), 3));

    @Test
    public void getAllCommentsByPostId() throws Exception {
        Mockito.when(commentService.getAllByPostId(1)).thenReturn(commentDb.subList(0, 2));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/posts/1/comments")
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        String expected = """
                [
                    {
                        id:1,
                        text:"Nice!",
                        creationDate:"2020-10-21T22:00:22.000000032",
                        postId:1
                    },
                    {
                        id:2,
                        text:"Cool!",
                        creationDate:"2020-10-21T22:00:22.000000032",
                        postId:1
                    }
                ]
                """;

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void addNewCommentByPostId() throws Exception {
        Comment expected = commentDb.get(0);

        String expectedJson = """
                    {
                        id:1,
                        text:"Nice!",
                        creationDate:"2020-10-21T22:00:22.000000032",
                        postId:1
                    }""";

        String addJson = """
                {
                    "text":"Nice!"
                }
                """;

        Mockito.when(commentService.add(1, Comment.builder().text("Nice!").build()))
                .thenReturn(expected);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/posts/1/comments")
                .accept(MediaType.APPLICATION_JSON)
                .content(addJson)
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        JSONAssert.assertEquals(expectedJson, response.getContentAsString(), false);
    }

    @Test
    public void getCommentByPostId() throws Exception {
        Comment expected = commentDb.get(0);

        String expectedJson = """
                    {
                        id:1,
                        text:"Nice!",
                        creationDate:"2020-10-21T22:00:22.000000032",
                        postId:1
                    }""";

        Mockito.when(commentService.getByPostId(1, 1))
                .thenReturn(expected);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/posts/1/comments/1")
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(expectedJson, response.getContentAsString(), false);
    }
}
