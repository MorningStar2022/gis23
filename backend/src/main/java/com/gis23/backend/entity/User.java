package com.gis23.backend.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class User {
    private int user_id;
    private String user_role;
    private String username;
    private String password;
    private String nickname;
    private String gender;
    private Date birthday;
    private String phone;
    private String email;
    private String address;
}
