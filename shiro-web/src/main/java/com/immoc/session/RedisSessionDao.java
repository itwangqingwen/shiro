package com.immoc.session;
import com.immoc.redis.JedisUitl;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RedisSessionDao extends AbstractSessionDAO {

    private JedisUitl jedisUitl;

    private  final String SHIRO_SESSION_PREFIX = "imooc-session:";
    private void saveSession(Session session){
        if(session!=null && session.getId()!=null){
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUitl.set(key,value);
            jedisUitl.expire(key,600);
        }
    }
    private byte[] getKey (String key){
        return (SHIRO_SESSION_PREFIX+key).getBytes();
    }
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            return null;
        }
        byte[] key = getKey(sessionId.toString());
        byte[] value =jedisUitl.getKey(key);
        return (Session) SerializationUtils.deserialize(value);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if(session == null && session.getId()== null){
            return;
        }
        byte[] key =  getKey(session.getId().toString());
        jedisUitl.del(key);

    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisUitl.keys(SHIRO_SESSION_PREFIX);
        Set<Session> sessions = new HashSet<>();
        if(CollectionUtils.isEmpty(sessions)){
            return sessions;
        }
        for (byte[] key : keys) {
            Session session = (Session) SerializationUtils.deserialize(jedisUitl.getKey(key));
            sessions.add(session);
        }
        return sessions;
    }
}
