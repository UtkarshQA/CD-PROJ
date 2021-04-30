package com.MyAccount.Utilities;

import com.MyAccount.Common.CommonMethods;
import com.MyAccount.Common.Consts;
import com.MyAccount.Common.Request;
import com.MyAccount.ObjectRepository.CommonObjectRepositories;
import com.MyAccount.ObjectRepository.Dashboard;
import com.MyAccount.ObjectRepository.ForgotPassword;
import com.MyAccount.ObjectRepository.ForgotUsername;
import com.MyAccount.ObjectRepository.Liferay;
import com.MyAccount.ObjectRepository.Login;
import com.MyAccount.ObjectRepository.MyProfile;
import com.MyAccount.ObjectRepository.PayNow;
import com.MyAccount.ObjectRepository.PaymentCenter_Products;
import com.MyAccount.ObjectRepository.PaymentMethods;
import com.MyAccount.ObjectRepository.ProductDetails;
import com.MyAccount.ObjectRepository.Products;
import com.MyAccount.ObjectRepository.Register;
import com.MyAccount.ObjectRepository.ResetPassword;

public class TestBase extends SeleniumUtil{
		
	// Objects - Common 
	
	public static CommonMethods commonMethods = new CommonMethods();
	public static Request request = new Request();
	public static Consts consts = new Consts();

	// Objects - Utilities 
	
	public static SeleniumUtil seleniumUtil = new SeleniumUtil();
	public static ExcelUtil excelUtil = new ExcelUtil();
	public static DataBaseUtil db = new DataBaseUtil();

	
	// Objects - Object Repository 
	
	public static Login login = new Login();
	public static Register register = new Register();
	public static ForgotUsername forgotUsername = new ForgotUsername();
	public static ForgotPassword forgotPassword = new ForgotPassword();
	public static Liferay liferay = new Liferay();
	public static ResetPassword resetPassword = new ResetPassword();
	public static MyProfile myProfile = new MyProfile();
	public static PaymentMethods paymentMethods = new PaymentMethods();
	public static PaymentCenter_Products paymentCenter_products = new PaymentCenter_Products();
	public static CommonObjectRepositories commonObjectRepositories = new CommonObjectRepositories();
	public static PayNow payNow = new PayNow();
	public static ProductDetails productDetails = new ProductDetails();
	public static Dashboard dashboard = new Dashboard();
	public static Products products = new Products();

	
	
	
	
	

	
	
	
}
