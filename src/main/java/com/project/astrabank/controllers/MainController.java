package com.project.astrabank.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.astrabank.models.Customer;
import com.project.astrabank.models.Deposit;
import com.project.astrabank.models.Withdraw;
import com.project.astrabank.repository.CustomerRepository;

@Controller
public class MainController {
	
	@Autowired
	CustomerRepository CR;
	
	//Homepage 
	@RequestMapping("/")
	public String welcomePage() {
		return "astrabank";
	}
	
	//new account creation page- 
	
	@GetMapping("/newaccount")
	public String accountCreation(Model model) {
		model.addAttribute(new Customer());	//adding customer model to the page
		return "newaccountpage";
	}
	@PostMapping("/submitted")
	public String accountCreated(@ModelAttribute Customer customer, Model model) {
		
		CR.save(customer);
		
		model.addAttribute("acc_no",customer.getAccount_no());
		model.addAttribute("name", customer.getName());
		model.addAttribute("bal", customer.getBalance());
		return "/success";
	}
	
	//Checking Balance Code -------
	
	@GetMapping("/chkbalance")
	public String chkBalance(Model model) {
		model.addAttribute(new Customer());	//adding customer model to the page
		return "check";
	}
	@PostMapping("/chkbal")
	public String displayBal(@RequestParam(name="a_no") int id, Model model) {
		// @Request Param will use parameter from our Model classs
		
		Customer temp = CR.getOne(id);
		
		model.addAttribute("bal", temp.getBalance());
		return "/currentBal";
	}
	
	// Withdraw code - 
	
	@GetMapping("/withdraw")
	public String withdrawPage(Model model) {
		model.addAttribute(new Withdraw());
		return "withdraw";
	}
	
	@PostMapping("/withdrawl")
	public String withdrawn(@ModelAttribute Withdraw withdraw, Model model) {
		
		Customer temp = CR.getOne(withdraw.getAccount_no());
		
		//updating in database-
		double new_bal= temp.getBalance()-withdraw.getAmount();
		temp.setBalance(new_bal);
		if(new_bal<0) {	//Checking if balance is negative
			return "failWT";
		}else {
			CR.save(temp);
			//sending Data to HTML page 
			model.addAttribute("withdrawn",withdraw.getAmount());
			model.addAttribute("new_bal",new_bal);
			return "/successWT";
		}
	}
	
	// ------ Deposit Code -
	@GetMapping("/deposit")
	public String depositMoney(Model model) {
		model.addAttribute(new Deposit());	//Unique cannot reuse withdraw strucutre
		return "deposit";
	}
	
	@PostMapping("/deposited")
	public String deposit(@ModelAttribute Deposit deposit, Model model) {
		
		Customer temp = CR.getOne(deposit.getAccount_no());
		
		//updating in database-
		double new_bal= temp.getBalance()+deposit.getAmount();
		temp.setBalance(new_bal);
		
			CR.save(temp);
			//sending Data to HTML page 
			model.addAttribute("deposited",deposit.getAmount());
			model.addAttribute("new_bal",new_bal);
			return "/successDP";
	}
}
