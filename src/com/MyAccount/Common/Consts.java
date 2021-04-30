package com.MyAccount.Common;

public class Consts {

	public String url_registration = "/#/account-details";
	public String url_forgotUsername = "/#/forgot-username";
	public String url_forgotPassword = "/#/forgot-password";
	public String url_reset_password = "/#/reset-password";
	public String url_dashboard = "/#/dashboard";
	public String url_welcome = "/welcome";
	public String url_myProfile = "/#/my-profile";
	public String url_paymentMethods = "/#/payment-center/payment-methods";
	public String url_paymentCenter_products = "/#/payment-center/products";
	public String url_productDetails = "/#/product-details/";
	
	
	public String val_msg_numeric_planNumber_ = "Plan number should be numeric.";
	public String val_msg_planNo_ten_digits = "Plan number should be of 10 digits.";
	public String val_msg_invalid = "Information does not match! Validation Failed!";
	public String val_msg_email_format = "The email address you have entered is not valid.";
	public String val_msg_exist_user = "User already exists in system.";
	public String val_msg_login_incorrect_credentials = "The entered Username and/or Password did not match. Please re-enter.";
	public String val_msg_forgot_username_incorrect_credentials = "No such policy found.";
	public String val_msg_forgot_password_incorrect_credentials = "No match found for the entered details. Please re-enter the correct details.";
	public String val_msg_forgot_password_incorrect_security_ans = "The entered answer did not match with our records. Please re-enter the correct answer and try again.";
	public String val_msg_state_and_zipCode_combination = "The information you provided is not valid.";
	public String val_msg_phoneNo_ten_digits = "Please enter a 10-digit phone number.";
	public String val_msg_new_password_not_same_current_password = "Your new Password can not be same as your current password.";
	public String val_msg_old_password_not_same_current_password = "Your password could not be updated. The Old Password does not match our records.";
	public String val_msg_validating_card = "Error. We were unable to update this payment option. Please check your account or card information and try again or enter an alternate form of payment.";
	public String val_msg_exceeded_the_max_length = "it has exceeded the max length of: [9]";
	public String val_msg_routingNum_and_confirmRoutingNum_notMatching = "Your routing numbers do not match.";
	public String val_msg_invalid_routingNum = "Invalid Transit Routing Number";
	public String val_msg_accountNum_and_confirmAccountNum_notMatching = "Your account numbers do not match.";

	public String succ_msg_profile_update_succ = "Your contact information has been successfully updated.";
	
	public String succ_password_update_succ = "Your password has been successfully updated.";
	public String succ_msg_security_update_succ = "Your security question has been successfully updated.";
	public String succ_msg_record_deleted_succ = "Record deleted successfully!";
	public String succ_msg_paymentMethod_add_succ = "Your payment method has been successfully added.";
	public String succ_msg_paymentMethod_update_succ = "Your payment method has been successfully added.";
	public String succ_msg_payment_update_succ = "Updating payment is successfull";
	public String succ_msg_chang_autoPay = "Your payment updates were made successfully.";
	public String succ_msg_delete_paymentMethod = "Your payment option has been successfully removed.";
	public String succ_msg_removed_autoPay = "removed from Auto Pay.";
	public String info_msg_used_for_autoPay = "This payment method cannot be deleted because it is being used for Auto Pay. Please update your Auto Pay payment method prior to deleting.";

	public String routingNumber = "091000019";
	public String accountNumber ="3310115755";
	public String accountNumber2 ="3310125755";

	public String paymentType_Checking ="Checking-5755";
	
	
	// MasterCard
	public String cardType_MasterCard = "MasterCard";
	public String cardNumber_MasterCard = "5555555555554444"; 
	public String paymentType_MasterCard = cardType_MasterCard+"-4444"; 
	
	// VISA
	public String cardType_VISA = "VISA";
	public String cardNumber_VISA = "4012888888881881"; 
	public String paymentType_VISA = cardType_VISA+"-1881";

	// Discovery
	public String cardType_Discovery = "Discover";
	public String cardNumber_Discovery = "6011000990139424"; 
	public String paymentType_Discovery = cardType_Discovery+"-9424";

	public String ddl_value_add_new_paymentMethod = "ADD_NEW_PAYMENT_METHOD";
	public String paymentFrequecy_Monthly = "Monthly";
	public String paymentFrequecy_Quarterly = "Quarterly";
	public String paymentFrequecy_SemiAnnually = "Semi-Annually";
	public String paymentFrequecy_Annually = "Annually";

	public String product_status_due_now = "Due now";
	public String product_status_past_due = "Past Due";
	public String product_status_auto_pay = "Auto Pay";

	
	public String frequecyMonthly = "Monthly";
	public String frequecyQuarterly = "Quarterly";
	public String frequecySemiAnnually = "Semi-Annually";
	public String frequecyAnnually = "Annually";

	public String modal_title_ = "Change Auto Pay or Frequency";

	
}
