import com.immoc.Realm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


public class demo1Test {
    @Test
    public void DemoTest(){
        Realm realm = new Realm();
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1);
        realm.setCredentialsMatcher(hashedCredentialsMatcher);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token  = new UsernamePasswordToken("zs","123");
        subject.login(token);
        System.out.println("isAuthentication:"+subject.isAuthenticated());
        subject.checkRole("admin");

    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123","immoc");
        System.out.println(md5Hash.toString());
    }


}
