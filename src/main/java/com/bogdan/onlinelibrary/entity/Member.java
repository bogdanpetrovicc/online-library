package com.bogdan.onlinelibrary.entity;

import com.bogdan.onlinelibrary.entity.domain.MemberType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "member_id")
    private Integer id;
    @JoinColumn(name = "user_fk", referencedColumnName = "user_id")
    @OneToOne
    private User user;
    @Column(name = "member_number")
    private Integer memberNumber;
    @Column(name = "discount")
    private Integer discount;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MemberType type;

}
