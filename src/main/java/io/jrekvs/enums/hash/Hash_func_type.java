/*
 *  文件创建时间： 2016年12月11日
 *  文件创建者: PigBrother(LZY/LZS)二师兄
 *  所属工程: JCache
 *  CopyRights
 *
 *  备注:
 */
package io.jrekvs.enums.hash;

/**
 * Created by Tommy.z on 2016/12/11 18:09.
 * 类功能描述：各种hash算法枚举（采用装饰者模式，仅完成一个hash重构方法）
 */
public enum Hash_func_type {
    JENKINS_HASH, MURMUR3_HASH, PIG_HASH
}
