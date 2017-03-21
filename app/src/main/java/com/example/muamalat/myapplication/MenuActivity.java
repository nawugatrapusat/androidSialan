package com.example.muamalat.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView contentView;
    private Button buttonView;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private ViewPager viewPager;
    String jsonData;
    JSONArray resultData = null;
    private static final String TAG_RESULTS="result";
//        public static final String jsonUrl = "http://10.80.40.27:80/sip-sialan/api/androidproduct/getallproduct";
    public static final String jsonUrl = "http://sialan.gunawansaputra.com/api/androidproduct/getallproduct";
    ArrayList<HashMap<String, String>> dataPrint=null;
    ListView listViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        setTitle("Home");
//        setContentView(R.layout.activity_main);
////        contentView = (TextView) findViewById(R.id.contentView);
////        contentView.setMovementMethod(new ScrollingMovementMethod());
////        buttonView = (Button) findViewById(R.id.buttonView);
////        buttonView.setOnClickListener(this);
////        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header_list,listView,false);
////        listView.addHeaderView(headerView);
        listViews = (ListView) findViewById(R.id.listViews);
        dataPrint = new ArrayList<HashMap<String, String>>();
        getJson(jsonUrl);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this,"YOUR MESSAGE",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");
        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void onClick(View v){
        if(v==buttonView){
            getJson(jsonUrl);
        }
    }

    private void getJson(String url){
        class GetJson extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(MenuActivity.this, "Please Wait . . ",null,true,true);
            }

            @Override
            protected String doInBackground(String... params){
                String uri = params[0];

                Log.w("myApp", uri);
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine()) != null){
                        sb.append(json+"\n");
                    }
                    return sb.toString().trim();

                }catch (Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
//                contentView.setText(s);
                jsonData=s;
                Log.w("myApp", s);
                showData();
            }
        }
        GetJson gj = new GetJson();
        gj.execute(url);
    }

    protected void showData(){
        dataPrint.clear();
        try{
            JSONObject pJsonObject = new JSONObject(jsonData);
            JSONArray pData = pJsonObject.getJSONArray("data");
//            Toast.makeText(MainActivity.this,"YOUR MESSAGE",Toast.LENGTH_LONG).show();
            for(int a=0;a<pData.length();a++){
                JSONObject getData = pData.getJSONObject(a);

                String pNama = getData.getString("nama");
                String pBerat = getData.getString("berat");
                String pHargaJual = getData.getString("harga_jual");
                String pStock = getData.getString("stock");
                String pPending = getData.getString("pending");

//                String upperString = pNama.substring(0,1).toUpperCase() + pNama.substring(1);
//                String upperString = Character.toUpperCase(pNama.charAt(0)) + pNama.substring(1);

                double parsed = Double.parseDouble(pHargaJual);
                String formatted = NumberFormat.getInstance().format(parsed);

                HashMap<String,String> showData = new HashMap<String, String>();
                showData.put("no",String.valueOf(a+1));
                showData.put("nama",Character.toUpperCase(pNama.charAt(0)) + pNama.substring(1));
                showData.put("berat",pBerat);
                showData.put("hargaJual","Rp. "+formatted);
                showData.put("stockNow",String.valueOf(Integer.valueOf(pStock)-Integer.valueOf(pPending)));

                dataPrint.add(showData);
            }
            Log.d("list", dataPrint.toString());
            ListAdapter adapters = new SimpleAdapter(
                    MenuActivity.this, dataPrint, R.layout.list_main,
                    new String[]{"no","nama","berat","hargaJual","stockNow"},
                    new int[]{R.id.no,R.id.nama,R.id.berat,R.id.hargaJual,R.id.stockNow}
            );

            Log.w("myApp", "try masuk 3");
            if(adapters == null ) {
                Log.w("myApp", "try masuk 44");

            }
            listViews.setAdapter(adapters);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
