package com.rutvik.bhagwatiholidays;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import jsonobj.PackageList;
import webservicehandler.PostHandler;

public class OffersActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    /*private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;*/

    private OffersAdapter offersAdapter;

    LazyAdapter lazyAdapter;

    private ListView lvOffers;

    private ProgressDialog progressDialog;

    ArrayList<PackageList.Package> packages=new ArrayList<PackageList.Package>();

    private int[] packageImage = {R.drawable.logo_bg, R.drawable.logo_bg, R.drawable.logo_bg, R.drawable.logo_bg};
/*    private String[] packageName = {"ABC", "ZXC", "ADFF", "GHHJ"};
    private String[] packageDays = {"2 Days", "2 Days", "2 Days", "2 Days"};
    private String[] packageNights = {"2 Nights", "2 Nights", "2 Nights", "2 Nights"};
    private String[] packagePlace = {"ABC", "ZXC", "ADFF", "GHHJ"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*mRecyclerView = (RecyclerView) findViewById(R.id.rcv_offers);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set Offers Adapter

        mAdapter = new OffersAdapter(OffersActivity.this,packageName,packageDays,packageNights,packagePlace,packageImage);
        mRecyclerView.setAdapter(mAdapter);*/

        lvOffers = (ListView) findViewById(R.id.lv_offers);

        offersAdapter = new OffersAdapter(OffersActivity.this,packages);

        lazyAdapter=new LazyAdapter(this,packages);

        lvOffers.setAdapter(lazyAdapter);

        loadOffersAsync();

    }


    void loadOffersAsync()
    {
        new AsyncTask<Void, Void, Void>() {

        @Override
        protected void onPreExecute() {

            progressDialog=ProgressDialog.show(OffersActivity.this,"Please Wait...","Loading Offers...",true,true);

        }

        @Override
        protected Void doInBackground(Void... params) {

            HashMap<String,String> postParams = new HashMap<>();

            postParams.put("method","get_package_list");

            String response = new PostHandler("BWT",4,2000).doPostRequest("http://bhagwatiholidays.com/admin/webservice/index.php",postParams);

            try {
                PackageList packageList = new PackageList(response, "package_list");
                packages.clear();
                for(PackageList.Package p:packageList.getPackageList()) {
                    packages.add(p);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.dismiss();

            lazyAdapter.notifyDataSetChanged();

        }
    }.execute();
    }


    /*class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

        private String[] packageName, packageDays, packageNights,packagePlace;
        private int[] packageImage;
        private Context mContext;

        public OffersAdapter(Context mContext, String[] packageName, String[] packageDays, String[] packageNights,String[] packagePlace, int[] PackageImage) {
            mContext = mContext;
            packageName = packageName;
            packageDays = packageDays;
            packageNights = packageNights;
            packagePlace = packagePlace;
            packageImage = packageImage;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_list_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(rowView);

            return viewHolder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.mPackageName.setText(packageName[position]);
            holder.mPackageDays.setText(packageDays[position]);
            holder.mPackageNights.setText(packageNights[position]);
            holder.mPackagePlace.setText(packagePlace[position]);
            holder.mPackageImage.setBackgroundResource(packageImage[position]);

        }

        @Override
        public int getItemCount() {
            return packageName.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView mPackageName,mPackageDays,mPackageNights,mPackagePlace;
            ImageView mPackageImage;

            public ViewHolder(View itemView) {
                super(itemView);
                mPackageName = (TextView) itemView.findViewById(R.id.tv_packageTitle);
                mPackageDays = (TextView) itemView.findViewById(R.id.tv_packageDays);
                mPackageNights = (TextView) itemView.findViewById(R.id.tv_packagenights);
                mPackagePlace = (TextView) itemView.findViewById(R.id.tv_packagePlace);
                mPackageImage = (ImageView) itemView.findViewById(R.id.iv_packageImage);
            }
        }
    }*/



}


class ViewHolder {
    TextView packageName, packageDays, packageNights, packagePlace;
    ImageView packageImage;
}


class OffersAdapter extends BaseAdapter {

    private Context mContext;
    private  ArrayList<PackageList.Package> packages=new ArrayList<PackageList.Package>();
    private LayoutInflater inflater;

    public OffersAdapter(Context mContext,  ArrayList<PackageList.Package> packages) {
        this.mContext = mContext;
        this.packages=packages;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return packages.size();
    }

    @Override
    public Object getItem(int position) {
        return packages.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
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

            viewHolder.packageName.setText(packages.get(position).getPackage_name());
            viewHolder.packageDays.setText(packages.get(position).getDays()+" Days");
            viewHolder.packageNights.setText(packages.get(position).getNights()+" Nights");
            viewHolder.packagePlace.setText(packages.get(position).getPlaces());

            loadBitmap(viewHolder.packageImage, "http://www.bhagwatiholidays.com/admin/images/package_icons/"+packages.get(position).getThumb_href());

            vi.setTag(viewHolder);

        }

        return vi;
    }

    public void loadBitmap(ImageView imageView,String url) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView,url);
        task.execute();
    }

    private Bitmap download_Image(String url) {
        //---------------------------------------------------
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
        }
        return bm;
        //---------------------------------------------------
    }

    class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        //private int data = 0;
        private final String url;

        public BitmapWorkerTask(ImageView imageView,String url) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            this.url=url;
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Void... params) {
            //data = params[0];
            return download_Image(url);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    //roundedImage = new RoundImage(bitmap);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }


}
