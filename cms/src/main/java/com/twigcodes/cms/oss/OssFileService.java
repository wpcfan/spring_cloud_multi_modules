package com.twigcodes.cms.oss;

import com.twigcodes.cms.oss.model.OssFIle;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface OssFileService {
    String upload(String path, byte[] bytes);

    String upload(String path, InputStream is);

    String upload(String path, MultipartFile file);

    void delete(String path);

    List<OssFIle> list(int limit);

    String getDomain();
}
