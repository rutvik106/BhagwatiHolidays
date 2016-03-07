package extras;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import webservicehandler.PostHandler;

/**
 * Created by ACER on 08-Feb-16.
 */
public class AppVerification {

    private static ProgressDialog progressDialog;

    Activity activity;

    final String packageName;

    public AppVerification(Activity activity,String packageName){
        this.activity=activity;
        this.packageName=packageName;
        verify();
    }

    private void verify(){

        new AsyncTask<Void,Void,Void>(){

            boolean allow;

            @Override
            protected void onPreExecute() {
                progressDialog=ProgressDialog.show(activity,"Please Wait","Loading...",true,false);
            }

            @Override
            protected Void doInBackground(Void... voids) {


                    HashMap<String, String> postParams = new HashMap<String, String>();
                    postParams.put("package_name", packageName);

                    new PostHandler("STS", 3, 2000).doPostRequest("http://rutvik.comlu.com/sts.php", postParams, new PostHandler.ResponseCallback() {
                        @Override
                        public void response(int status, String response) {
                            try {
                            JSONObject obj=new JSONObject(response);

                            allow=new ValidationResponse(obj.getJSONObject("response")).isAllowed();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });




                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
                if(allow){
                   return;
                }
                Toast.makeText(activity,"App verification failed",Toast.LENGTH_SHORT);
                activity.finish();

            }
        }.execute();

    }


    class ValidationResponse{

        String packageName,allow;

        ValidationResponse(JSONObject obj) throws JSONException{
            packageName=obj.getString("package_name");
            allow=obj.getString("allow");
            Log.i("STS","package: "+packageName+" allow: "+allow);
        }

        public boolean isAllowed(){
            Log.i("STS",AppVerification.this.packageName+" == "+packageName);
            if(AppVerification.this.packageName.equals(packageName) && allow.equals("1")){
                Log.i("STS","app verification passed");
                return true;
            }
            return false;
        }

    }


}
