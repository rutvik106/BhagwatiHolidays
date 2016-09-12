package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.rutvik.bhagwatiholidays.R;

import extras.EasingConstants;

/**
 * Created by rutvik on 10-09-2016 at 09:16 AM.
 */

public class EmptyVH extends RecyclerView.ViewHolder
{

    final Animation animation = new AlphaAnimation(1, 0.4f); // Change alpha from fully visible to invisible

    final View itemView;

    private EmptyVH(View itemView)
    {
        super(itemView);

        this.itemView = itemView;

        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        animation.setInterpolator(EasingConstants.easeInOutCirc);

    }

    public static EmptyVH create(final Context context, final ViewGroup parent)
    {
        return new EmptyVH(LayoutInflater.from(context).inflate(R.layout.single_empty_view_row, parent, false));
    }

    public static void bind(EmptyVH vh)
    {
        vh.itemView.setAnimation(vh.animation);
        vh.itemView.startAnimation(vh.animation);
    }

}
