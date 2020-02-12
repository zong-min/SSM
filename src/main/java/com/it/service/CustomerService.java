package com.it.service;

import com.it.pojo.Customer;

public interface CustomerService {
    // 查询客户信息
    Customer queryCustomerById(Integer id);

    // 添加新客户
    void insertCustomer(Customer customer);
}
