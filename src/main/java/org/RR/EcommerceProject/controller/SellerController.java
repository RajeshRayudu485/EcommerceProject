package org.RR.EcommerceProject.controller;

import org.RR.EcommerceProject.dto.Product;
import org.RR.EcommerceProject.dto.Seller;
import org.RR.EcommerceProject.service.SellerService;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/seller")
public class SellerController {
	
	@Autowired
	SellerService sellerService;

	@GetMapping("/register")
	public String sellerRegistration(ModelMap map) {
		return sellerService.loadRegister(map);
	}

	@PostMapping("/register")
	public String SellerRegistartion(@Valid Seller seller, BindingResult result, HttpSession session) {
		return sellerService.loadRegister(seller, result, session);
	}

	@GetMapping("/otp")
	public String loadOtpPage() {
		return "seller-otp.html";
	}

	@PostMapping("/submit-otp/{id}")
	public String postMethodName(@PathVariable int id, @RequestParam int otp, HttpSession session) {
		return sellerService.submitOtp(id, otp, session);
	}
	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		return sellerService.loadHome(session);
	}
	
	@GetMapping("/add-product")
	public String addProduct(HttpSession session,ModelMap map) {
		return sellerService.addProduct(session,map);
	}
	
	@PostMapping("/add-product")
	public String addProduct(HttpSession session,@Valid Product product,BindingResult result,@RequestParam MultipartFile image) {
		return sellerService.addProduct(session,product,result,image);
	}
	
	@GetMapping("/manage-products")
	public String viewProducts(HttpSession session,ModelMap map) {
		return sellerService.viewProducts(session,map);
	}
	
	@GetMapping("/delete-product/{id}")
	public String deleteProduct(@PathVariable int id, HttpSession session,ModelMap map) {
		return sellerService.deleteProduct(id,session,map);
	}
	
	@GetMapping("/edit-product/{id}")
	public String updateProduct(@PathVariable int id,HttpSession  session,ModelMap map) {
		return sellerService.updateProduct(id,session,map);
		
	}
	@PostMapping("/update-product")
	public String updateProduct(HttpSession session,@Valid Product product,BindingResult result,@RequestParam MultipartFile image) {
		return sellerService.updateProduct(session,product,result,image);
	}
}
