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

public class KuryerLoginErrorTest {
    private Kuryer kuryer;
    private KuryerCreateLoginDelete kuryerCreateLoginDelete;
    private LoginKuryer loginKuryer;
    private int kuryerId;

    @Before
    public void setup() {
        RestAssured.baseURI = Configurations.BASE_URL;
        kuryerCreateLoginDelete = new KuryerCreateLoginDelete();
        kuryer = new Kuryer("Alex235445", "123qwe", "Alexander");
        ValidatableResponse response = kuryerCreateLoginDelete.createKuryer(kuryer);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not 201 CREATED", SC_CREATED, statusCode);
    }

    @Test
    @DisplayName("Ввод без пароля")
    @Description("Expected status 400")
    public void kuryerLoginWithoutPassword(){
        loginKuryer = new LoginKuryer(kuryer.getLogin());
        ValidatableResponse LoginResponse = kuryerCreateLoginDelete.logInKuryer(loginKuryer);
        int statusLoginCode = LoginResponse.extract().statusCode();
        assertEquals("Status code is invalid", SC_BAD_REQUEST, statusLoginCode);
        String message = LoginResponse.extract().path("message");
        assertEquals("Message is uncorrected", "Недостаточно данных для входа", message );
    }

    @Test
    @DisplayName("Ввод несуществующих данных")
    @Description("Expected status 404")
    public void kuryerLoginNotExistLogin(){
        loginKuryer = new LoginKuryer("Bremo","1234qwer");
        ValidatableResponse LoginResponse = kuryerCreateLoginDelete.logInKuryer(loginKuryer);
        int statusLoginCode = LoginResponse.extract().statusCode();
        assertEquals("Status code is invalid", SC_NOT_FOUND, statusLoginCode);
        String message = LoginResponse.extract().path("message");
        assertEquals("Message is incorrect", "Учетная запись не найдена", message);
    }

    @Test
    @DisplayName("Ввод неверного пароля")
    @Description("Expected status 404")
    public void kuryerLoginWrongPassaword(){
        loginKuryer = new LoginKuryer("Alex235445","1234qwer");
        ValidatableResponse LoginResponse = kuryerCreateLoginDelete.logInKuryer(loginKuryer);
        int statusLoginCode = LoginResponse.extract().statusCode();
        assertEquals("Status code is invalid", SC_NOT_FOUND, statusLoginCode);
        String message = LoginResponse.extract().path("message");
        assertEquals("Message is incorrect", "Учетная запись не найдена", message);
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
