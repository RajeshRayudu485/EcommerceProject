package org.RR.EcommerceProject.controller;

import org.RR.EcommerceProject.dto.Customer;
import org.RR.EcommerceProject.dto.Seller;
import org.RR.EcommerceProject.repository.CustomerRepository;
import org.RR.EcommerceProject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerCotroller {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerService customerService;

	@GetMapping("/register")
	public String loadRegister(ModelMap map) {
		return customerService.loadRegister(map);
	}

	@PostMapping("/register")
	public String SellerRegistartion(@Valid Customer customer, BindingResult result, HttpSession session) {
		return customerService.loadRegister(customer, result, session);
	}

	@GetMapping("/otp")
	public String loadOtpPage() {
		return "customer-otp.html";
	}

	@PostMapping("/submit-otp/{id}")
	public String postMethodName(@PathVariable int id, @RequestParam int otp, HttpSession session) {
		return customerService.submitOtp(id, otp, session);
	}
	
	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		return customerService.loadHome(session);
	}
	
	@GetMapping("/products")
	public String viewProducts(HttpSession session,ModelMap map) {
		return customerService.viewProducts(session,map);
	}
	
	@GetMapping("/cart-add/{id}")
	public String addToCart(@PathVariable int id ,HttpSession session,ModelMap map) {
		return customerService.addToCart(session,id);
	}
	@GetMapping("/cart-remove/{id}")
	public String removeFromCart(@PathVariable int id ,HttpSession session,ModelMap map) {
		return customerService.removeFromCart(id,session );
	}
	@GetMapping("/resend-otp/{id}")
	public String resendOtp(@PathVariable int id,HttpSession session) {
		return customerService.resendOtp(id,session);
	}

	@GetMapping("/cart-add/item/{id}")
	public String addToCartItem(HttpSession session,@PathVariable int id) {
		return customerService.addToCartItem(session,id);
	}
	
	@GetMapping("/cart-remove/item/{id}")
	public String removeFromCartItem(HttpSession session,@PathVariable int id) {
		return customerService.removeFromCartItem(session,id);
	}
	@GetMapping("/cart")
	public String viewCart(HttpSession session,ModelMap map) {
		return customerService.viewCart(session,map);
	}
	

	@GetMapping("/checkout")
	public String checkout(HttpSession session,ModelMap map) {
		return customerService.checkout(session,map);
	}
	
	@PostMapping("/confirm-order/{id}")
	public String confirmOrder(HttpSession session,@PathVariable int id,@RequestParam String razorpay_payment_id) {
		return customerService.confirmOrder(session,id,razorpay_payment_id);
	}

	@GetMapping("/orders")
	public String viewOrders(HttpSession session,ModelMap map) {
        return customerService.viewOrders(session,map);
    }
}
