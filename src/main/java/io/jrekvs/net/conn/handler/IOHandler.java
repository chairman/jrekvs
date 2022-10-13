package io.jrekvs.net.conn.handler;

import java.io.IOException;

import io.jrekvs.net.conn.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface IOHandler{
	
    Logger logger = LoggerFactory.getLogger(IOHandler.class);

	default void onClosed(Connection conn, String reason){}

	default void onConnected(Connection conn) throws IOException {
		logger.debug("onConnected(): {}", conn);
	}

	public boolean doReadHandler(Connection conn) throws IOException;

}