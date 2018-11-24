/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author santosh.r
 */
public class ExcelReports extends AbstractExcelView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model, org.apache.poi.hssf.usermodel.HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List list = (List)model.get("data");
        String  titlesStr = (String)model.get("titles");
        String fieldTypesStr =(String) model.get("fieldTypes");
        
        
        if(titlesStr==null){        
            return ;
        }
        
        String[] titles = titlesStr.split("\\|");
        String[] fields = fieldTypesStr.split("\\|");
        
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow header = sheet.createRow(0);
        
         header.createCell(0).setCellValue("S.NO");
        for(int i=0;i<titles.length;i++){
            header.createCell(i+1).setCellValue(titles[i]);
        }
        
        int rowNum = 1;
        for(int j=0;j<list.size();j++){
            HSSFRow row = sheet.createRow(rowNum++);
            Object[] data = (Object[])list.get(j);
            row.createCell(0).setCellValue(j+1);
            for(int i=0;i<data.length;i++){
               switch(fields[i]){
                   case "string": 
                       row.createCell(i+1).setCellValue(data[i]!=null?String.valueOf(data[i]):"");
                       break;
                   case "double":
                       row.createCell(i+1).setCellValue(data[i]!=null?(double)data[i]:0);
                       break;
                   case "int":
                       row.createCell(i+1).setCellValue(data[i]!=null?(int)data[i]:0);
                       break;
                   case "date":
                       String rowData = "";
                       if (data[i] != null) {
                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                            formatter.setTimeZone(TimeZone.getTimeZone("IST"));
                            rowData = formatter.parse(String.valueOf(data[i])).toString();
                       }
                       row.createCell(i+1).setCellValue(rowData);
                       break;
                   default:
                       row.createCell(i+1).setCellValue(data[i]!=null?String.valueOf(data[i]):"");
               }
                
            }
        }
        
    }
    
}
