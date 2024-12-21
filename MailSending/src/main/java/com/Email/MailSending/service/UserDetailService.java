package com.Email.MailSending.service;

import com.Email.MailSending.dto.InvalidUserDetailDTO;
import com.Email.MailSending.module.UserDetail;
import com.Email.MailSending.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

//@Service
//public class UserDetailService {
//
//    @Autowired
//    private ExcelService excelService;
//    @Autowired
//    private UserDetailRepository userDetailRepository;
//    public void save(MultipartFile file){
//        try{
//            List<UserDetail> userDetailList= excelService.convert2ProductDetailList(file.getInputStream());
//            userDetailRepository.saveAll(userDetailList);
//
//        }catch (Exception e){
//            e.getStackTrace();
//            e.getMessage();
//        }
//
//    }
//}

@Service
public class UserDetailService {

    @Autowired
    private ExcelService excelService;
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private InvalidUserMailService invalidUserMailService;

    public void save(MultipartFile file) {
        try {
            List<InvalidUserDetailDTO> invalidRows = new ArrayList<>();
            List<UserDetail> validRows = excelService.convert2UserDetailList(file.getInputStream(), invalidRows);
            userDetailRepository.saveAll(validRows);

            if (!invalidRows.isEmpty()) {
                invalidUserMailService.sendInvalidUsersEmail(invalidRows, "propertyhub69@gmail.com");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}