package com.simple.base.components.file.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.simple.base.components.file.service.FileUploadService;

import org.apache.commons.io.FileUtils;

@RequestMapping("/files")
@Controller
public class FileController {
    
	private String fileBasePath = "e:/software/uploads/";
	
	@Autowired
	FileUploadService fileservice;
	
	@RequestMapping("/uploadpage")
	public String uploadfile() {
		return "upload";
	}

	@RequestMapping("/getFilePath")
	@ResponseBody
	public String getFileFullPath(@RequestParam("id") Long fId) {
		return fileservice.getFilePathById(fId);
	}
	@RequestMapping("/upload")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		String filePath = fileBasePath + file.getOriginalFilename();
		if (!file.isEmpty()) {
			try {
				
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(new File(filePath)));
				out.write(file.getBytes());
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			}
			String filename = file.getOriginalFilename();
			Long id = fileservice.save(filename, filePath, 0);
			
			System.out.println(filePath);
			return "file:" + filePath + "[ID:" +  id + "] 上传成功";
		} else {
			return "上传失败，因为文件是空的.";
		}
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public String FileUpload(@RequestParam("file") MultipartFile file) {
		String filePath = fileBasePath + file.getOriginalFilename();
		if (!file.isEmpty()) {
			try {
				
				FileUtils.writeByteArrayToFile(new File(filePath),
						file.getBytes());

			} catch (IOException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			} finally {
				final String filename = file.getOriginalFilename();
				fileservice.save(filename, filePath, 0);
				System.out.println(filePath);
				return "file:" + filePath + "上传成功";
			}

		} else {
			return "上传失败，因为文件是空的.";
		}
	}

	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
	@ResponseBody
	public String FilesUpload(HttpServletRequest request) {
		int failedNumber = 0;
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		for (int i = 0; i < files.size(); ++i) {
			
			MultipartFile file = files.get(i);
			String filePath = fileBasePath + file.getOriginalFilename();
			if (!file.isEmpty()) {
				try {
					
					FileUtils.writeByteArrayToFile(new File(filePath),
							file.getBytes());

				} catch (IOException e) {
					
					System.out.println("Failed to upload file:" + file.getOriginalFilename());
					failedNumber++;
					e.printStackTrace();
				} finally {
					String filename = file.getOriginalFilename();
					fileservice.save(filename, filePath, 0);
					System.out.println("success to upload file:" + filePath);
									
				}

			} else {
				failedNumber++;
				System.out.println("Failed to upload file:" + file.getOriginalFilename());
			}
		}
		
		return "success for upload files";
	}//end fo filesupload
}
