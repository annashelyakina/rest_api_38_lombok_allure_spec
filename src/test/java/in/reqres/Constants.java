package in.reqres;

import io.restassured.http.Header;

public class Constants {
    private Constants() {}
    public static final Header validApiKey = new Header("x-api-key", "reqres-free-v1");
    public static final Header invalidApiKey = new Header("x-api-key", "reqres-free-v111");
    public static final String validEmail = "eve.holt@reqres.in";
    public static final String validPassword = "pistol";
    public static final String invalidEmail = "eve111.holt@reqres.in";
    public static final String onlyPasswordInRegisterData = "{\"password\": \"pistol\"}";
    public static final String onlyEmailInRegisterData = "{\"email\": \"eve.holt@reqres.in\"}";

}
