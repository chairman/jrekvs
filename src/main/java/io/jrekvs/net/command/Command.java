package io.jrekvs.net.command;

import java.nio.charset.Charset;

import io.jrekvs.net.JrekvsGlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 命令接口
 * @author Tommy.z
 *
 */

public interface Command {
	
	Charset cs = Charset.forName (JrekvsGlobalConfig.defaultCahrset);
	
	static final Logger logger = LoggerFactory.getLogger(Command.class);
	
	public final int NREAD_ADD = 1;
	
	public final int NREAD_SET = 2;
	
	public final int NREAD_REPLACE = 3;
	
	public final int NREAD_APPEND = 4;
	
	public final int NREAD_PREPEND = 5;
	
	public final int NREAD_CAS = 6;

}
