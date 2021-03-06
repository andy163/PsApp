package me.pangshen.psapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.pangshen.psapp.weixinredbag.RedBagActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private List<String> list_title;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private List<View> listViews;
    private List<Fragment> list_fragment;

    private TextView app_versionTextView;

    private ImageView imageView;

    private void saveImageToGallery(){
        imageView.setDrawingCacheEnabled(true);
        Bitmap b = imageView.getDrawingCache();
        MediaStore.Images.Media.insertImage(getContentResolver(), b,"庞深", "公众号【庞深】");
        Toast.makeText(this,"已保存到相册",Toast.LENGTH_SHORT);
    }

    private void setAppVersion() {
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(
                    this.getPackageName(), 0);
            app_versionTextView = (TextView)findViewById(R.id.app_version);
            app_versionTextView.setText("" + pi.versionName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tablayout = (TabLayout)findViewById(R.id.tablayout);
        viewpager = (ViewPager)findViewById(R.id.tab_vp);

//        viewChanage();
        fragmentChange();

//        setAppVersion();
//        imageView = (ImageView)findViewById(R.id.app_img);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveImageToGallery();
//            }
//        });
    }

    /**
     * 采用在viewpager中切换 view 的方式,并添加了icon的功能
     */
    private void viewChanage()
    {
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();

        listViews.add(mInflater.inflate(R.layout.main_page, null));
        listViews.add(mInflater.inflate(R.layout.main_page, null));
        listViews.add(mInflater.inflate(R.layout.main_page, null));

        list_title = new ArrayList<>();
        list_title.add("新闻");
        list_title.add("体育");
        list_title.add("娱乐");

        //设置TabLayout的模式,这里主要是用来显示tab展示的情况的
        //TabLayout.MODE_FIXED          各tab平分整个工具栏,如果不设置，则默认就是这个值
        //TabLayout.MODE_SCROLLABLE     适用于多tab的，也就是有滚动条的，一行显示不下这些tab可以用这个
        //                              当然了，你要是想做点特别的，像知乎里就使用的这种效果
        tablayout.setTabMode(TabLayout.MODE_FIXED);

        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);

        //为TabLayout添加tab名称
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));


        ViewAdapter vAdapter = new ViewAdapter(this,listViews,list_title);
        viewpager.setAdapter(vAdapter);

        //将tabLayout与viewpager连起来
        tablayout.setupWithViewPager(viewpager);
    }

    /**
     * 采用viewpager中切换fragment
     */
    private void fragmentChange()
    {
        list_fragment = new ArrayList<>();

        list_fragment.add(new MainFragment());
        list_fragment.add(new SecondFragment());
        list_fragment.add(new ThreeFragment());

        list_title = new ArrayList<>();
        list_title.add(getResources().getString(R.string.main_title));
        list_title.add(getResources().getString(R.string.second_title));
        list_title.add(getResources().getString(R.string.three_title));

        FragmentAdapter fAdapter = new FragmentAdapter(getSupportFragmentManager(),list_fragment,list_title);
        viewpager.setAdapter(fAdapter);

        //将tabLayout与viewpager连起来
        tablayout.setupWithViewPager(viewpager);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            startActivity(new Intent(this,CalculatorActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this,TestActivity.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            startActivity(new Intent(this,RedBagActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
