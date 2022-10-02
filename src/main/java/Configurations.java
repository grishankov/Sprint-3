public interface Configurations {
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";
    public static final String BAD_REQUEST = "Недостаточно данных для создания учетной записи";
    public static final String BAD_REQUEST_LOGIN = "Недостаточно данных для входа";
    public static final String NOT_FOUND_LOGIN = "Учетная запись не найдена";
    public static final String CONFLICT = "Этот логин уже используется";
    public static final String NOT_FOUND_KURYER_ID = "Курьер с идентификатором {courierId} не найден";
    public static final String KURYER_PATH = "/api/v1/courier";
    public static final String LOGIN_PATH = "/api/v1/courier/login";
    public static final String DELETE_KURYER = "/api/v1/courier/";
    public static final String ORDER_PATH = "/api/v1/orders";
}