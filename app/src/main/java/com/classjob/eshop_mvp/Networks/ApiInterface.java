package com.classjob.eshop_mvp.Networks;

import com.classjob.eshop_mvp.Model.CartResponse;
import com.classjob.eshop_mvp.Model.Product;
import com.classjob.eshop_mvp.Model.ProductsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("products?limit=100")
    Call<ProductsResponse> getAllProducts();

    @GET("shopcart/show-cart.php")
    Call<CartResponse> getCarts();


    @GET()
    Call<Product> getSingleProducts(@Url String url);

    @GET()
    Call<ProductsResponse> getCategoryproducts(@Url String url);
}
