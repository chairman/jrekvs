package io.jrekvs.net.command.binary;

import java.io.IOException;

import io.jrekvs.net.conn.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jrekvs.net.command.BinaryCommand;

/**
Request:

MUST NOT have extras.
MUST have key.
MUST have value.
Response:

MUST NOT have extras.
MUST NOT have key.
MUST NOT have value.
MUST have CAS
These commands will either append or prepend the specified value to the requested key.

 * @author Tommy.z
 *
 */
public class BinaryPrependCommand implements BinaryCommand{

	private static final Logger logger = LoggerFactory.getLogger(BinaryPrependCommand.class);

	@Override
	public void execute(Connection conn) throws IOException {
        if(logger.isDebugEnabled()){
			logger.debug("prepend command");
		}
        process_bin_append_prepend(conn);
	}

}
