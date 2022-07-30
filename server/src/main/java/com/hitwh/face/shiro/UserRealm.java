package com.hitwh.face.shiro;

import com.hitwh.face.dao.UserDao;
import com.hitwh.face.entity.User;
import com.hitwh.face.entity.EquipmentGroup;
import com.hitwh.face.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

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
        String username = (String) principalCollection.getPrimaryPrincipal();
        Set<Role> roles = userDao.findByUsername(username).getRoles();
        Set<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toSet());
        Set<String> groupNames = roles.stream().flatMap(role -> role.getGroups().stream().map(EquipmentGroup::getName))
                .collect(Collectors.toSet());

        var info = new SimpleAuthorizationInfo();
        info.setRoles(roleNames);
        info.setStringPermissions(groupNames);
        return info;
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
