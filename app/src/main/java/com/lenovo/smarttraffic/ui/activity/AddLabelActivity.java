package com.lenovo.smarttraffic.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenovo.smarttraffic.InitApp;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.ui.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;

public class AddLabelActivity extends BaseActivity {
    @BindView(R.id.rv_t)
    RecyclerView rv_t;
    @BindView(R.id.rv_b)
    RecyclerView rv_b;
    @BindView(R.id.bt_add)
    Button bt_add;
    private ArrayList<String> topList;
    private ArrayList<String> bottomList;
    private RvAdapter trvAdapter;
    private RvAdapter brvAdapter;
    private boolean isEdit;

    @Override
    protected int getLayout() {
        return R.layout.activity_addlabel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(findViewById(R.id.toolbar), true, "添加订阅");
        initData();
    }

    private void initData() {
        String dingyue = InitApp.sp.getString("dingyue", null);
        String dai = InitApp.sp.getString("dai", null);
        if (null == dingyue || dai == null) {
            topList = new ArrayList<>(InitApp.label.subList(0, 4));
            bottomList = new ArrayList<>(InitApp.label.subList(4, InitApp.label.size()));
        } else {
            topList = InitApp.gson.fromJson(dingyue, ArrayList.class);
            bottomList = InitApp.gson.fromJson(dai, ArrayList.class);
        }
        trvAdapter = new RvAdapter(topList, bottomList, R.mipmap.plus);
        brvAdapter = new RvAdapter(bottomList, topList, R.mipmap.add2);
        rv_t.setAdapter(trvAdapter);
        rv_b.setAdapter(brvAdapter);
        itemTouchHelper.attachToRecyclerView(rv_t);
    }

    @OnClick(R.id.bt_add)
    public void add() {
        isEdit = false;
        rsData();
        InitApp.edit.putString("dingyue", InitApp.gson.toJson(topList));
        InitApp.edit.putString("dai", InitApp.gson.toJson(bottomList));
        InitApp.edit.commit();
    }

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        private ArrayList<String> list;
        RvAdapter adapter;

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            adapter = (RvAdapter) recyclerView.getAdapter();
            list = adapter.label;
            int dragFlags = 0;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
            return makeMovementFlags(dragFlags, 0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int formPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (formPosition < toPosition) {
                for (int i = formPosition; i < toPosition; i++) {
                    Collections.swap(list, i, i + 1);
                }
            } else {
                for (int i = formPosition; i > toPosition; i--) {
                    Collections.swap(list, i, i - 1);
                }
            }
            adapter.notifyItemMoved(formPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            adapter.notifyDataSetChanged();
        }
    });

    private void rsData() {
        trvAdapter.notifyDataSetChanged();
        brvAdapter.notifyDataSetChanged();
    }


    private class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvHolder> {
        public ArrayList<String> label;
        public ArrayList<String> item;
        public Integer img;

        public RvAdapter(ArrayList label, ArrayList item, Integer img) {
            this.label = label;
            this.item = item;
            this.img = img;
        }

        @NonNull
        @Override

        public RvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label, parent, false);
            return new RvHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RvHolder holder, int position) {
            holder.iv_icon.setImageResource(img);
            holder.tv_name.setText(label.get(position));
            if (isEdit) {
                holder.iv_icon.setVisibility(View.VISIBLE);
                bt_add.setVisibility(View.VISIBLE);
            } else {
                holder.iv_icon.setVisibility(View.INVISIBLE);
                bt_add.setVisibility(View.INVISIBLE);
            }
            holder.tv_name.setOnLongClickListener(view -> {
                isEdit = true;
                rsData();
                return false;
            });
            holder.iv_icon.setOnClickListener(view -> {
                item.add(label.get(position));
                label.remove(position);
                rsData();
            });
        }

        @Override
        public int getItemCount() {
            return label.size();
        }

        class RvHolder extends RecyclerView.ViewHolder {
            TextView tv_name;
            ImageView iv_icon;

            public RvHolder(View itemView) {
                super(itemView);
                tv_name = itemView.findViewById(R.id.tv_name);
                iv_icon = itemView.findViewById(R.id.iv_icon);
            }
        }
    }
}
