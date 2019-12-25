package com.example.nurafshonstudy.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.nurafshonstudy.App;
import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.backgroundtask.ExcelUtility;
import com.example.nurafshonstudy.config.Key_Values;
import com.example.nurafshonstudy.interfaces.ItemClickListener;
import com.example.nurafshonstudy.pojos.Category;
import com.example.nurafshonstudy.pojos.ResultTest;
import com.example.nurafshonstudy.pojos.SubCategory;
import com.example.nurafshonstudy.pojos.Test;
import com.example.nurafshonstudy.ui.fragment.EducationFragment;
import com.example.nurafshonstudy.ui.fragment.MainFragment;
import com.example.nurafshonstudy.ui.fragment.ResultTestFragment;
import com.example.nurafshonstudy.ui.fragment.SettingFragment;
import com.example.nurafshonstudy.ui.fragment.SoftwareFragment;
import com.example.nurafshonstudy.ui.fragment.StatisticsFragment;
import com.example.nurafshonstudy.ui.fragment.StatisticsItemFragment;
import com.example.nurafshonstudy.ui.fragment.SubCategoriesFragment;
import com.example.nurafshonstudy.ui.fragment.TestFragment;
import com.example.nurafshonstudy.ui.fragment.TestItemFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ItemClickListener {
    Type resultListType = new TypeToken<ArrayList<ResultTest>>() {
    }.getType();
    public static final String TAG = "MainActivity";
    private MainFragment mainFragment;
    private Category choosedCategory;
    private SubCategory choosedSubCategory;
    private ArrayList<ResultTest> resultTests;
//    private ArrayList<Test> tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        App.getInstance().loadSettings();
        if (createDirectory()) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.container, mainFragment, MainFragment.class.getSimpleName()).commit();
        } else {
            Toast.makeText(this, "APKni papkasi yaratilmadi.", Toast.LENGTH_LONG).show();
        }
    }


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.testanychart);
//        Pie pie = AnyChart.pie();
//        ArrayList<DataEntry> data = new ArrayList<>();
//        data.add(new ValueDataEntry("John", 10000));
//        data.add(new ValueDataEntry("Jake", 12000));
//        data.add(new ValueDataEntry("Peter", 18000));
//        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
//        anyChartView.setChart(pie);
//    }

    private boolean createDirectory() {
        boolean answer = true;
        //App app= (App) getApplication();
        App app = App.getInstance();
        File dir = new File(app.getContextFilesDir(), Key_Values.BASE_DIRECTORY);
        Log.d(TAG, dir.getAbsolutePath());
        if (!dir.exists()) {
            answer = dir.mkdir();
        }
        return answer;
    }

    @Override
    public void onClickDownload(Category category) {
        ExcelUtility e = ExcelUtility.getInstance();
        this.choosedCategory = category;
        e.setCategory(category);
        mainFragment.downloadCategoryFile(category);
    }

    @Override
    public void onClickCategory(Category category) {
        choosedCategory = category;
        if (ExcelUtility.getInstance().isExistsCategoryFile(category)) {
            ExcelUtility.getInstance().setCategory(category);
            getSupportFragmentManager().beginTransaction().addToBackStack(null).
                    replace(R.id.container, SubCategoriesFragment.newInstance(), SubCategoriesFragment.class.getSimpleName()).commit();
            //add(R.id.container, SubCategoriesFragment.newInstance(), SubCategoriesFragment.class.getSimpleName()).commit();
        } else {
            Toast.makeText(this, "Download file before opening " + category.getName(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClickSubCategory(SubCategory subCategory) {
        choosedSubCategory = subCategory;
        ExcelUtility excelUtility = ExcelUtility.getInstance();
        excelUtility.setSubCategory(subCategory);
        getSupportFragmentManager().beginTransaction().addToBackStack(null).
                replace(R.id.container, TestFragment.newInstance(), TestFragment.class.getSimpleName()).commit();
        //add(R.id.container, TestFragment.newInstance(), TestFragment.class.getSimpleName()).commit();
    }

    @Override
    public void onBackPressed() {

        TestItemFragment testItemFragment = (TestItemFragment) getSupportFragmentManager()
                .findFragmentByTag(TestItemFragment.class.getSimpleName());
        if (testItemFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(testItemFragment).commit();
            Log.d("TestItemFragment", "fing testitemfargment");
        }

        //super.onBackPressed();
        Log.d(TAG, "On back pressed");
        //getSupportFragmentManager().popBackStack();
        ResultTestFragment resultTestFragment = (ResultTestFragment) getSupportFragmentManager().
                findFragmentByTag(ResultTestFragment.class.getSimpleName());
        if (resultTestFragment != null && resultTestFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(resultTestFragment).commit();
            getSupportFragmentManager().popBackStack();
            Log.d(TAG, "On back pressed: resultTestFragment is visible");
        }

        TestFragment testFragment = (TestFragment) getSupportFragmentManager().
                findFragmentByTag(TestFragment.class.getSimpleName());
        if (testFragment != null && testFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(testFragment).commit();
            getSupportFragmentManager().popBackStack();
            Log.d(TAG, "On back pressed: testFragment is visible");
        }

        SubCategoriesFragment subCategoriesFragment = (SubCategoriesFragment) getSupportFragmentManager().
                findFragmentByTag(SubCategoriesFragment.class.getSimpleName());
        if (subCategoriesFragment != null && subCategoriesFragment.isVisible()) {
            ExcelUtility.getInstance().closeExcel();
            Log.d(TAG, "Excel is closed");
            getSupportFragmentManager().beginTransaction().remove(subCategoriesFragment).commit();
            getSupportFragmentManager().popBackStack();
            Log.d(TAG, "On back pressed: subCategoriesFragment is visible");

        }

        StatisticsFragment statisticsFragment = (StatisticsFragment) getSupportFragmentManager().
                findFragmentByTag(StatisticsFragment.class.getSimpleName());
        if (statisticsFragment != null && statisticsFragment.isVisible()) {
            ExcelUtility.getInstance().closeExcel();
            Log.d(TAG, "Excel is closed");
            getSupportFragmentManager().beginTransaction().remove(statisticsFragment).commit();
            getSupportFragmentManager().popBackStack();
            Log.d(TAG, "On back pressed: StatisticsFragment is visible");
        }

        SettingFragment settingFragment = (SettingFragment) getSupportFragmentManager().
                findFragmentByTag(SettingFragment.class.getSimpleName());
        if (settingFragment != null && settingFragment.isVisible()) {
            ExcelUtility.getInstance().closeExcel();
            Log.d(TAG, "Excel is closed");
            getSupportFragmentManager().beginTransaction().remove(settingFragment).commit();
            getSupportFragmentManager().popBackStack();
            Log.d(TAG, "On back pressed: SettingFragment is visible");
        }

        StatisticsItemFragment statItem = (StatisticsItemFragment) getSupportFragmentManager().
                findFragmentByTag(StatisticsItemFragment.class.getSimpleName());
        if (statItem != null && statItem.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(statItem).commit();
            getSupportFragmentManager().popBackStack();
            Log.d(TAG, "On back pressed: StatisticsItemFragment is visible");
        }

        SoftwareFragment softwareFragment = (SoftwareFragment) getSupportFragmentManager().
                findFragmentByTag(SoftwareFragment.class.getSimpleName());
        if (softwareFragment != null && softwareFragment.isVisible()) {
            ExcelUtility.getInstance().closeExcel();
            Log.d(TAG, "Excel is closed");
            getSupportFragmentManager().beginTransaction().remove(softwareFragment).commit();
            getSupportFragmentManager().popBackStack();
            Log.d(TAG, "On back pressed: SoftwareFragment is visible");
        }

        EducationFragment educationFragment = (EducationFragment) getSupportFragmentManager().
                findFragmentByTag(EducationFragment.class.getSimpleName());
        if (educationFragment != null && educationFragment.isVisible()) {
            ExcelUtility.getInstance().closeExcel();
            Log.d(TAG, "Excel is closed");
            getSupportFragmentManager().beginTransaction().remove(educationFragment).commit();
            getSupportFragmentManager().popBackStack();
            Log.d(TAG, "On back pressed: EducationFragment is visible");
        }




        if (mainFragment != null && mainFragment.isVisible()) {
//            getSupportFragmentManager().beginTransaction().remove(mainFragment).commit();
//            getSupportFragmentManager().popBackStack();
            Log.d(TAG, "On back pressed: mainFragment is visible");
            //super.onBackPressed();


            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);
        }

        Log.d(TAG, "On back pressed finished");

    }

    @Override
    public void onTestFinished(ArrayList<Test> tests) {
        saveResult(tests);
        getSupportFragmentManager().beginTransaction().//addToBackStack(null).
                replace(R.id.container, ResultTestFragment.newInstance(tests), ResultTestFragment.class.getSimpleName()).
                //addToBackStack(null).
                        commit();
    }

    @Override
    public void onClickStatistics() {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).
                replace(R.id.container, StatisticsFragment.newInstance(), StatisticsFragment.class.getSimpleName()).
                //addToBackStack(null).
                        commit();
    }

    @Override
    public void onClickSettings() {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).
                replace(R.id.container, SettingFragment.newInstance(), SettingFragment.class.getSimpleName()).
                //addToBackStack(null).
                        commit();
    }

    @Override
    public void onClickStatisticsItem(String item) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).
                replace(R.id.container, StatisticsItemFragment.newInstance(item,this.resultTests), StatisticsItemFragment.class.getSimpleName()).commit();
    }

    @Override
    public void onClickSoftware() {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).
                replace(R.id.container, SoftwareFragment.newInstance(), SoftwareFragment.class.getSimpleName()).commit();
    }

    @Override
    public void onCLickEducation() {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).
                replace(R.id.container, EducationFragment.newInstance(), EducationFragment.class.getSimpleName()).commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        App.getInstance().saveSettings();
        ExcelUtility.getInstance().closeExcel();
        Log.d(TAG, "Excel is closed");
    }

    private void saveResult(ArrayList<Test> tests) {
        int testCount = App.getInstance().getTestCount();
        int count = 0;
        for (Test item : tests) {
            if (item.getA().isCorrect() && item.getA().isSelected()) {
                count++;
            }
            if (item.getB().isCorrect() && item.getB().isSelected()) {
                count++;
            }
            if (item.getC().isCorrect() && item.getC().isSelected()) {
                count++;
            }
            if (item.getD().isCorrect() && item.getD().isSelected()) {
                count++;
            }
        }
        ResultTest resultTest = new ResultTest(choosedSubCategory.getName(), new Date(), testCount, count);
        Log.d(TAG, "resulttest: " + resultTest.toString());

        String filename = App.getInstance().getBaseDirectory() + File.separator + Key_Values.RESULT_FILENAME;
        File file = new File(filename);
        try {
            ArrayList<ResultTest> list = new ArrayList<>();
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
            if (!file.exists()) {
                file.createNewFile();
            } else {
                try {
                    StringBuilder text = new StringBuilder();
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                    String json = text.toString();
                    list = gson.fromJson(json, resultListType);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            list.add(resultTest);
            String jsonString = gson.toJson(list, resultListType);
            OutputStream out = new FileOutputStream(file, false);
            out.write(jsonString.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setResultTests(ArrayList<ResultTest> resultTests) {
        this.resultTests = resultTests;
    }
}
