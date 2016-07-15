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
import com.rutvik.bhagwatiholidays.R;

import java.util.zip.Inflater;

import model.Booking;
import model.FlightBooking;

/**
 * Created by rutvik on 14-06-2016 at 07:45 PM.
 */
public class FlightBookingVH extends RecyclerView.ViewHolder {

    ImageView ivMyBooking;

    TextView tvMyBookingTitle,tvMyBookingDate;

    Booking model;

    Context context;

    public FlightBookingVH(final Context context, View itemView) {
        super(itemView);
        this.context=context;
        ivMyBooking=(ImageView) itemView.findViewById(R.id.iv_myBooking);
        tvMyBookingTitle=(TextView) itemView.findViewById(R.id.tv_myBookingTitle);
        tvMyBookingTitle.setSelected(true);
        tvMyBookingDate=(TextView) itemView.findViewById(R.id.tv_myBookingDate);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, BookingDetailsActivity.class);
                i.putExtra("JSON",model.getJSON());
                context.startActivity(i);
            }
        });
    }

    public static FlightBookingVH create(Context context, ViewGroup parent){
        return new FlightBookingVH(context,LayoutInflater.from(context).inflate(R.layout.single_my_booking_row,parent,false));
    }

    public static void bind(final FlightBookingVH vh,final Booking model){
        vh.model=model;
        vh.tvMyBookingTitle.setText(model.getTitle());
        vh.tvMyBookingDate.setText(model.getDate());
    }

}
