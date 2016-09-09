package component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import model.SingleMultiFlightResult;

/**
 * Created by rutvik on 09-09-2016 at 01:38 PM.
 */

public class SingleMultiFlightView
{

    private static final String TAG = App.APP_TAG + SingleMultiFlightView.class.getSimpleName();

    private ImageView ivAirLogo;

    private TextView tvAirCode, tvStartTime, tvEndTime, tvAirPrice, tvTotalTime;

    private SingleMultiFlightResult model;

    private final Context context;

    private final View itemView;

    private SingleMultiFlightView(Context context, View itemView)
    {
        this.itemView = itemView;
        this.context = context;
        ivAirLogo = (ImageView) itemView.findViewById(R.id.iv_airLogo);
        tvAirCode = (TextView) itemView.findViewById(R.id.tv_airCode);
        tvStartTime = (TextView) itemView.findViewById(R.id.tv_startTime);
        tvEndTime = (TextView) itemView.findViewById(R.id.tv_endTime);
        tvAirPrice = (TextView) itemView.findViewById(R.id.tv_airPrice);
        tvTotalTime = (TextView) itemView.findViewById(R.id.tv_totalTime);
    }

    public static SingleMultiFlightView create(final Context context)
    {
        return new SingleMultiFlightView(context, LayoutInflater.from(context)
                .inflate(R.layout.single_multi_single_flight_row, null, false));
    }

    public static void bind(final SingleMultiFlightView vh, final SingleMultiFlightResult model)
    {
        vh.model = model;
        vh.tvAirCode.setText(vh.model.getAirCode() + " - " + vh.model.getFlightNumber());

        vh.tvAirPrice.setText(vh.model.getOriginDestination());

        String arrTime = vh.model.getStartTime();
        arrTime = arrTime.substring(arrTime.indexOf("T") + 1, arrTime.length() - 3);
        vh.tvStartTime.setText(arrTime);

        String depTime = vh.model.getEndTime();
        depTime = depTime.substring(depTime.indexOf("T") + 1, depTime.length() - 3);
        vh.tvEndTime.setText(depTime);

        vh.tvTotalTime.setText(vh.model.getTotalTime());

        String time1 = vh.model.getStartTime();
        String time2 = vh.model.getEndTime();

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

    public View getItemView()
    {
        return itemView;
    }

}
