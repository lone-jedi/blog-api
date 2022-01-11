package com.yarkin.blogapi.web;

import java.time.LocalDateTime;
import java.util.List;

import com.yarkin.blogapi.entity.Post;
import com.yarkin.blogapi.service.PostService;
import com.yarkin.blogapi.web.post.DefaultPostController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(value = DefaultPostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private List<Post> postsDb = List.of(
        new Post(1, "Test", "Testing api...", LocalDateTime.of(
                2020, 10, 21, 22, 0, 22, 32)),
        new Post(2, "News", "Some news here...", LocalDateTime.of(
                2022, 1, 3, 13, 23, 4, 39)),
        new Post(3, "Cars", "Some content about cars...", LocalDateTime.of(
                2012, 12, 13, 23, 20, 4, 39))
    );

    @Test
    public void getAllPosts() throws Exception {
        Mockito.when(postService.getAll()).thenReturn(postsDb);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/posts")
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        String expected = """
                [
                    {
                        id:1,
                        title:"Test",
                        content:"Testing api...",
                        creationDate:"2020-10-21T22:00:22.000000032"
                    },
                    {
                        id:2,
                        title:"News",
                        content:"Some news here...",
                        creationDate:"2022-01-03T13:23:04.000000039"
                    },
                    {
                        id:3,
                        title:"Cars",
                        content:"Some content about cars...",
                        creationDate:"2012-12-13T23:20:04.000000039"
                    }
                ]
                """;

        assertEquals(200, response.getStatus());

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }
}
