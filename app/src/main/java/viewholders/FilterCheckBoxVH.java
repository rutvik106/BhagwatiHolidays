package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.rutvik.bhagwatiholidays.R;

import model.FilterCheckBox;

/**
 * Created by rutvik on 11/09/2016 at 8:51 PM.
 */

public class FilterCheckBoxVH extends RecyclerView.ViewHolder
{

    private FilterCheckBox model;

    private final Context context;

    private final CheckBox cbFilterItem;

    FilterCheckBoxListener listener;

    public FilterCheckBoxVH(final Context context, final View itemView)
    {
        super(itemView);
        this.context = context;
        cbFilterItem = (CheckBox) itemView.findViewById(R.id.cb_filterItem);
        cbFilterItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                model.setChecked(b);
                if(listener!=null){
                    listener.onCheckChanged(model,b);
                }
            }
        });
    }

    public static FilterCheckBoxVH create(final Context context, final ViewGroup viewGroup)
    {
        return new FilterCheckBoxVH(context, LayoutInflater.from(context)
                .inflate(R.layout.single_filter_checkbox_row, viewGroup, false));

    }

    public static void bind(final FilterCheckBoxVH vh, FilterCheckBox model, FilterCheckBoxListener listener)
    {
        vh.model = model;

        vh.cbFilterItem.setText(model.getLabel());
        vh.cbFilterItem.setChecked(model.isChecked());
        vh.listener=listener;
    }

    public interface FilterCheckBoxListener{
        void onCheckChanged(FilterCheckBox model,boolean b);
    }

}
