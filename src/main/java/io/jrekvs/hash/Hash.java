package io.jrekvs.hash;

/**
 *
 * 类功能描述：重写hashcode方法。（采用装饰者模式，仅完成一个hash重构方法）
 * @author  Tommy.z
 * @version 0.0.1
 *
 */
public interface Hash {
    long hash(final String key , long ... length);
}


