package com.oilchem.trade.util;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;

import static com.oilchem.trade.util.CommonUtil.close;
import static com.oilchem.trade.util.CommonUtil.getYYYYMMDDHHMMSS;


/**
 * @author wei.luo
 * @brief <p><b>Zip file tool class</b></p>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; compress and decompress file
 * @see
 * @since 2012-2-10
 */
public abstract class ZipUtil {

    private static Logger log = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * 压缩文件
     *
     * @param src     指定压缩源，可以是目录或文件
     * @param archive //压缩包路径
     * @param comment
     */
    public static void writeByApacheZipOutputStream(
            String src, String archive, String comment) {

        boolean isCreateSrcDir = false;//是否创建源目录

        //----压缩文件：
        FileOutputStream f = null;
        BufferedOutputStream out = null;
        CheckedOutputStream csum = null;
        try {
            f = new FileOutputStream(archive);
            //使用指定校验和创建输出流
            csum = new CheckedOutputStream(f, new CRC32());

            ZipOutputStream zos = new ZipOutputStream(csum);
            //支持中文
            zos.setEncoding("UTF-8");
            out = new BufferedOutputStream(zos);
            //设置压缩包注释
            zos.setComment(comment);
            //启用压缩
            zos.setMethod(ZipOutputStream.DEFLATED);
            //压缩级别为最强压缩，但时间要花得多一点
            zos.setLevel(Deflater.BEST_COMPRESSION);

            File srcFile = new File(src);

            if (!srcFile.exists() || (srcFile.isDirectory() && srcFile.list().length == 0)) {
                throw new RuntimeException(
                        "File must exist and  ZIP file must have at least one entry.");
            }
            //获取压缩源所在父目录
            src = src.replaceAll("\\\\", "/");
            String prefixDir = null;
            if (srcFile.isFile()) {
                prefixDir = src.substring(0, src.lastIndexOf("/") + 1);
            } else {
                prefixDir = (src.replaceAll("/$", "") + "/");
            }

            //如果不是根目录
            if (prefixDir.indexOf("/") != (prefixDir.length() - 1) && isCreateSrcDir) {
                prefixDir = prefixDir.replaceAll("[^/]+/$", "");
            }

            //开始压缩
            writeRecursive(zos, out, srcFile, prefixDir);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            close(out);
//            close(f);
//            close(csum);
        }

        // 注：校验和要在流关闭后才准备，一定要放在流被关闭后使用
        log.info("Checksum: " + csum.getChecksum().getValue());
    }

