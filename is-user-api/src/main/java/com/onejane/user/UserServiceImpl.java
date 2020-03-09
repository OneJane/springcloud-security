package com.onejane.user;

import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public List<UserInfo> query(String name) {
        List<UserInfo> infoList = new ArrayList<>();
        List<User> userList  = userRepository.findByName(name);
        for (User user:userList) {
            infoList.add(user.buildInfo());
        }
        return infoList;
    }

    public UserInfo create(UserInfo info) {
        User user = new User();
        BeanUtils.copyProperties(info,user);
        user.setPassword(SCryptUtil.scrypt(user.getPassword(),32768,8,1));
        userRepository.save(user);
        info.setId(user.getId());
        return info;
    }

    public UserInfo update(UserInfo user) {
        return null;
    }

    public void delete(Long id) {

    }

    public UserInfo get(Long id) {
        return userRepository.findById(id).get().buildInfo();
    }

    @Override
    public User login(UserInfo info) {
        User result=null;
        User user = userRepository.findByUsername(info.getUsername());
        if(user!=null && SCryptUtil.check(info.getPassword(),user.getPassword())){
            result = user;
        }
        return result;
    }
}
