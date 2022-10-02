import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KuryerLoginTwiceTest {
    private Kuryer kuryer;
    private KuryerCreateLoginDelete kuryerCreateLoginDelete;
    private LoginKuryer loginKuryer;
    private int kuryerId;

    @Before
    public void setup() {
        RestAssured.baseURI = Configurations.BASE_URL;
        kuryerCreateLoginDelete = new KuryerCreateLoginDelete();
    }

    @Test
    @DisplayName("Этот логин уже используется")
    @Description("Expected status code 409")
    public void createKuryerSameLogin(){
        kuryer = new Kuryer("Alex72863", "123qwe","Alexander");
        ValidatableResponse response = kuryerCreateLoginDelete.createKuryer(kuryer);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not 201 CREATED", SC_CREATED, statusCode);
        ValidatableResponse responseSecond = kuryerCreateLoginDelete.createKuryer(kuryer);
        int statusCodeSecond = responseSecond.extract().statusCode();
        assertEquals("Status code is not 201 CREATED", SC_CONFLICT, statusCodeSecond);
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

