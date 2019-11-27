package rest.api.test.sanity.steps;

import io.restassured.http.ContentType;
import rest.api.test.sanity.utils.ConfigurationCore;

import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.annotation.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import io.restassured.http.ContentType;

public class SendRequests extends ConfigurationCore {

	public static void sendPaymentRequest(String authorization, String cardNumber, String cvv, String expirationDate,
			String amount, String usage, String transactionType, String cardHolder, String email, String address,
			int expectedStatusCode) {

		Map<Object, String> mapRequestPayment = new HashMap<>();
		String uniquePaymentId = null;
        
        mapRequestPayment.put("card_number", cardNumber);
        mapRequestPayment.put("cvv", cvv);
        mapRequestPayment.put("expiration_date", expirationDate);
        mapRequestPayment.put("amount", amount);
        mapRequestPayment.put("usage", usage);
        mapRequestPayment.put("transaction_type", transactionType);
        mapRequestPayment.put("card_holder", cardHolder);
        mapRequestPayment.put("email", email);
        mapRequestPayment.put("address", address);

        if (expectedStatusCode == 200) {
            try {
                uniquePaymentId = 
                given().
                    contentType(ContentType.JSON).
                    header("Authorization", getProperty(authorization)).
                    body(Collections.singletonMap("payment_transaction", mapRequestPayment)).
                when().
                    post(getProperty("server.post.payments")).
                then().
                    statusCode(expectedStatusCode).extract().path("unique_id");
                
            } catch (IOException e) {
                e.printStackTrace();
            }

            addValueToCollector(cardNumber, uniquePaymentId);
        }

        else {
            try {
                given().
                    contentType(ContentType.JSON).
                    header("Authorization", getProperty(authorization)).
                    body(Collections.singletonMap("payment_transaction", mapRequestPayment)).
                when().
                    post(getProperty("server.post.payments")).
                then()
                    .statusCode(expectedStatusCode);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public static void sendVoidRequest(String authorization, String uniquePaymentId, String transactionType,
			int expectedStatusCode) {

		Map<Object, String> mapRequestVoid = new HashMap<>();

        mapRequestVoid.put("reference_id", uniquePaymentId);
        mapRequestVoid.put("transaction_type", transactionType);

        try {
            given().
                contentType(ContentType.JSON).
                header("Authorization", getProperty(authorization)).
                body(Collections.singletonMap("payment_transaction", mapRequestVoid)).
            when().
                post(getProperty("server.post.payments")).
            then().
            statusCode(expectedStatusCode);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
