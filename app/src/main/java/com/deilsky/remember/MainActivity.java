package com.deilsky.remember;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.deilsky.awakening.widget.AwakeningView;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {
    private SlimAdapter adapter;
    private RecyclerView recyclerView;
    private AssetManager assets;
    private TabLayout tabLayout;
    private List<String> data = new ArrayList<String>();
    private String dir = "a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        initAssets();

    }

    private void initAssets() {
        assets = getApplicationContext().getResources().getAssets();
        data = new ArrayList<String>();
        try {
            String[] images = getApplicationContext().getResources().getAssets().list(dir);
            data = Arrays.asList(images);
            adapter.updateData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponent() {
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_white_inner1), true);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_white_outer1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_white_inner2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_white_outer2));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        dir = "a";
                        break;
                    case 1:
                        dir = "b";
                        break;
                    case 2:
                        dir = "c";
                        break;
                    case 3:
                        dir = "d";
                        break;
                }
                initAssets();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        adapter = SlimAdapter.create();
        adapter.register(R.layout.item_recycle, new SlimInjector<String>() {
            @Override
            public void onInject(@NonNull final String data, @NonNull IViewInjector injector) {

                injector.with(R.id.imageView, new IViewInjector.Action<ImageView>() {
                    @Override
                    public void action(ImageView view) {
                        AwakeningView.RectangleBuilder.create().connerAll(5).fillColor(R.color.transparent).strokeColor(R.color.transparent).strokeSize(1).build().target(view).build();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        Bitmap bitmap;
                        Log.d("action", data);
                        try {
                            bitmap = BitmapFactory.decodeStream(assets.open(dir + "/" + data), null, options);
                            view.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).attachTo(recyclerView);
    }

}
