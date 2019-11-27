package rest.api.test.sanity.suites;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import rest.api.test.sanity.TestData;
import rest.api.test.sanity.steps.SendRequests;
import rest.api.test.sanity.utils.ConfigurationCore;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRequests extends ConfigurationCore {

	@BeforeClass
	public static void setUpConnection() throws FileNotFoundException, IOException {
		ConfigurationCore.setupConnection();
	}

	/**
	 * Test Case 1: Send a valid payment transaction request and expect an approved
	 * response
	 * 
	 */
	@Test
	public void aSendValidPaymentRequest() {
		SendRequests.sendPaymentRequest(TestData.AUTHORIZATION_TRUE, TestData.CARD_NUMBER, TestData.CVV,
				TestData.EXPIRATION_DATE, TestData.AMOUNT, TestData.USAGE, TestData.TRANSACTION_TYPE_SALE,
				TestData.CARD_HOLDER, TestData.EMAIL, TestData.ADDRESS, TestData.STATUS_CODE_OK);
	}

	/**
	 * Test Case 2: Send a valid void transaction request and expect an approved
	 * response
	 * 
	 */
	@Test
	public void bSendValidVoidRequest() {
		SendRequests.sendVoidRequest(TestData.AUTHORIZATION_TRUE,
				ConfigurationCore.getValueFromCollector(TestData.CARD_NUMBER), TestData.TRANSACTION_TYPE_VOID,
				TestData.STATUS_CODE_OK);
	}

	/**
	 * Test Case 3: Send a valid payment transaction with an invalid authentication
	 * and expect an appropriate response (401 etc)
	 * 
	 */
	@Test
	public void cSendValidPaymentRequestNoValidAuthorization() {
		SendRequests.sendPaymentRequest(TestData.AUTHORIZATION_FALSE, TestData.CARD_NUMBER, TestData.CVV,
				TestData.EXPIRATION_DATE, TestData.AMOUNT, TestData.USAGE, TestData.TRANSACTION_TYPE_SALE,
				TestData.CARD_HOLDER, TestData.EMAIL, TestData.ADDRESS, TestData.STATUS_CODE_ACCESS_DENIED);
	}

	/**
	 * Test Case 4: Send a void transaction pointing to a non-existent payment
	 * transaction and expect 422 etc
	 * 
	 */
	@Test
	public void dSendNotValidVoidRequest() {
		SendRequests.sendVoidRequest(TestData.AUTHORIZATION_TRUE, TestData.NOT_VALID_TRANSACTION,
				TestData.TRANSACTION_TYPE_VOID, TestData.STATUS_CODE_UNPROCESSABLE_ENTITY);
	}
}
