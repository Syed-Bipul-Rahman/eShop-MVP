package com.classjob.eshop_mvp.Interfaces;

import com.classjob.eshop_mvp.Model.ProductsResponse;

public interface MainActivityInterface {

    interface mainView {

        public void allProductList(ProductsResponse productsResponse);


        void hideLoading();
        void showLoading();
        void showError();

    }

    interface MainPresenter {
        public void getAllData();
    }

}
