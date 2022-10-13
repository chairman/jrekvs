package io.jrekvs.net.command.binary;

import java.io.IOException;

import io.jrekvs.net.command.BinaryCommand;
import io.jrekvs.net.conn.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Tommy.z
 */
public class BinaryAddCommand implements BinaryCommand {
    private static final Logger logger = LoggerFactory.getLogger(BinaryAddCommand.class);

    @Override
    public void execute(Connection conn) throws IOException {
        if(logger.isDebugEnabled()){
			logger.debug("add command");
		}
        process_bin_update(conn);
    }
}
