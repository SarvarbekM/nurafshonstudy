package com.example.nurafshonstudy.config;

import android.os.Build;
import android.os.Environment;

import java.io.File;

public interface Key_Values {
    String BASE_URL="http://test.nurafshon-study.uz";
    String DOWNLOAD_URL=BASE_URL+"/api/default/upload?category_id=";
    String BASE_DIRECTORY = ".NurafshonStudy";
    //String BASE_EXTERNAL_DIRECTORY =Environment.getExternalStorageDirectory()+File.separator+ BASE_DIRECTORY;
    //String BASE_EXTERNAL_DIRECTORY =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+File.separator+ BASE_DIRECTORY;
    String OK="OK";
    String ERROR="ERROR";
    String CACHE_CATEGORIES_FILENAME="Categories.json";

    String EXCEL_DATE="Date";
    String EXCEL_DESCRIPTION="Description";
    String EXCEL_SUBJECT="Subject";
    String EXCEL_KEY="Key";
    String EXCEL_SUBCATEGORYCOUNT="SubCategoryCount";
    String EXCEL_SUBCATEGORIES="Sub-Categories";
    String EXCEL_EXTENTION=".xls";
    String KEY="KEY";
    String RESULT_FILENAME="Result.json";
    String RESULT_SPLITTER="|";
    String Shared_PREF_NAME="MY_PREFS_NAME";
    String PREF_TEST_COUNT="TEST_COUNT";
    String PREF_HISTORY_DAY="HISTORY_DAY";

}
