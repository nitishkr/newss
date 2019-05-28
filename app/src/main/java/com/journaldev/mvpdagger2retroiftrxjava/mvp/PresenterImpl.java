package com.journaldev.mvpdagger2retroiftrxjava.mvp;

import com.journaldev.mvpdagger2retroiftrxjava.pojo.NewsData;
import com.journaldev.mvpdagger2retroiftrxjava.retrofit.APIInterface;

import javax.inject.Inject;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PresenterImpl implements MainActivityContract.Presenter {

    APIInterface apiInterface;
    MainActivityContract.View mView;

    @Inject
    public PresenterImpl(APIInterface apiInterface, MainActivityContract.View mView) {
        this.apiInterface = apiInterface;
        this.mView = mView;
    }

    @Override
    public void loadData() {

        mView.showProgress();

        apiInterface.getData("in","84e256fd98524652a0d2fdcbff3e1abf").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsData>() {
                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError("Error occurred" + e.getMessage()+"\n"+e.getStackTrace());
                        mView.hideProgress();
                    }

                    @Override
                    public void onNext(NewsData data) {

                        mView.showData(data.articles);
                    }
                });
    }
}
