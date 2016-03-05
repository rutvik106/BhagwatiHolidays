package com.rutvik.bhagwatiholidays;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import jsonobj.PackageList;

/**
 * Created by ACER on 22-Jan-16.
 */
public class LazyAdapter extends RecyclerView.Adapter<LazyAdapter.ViewHolder> {

    public static final String TAG = App.APP_TAG + LazyAdapter.class.getSimpleName();

    private Activity activity;
    private LayoutInflater inflater = null;
    public ImageLoader imageLoader;
    DisplayImageOptions options;

    private List<PackageList.Package> packages;

    public LazyAdapter(Activity a, List<PackageList.Package> packages) {

        activity = a;

        this.packages = new ArrayList<>(packages);

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        imageLoader = ImageLoader.getInstance();

        // END - UNIVERSAL IMAGE LOADER SETUP

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.loading_image)
                .cacheInMemory()
                .cacheOnDisc()
                .displayer(new RoundedBitmapDisplayer(5))
                .build();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView packageName, packageDays, packageNights, packagePlace;
        ImageView packageImage;
        ProgressBar pb;

        public ViewHolder(View itemView) {
            super(itemView);
            packageName = (TextView) itemView.findViewById(R.id.tv_packageTitle);
            packageDays = (TextView) itemView.findViewById(R.id.tv_packageDays);
            packageNights = (TextView) itemView.findViewById(R.id.tv_packageNights);
            packagePlace = (TextView) itemView.findViewById(R.id.tv_packagePlace);
            packageImage = (ImageView) itemView.findViewById(R.id.iv_packageImage);
            pb = (ProgressBar) itemView.findViewById(R.id.pb);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = inflater.inflate(R.layout.offers_list_item, null);

        ViewHolder vh = new ViewHolder(vi);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.packageName.setText(packages.get(position).getPackage_name());
        holder.packageDays.setText(packages.get(position).getDays() + " Days");
        holder.packageNights.setText(packages.get(position).getNights() + " Nights");
        holder.packagePlace.setText(packages.get(position).getPlaces());

        //loadBitmap(viewHolder.packageImage, "http://www.bhagwatiholidays.com/admin/images/package_icons/"+packages.get(position).getThumb_href());

        try {
            display(holder.packageImage, "http://www.bhagwatiholidays.com/admin/images/package_icons/" + packages.get(position).getThumb_href(), holder.pb);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }



    public void removeItem(int position) {
        final PackageList.Package model = packages.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, PackageList.Package model) {
        packages.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final PackageList.Package model = packages.remove(fromPosition);
        packages.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }



    public void animateTo(List<PackageList.Package> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);

    }


    private void applyAndAnimateRemovals(List<PackageList.Package> newModels) {
        for (int i = packages.size() - 1; i >= 0; i--) {
            final PackageList.Package model = packages.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }



    private void applyAndAnimateAdditions(List<PackageList.Package> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final PackageList.Package model = newModels.get(i);
            if (!packages.contains(model)) {
                addItem(i, model);
            }
        }
    }


    private void applyAndAnimateMovedItems(List<PackageList.Package> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final PackageList.Package model = newModels.get(toPosition);
            final int fromPosition = packages.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }




    public void display(ImageView img, String url, final ProgressBar spinner) {
        Log.i(TAG, "loading image from url: " + url);
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


}
