package org.RR.EcommerceProject.controller;

import java.util.List;

import org.RR.EcommerceProject.dto.Product;
import org.RR.EcommerceProject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	ProductRepository productRepository;

	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		if (session.getAttribute("admin") != null)
			return "admin-home.html";
		else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

	@GetMapping("/products")
	public String displayProducts(HttpSession session, ModelMap map) {
		if (session.getAttribute("admin") != null) {
			List<Product> products = productRepository.findAll();
			if (products.isEmpty()) {
				session.setAttribute("failure", "No Products Added Yet");
				return "redirect:/admin/home";
			} else {
				map.put("products", products);
				return "admin-products.html";
			}
		} else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}
    @GetMapping("/approve-product/{id}")
    public String approveProduct(@PathVariable int id, HttpSession session,ModelMap map) {
    	if (session.getAttribute("admin") != null) {
    		Product product=productRepository.findById(id).orElseThrow();
    		product.setApproved(true);
    		productRepository.save(product);
    		
    		return "redirect:/admin/products";
    	}else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
    }
    @GetMapping("/reject-product/{id}")
    public String rejectProduct(@PathVariable int id, HttpSession session,ModelMap map) {
    	if (session.getAttribute("admin") != null) {
    		Product product=productRepository.findById(id).orElseThrow();
    		product.setApproved(false);
    		productRepository.save(product);
    		
    		return "redirect:/admin/products";
    	}else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
    }
}