    /**
     * 解压文件
     *
     * @param file          压缩文件
     * @param decompressDir 解压目录
     */
    public static void readByApacheZipFile(File file, String decompressDir) {

        ZipFile zf = null;
        try {
            zf = new ZipFile(file, "UTF-8");// 支持中文
            readByApacheZipFile(decompressDir, zf);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                zf.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException();
            }
        }
    }

    /**
     * 使用 org.apache.tools.zip.ZipFile 解压文件，它与 java 类库中的
     * java.util.zip.ZipFile 使用方式是一致的，只不过多了设置编码方式的
     * 接口。
     * <p/>
     * 注，apache 没有提供 ZipInputStream 类，所以只能使用它提供的ZipFile
     * 来读取压缩文件。
     *
     * @param archive       压缩包路径
     * @param decompressDir 解压路径
     */
    public static void readByApacheZipFile(String archive, String decompressDir) {

        ZipFile zf = null;
        try {
            zf = new ZipFile(archive, "GBK");//支持中文
            readByApacheZipFile(decompressDir, zf);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                zf.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * 解压zip文件
     *
     * @param decompressDir
     * @param zf
     */
    private static void readByApacheZipFile(String decompressDir, ZipFile zf) {

        BufferedInputStream bi = null;
        BufferedOutputStream bos = null;
        Enumeration<?> e = zf.getEntries();
        while (e.hasMoreElements()) {
            ZipEntry ze2 = (ZipEntry) e.nextElement();
            String entryName = ze2.getName();
            String path = decompressDir + "/" + entryName;
            if (ze2.isDirectory()) {
                log.info("正在创建解压目录 - " + entryName);
                File decompressDirFile = new File(path);
                if (!decompressDirFile.exists()) {
                    decompressDirFile.mkdirs();
                }
            } else {
                log.info("正在创建解压文件 - " + entryName);
                String fileDir = path.substring(0, path.lastIndexOf("/"));
                File fileDirFile = new File(fileDir);
                if (!fileDirFile.exists()) {
                    fileDirFile.mkdirs();
                }

                try {
                    bos = new BufferedOutputStream(
                            new FileOutputStream(decompressDir + "/" + entryName));

                    bi = new BufferedInputStream(zf.getInputStream(ze2));
                    byte[] readContent = new byte[1024];
                    int readCount = bi.read(readContent);
                    while (readCount != -1) {
                        //解压对文件二进制加密
                        bos.write(readContent, 0, readCount);
                        readCount = bi.read(readContent);
                    }

                } catch (IOException e1) {
                    log.error(e1.getMessage(), e1);
                    throw new RuntimeException(e1);
                } finally {
                    close(bi);
                    close(bos);
                }
            }
        }
        try {
            zf.close();
        } catch (IOException e1) {
            log.error(e1.getMessage(), e1);
            throw new RuntimeException(e1);
        }
    }

    /**
     * 递归压缩
     * <p/>
     * 使用 org.apache.tools.zip.ZipOutputStream 类进行压缩，它的好处就是支持中文路径，
     * 而Java类库中的 java.util.zip.ZipOutputStream 压缩中文文件名时压缩包会出现乱码。
     * 使用 apache 中的这个类与 java 类库中的用法是一新的，只是能设置编码方式了。
     *
     * @param zos
     * @param bo
     * @param srcFile
     * @param prefixDir
     */
    private static void writeRecursive(
            ZipOutputStream zos, BufferedOutputStream bo, File srcFile, String prefixDir) {
        ZipEntry zipEntry;

        String filePath = srcFile.getAbsolutePath().replaceAll("\\\\", "/").replaceAll(
                "//", "/");
        if (srcFile.isDirectory()) {
            filePath = filePath.replaceAll("/$", "") + "/";
        }


        BufferedInputStream bi = null;
        try {
            String entryName = filePath.replace(prefixDir, "").replaceAll("/$", "");
            if (srcFile.isDirectory()) {
                if (!"".equals(entryName)) {
                    log.info("正在创建目录 - " + srcFile.getAbsolutePath()
                            + "  entryName=" + entryName);

                    //如果是目录，则需要在写目录后面加上 /
                    zipEntry = new ZipEntry(entryName + "/");
                    zos.putNextEntry(zipEntry);
                }

                File srcFiles[] = srcFile.listFiles();
                for (int i = 0; i < srcFiles.length; i++) {
                    writeRecursive(zos, bo, srcFiles[i], prefixDir);
                }
            } else {
                log.info("正在写文件 - " + srcFile.getAbsolutePath() + "  entryName="
                        + entryName);
                bi = new BufferedInputStream(new FileInputStream(srcFile));

                //开始写入新的ZIP文件条目并将流定位到条目数据的开始处
                zipEntry = new ZipEntry(entryName);
                zos.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];
                int readCount = bi.read(buffer);

                while (readCount != -1) {
                    bo.write(buffer, 0, readCount);
                    readCount = bi.read(buffer);
                }
                //注，在使用缓冲流写压缩文件时，一个条件完后一定要刷新一把，不
                //然可能有的内容就会存入到后面条目中去了
                bo.flush();
                //文件读完后关闭
                bi.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            close(bi);
            close(bo);
        }
    }

    /**
     * 解压rar格式的压缩文件到指定目录下
     *
     * @param rarFileName 压缩文件
     * @param extPlace    解压目录
     * @throws Exception
     */
    public static void unrar(String rarFileName, String extPlace) {
        File rarFile = null;
        FileOutputStream os = null;
        try {
            (new File(extPlace)).mkdirs();
            rarFile = new File(rarFileName);
            Archive archive = new Archive(rarFile);

            List<FileHeader> headers = archive.getFileHeaders();
            Iterator<FileHeader> iterator = headers.iterator();

            while (iterator.hasNext()) {
                FileHeader fileHeader = iterator.next();
                try {
                    //如果是目录
                    if (fileHeader.isDirectory()) continue;
                    else {
                        String fileName = extPlace + "/" + fileHeader.getFileNameString();
                        File file = new File(fileName);
                        if (!file.exists()) {  //如果目录不存在
                            if (fileHeader.getFileNameString().contains(File.separator)) {
                                new File(StringUtils.substringBeforeLast(fileName, File.separator)).mkdirs();
                                os = new FileOutputStream(file);
                                archive.extractFile(fileHeader, os);
                            } else if (!fileHeader.getFileNameString().contains(".")) {
                                file.mkdirs();
                                continue;
                            }
                            os = new FileOutputStream(file);
                            archive.extractFile(fileHeader, os);
                        } else {
                            os = new FileOutputStream(file);
                            archive.extractFile(fileHeader, os);
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    try {
                        os.close();
                    } catch (IOException e2) {
                        log.error(e.getMessage(), e2);
                        throw new RuntimeException(e);
                    }
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 解压zip
     *
     * @param zipSource zip包源路径
     * @param unZipDir  解压目录
     * @param encoding  编码,如果传null，默认为UTF-8
     * @return
     */
    public static String unZip(String zipSource, String unZipDir, String encoding) {
        String unZipFile = null;
        InputStream is = null;
        OutputStream os = null;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipSource, (encoding == null ? "utf-8" : encoding));
            Enumeration<ZipEntry> zipEntrys = zipFile.getEntries();
            while (zipEntrys.hasMoreElements()) {
                ZipEntry zipEntry = zipEntrys.nextElement();
                unZipFile = unZipDir + "/" + getYYYYMMDDHHMMSS(new Date())
                        + "_" + zipEntry.getName();
                String unZipFileDir = unZipDir + "/" + zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    new File(unZipFileDir).mkdirs();
                    continue;
                } else {
                    File unZipDirFile = new File(unZipDir);
                    if (!unZipDirFile.exists())
                        unZipDirFile.mkdirs();
                    is = zipFile.getInputStream(zipEntry);
                    os = new FileOutputStream(unZipFile);
                    CommonUtil.inputStream2OutPutStream(is, os, 1024);
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (zipFile != null)
                    zipFile.close();
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return unZipFile;
    }

    /**
     * 解压rar
     *
     * @param rarSource
     * @param unRarDir
     * @return 最后一个解压的文件的全路径
     * @throws Exception
     */
    public static String unRar(String rarSource, String unRarDir) {
        String unRarFile = null;
        OutputStream os = null;
        Archive archive = null;
        try {
            archive = new Archive(new File(rarSource));

            Iterator<FileHeader> fileHeaderIterator = archive.getFileHeaders().iterator();
            while (fileHeaderIterator.hasNext()) {
                FileHeader fileHeader = fileHeaderIterator.next();
                unRarFile = unRarDir + "/" + getYYYYMMDDHHMMSS(new Date())
                        + "_" + fileHeader.getFileNameString();
                String unRarFileDir = unRarDir + "/" + fileHeader.getFileNameString();

                if (fileHeader.isDirectory()) {
                    new File(unRarFileDir).mkdirs();
                    continue;
                } else {
                    File unRarDirFile = new File(unRarDir);
                    if (!unRarDirFile.exists()) unRarDirFile.mkdirs();
                    os = new FileOutputStream(unRarFile);
                    archive.extractFile(fileHeader, os);
                }
            }
        } catch (RarException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (archive != null)
                    archive.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return unRarFile;
    }

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
//        String src = "d:/temp/src";//指定压缩源，可以是目录或文件
//        String decompressDir = "d:/temp/decompress";//解压路径
//        String archive = "d:/temp/test1.zip";//压缩包路径
//        String comment = "Java Zip luowei010101@gmail.com";//压缩包注释

        unrar("d:/temp/bbbb.rar", "d:/temp/bbbb");

//        //----压缩文件或目录
//        writeByApacheZipOutputStream(src, archive, comment);

        //----使用apace ZipFile解压压缩文件
//        readByApacheZipFile(archive, decompressDir);

//              File file = new File(archive);
//              readByApacheZipFile(file, decompressDir);
    }

}
