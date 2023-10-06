package com.classjob.eshop_mvp.Presenters;

import com.classjob.eshop_mvp.Interfaces.MainActivityInterface;
import com.classjob.eshop_mvp.MainActivity;
import com.classjob.eshop_mvp.Model.ProductsResponse;
import com.classjob.eshop_mvp.Networks.ApiClient;
import com.classjob.eshop_mvp.Networks.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainActivityInterface.MainPresenter {

    MainActivityInterface.mainView mainView;

    public MainActivityPresenter(MainActivityInterface.mainView mainView) {
        this.mainView = mainView;

        mainView.showLoading();

    }


    @Override
    public void getAllData() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProductsResponse> call = apiInterface.getAllProducts();

        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                if (response.isSuccessful()) {

                    mainView.allProductList(response.body());
                   // mainView.hideLoading();

                    // binding.progressbarContainer.setVisibility(View.GONE);


                    //  recyclerView.setVisibility(View.VISIBLE);
                    // List<Product> products = response.body().getProducts();
                    // setDataAdapter((ArrayList<Product>) products);

                    // dataAdapter.notifyDataSetChanged();

                } else {
                    //  Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                //    Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
