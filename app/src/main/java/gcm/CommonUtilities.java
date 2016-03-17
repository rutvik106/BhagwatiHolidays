package gcm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.rutvik.bhagwatiholidays.App;

import java.util.Calendar;
import java.util.GregorianCalendar;

public final class CommonUtilities {

	public static final String TAG = App.APP_TAG+CommonUtilities.class.getSimpleName();
	
	// give your server registration url here
	public static final String SERVER_URL = "http://bhagwatiholidays.com/bwt-gcm-server/app/templates/GCM/register.php";


	public static void createCalendarEntry(Context context,String title,String location,String description,GregorianCalendar calDate){

        Intent intent=new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);

        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calDate.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                calDate.getTimeInMillis());

        context.startActivity(intent);
	}


        public static void showAlertDialog(final Context context, final String calendarTitle
                , final String calendarLocation, final String calendarDescription) {
                final Calendar calendar = Calendar.getInstance();

                Log.d(TAG, "In AlertDialog.......");
                new AlertDialog.Builder(context)
                        .setTitle("Inquiry")
                        .setMessage("Inquiry successfully submitted! We will contact you shortly!")
                        .setPositiveButton("Set Reminder", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        CommonUtilities.createCalendarEntry(context,
                                                calendarTitle,
                                                calendarLocation,
                                                calendarDescription,
                                                new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                                        calendar.get(Calendar.DAY_OF_MONTH)));
                                }
                        })
                        .setNegativeButton("Done", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                }
                        })
                        .show();
        }


}
