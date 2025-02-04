package api_Scripts;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class FisGlobal_API_Automation {
	
	@Test
	public void verifyDescriptionOfGBP() {
		
		Response resp = RestAssured.get("https://api.coindesk.com/v1/bpi/currentprice.json");
		
		String expDesc = "British Pound Sterling";
		String actDesc = resp.jsonPath().get("bpi.GBP.description");
		System.out.println("Actual Description : "+actDesc);
		Assert.assertEquals(actDesc, expDesc);
		resp.then().log().all();
		
		int actStatus = resp.getStatusCode();
		
		Assert.assertEquals(actStatus, 200);
		Reporter.log("API automation passed.", true);
		
		
	}

}
