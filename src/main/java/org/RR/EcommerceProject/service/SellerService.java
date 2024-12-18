package org.RR.EcommerceProject.service;

import org.RR.EcommerceProject.dto.Product;
import org.RR.EcommerceProject.dto.Seller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
public interface SellerService {

	String loadRegister(ModelMap map);

	String loadRegister(Seller seller, BindingResult result, HttpSession session);

	String submitOtp(int id, int otp, HttpSession session);

	String loadHome(HttpSession session);

	String addProduct(HttpSession session, ModelMap map);

	String addProduct(HttpSession session, @Valid Product product, BindingResult result, MultipartFile image);

	String viewProducts(HttpSession session, ModelMap map);

	String deleteProduct(int id, HttpSession session, ModelMap map);

	String updateProduct(int id, HttpSession session, ModelMap map);

	String updateProduct(HttpSession session, @Valid Product product, BindingResult result, MultipartFile image);

}
