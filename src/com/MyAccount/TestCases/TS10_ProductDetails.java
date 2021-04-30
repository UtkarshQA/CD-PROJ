package com.MyAccount.TestCases;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class TS10_ProductDetails extends TestBase {

	List<String> policyNumber = new ArrayList<String>();

	@BeforeTest
	private void preCondition() throws Exception {
		System.out.println("***** Start Test Suite: " + getClass().getSimpleName() + " *****");
		seleniumUtil.openBrowser();
		seleniumUtil.openURL(g_url);
		commonMethods.userLogin(g_userName, g_userPassword);
		seleniumUtil.waitForElementVisible(dashboard.lbl_welcomeText);
		JSONObject JSOPaymentMethods = request.GetHomepageResponse();
		if (HomepageResponse == true) {
			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");
			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
				JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
				policyNumber.add((String) paymentMethod.get("policyNumber"));
			}
		} else {
			logger.error(HomepageResponse_message_content);
		}
	}

	@Test
	private void ProductDetails_TC01() throws Exception {

		logger = extent.createTest("To verify Product Overview details at product details page.")
				.assignCategory(getClass().getSimpleName());

		request.GetProductsDetailsResponse(policyNumber.get(0));

		if (ProductsDetailsResponse == true) {
			for (int i = 0; i < policyNumber.size(); i++) {

				JSONObject JSOProductsDetail = request.GetProductsDetailsResponse(policyNumber.get(i));
				JSONObject JSOProductDetail = JSOProductsDetail.getJSONObject("productDetail");
				String productDetailUrl = g_url + consts.url_productDetails + policyNumber.get(i);

				if (JSOProductDetail.get("isInsuranceProduct").equals("Y")) {
					seleniumUtil.openURL(productDetailUrl);
					seleniumUtil.refresh();
					seleniumUtil.waitForElementPresent(productDetails.lbl_productName);
					seleniumUtil.verify(
							commonMethods.remove_arrow_back(seleniumUtil.getText(productDetails.lbl_productName)),
							JSOProductDetail.get("productDesc"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_certificateNumber),
							JSOProductDetail.get("policyNumber"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_coverageType),
							JSOProductDetail.get("coverageType"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_status),
							JSOProductDetail.get("policyStatus"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_coverageEffectiveDate),
							JSOProductDetail.get("coverageEffectiveDate"));

					// PAYMENT DUE DATE

					if (JSOProductDetail.get("suppressPaymentDueDate").equals("N")) {
						String paymentDueDateLabel = (String) JSOProductDetail.get("paymentDueDateLabel");
						seleniumUtil.verify(
								seleniumUtil.getText(By
										.xpath("//span[text()='" + paymentDueDateLabel + "']/following-sibling::span")),
								JSOProductDetail.get("paymentDueDate"));
					}

					// isCancerOrHipOrHap

					if (JSOProductDetail.get("isCancerOrHipOrHap").equals("Y")) {
						seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_dailyHospitalBenefit),
								JSOProductDetail.get("benefitAmount"));
					} else {
						seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_baseBenefitAmount),
								JSOProductDetail.get("benefitAmount"));
					}

					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_annualPremium),
							JSOProductDetail.get("annualPremium")
									+ (String) JSOProductDetail.get("annualPremiumSpecialCharacter"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_paymentFrequency),
							JSOProductDetail.get("paymentFrequency"));

				} else if ((JSOProductDetail.get("isInsuranceProduct").equals("N"))) {

					seleniumUtil.openURL(productDetailUrl);
					seleniumUtil.refresh();
					seleniumUtil.waitForElementPresent(productDetails.lbl_productName);
					seleniumUtil.verify(
							commonMethods.remove_arrow_back(seleniumUtil.getText(productDetails.lbl_productName)),
							JSOProductDetail.get("productDesc"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_planNumber),
							JSOProductDetail.get("policyNumber"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_planType),
							JSOProductDetail.get("coverageType"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_status),
							JSOProductDetail.get("policyStatus"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_coverageEffectiveDate),
							JSOProductDetail.get("coverageEffectiveDate"));

					// PAID THROUGH

					if (JSOProductDetail.get("suppressPaymentDueDate").equals("N")) {
						String paymentDueDateLabel = (String) JSOProductDetail.get("paymentDueDateLabel");
						seleniumUtil.verify(
								seleniumUtil.getText(By
										.xpath("//span[text()='" + paymentDueDateLabel + "']/following-sibling::span")),
								JSOProductDetail.get("paymentDueDate"));

					}
//					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_paidThrough),
//							JSOProductDetail.get("paymentDueDate"));

					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_owner), JSOProductDetail.get("owner"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_annualProductCost),
							JSOProductDetail.get("annualPremium"));
					seleniumUtil.verify(seleniumUtil.getText(productDetails.lbl_paymentFrequency),
							JSOProductDetail.get("paymentFrequency"));
				}

			}
		} else {
			logger.error(ProductsDetailsResponse_message_content);

		}

	}

	@Test
	private void ProductDetails_TC02() throws Exception {

		logger = extent.createTest("To verify Optional Riders at product details page.")
				.assignCategory(getClass().getSimpleName());

		if (ProductsDetailsResponse == true) {
			int optionalRiders_counter = 0;
			for (int i = 0; i < policyNumber.size(); i++) {
				JSONObject JSOProductsDetail = request.GetProductsDetailsResponse(policyNumber.get(i));
				JSONArray JSOArrayOptionalRiders = JSOProductsDetail.getJSONArray("optionalRiders");

				if (JSOArrayOptionalRiders.length() != 0) {

					String productDetailUrl = g_url + consts.url_productDetails + policyNumber.get(i);

					seleniumUtil.openURL(productDetailUrl);
					seleniumUtil.refresh();
					seleniumUtil.waitForElementPresent(productDetails.lbl_productName);

					for (int j = 0; j < JSOArrayOptionalRiders.length(); j++) {

						JSONObject JSObjectOptionalRiders = JSOArrayOptionalRiders.getJSONObject(j);

						JSONObject JSObjectProductDetail = JSObjectOptionalRiders.getJSONObject("productDetail");

						String ORProductDesc = seleniumUtil.getText(By.xpath(
								"//div[@class='optionalRider']//table//tbody//tr[" + (j + 1) + "]/td[1]/span[1]"));
						String ORPolicyNumber = seleniumUtil.getText(By.xpath(
								"//div[@class='optionalRider']//table//tbody//tr[" + (j + 1) + "]/td[1]/span[2]"));
						String ORStatus = seleniumUtil.getText(
								By.xpath("//div[@class='optionalRider']//table//tbody//tr[" + (j + 1) + "]/td[2]"));
						String ORCoverageEffectiveDate = seleniumUtil.getText(
								By.xpath("//div[@class='optionalRider']//table//tbody//tr[" + (j + 1) + "]/td[3]"));
						String ORBenefitAmount = seleniumUtil.getText(
								By.xpath("//div[@class='optionalRider']//table//tbody//tr[" + (j + 1) + "]/td[4]"));
						String ORAnnualPremium = seleniumUtil.getText(
								By.xpath("//div[@class='optionalRider']//table//tbody//tr[" + (j + 1) + "]/td[5]"));

						seleniumUtil.verify(ORProductDesc, JSObjectProductDetail.get("productDesc"));
						seleniumUtil.verify(ORPolicyNumber, JSObjectProductDetail.get("policyNumber"));
						seleniumUtil.verify(ORStatus, JSObjectProductDetail.get("policyStatus"));
						seleniumUtil.verify(ORCoverageEffectiveDate,
								JSObjectProductDetail.get("coverageEffectiveDate"));
						seleniumUtil.verify(ORBenefitAmount, JSObjectProductDetail.get("benefitAmount"));
						seleniumUtil.verify(ORAnnualPremium, JSObjectProductDetail.get("annualPremium"));
						optionalRiders_counter++;
						break;
					}
				} else {
					logger.info("Optional Riders details are not found in this policy numaber: " + policyNumber.get(i));
				}
				seleniumUtil.refresh();
			}

			if (optionalRiders_counter == 0) {

				logger.skip("Optional Riders details are not found.");
			}
		} else {
			logger.error(ProductsDetailsResponse_message_content);
		}
	}

	@Test
	private void ProductDetails_TC03() throws Exception {

		logger = extent.createTest("To verify Product Documents at product details page.")
				.assignCategory(getClass().getSimpleName());
		if (ProductsDetailsResponse == true) {
			int temp_counter = 0;
			int productDocuments_counter = 0;
			for (int i = 0; i < policyNumber.size(); i++) {
				JSONObject JSOProductsDetail = request.GetProductsDetailsResponse(policyNumber.get(i));
				JSONArray JSOArrayProductDocuments = JSOProductsDetail.getJSONArray("productDocuments");

				if (JSOArrayProductDocuments.length() != 0) {

					String productDetailUrl = g_url + consts.url_productDetails + policyNumber.get(i);
					seleniumUtil.openURL(productDetailUrl);
					seleniumUtil.refresh();
					seleniumUtil.waitForElementPresent(productDetails.lbl_productName);

					temp_counter++;

					String strDate = seleniumUtil.getText(By.xpath(
							"//h2[text()='Product Documents']/parent::div/div[contains(@class,'productDocs')]//li[1]/span[1]/span[1]"));
					String strDocumentName = seleniumUtil.getText(By.xpath(
							"//h2[text()='Product Documents']/parent::div/div[contains(@class,'productDocs')]//li[1]/span[1]/span[2]"));

					for (int j = 0; j < JSOArrayProductDocuments.length(); j++) {

						JSONObject JSObjectProductDocuments = JSOArrayProductDocuments.getJSONObject(j);

						JSONObject JSObjectDocument = JSObjectProductDocuments.getJSONObject("document");

						if (strDocumentName.equalsIgnoreCase(JSObjectDocument.getString("documentName")) == true) {

							seleniumUtil.verify(strDate, JSObjectDocument.get("documentCreatedDate"));
							String documentName = (String) JSObjectDocument.get("documentName");
							seleniumUtil.verify(strDocumentName, documentName.toUpperCase());
							seleniumUtil.clickByJS(By.xpath(
									"//h2[text()='Product Documents']/parent::div/div[contains(@class,'productDocs')]//li["
											+ (j + 1) + "]/span[2]/a"));
							seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
							// Thread.sleep(5000);
							seleniumUtil.switchToWindow();
							String strPdfLink = driver.getCurrentUrl();
							logger.info("View document url: " + strPdfLink);
							strPdfLink = strPdfLink.substring(0, 4);
							seleniumUtil.verify(strPdfLink, "blob");
							driver.close();
							seleniumUtil.swithToDefaultWindow();
							productDocuments_counter++;
							break;
						}
					}
				} else {
					logger.info(
							"Product documents details are not found in this policy numaber: " + policyNumber.get(i));
				}
				if (temp_counter != 0) {
					break;
				}
			}
		if (productDocuments_counter == 0) {
				logger.skip("Product documents details are not found.");
			}
		} else {
			logger.error(ProductsDetailsResponse_message_content);
		}
	}

	@Test
	private void ProductDetails_TC04() throws Exception {

		logger = extent.createTest("To verify Additional Insured at product details page.")
				.assignCategory(getClass().getSimpleName());

		if (ProductsDetailsResponse == true) {
			int additionalInsured = 0;
			for (int i = 0; i < policyNumber.size(); i++) {

				JSONObject JSOProductsDetail = request.GetProductsDetailsResponse(policyNumber.get(i));
				JSONArray JSOArrayAdditionalInsured = JSOProductsDetail.getJSONArray("additionalInsured");

				if (JSOArrayAdditionalInsured.length() != 0) {

					String productDetailUrl = g_url + consts.url_productDetails + policyNumber.get(i);
					seleniumUtil.openURL(productDetailUrl);
					seleniumUtil.refresh();
					seleniumUtil.waitForElementPresent(productDetails.lbl_productName);

					for (int j = 0; j < JSOArrayAdditionalInsured.length(); j++) {

						JSONObject JSObjectProductDocuments = JSOArrayAdditionalInsured.getJSONObject(j);
						JSONObject JSObjectDependent = JSObjectProductDocuments.getJSONObject("dependent");

						String strFullName = seleniumUtil.getText(
								By.xpath("//table[contains(@class,'productTable')]//tbody//tr[" + (j + 1) + "]/td[1]"));
						String strRelationship = seleniumUtil.getText(
								By.xpath("//table[contains(@class,'productTable')]//tbody//tr[" + (j + 1) + "]/td[2]"));
						seleniumUtil.verify(strFullName, JSObjectDependent.get("fullName"));
						seleniumUtil.verify(strRelationship, JSObjectDependent.get("relationship"));
					}
					additionalInsured++;
					break;
				} else {
					logger.info("Additional Insured details are not found in this policy numaber: " + policyNumber.get(i));
				}
			}

			if (additionalInsured == 0) {
				logger.skip("Additional Insured details are not found.");
			}
		} else {
			logger.error(ProductsDetailsResponse_message_content);
		}
	}

	@Test
	private void ProductDetails_TC05() throws Exception {

		logger = extent.createTest("To verify Correspondence at product details page.")
				.assignCategory(getClass().getSimpleName());

		if (ProductsDetailsResponse == true) {
			int correspondence_counter = 0;
			int temp_counter = 0;
			for (int i = 0; i < policyNumber.size(); i++) {
				JSONObject JSOProductsDetail = request.GetProductsDetailsResponse(policyNumber.get(i));
				JSONArray JSOArrayCorrespondences = JSOProductsDetail.getJSONArray("correspondences");

				if (JSOArrayCorrespondences.length() != 0) {

					String productDetailUrl = g_url + consts.url_productDetails + policyNumber.get(i);
					seleniumUtil.openURL(productDetailUrl);
					seleniumUtil.refresh();
					seleniumUtil.waitForElementPresent(productDetails.lbl_productName);
					String strDate = seleniumUtil.getText(By.xpath(
							"//h2[text()='Correspondence']/parent::div/div[contains(@class,'productDocs')]//li[1]/span[1]/span[1]"));
					String strDocumentName = seleniumUtil.getText(By.xpath(
							"//h2[text()='Correspondence']/parent::div/div[contains(@class,'productDocs')]//li[1]/span[1]/span[2]"));

					temp_counter++;
					for (int j = 0; j < JSOArrayCorrespondences.length(); j++) {
						JSONObject JSObjectProductDocuments = JSOArrayCorrespondences.getJSONObject(j);
						JSONObject JSObjectDocument = JSObjectProductDocuments.getJSONObject("document");

						if (strDocumentName.equalsIgnoreCase(JSObjectDocument.getString("documentName")) == true) {
							seleniumUtil.verify(strDate, JSObjectDocument.get("documentCreatedDate"));
							String documentName = (String) JSObjectDocument.get("documentName");
							seleniumUtil.verify(strDocumentName, documentName.toUpperCase());
							seleniumUtil.clickByJS(By.xpath(
									"//h2[text()='Correspondence']/parent::div/div[contains(@class,'productDocs')]//li["
											+ (j + 1) + "]/span[2]/a"));
							seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
							seleniumUtil.switchToWindow();
							String strPdfLink = driver.getCurrentUrl();
							logger.info("View document url: " + strPdfLink);
							strPdfLink = strPdfLink.substring(0, 4);
							seleniumUtil.verify(strPdfLink, "blob");
							driver.close();
							seleniumUtil.swithToDefaultWindow();
							correspondence_counter++;
							break;
						}
					}
				} else {
					logger.info("Correspondence details are not found in this policy numaber: " + policyNumber.get(i));
				}

				if (temp_counter != 0) {
					break;
				}
			}

			if (correspondence_counter == 0) {
				logger.skip("Correspondence details are not found.");
			}
		} else {
			logger.error(ProductsDetailsResponse_message_content);
		}
	}

	@AfterTest
	public void postCondition() {
		seleniumUtil.quitBrowser();
		System.out.println("***** End Test Suite: " + getClass().getSimpleName() + " *****");

	}

}
