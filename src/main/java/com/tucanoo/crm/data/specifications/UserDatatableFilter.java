package com.tucanoo.crm.data.specifications;

import com.tucanoo.crm.data.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

public class UserDatatableFilter implements org.springframework.data.jpa.domain.Specification<User> {

    String userQuery;

    public UserDatatableFilter(String queryString) {
        this.userQuery = queryString;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(userQuery)) {
            String lowerCaseQuery = userQuery.toLowerCase();  // Convert query to lowercase

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), '%' + lowerCaseQuery + '%'));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), '%' + lowerCaseQuery + '%'));
        }

        return (!predicates.isEmpty() ? criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])) : null);
    }
}
