package com.kimsong.digital_banking.generators;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class SequenceGenerator {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String generateAccountNumber() {
        Long next = ((Number) entityManager
            .createNativeQuery("SELECT nextval('seq_account_number')")
            .getSingleResult()).longValue();
        return String.format("%09d", next);
    }

    @Transactional
    public String generateCifNumber() {
        Long next = ((Number) entityManager
            .createNativeQuery("SELECT nextval('seq_cif_number')")
            .getSingleResult()).longValue();
        return String.format("%06d", next);
    }

}
