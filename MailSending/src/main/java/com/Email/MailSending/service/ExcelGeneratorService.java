package com.Email.MailSending.service;


import com.Email.MailSending.dto.InvalidUserDetailDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelGeneratorService {

        Logger logger = LoggerFactory.getLogger(ExcelGeneratorService.class);
    public ByteArrayInputStream generateExcelForInvalidUsers(List<InvalidUserDetailDTO> invalidUsers) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        try {
            Sheet sheet = workbook.createSheet("Invalid Users");

            logger.info("sheet create");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "Invalid Field", "Validation Message"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Data Rows
            int rowIdx = 1;
            for (InvalidUserDetailDTO user : invalidUsers) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(user.getId() != null ? user.getId() : 0);
                row.createCell(1).setCellValue(user.getName() != null ? user.getName() : "N/A");
                row.createCell(2).setCellValue(user.getValidationField() != null ? user.getValidationField() : "N/A");
                row.createCell(3).setCellValue(user.getValidationMessage() != null ? user.getValidationMessage() : "N/A");
            }

            workbook.write(out);
            logger.info("data store in sheet");
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                workbook.close();
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}