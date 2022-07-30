package com.hitwh.face.shiro;

import com.hitwh.face.dao.UserDao;
import com.hitwh.face.entity.User;
import com.hitwh.face.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangrong
 * @date 2022/4/15 11:22
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("---------- 授权 ----------");
        // TODO 授权
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("---------- 认证 ----------");
        var userToken = (UsernamePasswordToken) token;
        User user = userDao.findByUsername(userToken.getUsername());
        if (user == null) {
            return null;
        }
        var subject = SecurityUtils.getSubject();
        var session = subject.getSession();
        session.setAttribute("user", user);

        return new SimpleAuthenticationInfo(user, user.getPassword(), "userRealm");
    }
}
