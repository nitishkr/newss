package com.journaldev.mvpdagger2retroiftrxjava.mvp;


import com.journaldev.mvpdagger2retroiftrxjava.pojo.Article;

import java.util.List;

public interface MainActivityContract {
    interface View {
        void showData(List<Article> data);

        void showError(String message);

        void showComplete();

        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void loadData();
    }
}
