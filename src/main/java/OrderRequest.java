import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderRequest extends RestClient {
    public ValidatableResponse createNewOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(Configurations.ORDER_PATH)
                .then();
    }
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(Configurations.ORDER_PATH)
                .then();
    }
}
