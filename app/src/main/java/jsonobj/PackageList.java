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

	    private String days;

        public String getInclusions() {
            return inclusions;
        }

        public void setInclusions(String inclusions) {
            this.inclusions = inclusions;
        }

        public String getExclusions() {
            return exclusions;
        }

        public void setExclusions(String exclusions) {
            this.exclusions = exclusions;
        }

        private String inclusions;

		private String exclusions;

	    private String package_name;

	    private String thumb_href;

	    private String nights;

	    private String package_id;

	    private String places;
	    
	    public Package(JSONObject obj) throws JSONException {
			days=obj.getString("days");
			inclusions=obj.getString("inclusions");
			exclusions=obj.getString("exclusions");
			package_name=obj.getString("package_name");
			thumb_href=obj.getString("thumb_href");
			nights=obj.getString("nights");
			package_id=obj.getString("package_id");
			places=obj.getString("places");
		}

	    public String getDays ()
	    {
	        return days;
	    }

	    public void setDays (String days)
	    {
	        this.days = days;
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
	        return "http://www.bhagwatiholidays.com/admin/images/package_icons/"+thumb_href;
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
	        return "ClassPojo [days = "+days+", package_name = "+package_name+", thumb_href = "+thumb_href+", nights = "+nights+", package_id = "+package_id+", places = "+places+"]";
	    }
	}
	
	

}


