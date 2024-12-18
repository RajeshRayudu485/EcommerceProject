package org.RR.EcommerceProject.service.implementation;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.RR.EcommerceProject.dto.Customer;
import org.RR.EcommerceProject.dto.Product;
import org.RR.EcommerceProject.dto.Seller;
import org.RR.EcommerceProject.helper.Aes;
import org.RR.EcommerceProject.helper.CloudinaryHelper;
import org.RR.EcommerceProject.helper.MyEmailSender;
import org.RR.EcommerceProject.repository.CustomerRepository;
import org.RR.EcommerceProject.repository.ProductRepository;
import org.RR.EcommerceProject.repository.SellerRepository;
import org.RR.EcommerceProject.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service
public class SellerServiceImpl implements SellerService {
	@Autowired
	Seller seller;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	Product product;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CloudinaryHelper cloudinaryHelper;

	@Autowired
	SellerRepository sellerRepository;

	@Autowired
	MyEmailSender emailSender;

	@Override
	public String loadRegister(ModelMap map) {
		map.put("seller", seller);
		return "seller-registration.html";
	}

	@Override
	public String loadRegister(Seller seller, BindingResult result, HttpSession session) {
		if (!seller.getPassword().equals(seller.getConfirmpassword()))
			result.rejectValue("confirmPassword", "error.confirmPassword", "* Password Missmatch");
		if (sellerRepository.existsByEmail(seller.getEmail()) || customerRepository.existsByEmail(seller.getEmail()))
			result.rejectValue("email", "error.email", "* Email should be Unique");
		if (sellerRepository.existsByMobile(seller.getMobile())
				|| customerRepository.existsByMobile(seller.getMobile()))
			result.rejectValue("mobile", "error.mobile", "* Mobile Number should be Unique");
		if (result.hasErrors())
			return "seller-registration.html";
		else {
			int otp = new Random().nextInt(100000, 1000000);
			seller.setOtp(otp);
			seller.setPassword(Aes.encrypt(seller.getPassword(), "123"));
			sellerRepository.save(seller);
			emailSender.sendOtp(seller);
			session.setAttribute("id", seller.getId());
			return "redirect:/seller/otp";
		}
	}

	@Override
	public String submitOtp(int id, int otp, HttpSession session) {
		Seller seller = sellerRepository.findById(id).orElseThrow();
		if (seller.getOtp() == otp) {
			seller.setVerified(true);
			sellerRepository.save(seller);
			session.setAttribute("success", "Account Created Success");
			return "redirect:/";
		} else {
			session.setAttribute("failure", "Invalid OTP");
			session.setAttribute("id", seller.getId());
			return "redirect:/seller/otp";
		}
	}

	@Override
	public String loadHome(HttpSession session) {
		if (session.getAttribute("seller") != null)
			return "seller-home.html";
		else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String addProduct(HttpSession session, ModelMap map) {
		if (session.getAttribute("seller") != null) {
			map.put("product", product);
			return "add-product.html";
		} else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String addProduct(HttpSession session, @Valid Product product, BindingResult result, MultipartFile image) {
		if (session.getAttribute("seller") != null) {
			if (result.hasErrors()) {
				return "add-product.html";
			} else {
				product.setSeller((Seller) session.getAttribute("seller"));
				product.setImageLink(cloudinaryHelper.saveImage(image));
				productRepository.save(product);

				session.setAttribute("success", "Product Added Success");
				return "redirect:/seller/home";
			}
		} else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String viewProducts(HttpSession session, ModelMap map) {
		if (session.getAttribute("seller") != null) {
			Seller seller = ((Seller) session.getAttribute("seller"));
			List<Product> products = productRepository.findBySeller_id(seller.getId());
			if (products.isEmpty()) {
				session.setAttribute("failure", "No Products Added Yet");
				return "redirect:/seller/home";
			} else {
                map.put("products", products);
                return "seller-products.html";
			}
		} else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String deleteProduct(int id, HttpSession session, ModelMap map) {
		if (session.getAttribute("seller") != null) {
			productRepository.deleteById(id);
			session.setAttribute("success", "Product Deleted Success");
			return "redirect:/seller/manage-products";
		} else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String updateProduct(int id, HttpSession session, ModelMap map) {
		if (session.getAttribute("seller") != null) {
			Product product=productRepository.findById(id).orElseThrow();
			
			map.put("product", product);
			return "edit-product.html";
			
		}else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
		
	}

	@Override
	public String updateProduct(HttpSession session, @Valid Product product, BindingResult result,
			MultipartFile image) {
		if (session.getAttribute("seller") != null) {
			if (result.hasErrors()) {
				return "edit-product.html";
			} else {
				product.setSeller((Seller) session.getAttribute("seller"));
				
				try {
					if (image != null && image.getBytes() != null && image.getBytes().length > 0) {
					    product.setImageLink(cloudinaryHelper.saveImage(image));
					} else {
					    Product existingProduct = productRepository.findById(product.getId()).orElseThrow();
					    product.setImageLink(existingProduct.getImageLink());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				productRepository.save(product);
				
				session.setAttribute("success", "Product Updated Success");
				
				return "redirect:/seller/manage-products";
			}
		}else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}

	}

}
