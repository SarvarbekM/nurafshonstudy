package com.example.nurafshonstudy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nurafshonstudy.App;
import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.backgroundtask.ExcelUtility;
import com.example.nurafshonstudy.config.Key_Values;
import com.example.nurafshonstudy.interfaces.IAPIMethods;
import com.example.nurafshonstudy.interfaces.IProgress;
import com.example.nurafshonstudy.interfaces.ItemClickListener;
import com.example.nurafshonstudy.network.DownloadTask;
import com.example.nurafshonstudy.network.RetrofitClientInstance;
import com.example.nurafshonstudy.pojos.Category;
import com.example.nurafshonstudy.ui.adapter.CategoryAdapter;
import com.example.nurafshonstudy.ui.adapter.EmptyAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainFragment";
    Type categoryListType = new TypeToken<ArrayList<Category>>() {}.getType();


    private RecyclerView categoryRV;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar prg;
    private IProgress onDownload;
    private ArrayList<Category> categoryList;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        categoryRV = view.findViewById(R.id.categoryRecycleView);
        categoryRV.setAdapter(new EmptyAdapter());
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategoryList();
            }
        });
        swipeRefreshLayout.measure(swipeRefreshLayout.getWidth(), swipeRefreshLayout.getHeight());
        swipeRefreshLayout.setRefreshing(true);
        prg = view.findViewById(R.id.prg);

        onDownload = new IProgress() {
            @Override
            public void onProgressStart() {
                prg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressUpdate(Integer value) {
            }

            @Override
            public void onProgressFinish(boolean isCorrect) {
                if (!isCorrect) {
                    Toast.makeText(getContext(), "Download error, please try again", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "File downloaded successful", Toast.LENGTH_SHORT).show();
                    printCategories();
                }
                prg.setVisibility(View.GONE);
            }
        };
        getCategoryList();
    }

    private void getCategoryList() {
        IAPIMethods service = RetrofitClientInstance.getRetrofitInstance().create(IAPIMethods.class);
        Call<ArrayList<Category>> call = service.getCategoryList();
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                prg.setVisibility(View.GONE);
                try{
                    categoryList = response.body();
                    printCategories();
                }catch(Exception ex){
                    Toast.makeText(getContext(), "Server javob olishda xatolik yuz berdi, Iltimos, yana urinib ko'ring", Toast.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                prg.setVisibility(View.GONE);
                String jsonString = readCategoriesFromCache();
                Log.d(TAG, jsonString);
                if (!jsonString.isEmpty()) {
                    categoryList = (new Gson()).fromJson(jsonString, categoryListType);
                    printCategories();
                    Toast.makeText(getContext(), "Serverdan javob kelmagani uchun CACHEdan foydalanildi", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Serverdan javob kelmadi. Iltimos, yana urinib ko'ring !", Toast.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void downloadCategoryFile(Category category) {
        App app = App.getInstance();
        File file = new File(app.getBaseDirectory(), category.getName() + Key_Values.EXCEL_EXTENTION);
        DownloadTask downloadTask = new DownloadTask(onDownload, file.getAbsolutePath());
        downloadTask.execute(Key_Values.DOWNLOAD_URL + category.getId().toString());
    }

    private String readCategoriesFromCache() {
        //App app= (App) getActivity().getApplication();
        App app = App.getInstance();
        String answer = "";
        File file = new File(app.getBaseDirectory(), Key_Values.CACHE_CATEGORIES_FILENAME);
        if (file.exists()) {
            try {
                StringBuilder text = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
                answer = text.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return answer;
    }

    private void writeCategoriesCache(String jsonString) {
        //App app= (App)getActivity().getApplication();
        App app = App.getInstance();
        File file = new File(app.getBaseDirectory(), Key_Values.CACHE_CATEGORIES_FILENAME);
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    OutputStream out = new FileOutputStream(file, false);
                    out.write(jsonString.getBytes());
                    out.flush();
                    out.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, jsonString);
    }

    private void printCategories() {
        CategoryAdapter adapter = new CategoryAdapter(categoryList);
        categoryRV.setAdapter(adapter);
        String jsonString = (new Gson()).toJson(categoryList);
        writeCategoriesCache(jsonString);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        ItemClickListener listener = (ItemClickListener) getContext();
        if (id == R.id.statItem) {
            File file = new File(App.getInstance().getBaseDirectory() + File.separator + Key_Values.RESULT_FILENAME);
            if (file.exists()) {
                listener.onClickStatistics();
            } else {
                Toast.makeText(getContext(), "Statistics file not found", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.settingsItem) {
            listener.onClickSettings();
            //Toast.makeText(getContext(), "Settings item click", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.ecItem) {
            listener.onCLickEducation();
        } else if (id == R.id.softItem) {
            listener.onClickSoftware();
        }

        DrawerLayout drawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
