import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KuryerLoginSuccessfullyTest {

    private Kuryer kuryer;
    private KuryerCreateLoginDelete kuryerCreateLoginDelete;
    private LoginKuryer loginKuryer;
    private int kuryerId;

    @Before
    public void setup() {
        RestAssured.baseURI = Configurations.BASE_URL;
        kuryerCreateLoginDelete = new KuryerCreateLoginDelete();
        kuryer = new Kuryer("Alex235445", "123qwe","Alexander");
        ValidatableResponse response = kuryerCreateLoginDelete.createKuryer(kuryer);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not 201 CREATED", SC_CREATED, statusCode);
        loginKuryer = new LoginKuryer(kuryer.getLogin(), kuryer.getPassword());
    }
    @Test
    @DisplayName("Курьер успешно залогинился")
    @Description("Expected status code 200")
    public void kuryerCanLogIn(){
        ValidatableResponse response = kuryerCreateLoginDelete.logInKuryer(loginKuryer);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is invalid", SC_OK, statusCode);
        kuryerId = response.extract().path("id");
        assertTrue("Courier id is not created", kuryerId > 0);
    }
    @After
    public void kuryerDeleteAfter() {
        ValidatableResponse loginResponse = kuryerCreateLoginDelete.logInKuryer(loginKuryer.from(kuryer));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code is not 201 CREATED", SC_OK, loginStatusCode);
        kuryerId = loginResponse.extract().path("id");
        assertTrue("Courier Id is invalid", kuryerId > 0);
        kuryerCreateLoginDelete.deleteKuryer(kuryerId);
    }
}
