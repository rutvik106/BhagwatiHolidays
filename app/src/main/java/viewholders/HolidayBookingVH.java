package viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rutvik.bhagwatiholidays.BookingDetailsActivity;
import com.rutvik.bhagwatiholidays.HolidayBookingDetailsActivity;
import com.rutvik.bhagwatiholidays.R;

import model.Booking;
import model.HolidayBooking;

/**
 * Created by rutvik on 15-06-2016 at 12:52 PM.
 */
public class HolidayBookingVH extends RecyclerView.ViewHolder {
    ImageView ivMyBooking;

    TextView tvMyBookingTitle,tvMyBookingDate,tvMyBookingPackageType;

    Booking model;

    Context context;

    public HolidayBookingVH(final Context context,View itemView) {
        super(itemView);
        this.context=context;
        ivMyBooking=(ImageView) itemView.findViewById(R.id.iv_myBooking);
        tvMyBookingTitle=(TextView) itemView.findViewById(R.id.tv_myBookingTitle);
        tvMyBookingTitle.setSelected(true);
        tvMyBookingDate=(TextView) itemView.findViewById(R.id.tv_myBookingDate);
        tvMyBookingPackageType=(TextView) itemView.findViewById(R.id.tv_myBookingPackageType);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, HolidayBookingDetailsActivity.class);
                i.putExtra("JSON",model.getJSON());
                context.startActivity(i);
            }
        });
    }

    public static HolidayBookingVH create(Context context, ViewGroup parent){
        return new HolidayBookingVH(context,LayoutInflater.from(context).inflate(R.layout.single_holiday_booking_row,parent,false));
    }

    public static void bind(final HolidayBookingVH vh,final Booking model){
        HolidayBooking holidayBooking=(HolidayBooking) model;
        vh.model=model;
        vh.tvMyBookingTitle.setText(holidayBooking.getTitle());
        vh.tvMyBookingDate.setText(holidayBooking.getDate());
        vh.tvMyBookingPackageType.setText(holidayBooking.getPackageType());
    }
}
