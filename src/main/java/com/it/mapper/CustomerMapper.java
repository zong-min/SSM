package com.it.mapper;

import com.it.pojo.Customer;

public interface CustomerMapper {
    // 根据客户 id 获取客户信息
    Customer findCustomerById(Integer id);

    // 添加客户信息
    Integer addCustomer(Customer customer);
}
