package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    private static final String FILE_PATH = "src/test/resources/TestData/LoginData.xlsx";

    public static String getData(int row, int col) {
        String value = "";
        FileInputStream fis = null;
        Workbook workbook = null;

        try {
            fis = new FileInputStream(FILE_PATH);
            workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("Login"); // Sheet Name in Excel

            if (sheet != null) {
                Row sheetRow = sheet.getRow(row);
                if (sheetRow != null) {
                    Cell cell = sheetRow.getCell(col);
                    if (cell != null) {
                        value = cell.getStringCellValue();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close resources properly
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
