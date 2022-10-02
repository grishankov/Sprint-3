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

public class KuryerCreateTest {
    private Kuryer kuryer;
    private KuryerCreateLoginDelete kuryerCreateLoginDelete;
    private LoginKuryer loginKuryer;
    private int kuryerId;

    @Before
    public void setup(){
        RestAssured.baseURI = Configurations.BASE_URL;
        kuryerCreateLoginDelete = new KuryerCreateLoginDelete();
    }

    @Test
    @DisplayName("Курьер успешно создан")
    @Description("Expected status code 201 created")
    public void kuryerCreatedSuccessfully(){
        kuryer = new Kuryer("Alex235445", "123qwe","Alexander");
        ValidatableResponse response = kuryerCreateLoginDelete.createKuryer(kuryer);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not 201 CREATED", SC_CREATED, statusCode);
        boolean isKuryerCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isKuryerCreated);
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
