package com.example.ri_test.test_test.service.fileUploader;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


public class FileJSON {

    @Getter
    private String base64Data;

    @Getter
    private String name;

    @JsonCreator
    public FileJSON(@JsonProperty(value = "base64Data", required = true) String base64Data,
                    @JsonProperty(value = "name", required = true) String name){
      this.base64Data = base64Data;
      this.name = name;
    }

}
