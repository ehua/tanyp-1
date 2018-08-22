package me.tanyp.util.basic;

import com.google.gson.Gson;
import me.tanyp.controller.common.SystemException;
import me.tanyp.util.file.UploadConst;
import me.tanyp.util.file.UploadFileCounterDto;

import java.io.*;
import java.util.Calendar;

public class Utils {

    public static SavePathInfo getAttachmentSavePath() {
        Calendar cal = Calendar.getInstance();
        String relativePathStart = "/";
        String relativePath = relativePathStart + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.DATE) + "/";
        SavePathInfo savePathInfo = new SavePathInfo();
        savePathInfo.baseRootPath = UploadConst.get().getUploadBaseRootPath().getUploadBaseRootPath();
        savePathInfo.topFolder = UploadConst.get().uploadedTopFolder;
        savePathInfo.relativePath = relativePath;
        return createParentPathName(savePathInfo, 3000).savePathInfo;
    }

    public static class SavePathInfo {
        /**
         * 如：d:/xxx/
         */
        public String baseRootPath;
        public String topFolder;
        public String relativePath;
        public String storeFileName;

        /**
         * rootPath = baseRootPath + topFolder
         */
        public String getRootPath() {
            return baseRootPath + topFolder;
        }

        /**
         * rootPath = baseRootPath + topFolder + relativePath
         */
        public String getFullPath() {
            return baseRootPath + topFolder + relativePath;
        }

        /**
         * relativePath + storeFileName
         */
        public String getRelativeFullFile() {
            return relativePath + storeFileName;
        }
    }


    public static class FileCounterInfo {
        public SavePathInfo savePathInfo;
        public String storeFileFullPath;
        public int pageNo;
        public int fileCount;

        public String getRelativeFullFile() {
            return savePathInfo.getRelativeFullFile();
        }
    }


    /**
     * 根据日志文件创建父文件夹
     * <p>
     * 根文件路径
     * 时间戳生成文件路径
     *
     * @param totalCount 文件总数，超过此总数来分隔文件夹
     * @return obj数组，第一个存放的是文件的存储全路径，第二个是文件存储的页数，第三个是文件存储的个数
     */
    private synchronized static FileCounterInfo createParentPathName(SavePathInfo savePathInfo, Integer totalCount) {
        FileCounterInfo uploadFileCounterInfo = new FileCounterInfo();
        uploadFileCounterInfo.savePathInfo = savePathInfo;
        String savePath = null;
        // 计数器文件
        String counterPath = savePathInfo.getRootPath() + savePathInfo.relativePath + "counter.log";
        File file = new File(counterPath);
        UploadFileCounterDto uploadFileCounterDto = null;
        Gson gson = new Gson();
        boolean needCreateCounter = true;
        if (file.exists() && file.isFile()) {// 文件存在。读取、修改
            try {
                FileReader fileReader = new FileReader(counterPath);
                BufferedReader br = new BufferedReader(fileReader);
                String counterInfo = null;
                try {
                    // 只要第一行
                    while ((counterInfo = br.readLine()) != null) {
                        break;
                    }
                } catch (IOException e) {
                    throw new SystemException("读取文件出错");
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                uploadFileCounterDto = gson.fromJson(counterInfo, UploadFileCounterDto.class);
                if (uploadFileCounterDto != null) {
                    Integer count = uploadFileCounterDto.getCount();
                    if ((count + 1) > totalCount) {// 超过数量了需要新建文件夹
                        uploadFileCounterDto.setCount(1);
                        uploadFileCounterDto.setPage(uploadFileCounterDto.getPage() + 1);
                        File dir = new File(savePathInfo.getRootPath() + savePathInfo.relativePath
                                + uploadFileCounterDto.getPage() + "/");
                        dir.mkdirs();
                    } else {
                        uploadFileCounterDto.setCount(++count);
                    }
                    // 修改计数器
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(counterPath);
                        fw.write(gson.toJson(uploadFileCounterDto));
                        fw.flush();
                        // 设置保存路径
                        savePath = savePathInfo.getRootPath() + savePathInfo.relativePath
                                + uploadFileCounterDto.getPage() + "/";
                    } catch (IOException e1) {
                        throw new SystemException("记录信息失败");
                    } finally {
                        try {
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    needCreateCounter = false;
                }
            } catch (FileNotFoundException e) {
                throw new SystemException("读取文件失败");
            }
        }

        if (needCreateCounter) {// 文件不存在 创建对应的文件夹、文件
            File dir = new File(savePathInfo.getRootPath() + savePathInfo.relativePath + "1/");
            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdirs();
            }

            try {// 新建并初始化文件信息
                file.createNewFile();
                FileWriter fw = null;
                try {
                    fw = new FileWriter(counterPath);
                    uploadFileCounterDto = new UploadFileCounterDto();
                    uploadFileCounterDto.setCount(1);
                    uploadFileCounterDto.setPage(1);
                    fw.write(gson.toJson(uploadFileCounterDto));
                    fw.flush();
                    // 设置保存路径
                    savePath = savePathInfo.getRootPath() + savePathInfo.relativePath + "1/";
                } catch (IOException e1) {
                    throw new SystemException("记录信息失败");
                } finally {
                    fw.close();
                }
            } catch (IOException e) {
                throw new SystemException("创建文件失败");
            }
        }
        uploadFileCounterInfo.savePathInfo.relativePath = uploadFileCounterInfo.savePathInfo.relativePath
                + uploadFileCounterDto.getPage() + "/";
        uploadFileCounterInfo.storeFileFullPath = savePath;
        uploadFileCounterInfo.pageNo = uploadFileCounterDto.getPage();
        uploadFileCounterInfo.fileCount = uploadFileCounterDto.getCount();
        return uploadFileCounterInfo;
    }

}