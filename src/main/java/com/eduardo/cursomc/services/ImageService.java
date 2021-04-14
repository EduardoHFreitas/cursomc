package com.eduardo.cursomc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eduardo.cursomc.services.exceptions.FileException;

@Service
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String extension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		
		if (!extension.equals("png") && !extension.equals("jpg")) {
			throw new FileException("Formato de imagem nao suportado!");
		}
		
		try {
			BufferedImage image = ImageIO.read(uploadedFile.getInputStream());
			
			if (extension.equals("png")) {
				image = convertToJPG(image);
			}
			
			return image;
		} catch (IOException e) {
			throw new FileException("Erro ao ler o arquivo carregado!");
		}
	}

	private BufferedImage convertToJPG(BufferedImage image) {
		BufferedImage jpgImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB); 
		
		jpgImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		
		return jpgImage;
	}
	
	public InputStream getInputStream(BufferedImage image, String extension) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(image, extension, output);

			return new ByteArrayInputStream(output.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler o arquivo!");
		}
	}
	
	public BufferedImage cropSquare(BufferedImage image) {
		int min = getMinScale(image);
		return Scalr.crop(image, (image.getWidth() / 2) - (min / 2), (image.getHeight() / 2) - (min / 2), min, min); 
	}

	public BufferedImage resize(BufferedImage image, int size) {
		return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, size);
	}
	
	private int getMinScale(BufferedImage image) {
		return image.getHeight() <= image.getWidth() ? image.getHeight() : image.getWidth();
	}
}
