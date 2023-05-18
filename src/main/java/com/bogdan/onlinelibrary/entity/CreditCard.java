package com.bogdan.onlinelibrary.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "credit_card")
public class CreditCard extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "credit_card_id")
    private Integer id;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "bank")
    private String bank;
    @Column(name = "balance")
    private Double balance;

}
