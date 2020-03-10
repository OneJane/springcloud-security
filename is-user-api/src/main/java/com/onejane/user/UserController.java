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


    /**
     * 127.0.0.1:8888/users/list?name='or 1=1 or name='  sql注入攻击查询所有用户信息
     * sql拼接存在sql注入，通过?进行替换用preparestatement预编译参数绑定
     * @param name
     * @return
     */
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
     *         yx2/123456  200
     * {
     * 	"name":"onejane",
     * 	"username":"onejane",
     * 	"password":"123456"
     * }
     * @param user  @RequestBody JSON自动转成对象
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

    /**
     * 身份认证
     * http://localhost:8888/users/get/2  快速请求提示 too many requests!!!,可以根据用户等级限流
     * Authorization Basic Auth yx2/123456 自动在Header中设置Authorization的值为Basic eXgyOjEyMzQ1Ng==
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get/{id}")
    public UserInfo get(@PathVariable Long id, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null || !user.getId().equals(id)){
            throw new RuntimeException("身份认证异常，获取用户信息失败");
        }
        return userService.get(id);
    }
}
