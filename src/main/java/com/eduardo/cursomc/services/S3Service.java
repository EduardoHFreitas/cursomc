package com.eduardo.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.eduardo.cursomc.services.exceptions.FileException;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucket;

	private Logger log = LoggerFactory.getLogger(S3Service.class);

	public URI uploadFile(MultipartFile file) {
		try {
			InputStream stream = file.getInputStream();

			String fileName = file.getOriginalFilename();
			String contentType = file.getContentType();

			return uploadFile(stream, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro na Entrada de Dados: " + e.getMessage());
		}
	}

	public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(contentType);

			s3Client.putObject(bucket, fileName, inputStream, objectMetadata);

			log.info("Iniciando Upload");
			return s3Client.getUrl(bucket, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI.");
		}
	}
}