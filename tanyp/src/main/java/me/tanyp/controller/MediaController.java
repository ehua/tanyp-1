package me.tanyp.controller;

import me.tanyp.controller.common.SystemException;
import me.tanyp.json.JSONResultModel;
import me.tanyp.util.file.FileType;
import me.tanyp.util.file.UploadConst;
import me.tanyp.util.basic.Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by tanyp on 2018/8/20
 */
@RestController
@RequestMapping("/media")
public class MediaController {

    @PostMapping("/upload")
    public JSONResultModel<Object> multiple(@RequestParam("file") MultipartFile[] files) {
        JSONResultModel<Object> responseModel = new JSONResultModel<>();
        List<String> paths = new ArrayList<>(files.length);
        Arrays.stream(files).forEach(file -> {
            try {
                String path = resolveMultipartFile(file);
                paths.add(path);
            } catch (IOException e) {
                throw new SystemException(e);
            }
        });
        responseModel.setData(paths);
        return responseModel;
    }

    private String resolveMultipartFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String realType = FileType.getRealType(originalFileName, new BufferedInputStream(file.getInputStream()));
        if (!UploadConst.get().allType.contains(realType)) {
            throw new SystemException("不支持的文件类型");
        }
        String storeFileName = UUID.randomUUID().toString();
        Utils.SavePathInfo pathInfo = Utils.getAttachmentSavePath();
        String topFolderNoSuffix = pathInfo.topFolder;
        if (topFolderNoSuffix.endsWith("/")) {
            topFolderNoSuffix = topFolderNoSuffix.substring(0, topFolderNoSuffix.length() - 1);
        }
        String path = topFolderNoSuffix + pathInfo.relativePath + storeFileName + '.' + realType;
        String diskSavePath = pathInfo.getFullPath() + storeFileName + '.' + realType;
        file.transferTo(new File(diskSavePath));
        return path;
    }
}
