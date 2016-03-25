package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.rutvik.bhagwatiholidays.App;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.SimpleOffersAndPromotions;
import viewholders.OffersAndPromotionsItemVH;

/**
 * Created by ACER on 06-Mar-16.
 */
public class OffersAndPromotionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG= App.APP_TAG+OffersAndPromotionsAdapter.class.getSimpleName();

    Context context;

    private OnOfferClickListener offerClickListener;

    public static interface OnOfferClickListener{
        public void onOfferClick(SimpleOffersAndPromotions model);
    }


    public List<OffersAndPromotionsItem> getList() {
        return list;
    }

    private final List<OffersAndPromotionsItem> list;


    public OffersAndPromotionsAdapter(Context context, OnOfferClickListener offerClickListener){
        Log.i(TAG,"CONSTRUCTING OFFERS AND PROMOTION ADAPTER");
        this.context=context;
        this.offerClickListener=offerClickListener;
        list=new ArrayList<>();
    }


    public List<SimpleOffersAndPromotions> getModelList() {
        Log.i(TAG,"GETTING MODEL LIST FROM MAIN LIST");
        List<SimpleOffersAndPromotions> lst=new ArrayList<>();
        for(OffersAndPromotionsItem item:list){
            lst.add((SimpleOffersAndPromotions)item.object);
        }
        Log.i(TAG,"RETURNING MODEL LIST OF SIZE: "+lst.size());
        return lst;
    }

/*    public void setModelList(List<SimpleOffersAndPromotions> lst){
        for(SimpleOffersAndPromotions model:lst){
            addOffersAndPromotionsItem();
        }
    }*/


    public void addOffersAndPromotionsItem(int viewType,SimpleOffersAndPromotions model){
        Log.i(TAG,"ADDING SIMPLE ITEM TO LIST");
        list.add(new OffersAndPromotionsItem(viewType,model));
    }

    @Override
    public int getItemViewType(int position) {
        Log.i(TAG,"GETTING ITEM VIEW TYPE FOR ITEM: "+position);
        return list.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i(TAG,"VIEW TYPE: "+viewType);

        switch (viewType){

            case OffersAndPromotionsItem.SIMPLE:
                return OffersAndPromotionsItemVH.create(context,parent);


        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)){
            case OffersAndPromotionsItem.SIMPLE:
                OffersAndPromotionsItemVH
                        .bind((OffersAndPromotionsItemVH)holder,
                                (SimpleOffersAndPromotions)list.get(position).object,offerClickListener);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public void addItem(int position, SimpleOffersAndPromotions model) {
        Log.i(TAG,"ADDING ITEM");

        list.add(position, new OffersAndPromotionsItem(OffersAndPromotionsItem.SIMPLE,model));
        Log.i(TAG,"LIST SIZE AFTER ADD: "+list.size());
        notifyItemInserted(position);
    }



    public void removeItem(int position) {
        Log.i(TAG,"REMOVING ITEM");
        list.remove(position);
        Log.i(TAG,"LIST SIZE AFTER REMOVE: "+list.size());
        notifyItemRemoved(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        Log.i(TAG,"MOVING ITEM");
        final SimpleOffersAndPromotions model =(SimpleOffersAndPromotions) list.remove(fromPosition).object;
        addOffersAndPromotionsItem(OffersAndPromotionsItem.SIMPLE,model);
        Log.i(TAG,"LIST SIZE AFTER MOVE: "+list.size());
        notifyItemMoved(fromPosition, toPosition);
    }



    public void animateTo(List<SimpleOffersAndPromotions> models) {

        Log.i(TAG,"ANIMATING ROWS and DOING ADD,ROMOVE,MOVE");

        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);

    }


    private void applyAndAnimateRemovals(List<SimpleOffersAndPromotions> newModels) {
        Log.i(TAG,"APPLY ANIMATION REMOVAL");
        for (int i = list.size() - 1; i >= 0; i--) {
            final SimpleOffersAndPromotions model = (SimpleOffersAndPromotions)list.get(i).object;
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }



    private void applyAndAnimateAdditions(List<SimpleOffersAndPromotions> newModels) {
        Log.i(TAG,"APPLY ANIMATION ADDITION");
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final SimpleOffersAndPromotions model = newModels.get(i);
            if (!getModelList().contains(model)) {
                addItem(i, model);
            }
        }
    }


    private void applyAndAnimateMovedItems(List<SimpleOffersAndPromotions> newModels) {
        Log.i(TAG,"APPLY ANIMATION MOVED");
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final SimpleOffersAndPromotions model = newModels.get(toPosition);
            final int fromPosition = getModelList().indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }






    public static class OffersAndPromotionsItem<T>{

        public static final int SIMPLE=0;
        public static final int FLIGHT=1;
        public static final int AIRTICKET=2;
        public static final int VISA=3;

        private int viewType;

        public T object;

        public int getViewType(){
            return viewType;
        }

        public OffersAndPromotionsItem(int viewType,T object){
            this.viewType=viewType;
            this.object=object;
        }

    }

}
