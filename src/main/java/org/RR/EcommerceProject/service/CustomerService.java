package org.RR.EcommerceProject.service;

import org.RR.EcommerceProject.dto.Customer;
import org.RR.EcommerceProject.dto.Seller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface CustomerService {


	String submitOtp(int id, int otp, HttpSession session);

	String loadRegister(ModelMap map);

	String loadRegister(Customer customer, BindingResult result, HttpSession session);

	String loadHome(HttpSession session);

	String viewProducts(HttpSession session, ModelMap map);

	String removeFromCart(int id, HttpSession session);

	String resendOtp(int id, HttpSession session);

	String viewCart(HttpSession session, ModelMap map);

	String checkout(HttpSession session, ModelMap map);

	String confirmOrder(HttpSession session, int id, String razorpay_payment_id);

	String viewOrders(HttpSession session, ModelMap map);

	String addToCartItem(HttpSession session, int id);

	String removeFromCartItem(HttpSession session, int id);

	String addToCart(HttpSession session, int id);
}
