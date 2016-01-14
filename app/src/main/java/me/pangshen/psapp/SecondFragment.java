package me.pangshen.psapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.second_page, container, false);
        initRecleView(rootView);
        return rootView;
    }

    private void initRecleView(View rootView) {
        List<String> data = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.second_page_recyclerView);
        for (int i = 0; i < 100; i++) {
            data.add("test" + i);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MainFragmentAdapter(data));
    }

    class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {
        List<String> data;

        public MainFragmentAdapter(List<String> data) {
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
            holder.tx.setText(data.get(position));

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