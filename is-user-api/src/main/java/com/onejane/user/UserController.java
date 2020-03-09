package com.onejane.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public void login(@Validated UserInfo info,HttpServletRequest request){
        User user  = userService.login(info);
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        request.getSession(true).setAttribute("user",user);

    }

    @GetMapping("/logout")
    public void login(HttpServletRequest request){
       request.getSession().invalidate();
    }


    @GetMapping("/list")
    public List jdbcQuery(String name){
        String sql = "select id,name from user where name = '"+name+"'";
        List data   = jdbcTemplate.queryForList(sql);
        return data;
    }

    @GetMapping
    @ResponseBody
    public List<UserInfo> jpqQuer(String name){
        return userService.query(name);
    }


    /**
     * 127.0.0.1:8888/users/create
     * Authorization Basic Auth  数据库中yx1权限r yx2权限rw
     *         yx1/123456  401 forbidden
     *         yx2/123456  401 forbidden
     * {
     * 	"name":"onejane",
     * 	"username":"onejane",
     * 	"password":"123456"
     * }
     * @param user
     * @return
     */
    @PostMapping("/create")
    public UserInfo create(@RequestBody @Validated UserInfo user){
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserInfo update(@RequestBody UserInfo user){
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping("/get/{id}")
    public UserInfo get(@PathVariable Long id, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null || !user.getId().equals(id)){
            throw new RuntimeException("身份认证异常，获取用户信息失败");
        }
        return userService.get(id);
    }
}
