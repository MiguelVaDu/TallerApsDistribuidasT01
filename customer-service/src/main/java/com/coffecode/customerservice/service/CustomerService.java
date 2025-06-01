package com.coffecode.customerservice.service;

import com.coffecode.customerservice.dto.CreateCustomerRequestDTO;
import com.coffecode.customerservice.dto.CustomerDTO;
import com.coffecode.customerservice.dto.LoginRequestDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO registerCustomer(CreateCustomerRequestDTO request);
    CustomerDTO getCustomerById(Long id);
    CustomerDTO login(LoginRequestDTO request);
    List<CustomerDTO> getAllCustomers();
}
