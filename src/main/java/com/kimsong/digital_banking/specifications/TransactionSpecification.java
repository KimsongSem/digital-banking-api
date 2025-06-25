package com.kimsong.digital_banking.specifications;

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
            if (Objects.nonNull(filter.getKeyword())) {
                predicateList.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("transactionReference"), filter.getKeyword()),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("purpose")), "%" + filter.getKeyword().toLowerCase().trim() + "%")));
            }
            if (Objects.nonNull(filter.getAccountNumber())) {
                predicateList.add(criteriaBuilder.equal(root.get("account").get("accountNumber"), filter.getAccountNumber()));
            }
            if (Objects.nonNull(filter.getTransactionReference())) {
                predicateList.add(criteriaBuilder.equal(root.get("transactionReference"), filter.getTransactionReference()));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }

}
