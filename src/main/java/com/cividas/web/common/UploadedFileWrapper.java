package com.cividas.web.common;


import org.primefaces.model.file.UploadedFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


public class UploadedFileWrapper {

	private UploadedFile file;
	private String secondfilename;
	private Integer tmpId;
	private Number iddocumentmastertype;
	private String documentTypeName;
	private String attachmentdatadesc;
	private String hash;

	public UploadedFileWrapper(UploadedFile file, Integer tmpId) {
		super();
		this.file = file;
		this.tmpId = tmpId;
	}

	public UploadedFileWrapper(UploadedFile file, Integer tmpId, Integer iddocumentmastertype, String documentTypeName) {
		super();
		this.file = file;
		this.tmpId = tmpId;
		this.iddocumentmastertype = iddocumentmastertype;
		this.documentTypeName = documentTypeName;
	}

	public UploadedFileWrapper(byte[] content, String filename, String secondfilename, Integer tmpId, Integer iddocumentmastertype, String documentTypeName) {
		super();
		this.file = new WrappedFile(filename, secondfilename, content);
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

	public String getSecondFilename() {
		return secondfilename;
	}

	public void setSecondFilename(String secondfilename) {
		this.secondfilename = secondfilename;
	}

	public Integer getTmpId() {
		return tmpId;
	}

	public void setTmpd(Integer tmpId) {
		this.tmpId = tmpId;
	}

	public Number getIddocumentmastertype() {
		return iddocumentmastertype;
	}

	public void setIddocumentmastertype(Number iddocumentmastertype) {
		this.iddocumentmastertype = iddocumentmastertype;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public String getAttachmentdatadesc() {
		return attachmentdatadesc;
	}

	public void setAttachmentdatadesc(String attachmentdatadesc) {
		this.attachmentdatadesc = attachmentdatadesc;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getFileBase64(){
		return Base64.getEncoder().encodeToString(file.getContent());
	}

	public static class WrappedFile implements UploadedFile {

		private final String filename;
		private final String secondfilename;
		private InputStream inputstream;
		private final byte[] content;
		private String contentType;

		public WrappedFile(String filename, String secondfilename, byte[] content) {
			this.filename = filename;
			this.secondfilename = secondfilename;
			this.content = content;
		}

		public WrappedFile(String filename, String secondfilename, InputStream inputstream, byte[] content, String contentType) {
			this.filename = filename;
			this.secondfilename = secondfilename;
			this.inputstream = inputstream;
			this.content = content;
			this.contentType = contentType;
		}

		@Override
		public String getFileName() {
			return filename;
		}

		public String getSecondFilename() {
			return secondfilename;
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
		public void write(String filePath) throws Exception {
		}

		@Override
		public void delete() throws IOException {

		}
	}
}