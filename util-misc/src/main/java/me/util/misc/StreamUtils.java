package me.util.misc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtils {

    final static String ENCODING = "UTF-8";

    /**
     * 将InputStream转换成String
     *
     * @param inputStream
     * @return
     */
    public static String stream2String(InputStream inputStream) {
        InputStreamReader in = null;
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        String line;
        try {
            in = new InputStreamReader(inputStream, ENCODING);
            reader = new BufferedReader(in);

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            reader.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader = null;
            in = null;
        }

        return sb.toString();
    }

    /**
     * 将byte[]转换成InputStream
     *
     * @param b
     * @return
     */
    public static InputStream bytes2Stream(byte[] b) {
        return new ByteArrayInputStream(b);
    }

    /**
     * 将InputStream转换成byte[]
     *
     * @param in
     * @return
     */
    public static byte[] stream2Bytes(InputStream in) {
        String str = "";
        byte[] readByte = new byte[1024];
        try {
            while (in.read(readByte, 0, 1024) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
