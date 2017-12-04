package com.example.ri_test.test_test.controller;

import com.example.ri_test.test_test.service.fileUploader.FileJSON;
import com.example.ri_test.test_test.service.fileUploader.FileNotReadableException;
import com.example.ri_test.test_test.service.fileUploader.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

  @Autowired
  FileUploader fileUploader;

  @PostMapping(value = "/")
  public Map saveFile(@RequestBody FileJSON fileJson){
    String fileName = fileUploader.uploadFile(fileJson);
    return Collections.singletonMap("src", "/file/" + fileName + "/");
  }


  @RequestMapping(value = "/{filename}/",  method = RequestMethod.GET)
  public ResponseEntity getFile(@PathVariable("filename") String filename) throws Exception {
    File file;
    try {
      file = fileUploader.getFileByName(filename);
    } catch (FileNotReadableException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found!");
    }
    return ResponseEntity.ok()
        .header("Content-Disposition", "attachment; filename=" +file.getName())
        .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
        .body(Files.readAllBytes(file.toPath()));
  }



}
