package in.reqres;

import io.restassured.http.Header;

public class Constants {
    private Constants() {}
    public static final Header VALID_API_KEY = new Header("x-api-key", "reqres-free-v1");
    public static final Header INVALID_API_KEY = new Header("x-api-key", "reqres-free-v111");
    public static final String VALID_EMAIL = "eve.holt@reqres.in";
    public static final String VALID_PASSWORD = "pistol";
    public static final String INVALID_EMAIL = "eve111.holt@reqres.in";
    public static final String VALID_NAME = "morpheus";
    public static final String VALID_JOB = "zion resident";
 }
