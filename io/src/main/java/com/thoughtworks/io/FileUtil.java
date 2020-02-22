package com.thoughtworks.io;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class FileUtil {

    /**
     * 完成复制文件夹方法:
     * 1. 把给定文件夹from下的所有文件(包括子文件夹)复制到to文件夹下
     * 2. 保证to文件夹为空文件夹，如果to文件夹不存在则自动创建
     * <p>
     * 例如把a文件夹(a文件夹下有1.txt和一个空文件夹c)复制到b文件夹，复制完成以后b文件夹下也有一个1.txt和空文件夹c
     */
    public static void copyDirectory(File from, File to) throws IOException {
        makeSureFileExits(from, to);
        if (from.isDirectory()) {
            clearTheNewDirectory(to);
            for (File file : from.listFiles()) {
                copyDirectory(new File(from.getPath(), file.getName()), new File(to.getPath(), file.getName()));
            }
        } else {
            copyFile(from, to);
        }
    }

    /**
     * 确保题目条件2， 新文件夹为空文件夹
     *
     * @param file 新文件夹
     */
    public static void clearTheNewDirectory(File file) {
        for (File needDelete : file.listFiles()) {
            needDelete.delete();
        }
    }

    /**
     * 确认文件或文件夹是否存在，同时确保不会有空指针异常
     *
     * @param from 需要复制的目录
     * @param to   新建的目录
     * @throws IOException
     */
    public static void makeSureFileExits(File from, File to) throws IOException {
        if (from.isDirectory()) {
            to.mkdirs();
        } else {
            to.createNewFile();
        }
    }

    /**
     * 对于不是文件夹的文件进行复制
     *
     * @param from 被复制的文件
     * @param to   新的文件
     * @throws IOException
     */
    public static void copyFile(File from, File to) throws IOException {
        int fileSize = (int) from.length();
        byte[] data = new byte[1024];
        if (fileSize < 1024) {
            data = new byte[(int) from.length()];
        }
        try (InputStream inputStream = new FileInputStream(from);
             OutputStream outputStream = new FileOutputStream(to)) {
            while (inputStream.read(data) != -1) {
                outputStream.write(data);
            }
            outputStream.flush();
        }
    }

}
