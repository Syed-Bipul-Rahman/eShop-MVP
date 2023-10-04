package com.classjob.eshop_mvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.classjob.eshop_mvp.Adapters.HomeAdapter;
import com.classjob.eshop_mvp.Networks.ApiInterface;
import com.classjob.eshop_mvp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    HomeAdapter dataAdapter;
    ApiInterface apiInterface;


    ActivityMainBinding binding;


   // String url = "https://fakestoreapi.com/products";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




    }
}