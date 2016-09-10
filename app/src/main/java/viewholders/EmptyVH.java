package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rutvik.bhagwatiholidays.R;

/**
 * Created by rutvik on 10-09-2016 at 09:16 AM.
 */

public class EmptyVH extends RecyclerView.ViewHolder
{

    private EmptyVH(View itemView)
    {
        super(itemView);
    }

    public static EmptyVH create(final Context context, final ViewGroup parent)
    {
        return new EmptyVH(LayoutInflater.from(context).inflate(R.layout.single_empty_view_row, parent, false));
    }

    public static void bind(EmptyVH vh)
    {

    }

}
