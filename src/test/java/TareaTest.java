import Constants.PostEndpoint;
import Entities.Post;
import Utils.PostRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class TareaTest {
    @Test
    public void getPostsTest() {
        Response response = PostRequest.get(PostEndpoint.GET);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.not(0));
    }

    @Test
    public void getPostByIdTest() {
        int id = 2;
        Response response = PostRequest.getById(PostEndpoint.GET_ID, id);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("id", Matchers.equalTo(id));
        response.then().assertThat().body("body", Matchers.containsString("est rerum tempore"));
    }

    @Test
    public void postTest() throws JsonProcessingException {
        Post post = new Post();
        post.setTitle("nuevo post");
        post.setBody("descripcion del post");
        post.setUserId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(post);

        Response response = PostRequest.post(PostEndpoint.POST, payload);

        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("id", Matchers.equalTo(101));
        response.then().assertThat().body("title", Matchers.equalTo("nuevo post"));
        response.then().assertThat().body("body", Matchers.equalTo("descripcion del post"));
        response.then().assertThat().body("userId", Matchers.equalTo(1));
    }

    @Test
    public void putTest() throws JsonProcessingException {
        int id = 2;
        Post post = new Post();
        post.setTitle("Titulo modificado");
        post.setBody("Descripcion del body modificado");
        post.setUserId(2);

        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(post);

        Response response = PostRequest.put(PostEndpoint.PUT, id, payload);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("title", Matchers.equalTo("Titulo modificado"));
        response.then().assertThat().body("userId", Matchers.equalTo(2));
    }

    @Test
    void deleteTest(){
        int id = 2;
        Response response = PostRequest.delete(PostEndpoint.DELETE, id);

        response.then().assertThat().statusCode(200);
    }
}
