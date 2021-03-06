package com.manos.prototype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.service.PictureServiceImpl;

@RestController
@RequestMapping("/product-pictures")
public class ProductPictureController {

	@Autowired
	private PictureServiceImpl pictureService;
	
	@GetMapping(value = "/{id}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
			MediaType.IMAGE_GIF_VALUE })
	public ResponseEntity<ByteArrayResource> getPicture(@PathVariable("id") int pictureId) {
		ProductPicture picture = pictureService.getPicture(pictureId);
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(picture.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + picture.getName() + "\"")
                .body(new ByteArrayResource(picture.getPicture()));
	}
}
