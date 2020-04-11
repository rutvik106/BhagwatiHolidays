package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rutvik.bhagwatiholidays.R;

import java.util.List;

import component.SingleMultiFlightView;
import model.MultiFlightResult;
import model.SingleFlightResult;
import model.SingleMultiFlightResult;

/**
 * Created by rutvik on 09-09-2016 at 09:34 AM.
 */

public class MultiFlightResultVH extends RecyclerView.ViewHolder
{
    final Context context;

    LinearLayout llMultiFlightContainer;

    TextView tvMultiFlightFair;

    MultiFlightResult model;

    public MultiFlightResultVH(final Context context, View itemView)
    {
        super(itemView);
        this.context = context;
        llMultiFlightContainer = (LinearLayout) itemView.findViewById(R.id.ll_multiFlightContainer);
        tvMultiFlightFair = (TextView) itemView.findViewById(R.id.tv_multiFlightFair);
    }

    public static MultiFlightResultVH create(Context context, ViewGroup parent)
    {
        return new MultiFlightResultVH(context, LayoutInflater.from(context)
                .inflate(R.layout.single_multiflight_result_row, parent, false));
    }

    public static void bind(MultiFlightResultVH vh, MultiFlightResult model)
    {
/**        if(!model.isVisible()){
            vh.itemView.setVisibility(View.GONE);
            return;
        }
        else {
            if(vh.itemView.getVisibility()==View.GONE)
            {
                vh.itemView.setVisibility(View.VISIBLE);
            }
        }*/
        vh.model=model;
        if (vh.llMultiFlightContainer != null)
        {
            vh.llMultiFlightContainer.removeAllViews();

            for (SingleMultiFlightResult singleMultiFlightResult : vh.model.getSingleMultiFlightResultList())
            {
                SingleMultiFlightView subVH = SingleMultiFlightView.create(vh.context);
                SingleMultiFlightView
                        .bind(subVH, singleMultiFlightResult);

                vh.llMultiFlightContainer.addView(subVH.getItemView());

            }

        }
        vh.tvMultiFlightFair.setText(vh.context.getResources().getString(R.string.rs)+" "+vh.model.getPublishedFair());
    }

}
