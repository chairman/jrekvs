package io.jrekvs.enums.protocol.binary;


/**
 * @author Tommy.z
 */
public enum ProtocolDatatypes {
    PROTOCOL_BINARY_RAW_BYTES((byte)0);

    ProtocolDatatypes(byte type){
        this.type =  type;
    }

    private byte type;
    
    public byte getByte(){
    	return type;
    }
}
