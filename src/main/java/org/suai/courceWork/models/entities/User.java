package org.suai.courceWork.models.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.suai.courceWork.models.enums.Role;
import org.suai.courceWork.models.forms.UserForm;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Bucket bucket;
    //+

    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private List<Order> orderList;
    //+

    public User(UserForm userForm){
        this.name = userForm.getName();
        this.password = userForm.getPassword();
        this.email = userForm.getEmail();
    }

}
