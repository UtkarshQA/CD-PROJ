package com.MyAccount.TestCases;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class TS11_Products extends TestBase {

	@BeforeTest
	private void preCondition() throws Exception {
		seleniumUtil.openBrowser();
		seleniumUtil.openURL(g_url);
		commonMethods.userLogin(g_userName, g_userPassword);
		seleniumUtil.waitForElementVisible(products.lnk_products);
		seleniumUtil.clickByJS(products.lnk_products);

	}

	@Test
	private void Products_TC01() throws Exception {
		System.out.println("***** Start Test Suite: " + getClass().getSimpleName() + " *****");

		logger = extent.createTest("To verify Active Products at products page.")
				.assignCategory(getClass().getSimpleName());
		JSONObject JSOProductSummary = request.GetProductSummaryResponse();

		if (ProductSummaryResponse == true) {

			JSONArray JSArrayActiveProducts = JSOProductSummary.getJSONArray("activeProducts");

			if (JSArrayActiveProducts.length() != 0) {

				ArrayList<String> policyNumber = new ArrayList<String>();
				for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
					JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
					JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
					policyNumber.add(paymentMethod.getString("policyNumber"));
				}

				for (int j = 0; j < policyNumber.size(); j++) {
					JSOProductSummary = request.GetProductSummaryResponse();
					JSArrayActiveProducts = JSOProductSummary.getJSONArray("activeProducts");
					JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(j);
					JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");

					if (paymentMethod.get("policyNumber").equals(policyNumber.get(j))) {
						
						
						seleniumUtil.waitForElementPresent(By.xpath(
								"//h2[text()='Active Products']/following-sibling::table[contains(@class,'productTable')]//tbody//tr["
										+ (j + 1) + "]//td[1]//a//span[@class='anchorText']"));

						seleniumUtil.verify(seleniumUtil.getText(By.xpath(
								"//h2[text()='Active Products']/following-sibling::table[contains(@class,'productTable')]//tbody//tr["
										+ (j + 1) + "]//td[1]//a//span[@class='anchorText']")),
								paymentMethod.get("productDesc"));

						seleniumUtil.verify(seleniumUtil.getText(By.xpath(
								"//h2[text()='Active Products']/following-sibling::table[contains(@class,'productTable')]//tbody//tr["
										+ (j + 1) + "]//td[1]//span[@class='smallTableText']")),
								paymentMethod.get("policyNumber"));

						seleniumUtil.verify(seleniumUtil.getText(By.xpath(
								"//h2[text()='Active Products']/following-sibling::table[contains(@class,'productTable')]//tbody//tr["
										+ (j + 1) + "]//td[2]")),
								paymentMethod.get("coverageType"));
						seleniumUtil.verify(seleniumUtil.getText(By.xpath(
								"//h2[text()='Active Products']/following-sibling::table[contains(@class,'productTable')]//tbody//tr["
										+ (j + 1) + "]//td[3]")),
								paymentMethod.get("coverageEffectiveDate"));
						seleniumUtil.verify(seleniumUtil.getText(By.xpath(
								"//h2[text()='Active Products']/following-sibling::table[contains(@class,'productTable')]//tbody//tr["
										+ (j + 1) + "]//td[4]")),
								paymentMethod.getString("annualPremium")
										+ paymentMethod.getString("annualPremiumSpecialCharacter"));
						seleniumUtil.verify(seleniumUtil.getText(By.xpath(
								"//h2[text()='Active Products']/following-sibling::table[contains(@class,'productTable')]//tbody//tr["
										+ (j + 1) + "]//td[5]")),
								paymentMethod.get("currentPaymentFrequecyType"));
						seleniumUtil.verify(seleniumUtil.getText(By.xpath(
								"//h2[text()='Active Products']/following-sibling::table[contains(@class,'productTable')]//tbody//tr["
										+ (j + 1) + "]//td[6]//span[@class='dateValue']")),
								paymentMethod.get("dueDate"));
					}
				}
			} else {

				logger.skip("Active products are not found.");
			}
		} else {
			logger.error(ProductSummaryResponse_message_content);
		}

	}

	@Test
	private void Products_TC02() throws Exception {

		logger = extent.createTest("To verify InActive Products at products page.")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSOProductSummary = request.GetProductSummaryResponse();

		if (ProductSummaryResponse == true) {

			JSONArray JSArrayInActiveProducts = JSOProductSummary.getJSONArray("inactiveProducts");

			if (JSArrayInActiveProducts.length() != 0) {

				seleniumUtil.clickByJS(products.lnk_show);

				ArrayList<String> policyNumber = new ArrayList<String>();
				for (int i = 0; i < JSArrayInActiveProducts.length(); i++) {
					JSONObject JSObjectPaymentMethods = JSArrayInActiveProducts.getJSONObject(i);
					JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
					policyNumber.add(paymentMethod.getString("policyNumber"));
				}

				for (int i = 0; i < JSArrayInActiveProducts.length(); i++) {
					JSOProductSummary = request.GetProductSummaryResponse();
					JSArrayInActiveProducts = JSOProductSummary.getJSONArray("inactiveProducts");
					JSONObject JSObjectPaymentMethods = JSArrayInActiveProducts.getJSONObject(i);
					JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");

					if (paymentMethod.get("policyNumber").equals(policyNumber.get(i))) {

						seleniumUtil.waitForElementPresent(
								By.xpath("//div[@class='inActiveContent']//span[@class='smallTableText'][text()='"
										+ policyNumber.get(i) + "']/preceding-sibling::a//span[@class='anchorText']"));

						seleniumUtil.verify(seleniumUtil.getText(
								By.xpath("//div[@class='inActiveContent']//span[@class='smallTableText'][text()='"
										+ policyNumber.get(i) + "']/preceding-sibling::a//span[@class='anchorText']")),
								paymentMethod.get("productDesc"));
						seleniumUtil.verify(seleniumUtil.getText(
								By.xpath("//div[@class='inActiveContent']//span[@class='smallTableText'][text()='"
										+ policyNumber.get(i) + "']")),
								paymentMethod.get("policyNumber"));

						seleniumUtil.verify(
								seleniumUtil.getText(By
										.xpath("//div[@class='inActiveContent']//span[@class='smallTableText'][text()='"
												+ policyNumber.get(i) + "']/parent::td/parent::tr//td[4]")),
								paymentMethod.getString("amountDue")
										+ paymentMethod.getString("amountDueSpecialCharacter"));
						seleniumUtil.verify(
								seleniumUtil.getText(By
										.xpath("//div[@class='inActiveContent']//span[@class='smallTableText'][text()='"
												+ policyNumber.get(i) + "']/parent::td/parent::tr//td[5]")),
								paymentMethod.get("dueDate"));

					}

				}
			} else {

				logger.skip("InActive products are not found.");
			}

		} else {
			logger.error(ProductSummaryResponse_message_content);
		}

	}

	@AfterTest
	public void postCondition() {
		seleniumUtil.quitBrowser();
		System.out.println("***** End Test Suite: " + getClass().getSimpleName() + " *****");

	}

}
