package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import component.FilterDrawerComponent;
import model.FilterCheckBox;
import viewholders.FilterCheckBoxVH;

/**
 * Created by rutvik on 11/09/2016 at 8:34 PM.
 */

public class FilterDrawerAdapter extends RecyclerView.Adapter
{

    private final Context context;

    public final List<FilterDrawerComponent> filterDrawerComponentList;

    public final FilterCheckBoxVH.FilterCheckBoxListener listener;


    public FilterDrawerAdapter(final Context context, final FilterCheckBoxVH.FilterCheckBoxListener listener)
    {
        this.context = context;
        filterDrawerComponentList = new ArrayList<>();
        this.listener=listener;
    }

    @Override
    public int getItemViewType(int position)
    {
        return filterDrawerComponentList.get(position).getViewType();
    }

    public void addFilterCheckBox(FilterCheckBox filterCheckBox)
    {
        filterDrawerComponentList.add(new FilterDrawerComponent(filterCheckBox, FilterDrawerComponent.CHECK_BOX));
        notifyItemInserted(filterDrawerComponentList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case FilterDrawerComponent.CHECK_BOX:
                return FilterCheckBoxVH.create(context, parent);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        switch (getItemViewType(position))
        {
            case FilterDrawerComponent.CHECK_BOX:
                FilterCheckBoxVH.bind((FilterCheckBoxVH) holder,
                        (FilterCheckBox) filterDrawerComponentList.get(position).getModel(),listener);
                break;
        }

    }

    @Override
    public int getItemCount()
    {
        return filterDrawerComponentList.size();
    }

}
