package io.jrekvs.net.conn.handler.redis.strings;

import io.jrekvs.memory.redis.RedisStorage;
import io.jrekvs.message.RedisMessage;
import io.jrekvs.net.conn.Connection;
import io.jrekvs.net.conn.handler.redis.AbstractRedisComandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Append 命令处理器
 *
 * @author Tommy.z
 */

public class RedisAppendCommandHandler extends AbstractRedisComandHandler {

    private static final Logger logger = LoggerFactory.getLogger(RedisAppendCommandHandler.class);

    @Override
    public void handle(Connection conn, RedisMessage message) {
        logger.debug("Append command handler...");

        String key = message.cmdParams()[1];
        String value = message.cmdParams()[2];
        String oldValue = (String) RedisStorage.getStringStorage().get(key);
        if(oldValue==null || "".equals(oldValue)){
            RedisStorage.getStringStorage().put(key,value);
            message.replay(":"+value.length()+"\r\n");
            writeResponseToClient(conn,message);
        }else{
            RedisStorage.getStringStorage().put(key,oldValue+value);
            message.replay(":"+(oldValue.length()+value.length())+"\r\n");
            writeResponseToClient(conn,message);
        }
    }
}
