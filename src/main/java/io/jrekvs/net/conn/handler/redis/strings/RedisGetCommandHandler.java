package io.jrekvs.net.conn.handler.redis.strings;

import io.jrekvs.memory.redis.RedisStorage;
import io.jrekvs.message.RedisMessage;
import io.jrekvs.net.conn.Connection;
import io.jrekvs.net.conn.handler.redis.AbstractRedisComandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;

/**
 * get 命令处理器
 @author Tommy.z
 */

public class RedisGetCommandHandler extends AbstractRedisComandHandler {

    private static final Logger logger = LoggerFactory.getLogger(RedisGetCommandHandler.class);

    @Override
    public void handle(Connection conn, RedisMessage message) {
        logger.debug("handle get command....");
        String[] cmdParams = message.cmdParams();
        String params = cmdParams[1];

        ConcurrentMap<String, Object> strStorage = RedisStorage.getStringStorage();
        if(!strStorage.containsKey(params)){
            message.addNilReply(message);
            writeResponseToClient(conn,message);
            return;
        }
        String value = (String)strStorage.get(params);
        if(value==null || value.equals("")){
            message.addNilReply(message);
        }else {
            message.replay("$" + value.length() + "\r\n" + value + "\r\n");
        }
        writeResponseToClient(conn,message);
    }
}
