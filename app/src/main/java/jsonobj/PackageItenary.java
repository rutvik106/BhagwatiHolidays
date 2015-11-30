package jsonobj;

import java.util.ArrayList;

import jsonobj.PackageList.Package;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PackageItenary {
	
	
	private ArrayList<SingelPackageItenary> singelPackageItenariesList=new ArrayList<SingelPackageItenary>();

	public PackageItenary(String response,String arrayName) throws JSONException {
		
		JSONArray arr=new JSONObject(response).getJSONArray(arrayName);
		
		for(int i=0;i<arr.length();i++)
		{
			SingelPackageItenary singelPackageItenary=new SingelPackageItenary(arr.getJSONObject(i));
			singelPackageItenariesList.add(singelPackageItenary);
			
		}
		
	}
	
	
    public ArrayList<SingelPackageItenary> getPackageItenary ()
    {
        return singelPackageItenariesList;
    }

    public void setPackageItenary (ArrayList<SingelPackageItenary> singelPackageItenariesList)
    {
        this.singelPackageItenariesList = singelPackageItenariesList;
    }

    public ArrayList<String> getItenaryHeading()
    {
    	ArrayList<String> headingList=new ArrayList<String>();
        for(int i=0; i<singelPackageItenariesList.size(); i++)
        {
        	int day=i+1;
        	headingList.add("DAY " + day + " - " + singelPackageItenariesList.get(i).getItenary_heading());
        }
    	
    	return headingList;
    }
    
    public ArrayList<String> getItenaryDescription()
    {
    	ArrayList<String> descriptionList=new ArrayList<String>();
        for(int i=0; i<singelPackageItenariesList.size(); i++)
        {
        	descriptionList.add(singelPackageItenariesList.get(i).getItenary_description());
        }
    	
    	return descriptionList;
    }
	
	
	
	public class SingelPackageItenary
	{
	    private String itenary_description;

	    private String package_id;

	    private String itenary_heading;
	    
	    public SingelPackageItenary(JSONObject obj) throws JSONException {
			
	    	itenary_description=obj.getString("itenary_description");
	    	package_id=obj.getString("package_id");
	    	itenary_heading=obj.getString("itenary_heading");
	    	
		}

	    public String getItenary_description ()
	    {
	        return itenary_description;
	    }

	    public void setItenary_description (String itenary_description)
	    {
	        this.itenary_description = itenary_description;
	    }

	    public String getPackage_id ()
	    {
	        return package_id;
	    }

	    public void setPackage_id (String package_id)
	    {
	        this.package_id = package_id;
	    }

	    public String getItenary_heading ()
	    {
	        return itenary_heading;
	    }

	    public void setItenary_heading (String itenary_heading)
	    {
	        this.itenary_heading = itenary_heading;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [itenary_description = "+itenary_description+", package_id = "+package_id+", itenary_heading = "+itenary_heading+"]";
	    }
	}

}
