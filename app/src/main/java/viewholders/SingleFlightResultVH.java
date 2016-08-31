package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rutvik.bhagwatiholidays.R;

import model.SingleFlightResult;

/**
 * Created by rutvik on 31-08-2016 at 10:00 PM.
 */

public class SingleFlightResultVH extends RecyclerView.ViewHolder
{

    private ImageView ivAirLogo;

    private TextView tvAirCode, tvStartTime, tvEndTime, tvAirPrice, tvAirPriceExtra, tvTotalTime, tvIsNonStop;

    private SingleFlightResult model;

    private SingleFlightResultVH(View itemView)
    {
        super(itemView);
        ivAirLogo = (ImageView) itemView.findViewById(R.id.iv_airLogo);
        tvAirCode = (TextView) itemView.findViewById(R.id.tv_airCode);
        tvStartTime = (TextView) itemView.findViewById(R.id.tv_startTime);
        tvEndTime = (TextView) itemView.findViewById(R.id.tv_endTime);
        tvAirPrice = (TextView) itemView.findViewById(R.id.tv_airPrice);
        tvAirPriceExtra = (TextView) itemView.findViewById(R.id.tv_airPriceExtra);
        tvTotalTime = (TextView) itemView.findViewById(R.id.tv_totalTime);
        tvIsNonStop = (TextView) itemView.findViewById(R.id.tv_isNonStop);
    }

    public static SingleFlightResultVH create(final Context context, final ViewGroup parent)
    {
        return new SingleFlightResultVH(LayoutInflater.from(context)
                .inflate(R.layout.single_flight_result_row_item, parent, false));
    }

    public static void bind(final SingleFlightResultVH vh, final SingleFlightResult model)
    {
        vh.model = model;
        vh.tvAirCode.setText(model.getAirCode());
        vh.tvAirPrice.setText(model.getAirPrice());
        vh.tvStartTime.setText(model.getStartTime());
        vh.tvEndTime.setText(model.getEndTime());
        vh.tvAirPriceExtra.setText(model.getAirPriceExtra());
        vh.tvTotalTime.setText(model.getTotalTime());
        vh.tvIsNonStop.setText(model.getIsNonStop());
    }

}
