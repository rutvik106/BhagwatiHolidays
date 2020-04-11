package bhfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rutvik.bhagwatiholidays.R;

import adapter.FilterDrawerAdapter;
import extras.CommonUtilities;

/**
 * Created by rutvik on 11/09/2016 at 9:13 PM.
 */

public class FragmentFilterDrawer extends Fragment
{

    private RecyclerView rvFilterComponent;

    private DrawerLayout drawerLayout;

    FragmentDrawer.FragmentDrawerListener drawerListener;


    public void setUp(DrawerLayout drawerLayout, FilterDrawerAdapter adapter)
    {
        this.drawerLayout = drawerLayout;
        if(rvFilterComponent!=null){
            rvFilterComponent.setAdapter(adapter);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_filter_drawer, container, false);

        rvFilterComponent = (RecyclerView) view.findViewById(R.id.rv_filterComponent);
        rvFilterComponent.setHasFixedSize(true);

        rvFilterComponent.setLayoutManager(new LinearLayoutManager(getActivity()));




        rvFilterComponent.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getActivity(), rvFilterComponent, new FragmentDrawer.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                drawerListener.onDrawerItemSelected(view, position);
            }

            @Override
            public void onLongClick(View view, int position)
            {

            }
        }));

        return view;
    }

    public DrawerLayout getDrawerLayout()
    {
        return drawerLayout;
    }

    public void setDrawerListener(FragmentDrawer.FragmentDrawerListener listener)
    {
        this.drawerListener = listener;
    }


}
