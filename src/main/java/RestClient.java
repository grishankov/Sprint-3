import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;


public class RestClient {
    public RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder().addHeader("Content-type", "application/json").setBaseUri(Configurations.BASE_URL).build();
    }
}
