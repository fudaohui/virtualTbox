package com.fdh.simulator.utils;

import com.fdh.simulator.model.Tbox;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fudh
 * @ClassNmme ExcelUtils
 * @date 2019/1/28 11:24
 * @Description: TODO
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 从excel中读取TBOX数据
     *
     * @return
     */
    public static List<Tbox> getTBoxDataFromExcel() {

        List<Tbox> tboxes = new ArrayList<>();
        File file = new File("D:\\TBOX在网记录.xlsx");
        if (!file.exists()) {
            return tboxes;
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);//获取第一个sheet
            int importSize = sheet.getLastRowNum();
            if (importSize <= 1) {
                return tboxes;
            }

            for (int i = 0; i < importSize; i++) {
                Row row = sheet.getRow(i+1);//从第三行开始
                if (row != null) {
                    Cell cell0 = row.getCell(2);//取第一列VIN
                    Cell cell1 = row.getCell(3);//取第一列VIN
                    Cell cell2 = row.getCell(4);//取第一列VIN
                    if(cell0 == null || cell1==null || cell2==null){
                        continue;
                    }
                    cell2.setCellType(Cell.CELL_TYPE_STRING);
                    String vin = cell0.toString().trim();
                    String iccid = cell1.toString().trim();
                    String deviceId = cell2.toString().trim();
                    if(StringUtils.isEmpty(vin) || StringUtils.isEmpty(iccid)||StringUtils.isEmpty(deviceId)){
                        continue;
                    }
                    Tbox tbox = new Tbox(vin,iccid,deviceId);
                    tboxes.add(tbox);
                }
            }


        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            return tboxes;
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tboxes;
        }

    }

    public static void main(String[] args) {
        List<Tbox> tBoxDataFromExcel = getTBoxDataFromExcel();
        System.out.println(tBoxDataFromExcel.size());
    }
}
