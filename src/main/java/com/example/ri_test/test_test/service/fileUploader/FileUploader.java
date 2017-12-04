package com.example.ri_test.test_test.service.fileUploader;

import lombok.Getter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

/**
* Сервис загрузки файлов на сервер.
* Конфигурируем его через Spring
* И внедряем в сервис, который принимает файлы*/
public class FileUploader {

    private FileUploader(){};

    @Getter
    private String directoryPath;
    @Getter
    private Integer maxSizeInBytes;
    @Getter
    private Set<String> allowedExtensions;


    public FileUploader(String directoryPath, Integer maxSizeInBytes, Set<String> allowedExtensions){
        this.directoryPath = directoryPath;
        this.maxSizeInBytes = maxSizeInBytes;
        this.allowedExtensions = allowedExtensions;
    }

    /**
     * @param fileJson файл, который сохраним п папку на сервере
     * @return Имя файла в папке. При получении файла запрашиваем это имя.
     */
    public String uploadFile(FileJSON fileJson){

       if(! fileSizeIsValid(fileJson)){
           throw new FileSizeIsTooBigException("file size is bigger then " + maxSizeInBytes +" Bytes");
       }

        if(! fileExtensionIsValid(fileJson)){
            throw new NotAllowedFileExtensionException("file extension is not allowed");
        }
       String fileName = UUID.randomUUID().toString();
       String fileExtension = FilenameUtils.getExtension(fileJson.getName());
       String fullName = fileName + "." + fileExtension;
       String filePath = directoryPath + "/" + fullName;
       Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            byte[] fileBytes = Base64.decodeBase64(fileJson.getBase64Data());
            Files.write(path, fileBytes);
        } catch (IOException e) {
            throw new FileNotCreatedException(e);
        }
        return fullName;
    }


    /**
     * @param fileName имя файла, который нужно получить
     * @return объект File для файла с этим именем.
     */
    public File getFileByName(String fileName){
        String fullPath = directoryPath + "/" + fileName;
        File file = new File(fullPath);
        if(!file.exists() || !file.canRead()){
            throw new FileNotReadableException("unable to read file" + fileName);
        }
        return file;
    }

    private Boolean fileSizeIsValid(FileJSON fileJSON){
        return fileJSON.getBase64Data().length() <= maxSizeInBytes;
    }

    private Boolean fileExtensionIsValid(FileJSON fileJSON){
        String extension = FilenameUtils.getExtension(fileJSON.getName());
        return allowedExtensions.contains(extension);
    }


}
