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
import com.rutvik.bhagwatiholidays.HotelBookingDetailsActivity;
import com.rutvik.bhagwatiholidays.R;

import model.Booking;

/**
 * Created by rutvik on 15-06-2016 at 12:52 PM.
 */
public class HotelBookingVH extends RecyclerView.ViewHolder {
    ImageView ivMyBooking;

    TextView tvMyBookingTitle,tvMyBookingDate;

    Booking model;

    Context context;

    public HotelBookingVH(final Context context,View itemView) {
        super(itemView);
        this.context=context;
        ivMyBooking=(ImageView) itemView.findViewById(R.id.iv_myBooking);
        ivMyBooking.setImageResource(R.drawable.ic_hotel2);
        tvMyBookingTitle=(TextView) itemView.findViewById(R.id.tv_myBookingTitle);
        tvMyBookingTitle.setSelected(true);
        tvMyBookingDate=(TextView) itemView.findViewById(R.id.tv_myBookingDate);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, HotelBookingDetailsActivity.class);
                i.putExtra("JSON",model.getJSON());
                context.startActivity(i);
            }
        });
    }

    public static HotelBookingVH create(Context context, ViewGroup parent){
        return new HotelBookingVH(context,LayoutInflater.from(context).inflate(R.layout.single_my_booking_row,parent,false));
    }

    public static void bind(final HotelBookingVH vh,final Booking model){
        vh.model=model;
        vh.tvMyBookingTitle.setText(model.getTitle());
        vh.tvMyBookingDate.setText(model.getDate());
    }
}
