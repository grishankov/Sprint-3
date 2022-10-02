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

public class KuryerCreateWithoutOneParametrTest {
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
    @DisplayName("Недостаточно данных для создания учетной записи, без логина")
    @Description("Expected status code 400 Bad Request")
    public void kuryerCreateWithoutLogin(){
        kuryer = new Kuryer("", "123qwe","Alexander");
        ValidatableResponse response = kuryerCreateLoginDelete.createKuryer(kuryer);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not 201 CREATED", SC_BAD_REQUEST, statusCode);
        boolean isKuryerNotCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isKuryerNotCreated);
    }

    @Test
    @DisplayName("Недостаточно данных для создания учетной записи, без пароля")
    @Description("Expected status code 400 Bad Request")
    public void kuryerCreateWithoutPassword(){
        kuryer = new Kuryer("Alex123234", "","Alexander");
        ValidatableResponse response = kuryerCreateLoginDelete.createKuryer(kuryer);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not 201 CREATED", SC_BAD_REQUEST, statusCode);
        boolean isKuryerNotCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isKuryerNotCreated);
    }

    @Test
    @DisplayName("Недостаточно данных для создания учетной записи, без пароля")
    @Description("Expected status code 400 Bad Request")
    public void kuryerCreateWithoutName(){
        kuryer = new Kuryer("Alex123234", "123qwe","");
        ValidatableResponse response = kuryerCreateLoginDelete.createKuryer(kuryer);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not 201 CREATED", SC_BAD_REQUEST, statusCode);
        boolean isKuryerNotCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isKuryerNotCreated);
    }

    @After
    public void kuryerDeleteAfter() {
        ValidatableResponse loginResponse = kuryerCreateLoginDelete.logInKuryer(loginKuryer.from(kuryer));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code is not 201 CREATED", SC_BAD_REQUEST, loginStatusCode);
        kuryerId = loginResponse.extract().path("id");
        assertTrue("Courier Id is invalid", kuryerId > 0);
        kuryerCreateLoginDelete.deleteKuryer(kuryerId);
    }
}
