package com.login.loginpage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String email;
    private String gender;
    private String password;

    @Column(name = "phone") // if your DB column is "phone"
    private String mobile;

    private String branch;

    private String year;   // ⚠️ keep as String to match dropdown values

    private String studentId;

    // ✅ added to match your form
   // @Lob
   // @Column(name = "profile_image", columnDefinition = "LONGBLOB") // MySQL
   // private byte[] profileImage;

}
