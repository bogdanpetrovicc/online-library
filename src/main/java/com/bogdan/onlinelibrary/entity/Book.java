package com.bogdan.onlinelibrary.entity;

import com.bogdan.onlinelibrary.entity.domain.Genre;
import lombok.AllArgsConstructor;
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
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;

    public Book(Integer id, Author author, String name, Double price, Integer amount) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
}
