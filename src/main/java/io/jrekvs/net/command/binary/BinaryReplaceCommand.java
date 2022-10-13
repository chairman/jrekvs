package io.jrekvs.net.command.binary;


import java.io.IOException;

import io.jrekvs.net.conn.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jrekvs.net.command.BinaryCommand;

/**
 * Created by qd on 2016/12/2.
 * @author  Tommy.z
 */
public class BinaryReplaceCommand implements BinaryCommand {
    private static final Logger logger = LoggerFactory.getLogger(BinaryReplaceCommand.class);

    @Override
    public void execute(Connection conn) throws IOException {
        if(logger.isDebugEnabled()){
			logger.debug("replace command");
		}
        process_bin_update(conn);
    }
}
