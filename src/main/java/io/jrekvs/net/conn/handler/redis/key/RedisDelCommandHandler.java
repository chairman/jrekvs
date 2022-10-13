package io.jrekvs.net.conn.handler.redis.key;

import io.jrekvs.memory.redis.RedisStorage;
import io.jrekvs.message.RedisMessage;
import io.jrekvs.net.conn.Connection;
import io.jrekvs.net.conn.handler.redis.AbstractRedisComandHandler;
import io.jrekvs.net.conn.handler.redis.strings.RedisGetCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * del 命令处理器
 *
 * @author Tommy.z
 */

public class RedisDelCommandHandler extends AbstractRedisComandHandler {

    private static final Logger logger = LoggerFactory.getLogger(RedisGetCommandHandler.class);

    @Override
    public void handle(Connection conn, RedisMessage message) {
        logger.debug("del command handler...");

        String key = message.cmdParams()[1];

        boolean exists = RedisStorage.getStringStorage().containsKey(key);
        if(!exists){
            message.replay(":0\r\n");
            writeResponseToClient(conn,message);
            return;
        }
        RedisStorage.getStringStorage().remove(key);
        message.replay(":1\r\n");
        writeResponseToClient(conn,message);
    }
}
