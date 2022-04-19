package cn.xiaosm.cloud.core.util;

import java.io.*;

/**
 * @author Young
 * @create 2021/5/8
 * @since 1.0.0
 */
public class SerializeUtils {

    /**
     * 序列化对象
     * @param o
     * @return
     */
    public static byte[] serialize(Object o) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = null;
        try {
            oout = new ObjectOutputStream(bout);
            oout.writeObject(o);
            return bout.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(bout, oout);
        }
        return null;
    }

    /**
     * 反序列化对象
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null) return null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream oin = null;
        try {
            oin = new ObjectInputStream(in);
            return oin.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(in, oin);
        }
        return null;
    }

    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}