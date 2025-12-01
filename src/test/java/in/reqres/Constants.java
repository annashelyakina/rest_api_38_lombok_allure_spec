package in.reqres;

import io.restassured.http.Header;

public class Constants {
    private Constants() {}
    public static final Header VALID_API_KEY = new Header("x-api-key", "reqres_0a06ba7540a741c0b80511deb85755cc");
    public static final Header INVALID_API_KEY = new Header("x-api-key", "reqres_0a06ba7540a741c0b80511deb8575511");
    public static final String VALID_EMAIL = "eve.holt@reqres.in";
    public static final String INVALID_EMAIL = "eve111.holt@reqres.in";
    public static final String VALID_PASSWORD = "pistol";
    public static final String VALID_NAME = "morpheus";
    public static final String VALID_JOB = "zion resident";
 }
