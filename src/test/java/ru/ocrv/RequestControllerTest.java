//package ru.ocrv;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Test;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.equalTo;
//
//public class RequestControllerTest {
//    @Test
//    public void canConnect(){
//        String findAllURL = "http://localhost:8080/request";
//
//        given()
//                .when()
//                .get(findAllURL)
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    public void canCreateRequest() throws JSONException {
//
//        JSONObject jsonObject = new JSONObject()
//                .put("description", "Description1");
//
//        //System.out.println(jsonObject.toString());
//
//        String findAllURL = "http://localhost:8080/request";
//
//        given()
//                .contentType("application/json")
//                .body(jsonObject.toString())
//                .when()
//                .post(findAllURL)
//                .then()
//                .statusCode(200)
//                .assertThat()
//                .body("$", equalTo("{description=Description1}"));
//    }
//}
