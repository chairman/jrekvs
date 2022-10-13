package io.jrekvs.net.conn.handler;

import io.jrekvs.message.RedisMessage;
import io.jrekvs.net.conn.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * redis io handler
 * @author Tommy.z
 */
public class RedisIOHandler implements IOHandler {

    private static final Logger logger = LoggerFactory.getLogger(Connection.class);

    public static final int REDIS_OK = 0, REDIS_ERR = -1, REDIS_INLINE_MAX_SIZE = 1024 * 64;

    @Override
    public boolean doReadHandler(Connection conn) throws IOException {
        RedisMessage redisMessage = new RedisMessage(conn.getReadDataBuffer(),
            conn.getLastMessagePos());

        while (redisMessage.position() < redisMessage.limit()) {
            redisMessage.replay(null);
            int result = processMultibulkBuffer(redisMessage);
            conn.getReadDataBuffer().position(redisMessage.position());
            conn.setLastMessagePos(redisMessage.position());

            if (result == REDIS_OK) {
                processCommand(conn,redisMessage);
            }

            if (redisMessage.replay() != null) {
                ByteBuffer writeBuf = ByteBuffer.wrap(redisMessage.replay().getBytes());
                writeBuf.compact();
                conn.addWriteQueue(writeBuf);
                conn.enableWrite(true);
            }
        }

        return true;
    }

    private int processMultibulkBuffer(RedisMessage redisMessage) {
        String message = redisMessage.message();
        logger.debug(message);

        if (message.charAt(0) != '*') {
            discardCurrentCmd(redisMessage, message);
            redisMessage.addErrReplay(redisMessage,
                "Protocol error: expected '*', got '" + message.charAt(0) + "'");
            return REDIS_ERR;
        }

        int lineEndPos = message.indexOf("\r\n");
        if (lineEndPos < 0) {
            return REDIS_ERR;
        }

        int multibulkLen;
        try {
            multibulkLen = Integer.parseInt(message.substring(1, lineEndPos));
            lineEndPos += 2;// 读取/r/n
        } catch (NumberFormatException e) {
            discardCurrentCmd(redisMessage, message);
            return REDIS_ERR;
        }

        if (multibulkLen > 1024 * 1024) {
            redisMessage.addErrReplay(redisMessage, "Protocol error: invalid multibulk length");
            discardCurrentCmd(redisMessage, message);
            return REDIS_ERR;
        }

        if (multibulkLen <= 0) {
            return REDIS_OK; // "Multibulk processing could see a <= 0 length"
        }

        String[] cmdParams = new String[multibulkLen];
        while (multibulkLen > 0) {
            int index = lineEndPos;
            lineEndPos = message.indexOf("\r\n", index);
            if (lineEndPos < 0) {
                return REDIS_ERR;
            }
            if (lineEndPos - index > REDIS_INLINE_MAX_SIZE) {
                discardCurrentCmd(redisMessage, message);
                redisMessage.addErrReplay(redisMessage, "Protocol error: too big bulk count string");
                return REDIS_ERR;
            }

            if (message.charAt(index) != '$') {
                discardCurrentCmd(redisMessage, message);
                redisMessage.addErrReplay(redisMessage,
                    "Protocol error: expected '$', got '" + message.charAt(index) + "'");
                return REDIS_ERR;
            }
            index++;// 读取$

            int bulkLen = 0;
            try {
                bulkLen = Integer.parseInt(message.substring(index, lineEndPos));
                lineEndPos += 2;// 读取/r/n
            } catch (NumberFormatException e) {
                discardCurrentCmd(redisMessage, message);
                redisMessage.addErrReplay(redisMessage, "Protocol error: invalid bulk length");
                return REDIS_ERR;
            }

            if (bulkLen < 0 || bulkLen > 512 * 1024 * 1024) {
                discardCurrentCmd(redisMessage, message);
                redisMessage.addErrReplay(redisMessage, "Protocol error: invalid bulk length");
                return REDIS_ERR;
            }

            index = lineEndPos;
            lineEndPos = message.indexOf("\r\n", index);
            if (lineEndPos < 0) {
                return REDIS_ERR;
            }

            cmdParams[cmdParams.length - multibulkLen] = message.substring(index, lineEndPos);
            lineEndPos += 2;// 读取/r/n
            multibulkLen--;
        }

        redisMessage.position(lineEndPos);
        redisMessage.cmdParams(cmdParams);
        return REDIS_OK;
    }

    /**
     * 丢弃当前指令
     *
     * @param redisMessage redis报文信息
     * @return 如果有下一条指令：返回true; 否则：返回false
     */
    private void discardCurrentCmd(RedisMessage redisMessage, String message) {
        int nextCmdPos = message.indexOf('*');
        if (nextCmdPos > 0) {
            // 清理nextCmdPos前无法解析的字符
            redisMessage.position(nextCmdPos);
        } else {
            // 清理无法解析的字符
            redisMessage.position(redisMessage.limit());
        }
    }

    private void processCommand(Connection conn,RedisMessage redisMessage) {
        logger.debug(Arrays.toString(redisMessage.cmdParams()));
        // 根据命令参数，获取不同的handler
        RedisCommandHandlerFactory.getHandler(redisMessage.cmdParams()[0]).handle(conn,redisMessage);
    }


}