package com.example.nurafshonstudy.backgroundtask;

import android.util.Log;

import com.example.nurafshonstudy.App;
import com.example.nurafshonstudy.config.Key_Values;
import com.example.nurafshonstudy.pojos.Answer;
import com.example.nurafshonstudy.pojos.Category;
import com.example.nurafshonstudy.pojos.SubCategory;
import com.example.nurafshonstudy.pojos.Test;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class ExcelUtility {
    private static final String TAG = "ExcelUtility";

    private static ExcelUtility excelUtility;
    private Category category;
    private SubCategory subCategory;
    private File file;
    private InputStream inputStream;
    private HSSFWorkbook workbook;
    private FormulaEvaluator formulaEvaluator;
    private boolean isOpenFile;

    public static ExcelUtility getInstance() {
        if (excelUtility == null) {
            excelUtility = new ExcelUtility();
        }
        return excelUtility;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public boolean isExistsCategoryFile() {
        return isExistsCategoryFile(category);
    }

    public boolean isExistsCategoryFile(Category category) {
        App app = App.getInstance();
        File file = new File(app.getBaseDirectory(), category.getName() + Key_Values.EXCEL_EXTENTION);
        return file.exists();
    }

    public boolean openExcelXLS() throws IOException {
        boolean answer = false;
        App app = App.getInstance();
        file = new File(app.getBaseDirectory(), this.category.getName() + Key_Values.EXCEL_EXTENTION);

        Log.d(TAG, "filename=" + file.getAbsolutePath());
        inputStream = new FileInputStream(file);
//        if (!inputStream.markSupported()) {
//            inputStream = new PushbackInputStream(inputStream, 8);
//        }
//        org.apache.poi.hssf.record.crypto.Biff8EncryptionKey.setCurrentUserPassword(Key_Values.KEY);
        Log.d(TAG, " log read subcategory 11");
        NPOIFSFileSystem fs = new NPOIFSFileSystem(inputStream);
        Log.d(TAG, " log read subcategory 12");
        workbook = new HSSFWorkbook(fs.getRoot(),true);
        formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Log.d(TAG, " log read subcategory 13");
        answer = true;
        isOpenFile = answer;
        return answer;
    }

//    public boolean openExcelXLSX() {
//        boolean answer = false;
//        App app = App.getInstance();
//        file = new File(app.getBaseDirectory(), this.category.getName() + Key_Values.EXCEL_EXTENTION);
//        Log.d(TAG, "filename=" + file.getAbsolutePath());
//        try {
//            inputStream = new FileInputStream(file);
////            if (!inputStream.markSupported()) {
////                inputStream = new PushbackInputStream(inputStream, 8);
////            }
////            Log.d(TAG, " log read subcategory 11");
////            NPOIFSFileSystem fs = new NPOIFSFileSystem(inputStream);
////            Log.d(TAG, " log read subcategory 12");
////            EncryptionInfo info = new EncryptionInfo(fs);
////            Log.d(TAG, " log read subcategory 13");
////            Decryptor d = Decryptor.getInstance(info);
////            Log.d(TAG, " log read subcategory 14");
////
////            answer=d.verifyPassword(Key_Values.KEY);
////            Log.d(TAG, " log read subcategory 15");
////            inputStream = d.getDataStream(fs);
//            Log.d(TAG, " log read subcategory 16");
//            //workbook = new XSSFWorkbook(OPCPackage.open(inputStream));
//            workbook = WorkbookFactory.create(file);
//            formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
//            Log.d(TAG, " log read subcategory 17");
//            answer = true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            answer = false;
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//            answer = false;
//        } catch (IOException e) {
//            e.printStackTrace();
//            answer = false;
//        }
//        isOpenFile = answer;
//        return answer;
//    }

    public boolean openExcel() throws IOException {
        return openExcelXLS();
    }

    public void closeExcel() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStream = null;
        workbook = null;
        isOpenFile = false;
    }

    public boolean isOpenExcel() {
        return isOpenFile;
    }

    public void setMAC() throws IOException, InvalidFormatException {
        //ArrayList<SubCategory> subCategoryArrayList = new ArrayList<>();
        Log.d(TAG, "readExcelData: Reading excel file");
        HSSFSheet sheet = workbook.getSheetAt(0);
        //XSSFSheet sheet=((XSSFWorkbook)workbook).getSheetAt(0);
        //XSSFSheet sheet=workbook.getSheetAt(0);
        int rowcount = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rowcount; i++) {
            Row row = sheet.getRow(i);
            int cellcount = row.getPhysicalNumberOfCells();
            if (cellcount < 1) {
                throw new IOException("Excel file format is not correct");
            }
            String value = getCellAsString(row, 0, formulaEvaluator);
            if (value.equals(Key_Values.EXCEL_KEY)) {
                String mac = App.getInstance().getMAC();
                row.getCell(1).setCellValue(mac);
                Log.d(TAG, "MAC ADDRESS=" + mac);
                break;
            }
        }

        FileOutputStream outFile = new FileOutputStream(file);
        Log.d(TAG, "closed 3");
        workbook.write(outFile);
        Log.d(TAG, "closed 4");

        outFile.close();
        Log.d(TAG, "closed 5");
        this.closeExcel();
        Log.d(TAG, "closed 6");
    }

    public void setPassword() throws InvalidFormatException, IOException, GeneralSecurityException {
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(fs, EncryptionMode.agile);
        Encryptor enc = info.getEncryptor();
        enc.confirmPassword(Key_Values.KEY); // s3cr3t is your password to open sheet.

        OPCPackage opc = OPCPackage.open(file, PackageAccess.READ_WRITE);
        OutputStream os = enc.getDataStream(fs);
        opc.save(os);
        opc.close();

        //File saveFile=new File(App.getInstance().getContextFilesDir(),"pass.xlsx");
        //FileOutputStream fos = new FileOutputStream("D:/path/excel.xlsx");
        FileOutputStream fos = new FileOutputStream(file);
        fs.writeFilesystem(fos);
        fos.close();
    }

    public ArrayList<SubCategory> getSubCategories() throws IOException {
        ArrayList<SubCategory> subCategoryArrayList = new ArrayList<>();

        //XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);
        Log.d(TAG, " log read subcategory 4");
        int rowcount = sheet.getPhysicalNumberOfRows();
        Log.d(TAG, " log read subcategory 5");
        Log.d(TAG, "row count=" + rowcount);
        int subCategoryCount = 0;
        int rowIndex = 0;
        for (int i = 1; i < rowcount; i++) {
            Log.d(TAG, " log read subcategory " + (7 + i));
            Row row = sheet.getRow(i);
            int cellcount = row.getPhysicalNumberOfCells();
            Log.d(TAG, " cellcount=" + cellcount);
            if (cellcount < 1) {
                throw new IOException("Excel file format is not correct");
            }
            String value = getCellAsString(row, 0, formulaEvaluator);

            if (value.equals(Key_Values.EXCEL_DATE)) {

            }
            if (value.equals(Key_Values.EXCEL_DESCRIPTION)) {

            }
            if (value.equals(Key_Values.EXCEL_KEY)) {

            }
            if (value.equals(Key_Values.EXCEL_SUBCATEGORYCOUNT)) {
                double count = Double.parseDouble(getCellAsString(row, 1, formulaEvaluator));
                subCategoryCount = (int) count;
                Log.d(TAG, "subcategory  double=" + count + "  int=" + subCategoryCount);
                rowIndex = i + 1;
                break;
            }
            if (value.equals(Key_Values.EXCEL_SUBJECT)) {

            }
        }

        for (int i = 0; i < subCategoryCount; i++) {
            Row row = sheet.getRow(i + rowIndex);
            String value = getCellAsString(row, 1, formulaEvaluator);
            SubCategory subCategory = new SubCategory();
            subCategory.setId(i);
            subCategory.setName(value);
            subCategoryArrayList.add(subCategory);
        }

//            inputStream.close();
//            workbook.close();

        return subCategoryArrayList;
    }

    public ArrayList<Test> getAllTestBySubCategory() throws IOException {
        return getAllTestsBySubCategory(this.subCategory);
    }

    public ArrayList<Test> getAllTestsBySubCategory(SubCategory subCategory) throws IOException {
        ArrayList<Test> testArrayList = new ArrayList<>();
        HSSFSheet sheet = workbook.getSheetAt(1);
        int rowcount = sheet.getPhysicalNumberOfRows();
        Log.d(TAG, "row count=" + rowcount);
        for (int i = 1; i < rowcount; i++) {
            Row row = sheet.getRow(i);
            int cellcount = row.getPhysicalNumberOfCells();
            if (cellcount < 7) {
                throw new IOException("Excel file format is not correct");
            }
            String value = getCellAsString(row, 1, formulaEvaluator);

            if (value.equals(subCategory.getName())) {
                String question = getCellAsString(row, 2, formulaEvaluator);
                Answer a = new Answer();
                a.setText(getCellAsString(row, 3, formulaEvaluator));
                a.setSelected(false);

                Answer b = new Answer();
                b.setText(getCellAsString(row, 4, formulaEvaluator));
                b.setSelected(false);

                Answer c = new Answer();
                c.setText(getCellAsString(row, 5, formulaEvaluator));
                c.setSelected(false);

                Answer d = new Answer();
                d.setText(getCellAsString(row, 6, formulaEvaluator));
                d.setSelected(false);

                String correct = getCellAsString(row, 7, formulaEvaluator);
                Log.d(TAG, "'" + correct + "'");
                switch (correct) {
                    case "1.0":
                        a.setCorrect(true);
                        b.setCorrect(false);
                        c.setCorrect(false);
                        d.setCorrect(false);
                        Log.d(TAG, " a correct");
                        break;
                    case "2.0":
                        b.setCorrect(true);
                        a.setCorrect(false);
                        c.setCorrect(false);
                        d.setCorrect(false);
                        Log.d(TAG, " b correct");
                        break;
                    case "3.0":
                        c.setCorrect(true);
                        b.setCorrect(false);
                        a.setCorrect(false);
                        d.setCorrect(false);
                        Log.d(TAG, " c correct");
                        break;
                    case "4.0":
                        d.setCorrect(true);
                        b.setCorrect(false);
                        c.setCorrect(false);
                        a.setCorrect(false);
                        Log.d(TAG, " d correct");
                        break;
                }
                testArrayList.add(new Test(question, a, b, c, d));
            }
        }
        return testArrayList;
    }

    public ArrayList<Test> getRandomTestBySubCategory() throws IOException {
        return getRandomTestBySubCategory(this.subCategory);
    }

    public ArrayList<Test> getRandomTestBySubCategory(SubCategory subCategory) throws IOException {
        ArrayList<Test> allTest = getAllTestsBySubCategory(subCategory);
        ArrayList<Test> tests = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        Random random = new Random();
        int length = App.getInstance().getTestCount();
        if (length >= allTest.size()) {
            return allTest;
        }
        int count = 0;
        do {
            int index = random.nextInt(allTest.size());
            if (!indexes.contains(index)) {
                indexes.add(index);
                tests.add(allTest.get(index));
                count++;
            }
        }
        while (count != length);
        return tests;
    }

    private String getCellAsString(Row row, int cellIndex, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(cellIndex);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = "" + cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = "" + String.valueOf(numericValue);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = "" + cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage());
        }
        return value;
    }


}
