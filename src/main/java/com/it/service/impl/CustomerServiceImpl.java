package com.it.service.impl;

import com.it.mapper.CustomerMapper;
import com.it.pojo.Customer;
import com.it.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer queryCustomerById(Integer id) {
        return customerMapper.findCustomerById(id);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertCustomer(Customer customer) {
        customerMapper.addCustomer(customer);
    }
}
