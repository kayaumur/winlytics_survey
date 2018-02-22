package winlytics.io.survey;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by Umur Kaya on 2/20/18.
 */

class WinlyticsAdapter extends Dialog{

    private final TextView title,winlytics_optional_text_title_area,winlytics_welcoming_text;
    private TextView referenceHolder;
    private final Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,winlytics_submit;
    private final LinearLayout winlytics_optional_text_area;
    private final EditText winlytics_optional_edit_text_area;
    private final ImageView winlytics_customer_logo;
    private final ImageButton winlytics_cancel_action;
    private final ScrollView winlytics_scroll;
    private String resultNumber;
    private Context context;
    Dialog dialog;

    interface WinlyticsAdapterNotifier{
        void notifyAdapterIsReady();
    }

    WinlyticsAdapter(WinlyticsAdapterNotifier mListener,final Context context){
        super(context);
        dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setContentView(R.layout.winlytics_default);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        this.context = context;
        //Dialog setup
        winlytics_customer_logo = (ImageView) dialog.findViewById(R.id.winlytics_customer_logo);
        title = (TextView) dialog.findViewById(R.id.winlytics_text_1);
        button0 = (Button) dialog.findViewById(R.id.winlytics_button_0);
        button1 = (Button) dialog.findViewById(R.id.winlytics_button_1);
        button2 = (Button) dialog.findViewById(R.id.winlytics_button_2);
        button3 = (Button) dialog.findViewById(R.id.winlytics_button_3);
        button4 = (Button) dialog.findViewById(R.id.winlytics_button_4);
        button5 = (Button) dialog.findViewById(R.id.winlytics_button_5);
        button6 = (Button) dialog.findViewById(R.id.winlytics_button_6);
        button7 = (Button) dialog.findViewById(R.id.winlytics_button_7);
        button8 = (Button) dialog.findViewById(R.id.winlytics_button_8);
        button9 = (Button) dialog.findViewById(R.id.winlytics_button_9);
        button10 = (Button) dialog.findViewById(R.id.winlytics_button_10);
        winlytics_optional_text_area = (LinearLayout) dialog.findViewById(R.id.winlytics_optional_text_area);
        winlytics_optional_text_title_area = (TextView) dialog.findViewById(R.id.winlytics_optional_text_title_area);
        winlytics_optional_edit_text_area = (EditText) dialog.findViewById(R.id.winlytics_optional_edit_text_area);
        winlytics_welcoming_text = (TextView) dialog.findViewById(R.id.winlytics_welcoming_text);
        winlytics_submit = (Button) dialog.findViewById(R.id.winlytics_submit);
        winlytics_scroll = (ScrollView) dialog.findViewById(R.id.winlytics_scroll);
        winlytics_cancel_action = (ImageButton) dialog.findViewById(R.id.winlytics_cancel_action);

        //Set Button UnHappy Listener

        View.OnClickListener buttonSad = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(referenceHolder != null){
                    referenceHolder.setBackgroundResource(R.drawable.rounded_button_background_gray_onlyborder);
                    referenceHolder.setTextColor(context.getResources().getColor(R.color.black));
                }
                referenceHolder = (TextView)v;
                resultNumber = ((String) referenceHolder.getTag());
                winlytics_optional_text_area.setVisibility(View.VISIBLE);
                v.post(new Runnable() {
                    @Override
                    public void run() {
                        if(winlytics_scroll.canScrollVertically(1)){
                            winlytics_scroll.smoothScrollTo(0,winlytics_scroll.getBottom());
                        }
                    }
                });
                referenceHolder.setBackgroundResource(R.drawable.rounded_button_background_gray_withborder);
                referenceHolder.setTextColor(context.getResources().getColor(R.color.white));
                //Send result
            }
        };

        //Set Button Happy Listener

        View.OnClickListener buttonHappy = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(referenceHolder != null){
                    referenceHolder.setBackgroundResource(R.drawable.rounded_button_background_gray_onlyborder);
                    referenceHolder.setTextColor(context.getResources().getColor(R.color.black));
                }
                referenceHolder = (TextView)v;
                resultNumber = ((String) referenceHolder.getTag());
                winlytics_optional_text_area.setVisibility(View.VISIBLE);
                v.post(new Runnable() {
                    @Override
                    public void run() {
                        if(winlytics_scroll.canScrollVertically(1)){
                            winlytics_scroll.smoothScrollTo(0,winlytics_scroll.getBottom());
                        }
                    }
                });
                referenceHolder.setBackgroundResource(R.drawable.rounded_button_background_gray_withborder);
                referenceHolder.setTextColor(context.getResources().getColor(R.color.white));
                //Send result
            }
        };

        //Set OnClickListener

        button0.setOnClickListener(buttonSad);
        button1.setOnClickListener(buttonSad);
        button2.setOnClickListener(buttonSad);
        button3.setOnClickListener(buttonSad);
        button4.setOnClickListener(buttonSad);
        button5.setOnClickListener(buttonSad);
        button6.setOnClickListener(buttonSad);
        button7.setOnClickListener(buttonHappy);
        button8.setOnClickListener(buttonHappy);
        button9.setOnClickListener(buttonHappy);
        button10.setOnClickListener(buttonHappy);

        winlytics_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultText = winlytics_optional_edit_text_area.getText().toString();
                //Send resultText and resultNumber to service
                dialog.dismiss();
            }
        });
        winlytics_cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mListener.notifyAdapterIsReady();
    }

    void setImage(String url){
        new DownloadImageTask().execute(url);
    }

    void setBrandColor(int color){
        title.setTextColor(color);
    }

    void setSelectedButtonColor(int color){
        GradientDrawable temp = (GradientDrawable)context.getResources().getDrawable(R.drawable.rounded_button_background_gray_withborder);
        temp.setColor(color);
    }

    void setSubmitButtonTextColor(int color){
        winlytics_submit.setTextColor(color);
    }

    void setSubmitButtonColor(int color){
        winlytics_submit.setBackgroundColor(color);
    }

    void setSubmitButtonText(String text){
        winlytics_submit.setText(text);
    }

    void setBrandName(String text){
        title.setText(text);
    }

    void setOptionalTextAreaText(String text){
        winlytics_optional_text_title_area.setText(text);
    }

    void setButtonAnswerWelcomingText(String text){
        winlytics_welcoming_text.setText(text);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            winlytics_customer_logo.setImageBitmap(result);
        }
    }
}