package com.tucanoo.crm.services;

import com.tucanoo.crm.data.entities.Customer;
import com.tucanoo.crm.data.repositories.CustomerRepository;
import com.tucanoo.crm.data.specifications.CustomerDatatableFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Page<Customer> getCustomersForDatatable(String queryString, Pageable pageable) {

        CustomerDatatableFilter customerDatatableFilter = new CustomerDatatableFilter(queryString);

        return customerRepository.findAll(customerDatatableFilter, pageable);
    }
}
