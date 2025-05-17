package com.cividas.web.common;


import org.primefaces.model.file.UploadedFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
//import org.primefaces.model.UploadedFile;
//import org.primefaces.model.file.UploadedFiles;


public class UploadedFileWrapperFerrol {
	
	private UploadedFile file;
	private Integer tmpId;
	

	private String attachmentdatadesc;

	private Number iddocumentmastertype;
	
	private String documentTypeName;
	
	private String observations;
	
	public UploadedFileWrapperFerrol(UploadedFile file, Integer tmpId) {
		super();
		this.file = file;
		this.tmpId = tmpId;
	}
	
	public UploadedFileWrapperFerrol(UploadedFile file, Integer tmpId, Integer iddocumentmastertype, String documentTypeName) {
		super();
		this.file = file;
		this.tmpId = tmpId;
		this.iddocumentmastertype = iddocumentmastertype;
		this.documentTypeName = documentTypeName;
	}
	
	public UploadedFileWrapperFerrol(byte[] content, String filename, Integer tmpId, Integer iddocumentmastertype, String documentTypeName) {
		super();
		this.file = new WarppedFile(filename, content);
		this.tmpId = tmpId;
		this.iddocumentmastertype = iddocumentmastertype;
		this.documentTypeName = documentTypeName;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Integer getTmpId() {
		return tmpId;
	}

	public void setTmpd(Integer tmpId) {
		this.tmpId = tmpId;
	}
	
	public String getAttachmentdatadesc() {
		return attachmentdatadesc;
	}

	public void setAttachmentdatadesc(String attachmentdatadesc) {
		this.attachmentdatadesc = attachmentdatadesc;
	}

	public Number getIddocumentmastertype() {
		return iddocumentmastertype;
	}

	public void setIddocumentmastertype(Number iddocumentmastertype) {
		this.iddocumentmastertype = iddocumentmastertype;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}
	
	public String getFileBase64(){
		return Base64.getEncoder().encodeToString(file.getContent());
	}
	
	public class WarppedFile implements UploadedFile{
		
		private String filename;
		private InputStream inputstream;
		private byte[] content;
		private String contentType;
		
		public WarppedFile(String filename,byte[] content) {
			this.filename=filename;
			this.content=content;
		}
		
		public WarppedFile(String filename,InputStream inputstream,byte[] content,String contentType) {
			this.filename=filename;
			this.inputstream=inputstream;
			this.content=content;
			this.contentType=contentType;
		}

		@Override
		public String getFileName() {
			return filename;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return inputstream;
		}

		@Override
		public long getSize() {
			return content.length;
		}

		@Override
		public byte[] getContent() {
			return content;
		}

		@Override
		public String getContentType() {
			return contentType;
		}

		@Override
		public void write(String filePath) throws Exception {}

		@Override
		public void delete() throws IOException {
		}
	}
}