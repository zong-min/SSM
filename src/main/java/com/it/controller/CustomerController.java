package com.it.controller;

import com.it.pojo.Customer;
import com.it.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/query/{id}")
    public String findCustomerService(@PathVariable Integer id, Model model) {
        Customer customer = customerService.queryCustomerById(id);
        model.addAttribute("customer", customer);
        // 返回客户信息展示页面
        return "customer";
    }
}
