import Constants.PostEndpoint;
import Entities.Post;
import Utils.PostRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class PostsRestAssuredTest {
    @Test
    public void getPostsTest() {
        Response response = PostRequest.get(PostEndpoint.GET);

        ValidatableResponse validatableResponse = response.then().assertThat();
        validatableResponse.statusCode(200);
        validatableResponse.body("size()", Matchers.not(0));
        validatableResponse.body("size()", Matchers.equalTo(100));
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

        String payload = convertPostToString(post);

        Response response = PostRequest.post(PostEndpoint.POST, payload);

        ValidatableResponse validatableResponse = response.then().assertThat();
        validatableResponse.statusCode(201);
        validatableResponse.body("id", Matchers.equalTo(101));
        validatableResponse.body("title", Matchers.equalTo("nuevo post"));
        validatableResponse.body("body", Matchers.equalTo("descripcion del post"));
        validatableResponse.body("userId", Matchers.equalTo(1));
    }

    @Test
    public void putTest() throws JsonProcessingException {
        int id = 2;
        Post post = new Post();
        post.setTitle("Titulo modificado");
        post.setBody("Descripcion del body modificado");
        post.setUserId(2);

        String payload = convertPostToString(post);

        Response response = PostRequest.put(PostEndpoint.PUT, id, payload);

        ValidatableResponse validatableResponse = response.then().assertThat();
        validatableResponse.statusCode(200);
        validatableResponse.body("title", Matchers.equalTo("Titulo modificado"));
        validatableResponse.body("userId", Matchers.equalTo(2));
    }

    @Test
    void deleteTest(){
        int id = 2;
        Response response = PostRequest.delete(PostEndpoint.DELETE, id);

        response.then().assertThat().statusCode(200);
    }

    private static String convertPostToString(Post post) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(post);
        return payload;
    }
}
