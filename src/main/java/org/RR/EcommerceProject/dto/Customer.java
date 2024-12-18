package org.RR.EcommerceProject.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
@Entity
@Component
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min = 3,max = 15,message = "*Enetr Between 3~15 Characters ")
	private String name;
	@DecimalMin(value = "6000000000", message = "Enter Proper Mobile Number")
	@DecimalMax(value = "9999999999",message = "Enter Proper Mobile Number")
	@NotNull(message = "Enter Proper Mobile Number")
	private long mobile;
	@Email(message = "Enter Proper Email")
	@NotEmpty(message = "Enter Proper Email")
	private String email;
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
	private String password;
	@Transient
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
	private String confirmpassword;
	@Size(min = 6,max = 150,message = "Enter Between 6~150 Characters")
	private String address;
	private boolean verified;
	private int otp;
	
	@OneToOne(cascade = CascadeType.ALL)
	Cart cart = new Cart();
	
}
