package com.kimsong.digital_banking.models;

import com.kimsong.digital_banking.utils.EChannel;
import com.kimsong.digital_banking.utils.ECurrency;
import com.kimsong.digital_banking.utils.ETransactionStatus;
import com.kimsong.digital_banking.utils.ETransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_reference")
    private String transactionReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private ETransactionType transactionType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private ECurrency currency;

    @Column(name = "transaction_date")
    private Date transactionDate = Date.from(Instant.now());

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ETransactionStatus status;

    @Column(name = "channel")
    @Enumerated(EnumType.STRING)
    private EChannel channel;

    private String purpose;

}
