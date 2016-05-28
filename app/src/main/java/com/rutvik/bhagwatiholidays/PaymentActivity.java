package com.rutvik.bhagwatiholidays;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import extras.CommonUtilities;
import extras.ICICIStringEncryptor;
import extras.Log;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = App.APP_TAG + PaymentActivity.class.getSimpleName();

    WebView webView;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Make Payment");

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().set
    }

    @Override
    protected void onStart() {

        super.onStart();

        String url = GenerateEncryptedURL(
                "106817|118305|235", //MANDATORY PARAMS: MERCHANTID|REFNO|SUBMERCHANTID|TRANAMOUNT|NAME|MOBILENO|CITY
                //Optional Params: Email
                "www.traveloplan.com",                            //Return URL
                "106817",                                               //Ref No
                "118305",                                               //Submerchant ID
                "235",                                                  //Amount
                "9");                                                   //Pay Mode


        Log.i(TAG, "ENCRYPTED URL: " + url);

        webView.loadUrl(url);

    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;

        }

    }

    private static String GenerateEncryptedURL(String mandFields, String returnUrl, String refNo, String subMerchantId, String amount, String paymentMode) {

        String encryptedUrl = "https://eazypay.icicibank.com/EazyPG?merchantid=106817" +
                "&mandatory fields=" + ICICIStringEncryptor.encrypt(mandFields) +
                "&optional fields=" +
                "&returnurl=" + ICICIStringEncryptor.encrypt(returnUrl) +
                "&Reference No=" + ICICIStringEncryptor.encrypt(refNo) +
                "&submerchantid=" + ICICIStringEncryptor.encrypt(subMerchantId) +
                "&transaction amount=" + ICICIStringEncryptor.encrypt(amount) +
                "&paymode=" + ICICIStringEncryptor.encrypt(paymentMode);

        return encryptedUrl;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case android.R.id.home:
                CommonUtilities.showSimpleAlertDialog(this, "Alert", "This will cancel payment", "GoBack", "Cancel",
                        new CommonUtilities.SimpleAlertDialog.OnClickListener() {
                            @Override
                            public void positiveButtonClicked(DialogInterface dialog, int which) {
                                PaymentActivity.this.finish();
                            }

                            @Override
                            public void negativeButtonClicked(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        }
        return super.onOptionsItemSelected(item);
    }
}
