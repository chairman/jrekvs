package io.jrekvs.net.conn.handler;

import io.jrekvs.net.conn.handler.redis.strings.RedisGetCommandHandler;
import io.jrekvs.net.conn.handler.redis.strings.RedisSetCommandHandler;

/**
 * command handler 工厂类
 * @author Tommy.z
 */

public final class RedisCommandHandlerFactory {

    /**
     * 根据不同的cmd，返回不同的命令处理器
     * @param cmd
     * @return
     */
    public static RedisCommandHandler getHandler(String cmd){
        switch (cmd){
            case "get":
                return  new RedisGetCommandHandler();
            case "set":
                return new RedisSetCommandHandler();
        }
        return null;
    }
}
