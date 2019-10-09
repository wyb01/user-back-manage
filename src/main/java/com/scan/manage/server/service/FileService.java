package com.scan.manage.server.service;

import com.scan.manage.server.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

	FileInfo save(MultipartFile file) throws IOException;

	void delete(String id);

}
