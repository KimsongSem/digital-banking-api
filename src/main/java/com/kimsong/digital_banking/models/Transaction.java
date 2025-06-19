package com.kimsong.digital_banking.models;

import com.kimsong.digital_banking.utils.EChannel;
import com.kimsong.digital_banking.utils.ETransactionStatus;
import com.kimsong.digital_banking.utils.ETransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private ETransactionType transactionType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(length = 3)
    private String currency = "USD";

    @Column(name = "transaction_date")
    private Date transactionDate = Date.from(Instant.now());

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ETransactionStatus status = ETransactionStatus.SUCCESS;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "channel")
    @Enumerated(EnumType.STRING)
    private EChannel channel;

    @Column(name = "purpose")
    private String purpose;

}
