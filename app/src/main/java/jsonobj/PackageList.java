package jsonobj;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PackageList {
	
	ArrayList<Package> packageList=new ArrayList<>();
	
	public PackageList(String response, String arrayName) throws JSONException {
		
		JSONArray arr=new JSONObject(response).getJSONArray(arrayName);
		
		for(int i=0;i<arr.length();i++)
		{
			Package packageObj=new Package(arr.getJSONObject(i));
			packageList.add(packageObj);
			
		}
		
	}
	
	
	public ArrayList<Package> getPackageList()
	{
		return packageList;
	}
	
	
	public class Package
	{
	    private String created_by;

	    private String last_updated_by;

	    private String days;

	    private String date_modified;

	    private String date_added;

	    private String package_name;

	    private String thumb_href;

	    private String nights;

	    private String package_id;

	    private String places;
	    
	    public Package(JSONObject obj) throws JSONException {
			created_by=obj.getString("created_by");
			last_updated_by=obj.getString("last_updated_by");
			days=obj.getString("days");
			date_modified=obj.getString("date_modified");
			date_added=obj.getString("date_added");
			package_name=obj.getString("package_name");
			thumb_href=obj.getString("thumb_href");
			nights=obj.getString("nights");
			package_id=obj.getString("package_id");
			places=obj.getString("places");
		}

	    public String getCreated_by ()
	    {
	        return created_by;
	    }

	    public void setCreated_by (String created_by)
	    {
	        this.created_by = created_by;
	    }

	    public String getLast_updated_by ()
	    {
	        return last_updated_by;
	    }

	    public void setLast_updated_by (String last_updated_by)
	    {
	        this.last_updated_by = last_updated_by;
	    }

	    public String getDays ()
	    {
	        return days;
	    }

	    public void setDays (String days)
	    {
	        this.days = days;
	    }

	    public String getDate_modified ()
	    {
	        return date_modified;
	    }

	    public void setDate_modified (String date_modified)
	    {
	        this.date_modified = date_modified;
	    }

	    public String getDate_added ()
	    {
	        return date_added;
	    }

	    public void setDate_added (String date_added)
	    {
	        this.date_added = date_added;
	    }

	    public String getPackage_name ()
	    {
	        return package_name;
	    }

	    public void setPackage_name (String package_name)
	    {
	        this.package_name = package_name;
	    }

	    public String getThumb_href ()
	    {
	        return thumb_href;
	    }

	    public void setThumb_href (String thumb_href)
	    {
	        this.thumb_href = thumb_href;
	    }

	    public String getNights ()
	    {
	        return nights;
	    }

	    public void setNights (String nights)
	    {
	        this.nights = nights;
	    }

	    public String getPackage_id ()
	    {
	        return package_id;
	    }

	    public void setPackage_id (String package_id)
	    {
	        this.package_id = package_id;
	    }

	    public String getPlaces ()
	    {
	        return places;
	    }

	    public void setPlaces (String places)
	    {
	        this.places = places;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [created_by = "+created_by+", last_updated_by = "+last_updated_by+", days = "+days+", date_modified = "+date_modified+", date_added = "+date_added+", package_name = "+package_name+", thumb_href = "+thumb_href+", nights = "+nights+", package_id = "+package_id+", places = "+places+"]";
	    }
	}
	
	

}


