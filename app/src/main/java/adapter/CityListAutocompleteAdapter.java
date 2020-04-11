package adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rutvik.bhagwatiholidays.App;
import com.rutvik.bhagwatiholidays.ParseCityList;
import com.rutvik.bhagwatiholidays.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import extras.Log;
import liveapimodels.City;

/**
 * Created by rutvik on 04-09-2016 at 03:21 PM.
 */

public class CityListAutocompleteAdapter extends BaseAdapter implements Filterable
{

    final Context context;

    private Map<String, AutoCompleteDropDownItem> dropdownItemMap;

    private List<String> suggestions = new ArrayList<>();

    private Filter filter;

    //private final List<City> cityList = new ArrayList<>();

    private static final String TAG = App.APP_TAG + CityListAutocompleteAdapter.class.getSimpleName();

    final AutoCompleteTextView otherAct;

    CityListAutocompleteAdapter otherActAdapter;

    public CityListAutocompleteAdapter(final Context context, final AutoCompleteTextView otherAct, CityListAutocompleteAdapter otherActAdapter)
    {
        this.context = context;
        this.otherAct = otherAct;
        this.otherActAdapter = otherActAdapter;
        dropdownItemMap = new HashMap<>();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            new PrepareCityList(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new PrepareCityList(context).execute();
        }

    }

    private CityListAutocompleteAdapter(CityListAutocompleteAdapter adapter)
    {
        this.otherAct = adapter.otherAct;
        this.context = adapter.context;
        this.dropdownItemMap = adapter.dropdownItemMap;
    }


    @Override
    public int getCount()
    {
        return suggestions.size();
    }

    @Override
    public Object getItem(int i)
    {
        return suggestions.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        //return Long.valueOf(dropdownItemMap.get(suggestions.get(i)).getKey());
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (context == null)
        {
            Log.i(TAG, "===============CONTEXT IS NULL");
        }

        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder holder;

        if (view == null)
        {
            view = inflater.inflate(R.layout.single_city_dropdown_row,
                    viewGroup,
                    false);
            holder = new ViewHolder();
            holder.autoText = (TextView) view.findViewById(R.id.tv_productDropdownValue);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.autoText.setText(suggestions.get(i));

        return view;
    }

    @Override
    public Filter getFilter()
    {
        filter = new CustomFilter();
        return filter;
    }

    public class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            /**Log.i(TAG, "performFiltering: FILTERING TEXT: " + constraint);
             suggestions.clear();
             android.util.Log.i(TAG, "performFiltering: CHECK 1");
             if (dropdownItemMap != null && constraint != null)
             { // Check if the Original List and Constraint aren't null.
             android.util.Log.i(TAG, "performFiltering: CHECK 2");
             //Collection<AutoCompleteDropDownItem> dpCollection = dropdownItemMap.values();
             Iterator<AutoCompleteDropDownItem> dpIterator = dropdownItemMap.values().iterator();
             while (dpIterator.hasNext())
             {
             AutoCompleteDropDownItem dp = dpIterator.next();
             if (dropdownItemMap.get(dp.getKey()).getValue().toLowerCase().contains(constraint))
             { // Compare item in original list if it contains constraints.
             android.util.Log.i(TAG, "performFiltering:" + dp.getKey());
             suggestions.add(dp.getValue()); // If TRUE add item in Suggestions.
             }
             }
             }
             FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
             results.values = suggestions;
             results.count = suggestions.size();*/

            FilterResults filterResults = new FilterResults();
            List<String> queryResults;
            if (constraint != null && constraint.length() > 0)
            {
                queryResults = autocomplete(constraint.toString());
            } else
            {
                queryResults = new ArrayList<>(); // empty list/no suggestions showing if there's no valid constraint
            }
            filterResults.values = queryResults;
            filterResults.count = queryResults.size();

            return filterResults;
        }

        private List<String> autocomplete(String input)
        {
            // don't use the here the resultList List on which the adapter is based!
            // some custom code to get items from http connection
            ArrayList<String> queryResults = new ArrayList<>(); // new list

            Iterator<AutoCompleteDropDownItem> dpIterator = dropdownItemMap.values().iterator();
            while (dpIterator.hasNext())
            {
                AutoCompleteDropDownItem dp = dpIterator.next();
                if (dropdownItemMap.get(dp.getKey()).getValue().toLowerCase().contains(input))
                { // Compare item in original list if it contains constraints.
                    android.util.Log.i(TAG, "performFiltering:" + dp.getKey());
                    queryResults.add(dp.getValue()); // If TRUE add item in Suggestions.
                }
            }

            return queryResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            suggestions = (ArrayList<String>) results.values;
            if (results.count > 0)
            {
                notifyDataSetChanged();
            } else
            {
                notifyDataSetInvalidated();
            }
        }
    }

    private static class ViewHolder
    {
        TextView autoText;
    }

    public interface AutoCompleteDropDownItem
    {
        String getValue();
        String getKey();
    }


    class PrepareCityList extends AsyncTask<Void, Void, List<City>>
    {

        final Context context;

        public PrepareCityList(Context context)
        {
            this.context = context;
        }

        @Override
        protected List<City> doInBackground(Void... voids)
        {
            return ParseCityList.parseXML(context);
        }

        @Override
        protected void onPostExecute(List<City> cities)
        {
            Log.i(TAG, "cityList.size(): " + cities.size());
            for (City city : cities)
            {
                dropdownItemMap.put(city.getCityCode(), city);
            }
            notifyDataSetChanged();
            otherActAdapter = new CityListAutocompleteAdapter(CityListAutocompleteAdapter.this);
            otherAct.setAdapter(otherActAdapter);
            otherActAdapter.notifyDataSetChanged();
        }
    }
}
