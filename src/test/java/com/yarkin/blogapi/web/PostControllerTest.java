package com.yarkin.blogapi.web;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

import com.yarkin.blogapi.entity.Post;
import com.yarkin.blogapi.service.PostService;
import com.yarkin.blogapi.web.post.DefaultPostController;
import org.json.JSONException;
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

@WebMvcTest(value = DefaultPostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private List<Post> postsDb = List.of(
        new Post(1, "Test", "Testing api...", LocalDateTime.of(
                2020, 10, 21, 22, 0, 22, 32), false),
        new Post(2, "News", "Some news here...", LocalDateTime.of(
                2022, 1, 3, 13, 23, 4, 39), false),
        new Post(3, "Cars", "Some content about cars...", LocalDateTime.of(
                2012, 12, 13, 23, 20, 4, 39), true),
        new Post(10, "News", "Some news here222...", LocalDateTime.of(
                2022, 1, 3, 13, 23, 4, 39), true)
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
                        creationDate:"2020-10-21T22:00:22.000000032",
                        star:false
                    },
                    {
                        id:2,
                        title:"News",
                        content:"Some news here...",
                        creationDate:"2022-01-03T13:23:04.000000039",
                        star:false
                    },
                    {
                        id:3,
                        title:"Cars",
                        content:"Some content about cars...",
                        creationDate:"2012-12-13T23:20:04.000000039",
                        star:true
                    },
                    {
                        id:10,
                        title:"News",
                        content:"Some news here222...",
                        creationDate:"2022-01-03T13:23:04.000000039",
                        star:true
                    }
                ]
                """;

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void addNewPost() throws Exception {
        Post expected = new Post(4, "New Post", "Content...", LocalDateTime.of(
                2012, 12, 13, 23, 20, 4, 39), false);

        String expectedJson = """
                    {
                        id:4,
                        title:"New Post",
                        content:"Content...",
                        creationDate:"2012-12-13T23:20:04.000000039",
                        star:false
                    }""";

        String addJson = """
                {
                    "title":"New Post",
                    "content":"Content..."
                }
                """;

        Mockito.when(postService.add(Post.builder().title("New Post").content("Content...").build()))
                .thenReturn(expected);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/posts")
                .accept(MediaType.APPLICATION_JSON)
                .content(addJson)
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        JSONAssert.assertEquals(expectedJson, response.getContentAsString(), false);
    }

    @Test
    public void editPostById() throws Exception {
        Post expected = new Post(2, "Post Title", "Content...", LocalDateTime.of(
                2012, 12, 13, 23, 20, 4, 39), false);

        String expectedJson = """
                    {
                        id:2,
                        title:"Post Title",
                        content:"Content...",
                        creationDate:"2012-12-13T23:20:04.000000039",
                        star:false
                    }""";

        String updateJson = """
                {
                    "title":"New Post Title",
                    "content":"New Content..."
                }
                """;

        Mockito.when(postService.update(2,
                    Post.builder().title("New Post Title").content("New Content...").build()))
                .thenReturn(expected);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/posts/2")
                .accept(MediaType.APPLICATION_JSON)
                .content(updateJson)
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(expectedJson, response.getContentAsString(), false);
    }

    @Test
    public void deletePostById() throws Exception {
        Post expected = new Post(2, "Post Title", "Content...", LocalDateTime.of(
                2012, 12, 13, 23, 20, 4, 39), false);

        String expectedJson = """
                    {
                        id:2,
                        title:"Post Title",
                        content:"Content...",
                        creationDate:"2012-12-13T23:20:04.000000039",
                        star:false
                    }""";

        Mockito.when(postService.delete(2)).thenReturn(expected);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/posts/2")
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(expectedJson, response.getContentAsString(), false);
    }

    @Test
    public void findPostsByTitleWithOneOrMultipleResults() throws Exception {
        Mockito.when(postService.findByTitle("News")).thenReturn(List.of(postsDb.get(1), postsDb.get(3)));
        Mockito.when(postService.findByTitle("Test")).thenReturn(List.of(postsDb.get(0)));

        RequestBuilder requestBuilderWithManyResults =
                MockMvcRequestBuilders.get("/api/v1/posts?title=News")
                .accept(MediaType.APPLICATION_JSON);

        RequestBuilder requestBuilderWithSingleResult =
                MockMvcRequestBuilders.get("/api/v1/posts?title=Test")
                        .accept(MediaType.APPLICATION_JSON);

        String expectedWithSingleResult = """
                {
                        id:1,
                        title:"Test",
                        content:"Testing api...",
                        creationDate:"2020-10-21T22:00:22.000000032",
                        star:false
                }
                """;

        String expectedWithManyResults = """
                [
                    {
                        id:2,
                        title:"News",
                        content:"Some news here...",
                        creationDate:"2022-01-03T13:23:04.000000039",
                        star:false
                    },
                    {
                        id:10,
                        title:"News",
                        content:"Some news here222...",
                        creationDate:"2022-01-03T13:23:04.000000039",
                        star:true
                    }
                ]
                """;

        MockHttpServletResponse responseWithManyResults = mockMvc.perform(requestBuilderWithManyResults).andReturn().getResponse();
        MockHttpServletResponse responseWithSingleResult = mockMvc.perform(requestBuilderWithSingleResult).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), responseWithManyResults.getStatus());
        assertEquals(HttpStatus.OK.value(), responseWithSingleResult.getStatus());

        JSONAssert.assertEquals(expectedWithManyResults, responseWithManyResults.getContentAsString(), false);
        JSONAssert.assertEquals(expectedWithSingleResult, responseWithSingleResult.getContentAsString(), false);
    }

    @Test
    public void getAllPostsSortedByTitle() throws Exception {
        Mockito.when(postService.getAllSortedByTitle()).thenReturn(postsDb);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/posts?sort=title")
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        String expected = """
                [
                    {
                        id:3,
                        title:"Cars",
                        content:"Some content about cars...",
                        creationDate:"2012-12-13T23:20:04.000000039",
                        star:true
                    },
                    {
                        id:2,
                        title:"News",
                        content:"Some news here...",
                        creationDate:"2022-01-03T13:23:04.000000039",
                        star:false
                    },
                    {
                        id:10,
                        title:"News",
                        content:"Some news here222...",
                        creationDate:"2022-01-03T13:23:04.000000039",
                        star:true
                    },
                    {
                        id:1,
                        title:"Test",
                        content:"Testing api...",
                        creationDate:"2020-10-21T22:00:22.000000032",
                        star:false
                    }
                ]
                """;

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void getAllTopPosts() throws Exception {
        Mockito.when(postService.getAllTopPosts()).thenReturn(List.of(postsDb.get(2), postsDb.get(3)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/posts/star")
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        String expected = """
                [
                    {
                        id:3,
                        title:"Cars",
                        content:"Some content about cars...",
                        creationDate:"2012-12-13T23:20:04.000000039",
                        star:true
                    },
                    {
                        id:10,
                        title:"News",
                        content:"Some news here222...",
                        creationDate:"2022-01-03T13:23:04.000000039",
                        star:true
                    }
                ]
                """;

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void markAsTopByPostId() throws Exception {
        Mockito.when(postService.markAsTop(3)).thenReturn(postsDb.get(2));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/posts/3/star")
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        String expected = """
                    {
                        id:3,
                        title:"Cars",
                        content:"Some content about cars...",
                        creationDate:"2012-12-13T23:20:04.000000039",
                        star:true
                    }""";

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void removeFromTopByPostId() throws Exception {
        Mockito.when(postService.removeFromTop(2)).thenReturn(postsDb.get(1));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/posts/3/star")
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        String expected = """
                    {
                        id:2,
                        title:"News",
                        content:"Some news here...",
                        creationDate:"2022-01-03T13:23:04.000000039",
                        star:false
                    }""";

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }
}
