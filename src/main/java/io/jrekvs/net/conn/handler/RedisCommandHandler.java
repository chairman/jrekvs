package io.jrekvs.net.conn.handler;

import io.jrekvs.message.RedisMessage;
import io.jrekvs.net.conn.Connection;

/**
 * @author Tommy.z
 */
public interface RedisCommandHandler {

    /**
     * 处理command
     * @param conn
     * @param message
     */
    void handle(Connection conn, RedisMessage message);
}
