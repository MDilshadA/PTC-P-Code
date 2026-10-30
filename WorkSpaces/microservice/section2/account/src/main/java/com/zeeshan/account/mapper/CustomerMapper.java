package com.zeeshan.account.mapper;

import com.zeeshan.account.dto.CustomerDto;
import com.zeeshan.account.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer , CustomerDto customerDto){
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto , Customer customer){
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

}
