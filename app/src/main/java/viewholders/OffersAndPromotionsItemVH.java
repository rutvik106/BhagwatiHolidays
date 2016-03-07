package viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;

import org.json.JSONObject;

import adapter.OffersAndPromotionsAdapter;
import model.SimpleOffersAndPromotions;

/**
 * Created by ACER on 06-Mar-16.
 */
public class OffersAndPromotionsItemVH extends RecyclerView.ViewHolder {

    private static final String TAG= App.APP_TAG+OffersAndPromotionsItemVH.class.getSimpleName();

    public ImageView image;

    public TextView title,subTitle;

    public ImageLoader imageLoader;
    public DisplayImageOptions options;

    public OffersAndPromotionsItemVH(View itemView) {
        super(itemView);
        image=(ImageView) itemView.findViewById(R.id.iv_offersImage);
        title=(TextView) itemView.findViewById(R.id.tv_offerTitle);
        subTitle=(TextView) itemView.findViewById(R.id.tv_offerSubTitle);
    }

    public static OffersAndPromotionsItemVH create(Context context,ViewGroup parent){
        return new OffersAndPromotionsItemVH(LayoutInflater.from(context)
                .inflate(R.layout.offers_and_promotion_list_raw, parent, false));
    }

    public static void bind(OffersAndPromotionsItemVH holder, SimpleOffersAndPromotions model){

        holder.imageLoader = ImageLoader.getInstance();

        // END - UNIVERSAL IMAGE LOADER SETUP

        holder.options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.loading_image)
                .cacheInMemory()
                .cacheOnDisc()
                .displayer(new RoundedBitmapDisplayer(5))
                .build();

        holder.display(holder.image,model.getImageUrl());
        holder.title.setText(model.getTitle());
        holder.subTitle.setText(model.getDescription());
    }


    public void display(ImageView img, String url) {
        Log.i(TAG, "loading image from url: " + url);
        imageLoader.displayImage(url, img, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {



            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }

        });

    }


}
