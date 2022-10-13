package io.jrekvs.net.conn.state;

import io.jrekvs.net.conn.Connection;

/**
 * 
 * 连接处理状态
 * @author Tommy.z
 *
 */
public interface ConnState {
	
	void doWork(Connection conn);

}
