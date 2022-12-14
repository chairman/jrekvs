package io.jrekvs.net.command.binary;

import java.io.IOException;

import io.jrekvs.net.conn.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jrekvs.enums.protocol.binary.ProtocolResponseStatus;
import io.jrekvs.net.command.BinaryCommand;


/**
 * Touch is used to set a new expiration time for an existing item. 
 * GAT (Get and touch) and GATQ will return the value 
 * for the object if it is present in the cache.
 * 
 * Request:

	MUST have extras.
	MUST have key.
	MUST NOT have value.

	Extra data for set/add/replace:

     Byte/     0       |       1       |       2       |       3       |
        /              |               |               |               |
       |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
       +---------------+---------------+---------------+---------------+
      0| Expiration                                                    |
       +---------------+---------------+---------------+---------------+
       Total 4 bytes
       
 * @author Tommy.z
 *
 */
public class BinaryGatCommand implements BinaryCommand{
	
	private static final Logger logger = LoggerFactory.getLogger(BinaryGatCommand.class);
		
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
