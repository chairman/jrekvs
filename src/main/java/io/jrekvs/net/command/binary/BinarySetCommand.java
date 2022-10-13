package io.jrekvs.net.command.binary;

import java.io.IOException;

import io.jrekvs.net.command.BinaryCommand;
import io.jrekvs.net.conn.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * set 命令 
 * @author  Tommy.z
 *
 */
public class BinarySetCommand implements BinaryCommand {
	
	private static final Logger logger = LoggerFactory.getLogger(BinarySetCommand.class);

	@Override
	public void execute(Connection conn) throws IOException {
		if(logger.isDebugEnabled()){
			logger.debug("execute set command");
		}
		process_bin_update(conn);
		
	}
}
