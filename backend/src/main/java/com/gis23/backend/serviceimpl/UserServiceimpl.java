package com.gis23.backend.serviceimpl;

import com.gis23.backend.entity.TrackLine;
import com.gis23.backend.entity.TrackPoint;
import com.gis23.backend.entity.User;
import com.gis23.backend.mapper.UserMapper;
import com.gis23.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    UserMapper userMapper;

    public void LoadUserData(String file_path) throws IOException{
        File file=new File(file_path);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line="";
        line= br.readLine();
        line= br.readLine();
        while (line!= null){
            String[] line_info=line.split(",");
            line= br.readLine();
            User user=new User();
            user.setUser_id(Integer.parseInt(line_info[0]));
            user.setUser_role(line_info[1]);
            user.setUsername(line_info[2]);
            user.setPassword(line_info[3]);
            user.setNickname(line_info[4]);
            user.setGender(line_info[5]);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date date=sdf.parse(line_info[5]);
            user.setBirthday(Date.valueOf(line_info[6]));
            user.setPhone(line_info[7]);
            user.setEmail(line_info[8]);
            user.setAddress(line_info[line_info.length-1]);
            userMapper.insertUser(user.getUser_id(), user.getUser_role(), user.getUsername(), user.getPassword(),user.getNickname(),user.getGender(),user.getBirthday() , user.getPhone(), user.getEmail(), user.getAddress());
        }
    }
    @Override
    public User userLogin(String username,String password,String user_role){
        return userMapper.userLogin(username,password,user_role);
    }
    @Override
    public User userInfo(String username,String user_role){
        return userMapper.userInfo(username,user_role);
    }

    @Override
    public String updatePasswordById(int user_id,String old_password,String new_password){
        User user=userMapper.findById(user_id);
        if (user != null){
            if (StringUtils.isEmpty(old_password)||StringUtils.isEmpty(new_password)){
                return "输入不能为空";
            }else if (!user.getPassword().equals(old_password)){
                return "原来密码错误";
            }
            else if (user.getPassword().equals(new_password)){
                return "新密码不能与旧密码相同！";
            }else if (user.getPassword().equals(old_password)){
                userMapper.updatePasswordById(user_id,new_password);
                return "密码修改成功";
            }else return "用户密码未修改";
        }
        else{
            return "用户不能为空";
        }

    }
    @Override
    public String userInfoToJson(User user){
        StringBuilder sb=new StringBuilder();
        sb.append("{\"data\":[");
        sb.append("{\"user_role\":").append(user.getUser_role()).append(",");
        sb.append("\"username\":").append(user.getUsername()).append(",");
        sb.append("\"nickname\":").append(user.getNickname()).append(",");
        sb.append("\"gender\":").append(user.getGender()).append(",");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(user.getBirthday());
        sb.append("\"birthday\":").append(dateString).append(",");
        sb.append("\"phone\":").append(user.getPhone()).append(",");
        sb.append("\"email\":").append(user.getEmail()).append(",");
        sb.append("\"address\":").append(user.getAddress()).append("}");
        sb.append("]}");
        return sb.toString();
    }



}
