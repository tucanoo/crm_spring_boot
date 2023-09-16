package com.tucanoo.crm.data.specifications;

import com.tucanoo.crm.data.entities.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

public class CustomerDatatableFilter implements org.springframework.data.jpa.domain.Specification<Customer> {

    String userQuery;

    public CustomerDatatableFilter(String queryString) {
        this.userQuery = queryString;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(userQuery)) {
            String lowerCaseQuery = userQuery.toLowerCase();  // Convert query to lowercase

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), '%' + lowerCaseQuery + '%'));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), '%' + lowerCaseQuery + '%'));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), '%' + lowerCaseQuery + '%'));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("emailAddress")), '%' + lowerCaseQuery + '%'));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), '%' + lowerCaseQuery + '%'));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("country")), '%' + lowerCaseQuery + '%'));
        }

        return (!predicates.isEmpty() ? criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])) : null);
    }
}
