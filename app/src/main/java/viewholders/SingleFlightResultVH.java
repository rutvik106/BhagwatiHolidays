package viewholders;

import android.content.Context;
import android.support.v4.util.TimeUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import extras.Log;
import model.SingleFlightResult;

/**
 * Created by rutvik on 31-08-2016 at 10:00 PM.
 */

public class SingleFlightResultVH extends RecyclerView.ViewHolder
{

    private static final String TAG = App.APP_TAG + SingleFlightResultVH.class.getSimpleName();

    private ImageView ivAirLogo;

    private TextView tvAirCode, tvStartTime, tvEndTime, tvAirPrice, tvAirPriceExtra, tvTotalTime, tvIsNonStop;

    private SingleFlightResult model;

    private final Context context;

    private SingleFlightResultVH(Context context, View itemView)
    {
        super(itemView);
        this.context = context;
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
        return new SingleFlightResultVH(context, LayoutInflater.from(context)
                .inflate(R.layout.single_flight_result_row_item, parent, false));
    }

    public static void bind(final SingleFlightResultVH vh, final SingleFlightResult model)
    {
        vh.model = model;
        vh.tvAirCode.setText(model.getAirCode() + " - " + model.getFlightNumber());

        String rs = vh.context.getResources().getString(R.string.rs);

        vh.tvAirPrice.setText(rs + " " + model.getAirPrice());

        String arrTime = model.getStartTime();
        arrTime = arrTime.substring(arrTime.indexOf("T") + 1, arrTime.length() - 3);
        vh.tvStartTime.setText(arrTime);

        String depTime = model.getEndTime();
        depTime = depTime.substring(depTime.indexOf("T") + 1, depTime.length() - 3);
        vh.tvEndTime.setText(depTime);

        vh.tvAirPriceExtra.setText(model.getAirPriceExtra());
        vh.tvTotalTime.setText(model.getTotalTime());
        vh.tvIsNonStop.setText(model.getIsNonStop());

        String time1 = model.getStartTime();
        String time2 = model.getEndTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getDefault());
        try
        {
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);
            long difference = date2.getTime() - date1.getTime();

            String hms = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(difference),
                    TimeUnit.MILLISECONDS.toMinutes(difference) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference)),
                    TimeUnit.MILLISECONDS.toSeconds(difference) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)));

            String[] totalTime = hms.split(":");

            vh.tvTotalTime.setText(totalTime[0] + "h " + totalTime[1] + "m");

        } catch (ParseException e)
        {
            e.printStackTrace();
        }


        try
        {
            int resourceImage = vh.context.getResources().getIdentifier("bwt_" + model.getAirCode().toLowerCase(), "drawable", vh.context.getPackageName());
            vh.ivAirLogo.setImageResource(resourceImage);
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

}
