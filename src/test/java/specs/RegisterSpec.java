package specs;

import in.reqres.Constants;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class RegisterSpec {

    public static RequestSpecification getUserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .header(Constants.VALID_API_KEY);

    public static RequestSpecification registerRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .header(Constants.VALID_API_KEY)
            .contentType(ContentType.JSON);

    public static RequestSpecification invalidApiKeyRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .header(Constants.INVALID_API_KEY)
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseSpec200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification responseSpec204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification responseSpec400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification responseSpec403 = new ResponseSpecBuilder()
            .expectStatusCode(403)
            .log(LogDetail.ALL)
            .build();
}
