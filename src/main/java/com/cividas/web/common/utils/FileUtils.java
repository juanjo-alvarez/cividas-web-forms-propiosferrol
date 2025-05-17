package com.cividas.web.common.utils;

import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cividas.customforms.webapp.controller.base.BaseController;

public class FileUtils {
	public static final long CONVERT_MB_B = 1048576;
	public static final long CONVERT_KB_B = 1024;
	public static final long CONVERT_GB_B = 1073741824;
	public static final String DEFAULT_KB = "KB";
	public static final String DEFAULT_GB = "GB";
	public static final String DEFAULT_MB = "MB";
	public static final String DEFAULT_BYTES = "Bytes";
	protected static final Logger log = LoggerFactory.getLogger(BaseController.class);

	/**
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(UploadedFile file) throws Exception {
		long filesize = file.getSize();
		return filesize;
	}

	/**
	 *
	 * @param number
	 * @return
	 */
	public static double convertFileSize(Double number) {
		double s = 0;

		if (number != null) {
			Double sizeConvert = number / CONVERT_KB_B;
			s = Math.round(sizeConvert * 100.0) / 100.0;
		}

		return s;
	}

	/**
	 * 
	 * @param f
	 * @return
	 */
	public static String getExtension(UploadedFile f) {
		String ext = null;
		String s = f.getFileName();
		int i = s.lastIndexOf('.');

		if ((i > 0) && (i < (s.length() - 1))) {
			ext = s.substring(i + 1);
		}
		return ext;
	}

	private static int valideExtension(String eFile, String eValide) {
		int uploadFile = 0;
		if(eValide.indexOf(",")!=-1) {
			String[] extensions = eValide.split(",");
			for (String extension : extensions) {
				if (extension.equalsIgnoreCase(eFile)) {
					uploadFile=0;
					break;
				}else {
					uploadFile=1;
				}
			}
		}else {
			if (!eValide.equalsIgnoreCase(eFile)) {
				uploadFile = 1;
				
			}
		}
		return uploadFile;

	}

	public static int valideMaxFile(String maxFile, int countFile) {
		int uploadFile = 0;
		int maxFilenum = Integer.parseInt(maxFile);
		if (maxFilenum <= countFile) {
			uploadFile = 2;
		}

		return uploadFile;
	}
	
	private static int valideMaxSizeFile(String maxSizeFile, double fileSize) {
		int uploadFile = 0;
		try {
			double maxSizeFileNum = FileUtils.convertFileSize(Double.parseDouble(maxSizeFile));	
			double fileSizeConvert = FileUtils.convertFileSize(fileSize);
			if(maxSizeFileNum <= fileSizeConvert) {
				uploadFile = 3;
			}

		} catch (NumberFormatException ex) {
			log.error("Error al convertir maxSizeFile");
		}	
		return uploadFile;
	}
	
	public static int valideFile(String extenFile, String extenValide, String maxFile, int countFile, String maxSizeFile, double fileSize) {
		int uploadFile = 0;
		
		if(extenValide!=null) {
			uploadFile = valideExtension(extenFile, extenValide);
		}
		if(uploadFile==0) {
			uploadFile = valideMaxFile(maxFile, countFile);
		}
		if(uploadFile==0 && maxSizeFile!=null) {
			uploadFile = valideMaxSizeFile(maxSizeFile, fileSize);
		}
		
		return uploadFile;
	}
	
	public static int valideFilemasterType(String extenFile, String extenValide, String maxSizeFile, double fileSize) {
		int uploadFile = 0;
		
		if(extenValide!=null) {
			uploadFile = valideExtension(extenFile, extenValide);
		}
		if(uploadFile==0 && !maxSizeFile.isEmpty()) {
			uploadFile = valideMaxSizeFile(maxSizeFile, fileSize);
		}
		
		return uploadFile;
	}
	
	public static String messageValidationFile(int error) {
		String message = null;
		switch (error) {
		case 1: message = "wrong_file_extension";
			
			break;

		case 2: message = "wrong_max_file";
			
			break;
			
		case 3: message = "wrong_file_size";
			
			break;
		}
		return message;
	}

}
