package com.journaldev.mvpdagger2retroiftrxjava;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.journaldev.mvpdagger2retroiftrxjava.di.component.ApplicationComponent;
import com.journaldev.mvpdagger2retroiftrxjava.di.component.DaggerMainActivityComponent;
import com.journaldev.mvpdagger2retroiftrxjava.di.component.MainActivityComponent;
import com.journaldev.mvpdagger2retroiftrxjava.di.module.MainActivityContextModule;
import com.journaldev.mvpdagger2retroiftrxjava.di.module.MainActivityMvpModule;
import com.journaldev.mvpdagger2retroiftrxjava.di.qualifier.ActivityContext;
import com.journaldev.mvpdagger2retroiftrxjava.di.qualifier.ApplicationContext;
import com.journaldev.mvpdagger2retroiftrxjava.mvp.MainActivityContract;
import com.journaldev.mvpdagger2retroiftrxjava.mvp.PresenterImpl;
import com.journaldev.mvpdagger2retroiftrxjava.pojo.Article;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View, RecyclerViewAdapter.ClickListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    MainActivityComponent mainActivityComponent;
    @Inject
    public RecyclerViewAdapter recyclerViewAdapter;


    @Inject
    @ApplicationContext
    public Context mContext;

    @Inject
    @ActivityContext
    public Context activityContext;

    @Inject
    PresenterImpl presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityContextModule(new MainActivityContextModule(this))
                .mainActivityMvpModule(new MainActivityMvpModule(this))
                .applicationComponent(applicationComponent)
                .build();

        mainActivityComponent.injectMainActivity(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        recyclerView.setAdapter(recyclerViewAdapter);
        progressBar = findViewById(R.id.progressBar);

        presenter.loadData();


    }

    @Override
    public void launchIntent(String url) {
        //Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(activityContext, WebViewActivity.class).putExtra("url", url));
    }

    @Override
    public void showData(List<Article> data) {
        recyclerViewAdapter.setData(data);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        Log.e("Data error",message);
    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
