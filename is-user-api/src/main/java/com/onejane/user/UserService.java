package com.onejane.user;

import java.util.List;

public interface UserService {

    List<UserInfo> query(String name);

    UserInfo create(UserInfo user);

    UserInfo update(UserInfo user);

    void delete(Long id);

    UserInfo get(Long id);

    User login(UserInfo user);
}
