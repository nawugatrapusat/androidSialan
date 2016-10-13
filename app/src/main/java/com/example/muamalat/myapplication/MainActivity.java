package com.example.muamalat.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ParagraphStyle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView contentView;
    private Button buttonView;
    String jsonData;
    JSONArray resultData = null;
    private static final String TAG_RESULTS="result";
//    public static final String jsonUrl = "http://10.80.40.27:80/sip-sialan/api/androidproduct/getallproduct";
        public static final String jsonUrl = "http://sialan.gunawansaputra.com/api/androidproduct/getallproduct";
    ArrayList<HashMap<String, String>> dataPrint=null;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sistem Informasi Penjualan");
        setContentView(R.layout.activity_main);
///        contentView = (TextView) findViewById(R.id.contentView);
//        contentView.setMovementMethod(new ScrollingMovementMethod());
        listView = (ListView) findViewById(R.id.listView);
//        buttonView = (Button) findViewById(R.id.buttonView);
//        buttonView.setOnClickListener(this);
        dataPrint = new ArrayList<HashMap<String, String>>();
//        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header_list,listView,false);
//        listView.addHeaderView(headerView);
        getJson(jsonUrl);
    }

    public void onClick(View v){
        if(v==buttonView){
            getJson(jsonUrl);
        }
    }

    private void getJson(String url){
        class GetJson extends AsyncTask<String, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait . . ",null,true,true);
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
                    Log.w("myApp", "try masuk");
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

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, dataPrint, R.layout.list_main,
                    new String[]{"no","nama","berat","hargaJual","stockNow"},
                    new int[]{R.id.no,R.id.nama,R.id.berat,R.id.hargaJual,R.id.stockNow}
            );
            listView.setAdapter(adapter);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
