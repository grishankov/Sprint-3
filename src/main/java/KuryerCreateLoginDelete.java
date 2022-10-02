import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class KuryerCreateLoginDelete extends RestClient {

    public ValidatableResponse createKuryer (Kuryer kuryer) {
        return given()
                .spec(getBaseSpec())
                .body(kuryer)
                .when()
                .post(Configurations.KURYER_PATH)
                .then();
    }

    public ValidatableResponse  logInKuryer (LoginKuryer loginKuryer) {
        return given()
                .spec(getBaseSpec())
                .body(loginKuryer)
                .when()
                .post(Configurations.LOGIN_PATH)
                .then();
    }

    public ValidatableResponse  deleteKuryer ( int id) {
        return given()
                .spec(getBaseSpec())
                .pathParam("id", id)
                .when()
                .delete(Configurations.DELETE_KURYER + "{id}")
                .then();
    }
}
