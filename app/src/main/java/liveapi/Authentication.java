package liveapi;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rutvik on 01-09-2016 at 12:19 AM.
 */

public class Authentication
{

    public static final String URL_AUTHENTICATION = ApiConstants.URL + "/SharedServices/SharedData.svc/rest/Authenticate";

    private static final String ClientId = "ApiIntegration";
    private static final String UserName = "bhagwatit";
    private static final String Password = "bhagwatit@12";
    private static final String LoginType = "2";
    private static final String EndUserIp = "111.111.111.111";

    public static final String getJSON()
    {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ClientId", ClientId);
        map.put("UserName", UserName);
        map.put("Password", Password);
        map.put("LoginType", LoginType);
        map.put("EndUserIp", EndUserIp);
        return new JSONObject(map).toString();

    }


}
