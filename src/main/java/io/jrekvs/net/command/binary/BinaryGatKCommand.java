package io.jrekvs.net.command.binary;

import java.io.IOException;

import io.jrekvs.enums.protocol.binary.ProtocolResponseStatus;
import io.jrekvs.net.command.BinaryCommand;
import io.jrekvs.net.conn.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * gotk 命令 
 * @author Tommy.z
 *
 */
public class BinaryGatKCommand implements BinaryCommand {
	
	private static final Logger logger = LoggerFactory.getLogger(BinaryGatKCommand.class);
		
	@Override
	public void execute(Connection conn) throws IOException {
		int keylen = conn.getBinaryRequestHeader().getKeylen();
		int bodylen = conn.getBinaryRequestHeader().getBodylen();
		int extlen  = conn.getBinaryRequestHeader().getExtlen();
		
		if (extlen == 0 && bodylen == keylen && keylen > 0) {
			process_bin_get_or_touch(conn);
		} else {
			writeResponse(conn, conn.getCurCommand().getByte(), ProtocolResponseStatus.PROTOCOL_BINARY_RESPONSE_EINVAL.getStatus(), 0L);
		}
	}
}
