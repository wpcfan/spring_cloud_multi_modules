package com.fangxiaoer.cms.oss.qiniu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.fangxiaoer.cms.config.OssProperties;
import com.fangxiaoer.cms.oss.OssFileService;
import com.fangxiaoer.cms.oss.model.OssFIle;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "qiniu")
@Service
public class OssFileServiceQiniuImpl implements OssFileService {

    private final UploadManager uploadManager;
    private final BucketManager bucketManager;
    private final Auth auth;
    private final OssProperties properties;
    private final ObjectMapper objectMapper;

    @Override
    public String upload(String path, byte[] bytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return upload(path, inputStream);
    }

    @Override
    public String upload(String path, InputStream is) {
        try {
            val index = path.lastIndexOf("/");
            if (index == -1) {
                throw new RuntimeException("文件路径不合法");
            }
            val fileName = path.substring(index + 1);
            var response = uploadManager.put(
                    is,
                    fileName,
                    auth.uploadToken(properties.getBucket()),
                    null,
                    null);
            val result = objectMapper.readValue(response.bodyString(), DefaultPutRet.class);
            return getDomain() + "/" + result.key;
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public String upload(String path, MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }
        try {
            return upload(path, file.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败", e);
        }
    }

    @Override
    public void delete(String path) {
        try {
            bucketManager.delete(properties.getBucket(), path);
        } catch (Exception e) {
            throw new RuntimeException("删除文件失败", e);
        }
    }

    @Override
    public List<OssFIle> list(int limit) {
        try {
            var files = bucketManager.listFiles(
                    properties.getBucket(),
                    "",
                    "",
                    limit,
                    "");
            return Arrays.stream(files.items).map(file -> new OssFIle(file.key, getDomain() + "/" + file.key))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("获取文件列表失败", e);
        }
    }

    @Override
    public String getDomain() {
        return properties.getDomain();
    }
}
