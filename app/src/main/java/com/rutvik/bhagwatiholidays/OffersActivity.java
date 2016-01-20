package com.rutvik.bhagwatiholidays;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OffersActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    /*private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;*/

    private OffersAdapter offersAdapter;

    private ListView lvOffers;

    private int[] packageImage = {R.drawable.logo_bg, R.drawable.logo_bg, R.drawable.logo_bg, R.drawable.logo_bg};
    private String[] packageName = {"ABC", "ZXC", "ADFF", "GHHJ"};
    private String[] packageDays = {"2 Days", "2 Days", "2 Days", "2 Days"};
    private String[] packageNights = {"2 Nights", "2 Nights", "2 Nights", "2 Nights"};
    private String[] packagePlace = {"ABC", "ZXC", "ADFF", "GHHJ"};

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

        offersAdapter = new OffersAdapter(OffersActivity.this,packageName,packageDays,packageNights
                                ,packagePlace,packageImage);

        lvOffers.setAdapter(offersAdapter);
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

    class OffersAdapter extends BaseAdapter {

        private Context mContext;
        private String[] packageName, packageDays, packageNights, packagePlace;
        private int[] PackageImage;
        private LayoutInflater inflater;

        public OffersAdapter(Context mContext, String[] packageName, String[] packageDays, String[] packageNights
                , String[] packagePlace, int[] PackageImage) {
            this.mContext = mContext;
            this.packageName = packageName;
            this.packageDays = packageDays;
            this.packageNights = packageNights;
            this.packagePlace = packagePlace;
            this.PackageImage = packageImage;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return packageName.length;
        }

        @Override
        public Object getItem(int position) {
            return packageName.length;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = new ViewHolder();
            View rootView;

            rootView = inflater.inflate(R.layout.offers_list_item,null);

            viewHolder.packageName = (TextView) rootView.findViewById(R.id.tv_packageTitle);
            viewHolder.packageDays = (TextView) rootView.findViewById(R.id.tv_packageDays);
            viewHolder.packageNights = (TextView) rootView.findViewById(R.id.tv_packageNights);
            viewHolder.packagePlace = (TextView) rootView.findViewById(R.id.tv_packagePlace);
            viewHolder.packageImage = (ImageView) rootView.findViewById(R.id.iv_packageImage);

            viewHolder.packageName.setText(packageName[position]);
            viewHolder.packageDays.setText(packageDays[position]);
            viewHolder.packageNights.setText(packageNights[position]);
            viewHolder.packagePlace.setText(packagePlace[position]);

            viewHolder.packageImage.setBackgroundResource(packageImage[position]);

            return rootView;
        }

        public class ViewHolder {
            TextView packageName, packageDays, packageNights, packagePlace;
            ImageView packageImage;
        }
    }

    class OffersAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
