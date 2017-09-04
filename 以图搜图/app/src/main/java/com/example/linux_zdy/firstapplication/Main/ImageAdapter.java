package com.example.linux_zdy.firstapplication.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.linux_zdy.firstapplication.R;

import java.util.List;

/**
 * Created by linux-zdy on 17-8-7.
 */
class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ImageBean.ResultsBean> beanList;
    private RecyclerViewOnClick itemOnClick;

    private static final int TYPE_ITEM = 0;              //一般item
    private static final int TYPE_FOOTER = 1;            //底部item


    //设置点击事件借口
    public interface RecyclerViewOnClick{
        View onClick(View v,int position);
    }

    public void setItemOnClick(RecyclerViewOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }



    //带一个参数的构造器
    public ImageAdapter(Context context,List<ImageBean.ResultsBean> beanList) {

        //传递url
        this.beanList = beanList;
        //传递一个上下文用作glide参数
        this.context = context;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;
        }else if(viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(context).inflate(R.layout.footer_item,parent,false);
            FootViewHolder footViewHolder = new FootViewHolder(view);
            return footViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(getItemViewType(position) == TYPE_ITEM) {
            ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
            itemViewHolder.setData(position);
        }

    }

    //获取子布局的数量
    @Override
    public int getItemCount() {
        return beanList.size() == 0 ? 0 : beanList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position+1 == getItemCount()){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }

    //普通条目的ViewHolder
    class ItemViewHolder extends RecyclerView.ViewHolder{   

        private ImageView item ;

        public ItemViewHolder(View itemView) {
            super(itemView);

            //设置复用控件
            item = (ImageView)itemView.findViewById(R.id.image_item);
        }

        public void setData(final int position){
            //获取URL并且转换为String类型的常量
            String url = beanList.get(position).getUrl();

            //使用Glide框架进行加载，加载网络图片时如果加载超时则默认显示android机器人图标
            Glide.with(context)
                    .load(url)                                //设置图片路径
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存全尺寸图片
                    .placeholder(R.mipmap.ic_launcher)        //设置默认图片
                    .override(250, 350)                        //设置图片的宽，高
                    .into(item);


            //判断不为空时进行点击事件
            if (itemOnClick != null) {
                //给条目图片设置点击时间
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemOnClick.onClick(item, position);
                    }
                });
            }
        }
    }

    //底部条目的ViewHolder
    class FootViewHolder extends RecyclerView.ViewHolder{

        private TextView tips ;
        private ProgressBar progressBar;

        public FootViewHolder(View itemView) {
            super(itemView);
            tips = (TextView)itemView.findViewById(R.id.tv_footview);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
        }
    }


}
