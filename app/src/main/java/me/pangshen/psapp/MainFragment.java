package me.pangshen.psapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.pangshen.psapp.model.WeixinHot;
import me.pangshen.psapp.util.SharedPreferencesUtil;
import me.pangshen.psapp.util.Util;

public class MainFragment extends BaseFragment {
    private static final String TAG = MainFragment.class.getSimpleName();
    private List<WeixinHot> data = new ArrayList<WeixinHot>();
    private MainFragmentAdapter mainFragmentAdapter;
    private final  static  int num = 100;
    private  final static  String key = "WeixinHotList";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_page, container, false);
        initRecleView(rootView);
        getData(getActivity());
        return rootView;
    }

    private void getData(Context context) {
        String string  = SharedPreferencesUtil.getInstance(getContext()).getSp().getString(key,null);
        if (!TextUtils.isEmpty(string)){
            try {
                Log.d(TAG, "has cache.");
                prepareData(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        String key = "73c2d1b871a5883f40dc3b1d58e86e26";
        RequestQueue mQueue = Volley.newRequestQueue(context);
        final String requesetUrl=
                Util.messageFormat("http://api.tianapi.com/wxnew/?key={0}&num={1}", key,num);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,requesetUrl,
             new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String json = response.get("newslist").toString();
                        Log.d(TAG, "json="+json);
                        SharedPreferencesUtil.getInstance(getContext()).getEditor().
                                putString("WXhotList",json).commit();
                        prepareData(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        });
        mQueue.add(jsonObjectRequest);
    }

    private  void prepareData(String response){
        try {
            List<WeixinHot> list = Util.fromJson(response,new TypeToken<List<WeixinHot>>() {
                }.getType());
            if (data!=null) {
                data.clear();
                data.addAll(list);
            }
            Log.d(TAG, Util.toJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFragmentAdapter.notifyDataSetChanged();
    }

    private void initRecleView(View rootView) {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.main_page_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mainFragmentAdapter =new MainFragmentAdapter(data);
        recyclerView.setAdapter(mainFragmentAdapter);
    }

    class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {
        List<WeixinHot> data;

        public MainFragmentAdapter(List<WeixinHot> data) {
            this.data = data;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayout = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_page_item, null);
            return new ViewHolder(itemLayout, 0);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tx.setText(data.get(position).getTitle());
            holder.tx_desc.setText(data.get(position).getDescription());
            ImageLoader.getInstance().displayImage(data.get(position).getPicUrl(), holder.img, MyApplication.getDisplayImageOptions(false, 0));
            holder.main_page_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("urlData",data.get(position).getUrl());
                    jumpTo(DetailActivity.class, bundle);
                }
            });

        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView main_page_item;
            TextView tx;
            TextView tx_desc;
            ImageView img;
            public ViewHolder(View itemView, int itemType) {
                super(itemView);
                main_page_item =  (CardView)itemView.findViewById(R.id.news_list_card_view);
                tx = (TextView) itemView.findViewById(R.id.question_title);
                tx_desc = (TextView) itemView.findViewById(R.id.daily_title);
                img = (ImageView) itemView.findViewById(R.id.thumbnail_image);
            }
        }
    }

    protected void jumpTo(Class<? extends Activity> cls, Bundle args) {
        Intent intent = new Intent(getActivity(), cls);
        if (args != null) {
            intent.putExtras(args);
        }
        startActivity(intent);

    }
}