package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Image;
import com.example.demo.service.ImageService;

@RestController
public class ImageController {

    @Autowired 
    ImageService imageService;

    @GetMapping("/image/{id}")
    public ResponseEntity<Image> getImage(@PathVariable Long id) {
        return new ResponseEntity<>(imageService.getImage(id), HttpStatus.OK);
    }

    @GetMapping("/image")
    public ResponseEntity <List<Image>> getAllImages() {
        return new ResponseEntity<>(imageService.getAllImages(), HttpStatus.OK);
    }

     @PostMapping("/image")
     public ResponseEntity<Image> createNewImage(@RequestParam("text") String text, @RequestParam("source") String source, @RequestParam("id") Long id, @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(imageService.createNewImage(text, source, id, file), HttpStatus.CREATED);
     }

     @DeleteMapping("/image/{id}")
     public ResponseEntity<HttpStatus> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }
}
