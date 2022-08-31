import Constants.PostEndpoint;
import Entities.Post;
import Utils.PostRequest;
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
        String id = "2";
        Response response = PostRequest.getById(PostEndpoint.GET_ID, id);

        ValidatableResponse res = response.then().assertThat();
        res.statusCode(200);
        res.body("id", Matchers.equalTo(id));
    }
}
