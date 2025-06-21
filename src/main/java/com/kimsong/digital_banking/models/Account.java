package com.kimsong.digital_banking.models;

import com.kimsong.digital_banking.utils.EAccountStatus;
import com.kimsong.digital_banking.utils.EAccountType;
import com.kimsong.digital_banking.utils.ECurrency;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "account_number", unique = true)
    private Integer accountNumber;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private EAccountType accountType;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private ECurrency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EAccountStatus status = EAccountStatus.ACTIVE;

    @Column(name = "opened_at")
    private Date openedAt = Date.from(Instant.now());

    @Column(name = "closed_at")
    private Date closedAt;

    @Column(name = "created_at")
    private Date createdAt = Date.from(Instant.now());

    @Column(name = "created_by")
    private String createBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

}
