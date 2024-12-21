package com.Email.MailSending.controller;

import com.Email.MailSending.service.ExcelService;
import com.Email.MailSending.service.UserDetailService;
import com.twilio.rest.chat.v1.service.UserDeleter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class UserDetailController {

    private final UserDetailService userDetailService;
    private final ExcelService excelService;
    public UserDetailController(UserDetailService userDetailService, ExcelService excelService) {
        this.userDetailService = userDetailService;
        this.excelService = excelService;
    }


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile multipartFile){

        if(excelService.isExcelFile(multipartFile)){
                this.userDetailService.save(multipartFile);
                return ResponseEntity.ok(Map.of("message","file upload successfully...."));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("upload Excel file");
    }
}
