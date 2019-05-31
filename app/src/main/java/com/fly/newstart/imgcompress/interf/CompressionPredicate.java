package com.fly.newstart.imgcompress.interf;

/**
 * 对于给定的输入路径，返回true或false的函数接口（回调）应该被压缩。
 * A functional interface (callback) that returns true or false for the given input path should be compressed.
 */

public interface CompressionPredicate {

    /**
     * Determine the given input path should be compressed and return a boolean.
     * 确定给定的输入路径应该被压缩并返回一个布尔值。
     *
     * @param path input path
     * @return the boolean result
     */
    boolean apply(String path);
}
