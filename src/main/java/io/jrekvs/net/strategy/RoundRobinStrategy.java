package io.jrekvs.net.strategy;

import io.jrekvs.net.NIOReactor;

/**
 *  轮询策略
 * @author Tommy.z
 *
 */
public class RoundRobinStrategy implements ReactorStrategy{

	@Override
	public int getNextReactor(NIOReactor[] reactors, int lastreactor) {
		return (lastreactor + 1) % reactors.length;
	}
}
