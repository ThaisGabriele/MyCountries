package u.icomp.mycountries;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import u.icomp.mycountries.Adapter.CustomAdapter;
import u.icomp.mycountries.Dao.Operations;
import u.icomp.mycountries.Model.Country;
import u.icomp.mycountries.Util.Http;
import u.icomp.mycountries.Util.HttpRetrofit;

public class ListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private CustomAdapter adapter;
    private List<Country> countriesList;
    private ListView listView;
    private SwipeRefreshLayout swiperefresh;
    Operations db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        swiperefresh = (SwipeRefreshLayout) findViewById((R.id.swiperefresh));
        //seta cores
        swiperefresh.setColorScheme(R.color.colorPrimary, R.color.colorAccent);
        swiperefresh.setOnRefreshListener(this);

        listView = (ListView) findViewById(R.id.listView);

        countriesList = new ArrayList<Country>();

        adapter = new CustomAdapter(this, countriesList);
        db = new Operations(getBaseContext());
        getDataRetro();

        listView.setAdapter((ListAdapter) adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplication(), countriesList.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });


       /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hasPermission();

                Intent intent = new Intent(ListActivity.this, MapsActivity.class);
                intent.putExtra("c",countriesList.get(i));
                startActivity(intent);
            }
        });
        */



    }


    @Override
    public void onRefresh() {
        getDataRetro();

    }

    class CountryTask extends AsyncTask<Void, Void, List<Country>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swiperefresh.setRefreshing(true);
        }

        @Override
        protected List<Country> doInBackground(Void... voids) {
            return Http.loadCountriesJson();
        }

        @Override
        protected void onPostExecute(List<Country> c) {
            super.onPostExecute(c);
            if (c != null) {
                countriesList.clear();
                countriesList.addAll(c);
                adapter.notifyDataSetChanged();
            }
            swiperefresh.setRefreshing(false);
        }
    }
    public void getDataHttp () {
        CountryTask cTask = new CountryTask();
        cTask.execute();
    }

    public Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( cm != null ) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnected();
        }
        return false;
    }

    public void getDataRetro() {

        swiperefresh.setRefreshing(true);

        // if connection exists get data via GET
        if (isConnected()) {
            HttpRetrofit.getCountryClient().getCountry().enqueue(new Callback<List<Country>>() {
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                    if (response.isSuccessful()) {
                        List<Country> countryBody = response.body();
                        countriesList.clear();

                        db.deleteAll();

                        for (Country c : countryBody) {
                            countriesList.add(c);
                            db.insertAll(c);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        System.out.println(response.errorBody());
                    }
                    swiperefresh.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {
                    t.printStackTrace();
                }

            });

        }else {
            swiperefresh.setRefreshing(false);
            Toast.makeText(this,"Not connected...",Toast.LENGTH_SHORT).show();
            getDataSqlite();
        }
    }
    private void getDataSqlite() {
        countriesList.clear();
        countriesList.addAll(db.listCountries());
        adapter.notifyDataSetChanged();
    }

    void hasPermission(){
        //pede permissao de localizacao
        if (ContextCompat.checkSelfPermission(ListActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // ja pediu permissao?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ListActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                // solicita permissao de localizacao
                ActivityCompat.requestPermissions(ListActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }
    }


}
