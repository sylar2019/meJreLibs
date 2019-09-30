package me.java.library.utils.base;

import com.google.common.base.Charsets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * @author :  sylar
 * @FileName :
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
public class ByteUtils {

    public static final Charset CHARSET = Charsets.UTF_8;
    public static final ByteOrder DEFAULT_BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;

    /**
     * byte 与 int 的相互转换
     */
    public static byte toByte(int x) {
        return (byte) x;
    }

    public static short toShort(byte b) {
        return (short) toInt(b);
    }

    public static int toInt(byte b) {
        // Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    public static int toUnsignedInt(byte[] bytes) {
        int ret = 0;
        int size = bytes.length;
        int beginPos = size > 4 ? 4 : size;
        for (int i = beginPos - 1; i >= 0; --i) {
            ret = (ret << 8) | (bytes[i] & 0xff);
        }
        return ret;
    }

    public static short toInt16(byte[] value, int startIndex,
                                ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(2, byteOrder);
        byteBuffer.put(value, startIndex, 2);
        return byteBuffer.getShort(0);
    }

    public static int toInt32(byte[] value, int startIndex, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(4, byteOrder);
        byteBuffer.put(value, startIndex, 4);
        return byteBuffer.getInt(0);

    }

    public static long toInt64(byte[] value, int startIndex, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(8, byteOrder);
        byteBuffer.put(value, startIndex, 8);
        return byteBuffer.getLong(0);
    }

    public static float toFloat(byte[] value, int startIndex,
                                ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(4, byteOrder);
        byteBuffer.put(value, startIndex, 4);
        return byteBuffer.getFloat(0);
    }

    public static double toDouble(byte[] value, int startIndex,
                                  ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(8, byteOrder);
        byteBuffer.put(value, startIndex, 8);
        return byteBuffer.getDouble(0);
    }

    public static byte[] getBytes(short value, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(2, byteOrder);
        byteBuffer.putShort(value);
        return byteBuffer.array();
    }

    public static byte[] getBytes(int value, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(4, byteOrder);
        byteBuffer.putInt(value);
        return byteBuffer.array();
    }

    public static byte[] getBytes(long value, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(8, byteOrder);
        byteBuffer.putLong(value);
        return byteBuffer.array();
    }

    public static byte[] getBytes(float value, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(4, byteOrder);
        byteBuffer.putFloat(value);
        return byteBuffer.array();
    }

    public static byte[] getBytes(double value, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = allocate(8, byteOrder);
        byteBuffer.putDouble(value);
        return byteBuffer.array();
    }

    public static String toString(ByteBuffer buffer, int length) {
        byte[] value = new byte[length];
        buffer.get(value);
        return toString(value, 0, length);
    }

    public static String toString(byte[] value, int startIndex, int length) {
        try {
            return new String(value, startIndex, Math.min(value.length, length), CHARSET).trim();
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] reversionByte(byte[] value) {
        int length = value.length;
        byte[] resultValue = new byte[length];
        int j = 0;
        for (int i = length - 1; i >= 0; i--) {
            resultValue[j++] = value[i];
        }
        return resultValue;
    }

    public static ByteBuffer allocate(int capacity, ByteOrder byteOrder) {
        return ByteBuffer.allocate(capacity).order(byteOrder);
    }
}
