package com.rutvik.bhagwatiholidays;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;

import java.io.File;
import java.util.ArrayList;

import jsonobj.PackageList;

/**
 * Created by ACER on 22-Jan-16.
 */
public class LazyAdapter extends BaseAdapter {

    public static final String TAG="bwt "+LazyAdapter.class.getSimpleName();

    private Activity activity;
    //private String data[];
    private LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    DisplayImageOptions options;

    ArrayList<PackageList.Package> packages=new ArrayList<PackageList.Package>();

    public LazyAdapter(Activity a,  ArrayList<PackageList.Package> packages) {
        activity = a;
        //data=d;

        this.packages=packages;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        File cacheDir = StorageUtils.getOwnCacheDirectory(a, "MyFolderCache");


        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                activity.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache()).build();

        ImageLoader.getInstance().init(config);

        imageLoader=ImageLoader.getInstance();

        // END - UNIVERSAL IMAGE LOADER SETUP

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.loading_image)
                .cacheInMemory()
                .cacheOnDisc()
                .displayer(new RoundedBitmapDisplayer(5))
                .build();

    }



    public int getCount() {
        return packages.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = null;
        convertView=null;
        vi=convertView;

        ViewHolder viewHolder;


        if(convertView==null) {

            vi = inflater.inflate(R.layout.offers_list_item, null);

            viewHolder=new ViewHolder();

            viewHolder.packageName = (TextView) vi.findViewById(R.id.tv_packageTitle);
            viewHolder.packageDays = (TextView) vi.findViewById(R.id.tv_packageDays);
            viewHolder.packageNights = (TextView) vi.findViewById(R.id.tv_packageNights);
            viewHolder.packagePlace = (TextView) vi.findViewById(R.id.tv_packagePlace);
            viewHolder.packageImage = (ImageView) vi.findViewById(R.id.iv_packageImage);
            viewHolder.pb=(ProgressBar) vi.findViewById(R.id.pb);

            viewHolder.packageName.setText(packages.get(position).getPackage_name());
            viewHolder.packageDays.setText(packages.get(position).getDays()+" Days");
            viewHolder.packageNights.setText(packages.get(position).getNights()+" Nights");
            viewHolder.packagePlace.setText(packages.get(position).getPlaces());

            //loadBitmap(viewHolder.packageImage, "http://www.bhagwatiholidays.com/admin/images/package_icons/"+packages.get(position).getThumb_href());

            try {
                display(viewHolder.packageImage, "http://www.bhagwatiholidays.com/admin/images/package_icons/" + packages.get(position).getThumb_href(), viewHolder.pb);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }

            //imageLoader.displayImage(data.get(position).toString(), image,options);

            vi.setTag(viewHolder);

        }

        return vi;

    }

    public void display(ImageView img, String url, final ProgressBar spinner)
    {
        Log.i(TAG , "url: " + url);
        imageLoader.displayImage(url, img, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                spinner.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                spinner.setVisibility(View.GONE);


            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                spinner.setVisibility(View.GONE);
            }
            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }

        });
    }
    public static class ViewHolder
    {
        TextView packageName, packageDays, packageNights, packagePlace;
        ImageView packageImage;
        ProgressBar pb;
    }

}
