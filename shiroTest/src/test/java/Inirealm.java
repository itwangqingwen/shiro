import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class Inirealm {
    DruidDataSource druidDataSource = new DruidDataSource();
    {
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/shirotest");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
    }

    @Test
    public void inirealmTest(){
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(druidDataSource);
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        jdbcRealm.setPermissionsLookupEnabled(true);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject s = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zs","123");
        s.login(token);
        System.out.println("isAuthenticated:"+ s.isAuthenticated());
        s.checkPermission("user:delete");
        s.checkRole("admin");


    }
}
