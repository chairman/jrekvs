/*
 *  文件创建时间： 2016年12月11日
 *  文件创建者: PigBrother(LZY/LZS)二师兄
 *  所属工程: JCache
 *  CopyRights
 *
 *  备注:
 */
package io.jrekvs.hash.impl;

import io.jrekvs.enums.hash.Hash_func_type;
import io.jrekvs.hash.Hash;
import io.jrekvs.hash.Hash_init;
import io.jrekvs.setting.Settings;
import io.jrekvs.hash.impl.algorithm.Jenkins_hash;
import io.jrekvs.hash.impl.algorithm.Pig_SDBM_hash;

/**
 *
 * @author Tommy.z
 */
@Deprecated
public class HashImpl implements Hash_init {

    private Hash hash;

    @Override
    public long hash(String key, long... length) {
        return hash.hash(key);
    }

    @Override
    public int hash_init(Hash_func_type type) {
        switch (type){
            case JENKINS_HASH:
                Settings.hash_algorithm="jenkins";
                hash = new Jenkins_hash();
                break;
            case MURMUR3_HASH:
                Settings.hash_algorithm="murmur3";
                break;
            case PIG_HASH:
                Settings.hash_algorithm="Pig_SDBM_hash";
                hash = new Pig_SDBM_hash();
                break;
            default:
            	return -1;
        }
        return 0;
    }
    public HashImpl(){
    }
    
    public HashImpl(Hash_func_type hashfunc_type){
        hash_init(hashfunc_type);
    }
}
