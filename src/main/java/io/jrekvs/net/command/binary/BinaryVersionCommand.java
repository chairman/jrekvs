package io.jrekvs.net.command.binary;

import java.io.IOException;

import io.jrekvs.enums.protocol.binary.ProtocolBinaryCommand;
import io.jrekvs.enums.protocol.binary.ProtocolResponseStatus;
import io.jrekvs.net.JrekvsGlobalConfig;
import io.jrekvs.net.command.BinaryCommand;
import io.jrekvs.net.conn.Connection;
import io.jrekvs.net.conn.handler.BinaryResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
	version
	
	Request:
	
	MUST NOT have extras.
	MUST NOT have key.
	MUST NOT have value.
	Response:
	
	MUST NOT have extras.
	MUST NOT have key.
	MUST have value.
	Request the server version.
	
	The server responds with a packet containing the version string in the body with the following format: "x.y.z"
 *  @author Tommy.z
 *
 */
public class BinaryVersionCommand implements BinaryCommand {
	
	private static final Logger logger = LoggerFactory.getLogger(BinaryVersionCommand.class);
	
	@Override
	public void execute(Connection conn) throws IOException {
		
		int keylen = conn.getBinaryRequestHeader().getKeylen();
		int bodylen = conn.getBinaryRequestHeader().getBodylen();
		int extlen  = conn.getBinaryRequestHeader().getExtlen();
		
		if (keylen == 0 && extlen == 0 && bodylen == 0) {
			logger.info("execute command quit ");
			BinaryResponseHeader header = buildHeader(conn.getBinaryRequestHeader(), ProtocolBinaryCommand.PROTOCOL_BINARY_CMD_VERSION.getByte(),null, JrekvsGlobalConfig.version.getBytes(),null,0l);
			writeResponse(conn,header,null,null,null);
		} else {
			writeResponse(conn, ProtocolBinaryCommand.PROTOCOL_BINARY_CMD_VERSION.getByte(), ProtocolResponseStatus.PROTOCOL_BINARY_RESPONSE_EINVAL.getStatus(), 0L);
		}
	}
}
