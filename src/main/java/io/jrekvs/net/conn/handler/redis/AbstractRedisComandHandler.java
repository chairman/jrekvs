package io.jrekvs.net.conn.handler.redis;

import io.jrekvs.message.RedisMessage;
import io.jrekvs.net.conn.Connection;
import io.jrekvs.net.conn.handler.RedisCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * @author Tommy.z
 */

public abstract class AbstractRedisComandHandler implements RedisCommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractRedisComandHandler.class);

    /**
     * 统一回复client
     * @param conn
     * @param message
     */
    protected  void writeResponseToClient(Connection conn, RedisMessage message){
        if(message.replay()!=null) {
            ByteBuffer writeBuf = ByteBuffer.wrap(message.replay().getBytes());
            writeBuf.compact();
            conn.addWriteQueue(writeBuf);
            conn.enableWrite(true);
        }else{
            if(logger.isWarnEnabled()){
                logger.warn("{} is not reply",message);
            }
        }
    }
}
