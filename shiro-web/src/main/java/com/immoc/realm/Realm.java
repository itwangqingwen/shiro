package com.immoc.realm;
import com.immoc.dao.UserDao;
import com.immoc.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Realm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String name = (String) principalCollection.getPrimaryPrincipal();
        Set<String> role =getRoleByName(name);
        Set<String> permission = getPermissionByName(name);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(role);
        simpleAuthorizationInfo.setStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionByName(String name) {
        List<String> list = userDao.getPermissionByName(name);
        Set<String> sets = new HashSet<String>(list);
        return sets;

    }

    private Set<String> getRoleByName(String name) {
        List<String> list = userDao.getRoleByName(name);
        Set<String> sets = new HashSet<String>(list);
        return sets;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String name = (String) authenticationToken.getPrincipal();
        String password =  getPasByName(name);
        System.out.println(password);
        if(password ==null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name,password,"realm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(name));

        return simpleAuthenticationInfo;
    }

    private String getPasByName(String name) {
        User user = userDao.getPasByName(name);
        if(user!=null){
           return user.getPassword();
        }
        else{
            return null;
        }
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123","zs");
        System.out.println(md5Hash.toString());
    }
}

