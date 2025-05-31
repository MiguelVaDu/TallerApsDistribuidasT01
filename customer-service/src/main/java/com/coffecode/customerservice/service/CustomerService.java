package com.coffecode.customerservice.service;

import com.coffecode.customerservice.dto.CreateCustomerRequestDTO;
import com.coffecode.customerservice.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO registerCustomer(CreateCustomerRequestDTO request);
    CustomerDTO getCustomerById(Long id);
    List<CustomerDTO> getAllCustomers();
}
