package com.bogdan.onlinelibrary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user")
public class UserEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "user_id")
    private Integer id;
    @JoinColumn(name = "role_fk", referencedColumnName = "role_id")
    @ManyToOne
    private Role role;
    @JoinColumn(name = "credit_card_fk", referencedColumnName = "credit_card_id")
    @ManyToOne
    private CreditCard creditCard;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

}

