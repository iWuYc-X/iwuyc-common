package com.iwuyc.tools.commons.util.file;

import com.iwuyc.tools.commons.basic.AbstractStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Optional;

/**
 * 文件处理工具类
 *
 * @author Neil
 * @since @Sep 3, 2017
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
    private static final String CLASSPATH_PREFIX = "classpath:";

    /**
     * 将指定的byte数组以指定长度，填充到指定的流中。
     *
     * @param out      指定的流。
     * @param length   需要填充的长度，可大于bytes数组的长度。
     * @param bytesArr bytes数组
     * @throws Exception
     * @author Neil
     */
    private static void fillEmptyByte(OutputStream out, int length, byte[] bytesArr) throws IOException{

        int cursor = 0;
        int fillSize = bytesArr.length;
        while(cursor < length){
            fillSize = fillSize < length - cursor ? fillSize : length;
            out.write(bytesArr, 0, fillSize);
            cursor += fillSize;
        }
    }

    /**
     * 安全删除文件
     *
     * @param file 将要删除的文件
     * @return 删除成功，则返回true，如果出错，则返回false
     * @author Neil
     */
    public static boolean safeDelete(File file){
        boolean result = false;
        // Return false if file not exists or not a file;
        if(!file.isFile()){
            return result;
        }
        try(FileInputStream in = new FileInputStream(file); FileOutputStream out = new FileOutputStream(file)){
            final byte[] emptyBytes = new byte[1024];

            int availableSize = 0;
            // Fill zero bytes into file.
            while((availableSize = in.available()) != 0){
                fillEmptyByte(out, availableSize, emptyBytes);
                in.skip(availableSize);
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        // Delete file.
        if(file.exists()){
            result = file.delete();
        }
        return result;
    }

    /**
     * 安全删除文件，防止恢复
     *
     * @param path 将要删除的文件的路径
     * @return 删除成功，则返回true，否则返回false
     * @author Neil
     */
    public static boolean safeDelete(String path){
        return safeDelete(new File(path));
    }

    /**
     * 读取整个文件的数据，调用该方法应当提前校验过文件的大小，不建议将大文件一次性读取出来
     *
     * @param filePath 文件路径
     * @return 文件中的内容
     */
    public static String readAll(String filePath){

        Optional<String> filePathOpt = absoluteLocation(filePath);
        if(!filePathOpt.isPresent()){
            return "";
        }

        File file = new File(filePath);

        try(FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr)){
            StringBuilder sb = new StringBuilder();
            String tmp;
            while((tmp = reader.readLine()) != null){
                sb.append(tmp).append("\n");
            }
            return sb.toString();
        }catch(Exception e){
            LOGGER.error("Read file raise an error.Cause:{}", e);
        }
        return AbstractStringUtils.NIL_STRING;
    }

    /**
     * 获取文件的绝对路径
     * @param location 路径，“classpath:”前缀将查找
     * @return 绝对路径
     */
    public static Optional<String> absoluteLocation(String location){
        if(AbstractStringUtils.isEmpty(location)){
            return Optional.empty();
        }

        if(location.startsWith(CLASSPATH_PREFIX)){
            location = location.substring(CLASSPATH_PREFIX.length());
            location = FileUtil.class.getResource(location).getFile();
        }
        String absoluteLocation = new File(location).getAbsolutePath();
        return Optional.of(absoluteLocation);
    }
}