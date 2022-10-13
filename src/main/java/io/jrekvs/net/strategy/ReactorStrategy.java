package io.jrekvs.net.strategy;

import io.jrekvs.net.NIOReactor;

/**
 * acceptor 线程分派连接给rector 线程的策略
 * @author Tommy.z
 *
 */
public interface ReactorStrategy {

	/**
	 * 获取下一个reactor
	 * @param reactors
	 * @return
	 */
	public int getNextReactor(NIOReactor[] reactors, int lastreactor);
	
}
