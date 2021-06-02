package com.example.spacexcrew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacexcrew.adapter.CrewAdapter;
import com.example.spacexcrew.model.Crew;
import com.example.spacexcrew.network.CrewAPI;
import com.example.spacexcrew.network.RetrofitRequest;
import com.example.spacexcrew.repository.CrewRepository;
import com.example.spacexcrew.viewmodel.CrewViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private CrewViewModel crewViewModel;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CrewAdapter adapter;
    private ArrayList<Crew> crewList;
    private CrewRepository crewRepository;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvError = (TextView) findViewById(R.id.tv_error);

        crewList = new ArrayList<>();
        adapter = new CrewAdapter(MainActivity.this, crewList);
        crewRepository = new CrewRepository(getApplication());

        boolean connection=isNetworkAvailable();
        if(connection){
            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
            refreshLayout();
            initRecyclerView();
            getCrews();
            networkRequest();
        }
        else{
            Toast.makeText(getApplicationContext(), "Internet Not Connected", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Connection Problem!");
        }
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
    }

    private void getCrews() {
        crewViewModel = new ViewModelProvider(this).get(CrewViewModel.class);
        crewViewModel.getAllCrew().observe(this, new Observer<List<Crew>>() {
            @Override
            public void onChanged(List<Crew> crewList) {
                Log.d("TAG", "onChanged: " + crewList);
                adapter.getAllCrew(crewList);
                recyclerView.setAdapter(adapter);
            }
        });
    };

    private void networkRequest() {
        RetrofitRequest.getRetrofitInstance().create(CrewAPI.class)
                .getAllCrews()
                .enqueue(new Callback<List<Crew>>() {
                    @Override
                    public void onResponse(Call<List<Crew>> call, Response<List<Crew>> response) {
                        if (!response.isSuccessful()) {
                            tvError.setVisibility(View.VISIBLE);
                            tvError.setText("Error in fetching");
                            return;
                        }
                        progressBar.setVisibility(View.GONE);
                        tvError.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        crewRepository.insert(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Crew>> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        tvError.setVisibility(View.VISIBLE);
                        tvError.setText("Error in fetching Data");
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.menu_delete:
                crewRepository.deleteAll();
                Toast.makeText(getApplicationContext(),"Deleted All",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_refresh:
                swipeRefreshLayout.setRefreshing(true);
                boolean connection=isNetworkAvailable();
                if(connection){
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
                    initRecyclerView();
                    getCrews();
                    networkRequest();
                }
                else{
                    swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Connection Problem!");
                }
                Toast.makeText(getApplicationContext(),"refreshing...",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                boolean connection=isNetworkAvailable();
                if(connection){
                    Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
                    initRecyclerView();
                    getCrews();
                    networkRequest();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Internet Not Connected", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Connection Problem!");
                }
            }
        });
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }

}