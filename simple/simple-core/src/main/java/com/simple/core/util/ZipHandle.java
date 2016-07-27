package com.simple.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 
 * @ClassName: ZipUtils
 * @Description: 鍘嬬缉鍜岃В鍘嬫枃浠舵垨鑰呮枃浠跺す
 * @author ranfi
 * @date Sep 7, 2011 1:18:29 PM
 * 
 */
public class ZipHandle {
	/**
	 * log4j 璁板綍鍣�
	 */
	protected static final Logger logger = Logger.getLogger(ZipHandle.class);

	private ZipHandle() {
	}

	private static class SingletonHolder {
		private static final ZipHandle INSTANCE = new ZipHandle();
	}

	public static ZipHandle getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 鍘嬬缉鏂囦欢鎴栬�呮枃浠跺す
	 * 
	 * @param sourcePath
	 *            鍘嬬缉婧愭枃浠惰矾寰�
	 * @param descPath
	 *            鍘嬬缉鍚庡瓨鏀剧殑璺緞
	 * @return
	 */
	public boolean compressFiles(String sourcePath, String descPath) {
		boolean flag = true;
		File file = null;
		ZipOutputStream zipOut = null;
		String basePath = null;
		if (logger.isDebugEnabled()) {
			logger.debug("inside compressed file");
		}
		try {
			file = new File(sourcePath);
			zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(descPath)));
			basePath = file.isDirectory() ? file.getPath() : file.getParent();
			recursiveCompressFile(file, basePath, zipOut);
			if (logger.isDebugEnabled()) {
				logger.debug("exiting compressed file");
			}
			zipOut.flush();
			zipOut.closeEntry();
			zipOut.close();
		}
		catch (FileNotFoundException e) {
			flag = false;
			e.printStackTrace();
			logger.error("鍘嬬缉鐨勬枃浠�:锛�" + file.getName() + "锛戒笉瀛樺湪");
		}
		catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error("鍘嬬缉鏂囦欢杩囩▼涓嚭鐜板紓甯革紒", e);
		}
		return flag;
	}

	/**
	 * 鍘嬬缉鏂囦欢鎴栬�呮枃浠跺す
	 * 
	 * @param sourcePath
	 *            鍘嬬缉婧愭枃浠惰矾寰�
	 * @param filterFolder
	 *            杩囨护鐨勭洰褰曠粨鏋�
	 * @param filter
	 *            鏂囦欢杩囨护鍣�
	 * @param descPath
	 *            鍘嬬缉鍚庡瓨鏀剧殑璺緞
	 * @return
	 */
	public boolean compressFiles(String sourcePath, String filterFolder, FilenameFilter filter, String descPath) {
		boolean flag = true;
		File file = null;
		ZipOutputStream zipOut = null;
		String basePath = null;
		if (logger.isDebugEnabled()) {
			logger.debug("inside compressed file");
		}
		try {
			file = new File(sourcePath);
			File descParentFile = new File(descPath).getParentFile();
			if (!descParentFile.exists()) {
				descParentFile.mkdirs();
			}
			if (StringUtils.isBlank(filterFolder)) {
				basePath = file.isDirectory() ? file.getPath() : file.getParent();
			}
			else {
				basePath = filterFolder;
			}
			zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(descPath)));
			recursiveCompressFile(file, basePath, filter, zipOut);
			if (logger.isDebugEnabled()) {
				logger.debug("exiting compressed file");
			}
			zipOut.flush();
			zipOut.closeEntry();
			zipOut.close();
		}
		catch (FileNotFoundException e) {
			flag = false;
			logger.error("鍘嬬缉鐨勬枃浠�:[" + file.getName() + "]涓嶅瓨鍦�", e);
		}
		catch (Exception e) {
			flag = false;
			logger.error("鍘嬬缉鐨勬枃浠�:[" + file.getName() + "]杩囩▼涓嚭鐜板紓甯革紒", e);
		}
		return flag;
	}

	/**
	 * 閫掕閬嶅巻鏂囦欢澶逛腑鐨勬簮鏂囦欢娣诲姞鍒板帇缂╄緭鍑烘祦
	 * 
	 * @param file
	 * @param zipOut
	 */
	private void recursiveCompressFile(File file, String basePath, ZipOutputStream zipOut) {
		BufferedInputStream in = null;
		File[] files = null;
		String pathName = null;
		try {
			if (file.isDirectory()) {
				files = file.listFiles();
				if (files.length > 0) {
					for (File fileObj : files) {
						if (fileObj.isDirectory()) {
							pathName = fileObj.getPath().substring(basePath.length() + 1) + File.separator;
							zipOut.putNextEntry(new ZipEntry(pathName));
						}
						recursiveCompressFile(fileObj, basePath, zipOut);
					}
				}
			}
			else {
				in = new BufferedInputStream(new FileInputStream(file));
				byte[] bt = new byte[1024 * 4];
				int c = 0;
				pathName = file.getPath().substring(basePath.length() + 1);
				zipOut.putNextEntry(new ZipEntry(pathName));
				while ((c = in.read(bt)) != -1) {
					zipOut.write(bt, 0, c);
				}
				zipOut.closeEntry();
			}
		}
		catch (FileNotFoundException e) {
			logger.error("鍘嬬缉鐨勬枃浠�:锛�" + file.getName() + "锛戒笉瀛樺湪");
			throw new RuntimeException("鍘嬬缉鐨勬枃浠�:锛�" + file.getName() + "锛戒笉瀛樺湪");
		}
		catch (Exception e) {
			logger.error("鍘嬬缉鏂囦欢杩囩▼涓嚭鐜板紓甯革紒", e);
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (null != in) {
					in.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 閫掕閬嶅巻鏂囦欢澶逛腑鐨勬簮鏂囦欢娣诲姞鍒板帇缂╄緭鍑烘祦
	 * 
	 * @param file
	 * @param basePath
	 * @param filter
	 *            鏂囦欢杩囨护鍣�
	 * @param zipOut
	 */
	private void recursiveCompressFile(File file, String basePath, FilenameFilter filter, ZipOutputStream zipOut) {
		BufferedInputStream in = null;
		File[] files = null;
		String pathName = null;
		try {
			if (file.isDirectory()) {
				files = file.listFiles(filter);
				if (files.length > 0) {
					for (File fileObj : files) {
						if (fileObj.isDirectory()) {
							pathName = fileObj.getPath().substring(basePath.length() + 1) + File.separator;
							zipOut.putNextEntry(new ZipEntry(pathName));
						}
						recursiveCompressFile(fileObj, basePath, filter, zipOut);
					}
				}
			}
			else {
				in = new BufferedInputStream(new FileInputStream(file));
				byte[] bt = new byte[1024 * 4];
				int c = 0;
				pathName = file.getPath().substring(basePath.length() + 1);
				zipOut.putNextEntry(new ZipEntry(pathName));
				while ((c = in.read(bt)) != -1) {
					zipOut.write(bt, 0, c);
				}
				zipOut.closeEntry();
			}
		}
		catch (FileNotFoundException e) {
			logger.error("鍘嬬缉鐨勬枃浠�:锛�" + file.getAbsolutePath() + "锛戒笉瀛樺湪");
			throw new RuntimeException("鍘嬬缉鐨勬枃浠�:锛�" + file.getName() + "锛戒笉瀛樺湪");
		}
		catch (Exception e) {
			logger.error("鍘嬬缉鏂囦欢杩囩▼涓嚭鐜板紓甯革紒", e);
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (null != in) {
					in.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 瑙ｅ帇缂╂枃浠舵垨鑰呮枃浠跺す
	 * 
	 * @param sourcePath
	 *            闇�瑕佽В鍘嬬缉鐨勬簮鏂囦欢璺緞
	 * @param descPath
	 *            瑙ｅ帇缂╂枃浠剁殑鐩爣璺緞
	 * @return
	 */
	public boolean decompressFiles(String sourcePath, String descPath) {
		boolean flag = true;
		ZipInputStream in = null;
		java.util.zip.ZipEntry entry = null;
		File file = null;
		try {
			if (StringUtils.isBlank(sourcePath))
				return false;
			in = new ZipInputStream(new BufferedInputStream(new FileInputStream(sourcePath)));
			descPath = StringUtils.isNotBlank(descPath) ? descPath : new File(sourcePath).getParent();

			while (null != (entry = in.getNextEntry())) {
				String childFileName = entry.getName();
				file = new File(descPath, childFileName);
				flag = writeFileToDisk(file, entry, in);
			}
			in.closeEntry();
			in.close();
		}
		catch (FileNotFoundException e) {
			flag = false;
			logger.error("瑙ｅ帇缂╃殑鏂囦欢:锛�" + file.getName() + "锛戒笉瀛樺湪");
			throw new RuntimeException("瑙ｅ帇鐨勬枃浠�:锛�" + file.getName() + "锛戒笉瀛樺湪");
		}
		catch (Exception e) {
			flag = false;
			throw new RuntimeException(e);
		}
		return flag;
	}

	/**
	 * 鏍规嵁杈撳叆娴佽В鍘嬬缉鏂囦欢鎴栬�呮枃浠跺す
	 * 
	 * @param in
	 *            闇�瑕佽В鍘嬬缉鐨勮緭鍏ユ祦鏂囦欢
	 * @param descPath
	 *            瑙ｅ帇鍚庡瓨鏀剧殑鏂囦欢璺緞
	 * @return
	 */
	public boolean decompressFiles(InputStream in, String descPath) {
		boolean flag = true;
		ZipInputStream zip = null;
		java.util.zip.ZipEntry entry = null;
		File file = null;
		try {
			zip = new ZipInputStream(new BufferedInputStream(in));
			while ((entry = zip.getNextEntry()) != null) {
				file = new File(descPath, entry.getName());
				flag = writeFileToDisk(file, entry, zip);
			}
		}
		catch (Exception e) {
			flag = false;
			logger.error("瑙ｅ帇缂╂枃浠惰繃绋嬩腑鍑虹幇寮傚父锛�", e);
		}
		finally {
			try {
				zip.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	private boolean writeFileToDisk(File file, java.util.zip.ZipEntry entry, ZipInputStream zip) {
		BufferedOutputStream out = null;
		boolean flag = true;
		try {
			if (entry.isDirectory()) {
				return file.mkdirs();
			}
			else {
				// 濡傛灉鎸囧畾鏂囦欢鐨勭洰褰曚笉瀛樺湪,鍒欏垱寤轰箣.
				File parent = file.getParentFile();
				if (!parent.exists()) {
					flag = parent.mkdirs();
				}
			}
			out = new BufferedOutputStream(new FileOutputStream(file));
			byte[] bt = new byte[1024 * 4];
			int length = 0;
			while ((length = zip.read(bt)) > 0) {
				out.write(bt, 0, length);
			}
			out.flush();
			out.close();
		}
		catch (Exception e) {
			flag = false;
			throw new RuntimeException(e);
		}
		return flag;
	}

	public static void main(String[] args) {
		try {
			ZipHandle.getInstance().compressFiles("/opt/tomcat7", "/opt/data/tomcat.zip");
			System.out.println(new File("/opt/data/tomcat.zip").length());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
