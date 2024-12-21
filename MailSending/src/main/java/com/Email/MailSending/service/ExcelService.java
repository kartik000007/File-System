package com.Email.MailSending.service;

import com.Email.MailSending.dto.InvalidUserDetailDTO;
import com.Email.MailSending.module.UserDetail;
import org.apache.commons.math3.analysis.function.Ceil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

//@Service
//public class ExcelService {
//
//    public boolean isExcelFile(MultipartFile file) {
//        String filename = file.getOriginalFilename();
//        return filename != null && (filename.endsWith(".xls") || filename.endsWith(".xlsx"));
//    }
//
//    public List<UserDetail> convert2ProductDetailList(InputStream file){
//        List<UserDetail> list=new ArrayList<>();
//            try{
//                XSSFWorkbook xssfWorkbook=new XSSFWorkbook(file);
//                XSSFSheet sheet=xssfWorkbook.getSheet("User detail");
//
//                int rowNumber=0;
//                Iterator<Row> iterator=sheet.iterator();
//                while(iterator.hasNext()){
//                    Row row=iterator.next();
//                    if(rowNumber==0){
//                        rowNumber++;
//                        continue;
//                    }
//                    Iterator<Cell> cellIterator=row.iterator();
//                    int cid=0;
//
//                    UserDetail userDetail=new UserDetail();
//                    while (cellIterator.hasNext()){
//                        Cell cell=cellIterator.next();
//                        switch (cid){
//                            case 0:
//                                userDetail.setId((long) cell.getNumericCellValue());
//                                break;
//                            case 1:
//                                userDetail.setName(cell.getStringCellValue());
//                                break;
//
//                                case 2:
//                                userDetail.setPhoneNumber((long) cell.getNumericCellValue());
//                                break;
//                                case 3:
//                                userDetail.setEmail(cell.getStringCellValue());
//                                break;
//                            default:
//                                break;
//                        }
//                        cid++;
//
//                    }
//                    list.add(userDetail);
//
//                }
//            }catch (Exception e){
//                e.getStackTrace();
//            }
//
//
//        return list;
//    }
//}


@Service
public class ExcelService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public boolean isExcelFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return filename != null && (filename.endsWith(".xls") || filename.endsWith(".xlsx"));
    }


    public List<UserDetail> convert2UserDetailList(InputStream file, List<InvalidUserDetailDTO> invalidRows) {
        List<UserDetail> validList = new ArrayList<>();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
            XSSFSheet sheet = xssfWorkbook.getSheet("User detail");

            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cid = 0;

                UserDetail userDetail = new UserDetail();
                boolean isValid = true;
                InvalidUserDetailDTO invalidDetail = null;

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cid) {
                        case 0:
                            userDetail.setId((long) cell.getNumericCellValue());
                            break;
                        case 1:
                            userDetail.setName(cell.getStringCellValue());
                            break;
                        case 2:
                            String phone = String.valueOf((long) cell.getNumericCellValue());
                            if (phone.length() != 10) {
                                isValid = false;
                                invalidDetail = new InvalidUserDetailDTO();
                                invalidDetail.setId(userDetail.getId());
                                invalidDetail.setName(userDetail.getName());
                                invalidDetail.setValidationField("phoneNumber");
                                invalidDetail.setValidationMessage("Invalid phone number: " + phone);
                            } else {
                                userDetail.setPhoneNumber(Long.parseLong(phone));
                            }
                            break;
                        case 3:
                            String email = cell.getStringCellValue();
                            if (!EMAIL_PATTERN.matcher(email).matches()) {
                                isValid = false;
                                if (invalidDetail == null) {
                                    invalidDetail = new InvalidUserDetailDTO();
                                    invalidDetail.setId(userDetail.getId());
                                    invalidDetail.setName(userDetail.getName());
                                }
                                invalidDetail.setValidationField("email");
                                invalidDetail.setValidationMessage("Invalid email format: " + email);
                            } else {
                                userDetail.setEmail(email);
                            }
                            break;
                        default:
                            break;
                    }
                    cid++;
                }

                if (isValid) {
                    validList.add(userDetail);
                } else if (invalidDetail != null) {
                    invalidRows.add(invalidDetail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return validList;
    }
}