package com.gis23.backend.service;

import com.gis23.backend.entity.User;

import java.io.IOException;

public interface UserService {
    void LoadUserData(String file_path) throws IOException;
    User userLogin(String username,String password,String user_role);
    User userInfo(String username,String user_role);
    String userInfoToJson(User user);
    String updatePasswordById(int user_id,String old_password,String new_password);
}
