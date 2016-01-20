package me.pangshen.psapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.pangshen.psapp.model.WXhot;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private List<WXhot> data = new ArrayList<WXhot>();
    private MainFragmentAdapter mainFragmentAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_page, container, false);
        initRecleView(rootView);
        getData(getActivity());
        return rootView;
    }

    private void getData(Context context) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        String requesetUrl= String.format("http://apis.baidu.com/txapi/weixin/wxhot?num=%d",10);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,requesetUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        for (int i = 0; i < 10; i++) {
                            try {
                                String str = response.getString(i+"");
                                WXhot wxhot = new Gson().fromJson(str,WXhot.class);
                                data.add(wxhot);
                                Log.d(TAG, wxhot.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mainFragmentAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            } })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", Configration.APIKEY);
                return headers;
            }
        };
        mQueue.add(jsonObjectRequest);
    }

    private void initRecleView(View rootView) {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.main_page_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mainFragmentAdapter =new MainFragmentAdapter(data);
        recyclerView.setAdapter(mainFragmentAdapter);
    }

    class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {
        List<WXhot> data;

        public MainFragmentAdapter(List<WXhot> data) {
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
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tx.setText(data.get(position).getTitle());

        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tx;

            public ViewHolder(View itemView, int itemType) {
                super(itemView);
                tx = (TextView) itemView.findViewById(R.id.main_page_item_text);
            }
        }
    }
}