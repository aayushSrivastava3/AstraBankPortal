package com.project.astrabank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.astrabank.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
