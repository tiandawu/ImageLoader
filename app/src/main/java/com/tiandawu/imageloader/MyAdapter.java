package com.tiandawu.imageloader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tiandawu.imageloaderlibrary.ImageLoader;
import com.tiandawu.imageloaderlibrary.cache.DoubleCache;
import com.tiandawu.imageloaderlibrary.config.ImageLoaderConfig;
import com.tiandawu.imageloaderlibrary.displayer.RoundedBitmapDisplayer;
import com.tiandawu.imageloaderlibrary.loader.BaseImageDownLoader;
import com.tiandawu.imageloaderlibrary.policy.FIFOPolicy;


/**
 * Created by tiandawu on 2017/3/14.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mImageUrl;

    public MyAdapter(Context context, String[] imageUrl) {
        this.mContext = context;
        this.mImageUrl = imageUrl;
        ImageLoaderConfig config = new ImageLoaderConfig.Builder(context)
                .setBitmapCache(new DoubleCache(mContext))
                .setLoadPolicy(new FIFOPolicy())
                .setImageOnFail(mContext.getResources().getDrawable(R.drawable.not_found))
                .setImageOnLoading(mContext.getResources().getDrawable(R.drawable.loading))
                .setImageForEmptyURI(mContext.getResources().getDrawable(R.mipmap.ic_launcher))
                .setBitmapDisplayer(new RoundedBitmapDisplayer(10, 5))
                .setImageDownLoader(new BaseImageDownLoader(mContext))
                .build();
        ImageLoader.getInstance().init(config);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item, null);
        }
        ViewHolder holder = ViewHolder.getViewHolder(convertView);
        ImageLoader.getInstance().displayImage(mImageUrl[position], holder.mImageView);
        return convertView;
    }

    @Override
    public int getCount() {
        return mImageUrl == null ? 0 : mImageUrl.length;
    }

    @Override
    public Object getItem(int position) {
        return mImageUrl == null ? null : mImageUrl[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private static class ViewHolder {
        private ImageView mImageView;

        private ViewHolder(View convertView) {
            mImageView = (ImageView) convertView.findViewById(R.id.imageView);
        }

        public static ViewHolder getViewHolder(View convertView) {
            if (convertView == null) {
                throw new IllegalArgumentException("convertView can not be null");
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
