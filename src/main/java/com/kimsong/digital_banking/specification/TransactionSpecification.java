package com.kimsong.digital_banking.specification;

import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryFilter;
import com.kimsong.digital_banking.models.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionSpecification {
    public static Specification<Transaction> getSpecification(TransactionHistoryFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (Objects.nonNull(filter.getSearch())) {
                predicateList.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), "%" + filter.getSearch().toLowerCase().trim() + "%"));
            }
            if (Objects.nonNull(filter.getAccountNumber())) {
                predicateList.add(criteriaBuilder.equal(root.get("account").get("accountNumber"), filter.getAccountNumber()));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }

}
