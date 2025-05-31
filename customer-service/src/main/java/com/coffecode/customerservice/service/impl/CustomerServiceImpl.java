package com.coffecode.customerservice.service.impl;

import com.coffecode.customerservice.dto.CreateCustomerRequestDTO;
import com.coffecode.customerservice.dto.CustomerDTO;
import com.coffecode.customerservice.entity.Customer;
import com.coffecode.customerservice.exception.CustomerNotFoundException;
import com.coffecode.customerservice.repository.CustomerRepository;
import com.coffecode.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerDTO registerCustomer(CreateCustomerRequestDTO request) {
        customerRepository.findByEmail(request.getEmail()).ifPresent(c -> {
            throw new IllegalStateException("El email ya está registrado");
        });

        Customer customer = new Customer();
        customer.setNombre(request.getNombre());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setDireccion(request.getDireccion());

        Customer savedCustomer = customerRepository.save(customer);
        return mapToDTO(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado con ID: " + id));
        return mapToDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CustomerDTO mapToDTO(Customer customer) {
        // Usando el Patrón Builder que añadimos en el DTO
        return CustomerDTO.builder()
                .id(customer.getId())
                .nombre(customer.getNombre())
                .email(customer.getEmail())
                .direccion(customer.getDireccion())
                .build();
    }
}