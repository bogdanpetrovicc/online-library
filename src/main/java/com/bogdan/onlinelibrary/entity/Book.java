package com.bogdan.onlinelibrary.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "book_id")
    private Integer id;
    @JoinColumn(name = "author_fk", referencedColumnName = "author_id")
    @ManyToOne
    private Author author;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "genre")
    private String genre;
    @Column(name = "available")
    private Boolean available;
    @Column(name = "description")
    private String description;
}
