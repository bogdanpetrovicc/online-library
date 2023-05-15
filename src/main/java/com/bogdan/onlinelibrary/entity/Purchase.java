package com.bogdan.onlinelibrary.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "purchase")
public class Purchase extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Integer id;
    @JoinColumn(name = "book_fk", referencedColumnName = "book_id")
    @ManyToOne
    private Book book;
    @JoinColumn(name = "member_fk", referencedColumnName = "member_id")
    @ManyToOne
    private Member member;
}
