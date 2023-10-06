package com.classjob.eshop_mvp.Views;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.classjob.eshop_mvp.Model.Product;
import com.classjob.eshop_mvp.Networks.ApiInterface;
import com.classjob.eshop_mvp.R;
import com.classjob.eshop_mvp.databinding.ActivityProductDetailsBinding;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetails extends AppCompatActivity {
    ActivityProductDetailsBinding binding;
    ApiInterface apiInterface;
    String id, pronameint;

    ImageSlider imageSlider;
    double productPrice;
    int defaultquantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);


        getSupportActionBar().hide();

        //init views


        imageSlider = findViewById(R.id.img_slid);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        pronameint = intent.getStringExtra("proname");

//split product name 2 words
        String[] split = pronameint.split(" ");


        if (split.length > 2) {
            String firstWord = split[0];
            String secondWord = split[1];
            binding.productnametitlebar.setText(firstWord + " " + secondWord);
        } else {
            binding.productnametitlebar.setText(pronameint);
        }


        //retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
        getDetails();


        binding.backArroworder.setOnClickListener(v -> {
            finish();
        });

        binding.quantityTextview.setText(defaultquantity + "");
        //increase decrease functionality
        //for increase
        binding.increaseQuantitybtn.setOnClickListener(v -> {
            defaultquantity += 1;
            binding.quantityTextview.setText(defaultquantity + "");

            productPrice *= defaultquantity;
            binding.totaltopayprice.setText("$" + productPrice);

        });
        //for decrease
        binding.decreaseQuantitybtn.setOnClickListener(v -> {
            if (defaultquantity > 1) {
                productPrice /= defaultquantity;
                defaultquantity -= 1;
                binding.quantityTextview.setText(defaultquantity + "");
                binding.totaltopayprice.setText("$" + productPrice);
            }

        });


        //show dialogue
        binding.orderNow.setOnClickListener(v -> {
            showAlert("Order has been successfully placed. You have to pay " + productPrice + "$ After receive the product.");
        });
        binding.addToCart.setOnClickListener(v -> {
            showAlert("Product Successfully added to your cart.");
        });


    }

    private void showAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetails.this);
        builder.setTitle("Success");
        builder.setIcon(R.drawable.ic_baseline_check_circle_24);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();

    }


    private void getDetails() {
        Call<Product> callAllProducts = apiInterface.getSingleProducts("products/" + id);
        callAllProducts.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                if (response.isSuccessful()) {
                    //hide progress bar
                    binding.linearLayout.setVisibility(View.VISIBLE);
                    binding.showloadin.setVisibility(View.GONE);

                    //get price for conversion
                    productPrice = response.body().getPrice();
                    String productname = response.body().getTitle();


                    binding.namedetails.setText(productname);
                    binding.branddetails.setText(response.body().getBrand());
                    binding.categorydetails.setText(response.body().getCategory());
                    binding.stockdetails.setText("Stock: " + response.body().getStock());
                    binding.totaltopayprice.setText("$" + productPrice);
                    binding.ratingdetails.setText(response.body().getRating() + " ");

                    binding.pricedetails.setText("$" + productPrice);
                    binding.descdetails.setText(response.body().getDescription());

//                    //show image using picasso
//                    String imageUrl = response.body().getThumbnail();
//                    Picasso.get().load(imageUrl).into(productimage);

                    // Load product images into ImageSlider
                    List<String> productImages = response.body().getImages();
                    List<SlideModel> imageList = new ArrayList<>();
                    for (String imageUrl : productImages) {
                        imageList.add(new SlideModel(imageUrl, ScaleTypes.CENTER_INSIDE));
                    }
                    imageSlider.setImageList(imageList);
                    //  Log.d("images",imageList.toString());

                } else {
                    Toast.makeText(ProductDetails.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductDetails.this, "Failed to connect with server", Toast.LENGTH_SHORT).show();
            }


        });
    }

}