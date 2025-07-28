package com.gis23.backend.mapper;

import com.gis23.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;

@Mapper
public interface UserMapper {
    void insertUser(
            @Param("user_id") int user_id,
            @Param("user_role") String user_role,
            @Param("username") String username,
            @Param("password") String password,
            @Param("nickname") String nickname,
            @Param("gender") String gender,
            @Param("birthday") Date birthday,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("address") String address
    );
    User userLogin(
            @Param("username") String username,
            @Param("password") String password,
            @Param("user_role") String user_role
    );
    User userInfo(
            @Param("username") String username,
            @Param("user_role") String user_role
    );
    User findById(
            @Param("user_id") int user_id
    );
    void updatePasswordById(
            @Param("user_id") int user_id,
            @Param("password") String password
    );
}
