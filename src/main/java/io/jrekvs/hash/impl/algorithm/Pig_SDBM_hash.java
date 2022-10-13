/*
 *  文件创建时间： 2016年12月11日
 *  文件创建者: PigBrother(LZY/LZS)二师兄
 *  所属工程: JCache
 *  CopyRights
 *
 *  备注:
 */
package io.jrekvs.hash.impl.algorithm;

import io.jrekvs.hash.Hash;

/**
 * @author Tommy.z
 *
 */
public class Pig_SDBM_hash implements Hash {
    @Override
    public long hash(String key, long... length) {
        long hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = key.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return hash;
    }
}
