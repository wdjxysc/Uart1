package com.example.uart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeviceMenuActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_menu);


        //找到RecyclerView控件
        RecyclerView home_rv = (RecyclerView) findViewById(R.id.menuRecyclerView);

        //做一些假数据
        List<ItemMenu> dataList = new ArrayList<ItemMenu>();

        dataList.add(new ItemMenu("GPRS表", R.drawable.setting_blue, GprsSettingsActivity.class));
        dataList.add(new ItemMenu("RF表", R.drawable.setting_purple, AtyListActivity.class));

        //实例化Adapter并且给RecyclerView设上
        final MyAdapter adapter = new MyAdapter(dataList);
        home_rv.setAdapter(adapter);

        // 如果我们想要一个GridView形式的RecyclerView，那么在LayoutManager上我们就要使用GridLayoutManager
        // 实例化一个GridLayoutManager，列数为3
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        //调用以下方法让RecyclerView的第一个条目仅为1列
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果位置是0，那么这个条目将占用SpanCount()这么多的列数，再此也就是3
                //而如果不是0，则说明不是Header，就占用1列即可
                return adapter.isHeader(position) ? layoutManager.getSpanCount() : 1;
            }
        });

        //把LayoutManager设置给RecyclerView
        home_rv.setLayoutManager(layoutManager);
    }

    private class ItemMenu{
        String title;
        int imgResId;
        Class aty;

        /**
         * 构造函数
         * @param title 标题
         * @param imgResId 标题图片
         * @param aty activity类
         */
        public ItemMenu(String title, int imgResId, Class aty) {
            this.title = title;
            this.imgResId = imgResId;
            this.aty = aty;
        }
    }


    private class MyAdapter extends RecyclerView.Adapter{

        //先定义两个ItemViewType，0代表头，1代表表格中间的部分
        private static final int ITEM_VIEW_TYPE_HEADER = 0;
        private static final int ITEM_VIEW_TYPE_ITEM = 1;
        //数据源
        private List<ItemMenu> dataList;

        //构造函数
        public MyAdapter(List<ItemMenu> dataList) {
            this.dataList = dataList;
        }

        /**
         * 判断当前position是否处于第一个
         * @param position
         * @return
         */
        public boolean isHeader(int position) {
            return position == 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
            if (viewType == ITEM_VIEW_TYPE_HEADER) {
                //对于Header，我们应该返回填充有Header对应布局文件的ViewHolder（再次我们返回的都是一个布局文件，请根据不同的需求做相应的改动）
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_header, null));
            } else {
                //对于Body中的item，我们也返回所对应的ViewHolder
                return new BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, null));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (isHeader(position)) {
                //大家在这里面处理头，这里只有一个TextView，大家可以根据自己的逻辑做修改
                ((HeaderViewHolder) holder).getTextView().setText("菜单");
            } else {
                //其他条目中的逻辑在此
                ((BodyViewHolder) holder).setView(dataList.get(position - 1));
            }
        }

        /**
         * 总条目数量是数据源数量+1，因为我们有个Header
         * @return
         */
        @Override
        public int getItemCount() {
            return dataList.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            //如果是0，就是头，否则则是其他的item
            return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
        }

        /**
         * 给头部专用的ViewHolder，大家根据需求自行修改
         */
        public class HeaderViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;
            public HeaderViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.item_tv);
            }

            public TextView getTextView() {
                return textView;
            }
        }

        /**
         * 给GridView中的条目用的ViewHolder，里面只有一个TextView
         */
        public class BodyViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;
            private ImageView imageView;
            private Class aty;
            public BodyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.item_tv);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClassName(getApplicationContext(), aty.getName());
                        startActivity(intent);
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return false;
                    }
                });
            }

            public void setView(ItemMenu itemMenu){
                textView.setText(itemMenu.title);
                imageView.setImageResource(itemMenu.imgResId);
                aty = itemMenu.aty;
            }
        }
    }
}
