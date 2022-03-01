package com.ligz.docker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    private String name;

    private String phoneNumber;

    private String email;

    private String bankCard;

    private String IdCard;

    private String age;

}
