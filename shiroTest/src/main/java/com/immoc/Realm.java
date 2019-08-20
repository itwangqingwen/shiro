package com.immoc;

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

import java.util.HashSet;
import java.util.Set;

public class Realm extends AuthorizingRealm {
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
        Set<String> s = new HashSet<String>();
        if (name =="zs"){
            s.add("user:add");
            s.add("user:delete");
            return s;
        }else {
            return null;
        }
    }

    private Set<String> getRoleByName(String name) {
        Set<String> s = new HashSet<String>();
        if (name =="zs"){
            s.add("admin");
            return s;
        }else {
            return null;
        }
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String name = (String) authenticationToken.getPrincipal();
        String password =  getPasByName(name);
        if(password ==null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("zs",password,"Realm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("immoc"));

        return simpleAuthenticationInfo;
    }

    private String getPasByName(String name) {
        if(name == "zs"){
            Md5Hash md5Hash = new Md5Hash("123","immoc");
            System.out.println("true");
            return  md5Hash.toString();
        }
        else{
            return null;
        }
    }
}

