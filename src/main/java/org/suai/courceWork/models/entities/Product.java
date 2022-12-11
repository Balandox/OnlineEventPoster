package org.suai.courceWork.models.entities;


import lombok.*;
import org.hibernate.annotations.Cascade;
import org.suai.courceWork.models.enums.Category;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;

    @Column(name = "img_name")
    private String imgName;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEvent;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @ManyToMany(mappedBy = "productList", cascade = CascadeType.ALL)
    private List<Bucket> bucketList;
    //+

    @OneToMany(mappedBy = "product")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private List<OrderDetails> orderDetails;
    //+

}
