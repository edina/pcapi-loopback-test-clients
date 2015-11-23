package loopback.edina.ac.uk.loopbackclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import loopback.edina.ac.uk.loopbackclient.util.HtmlFragment;

/**
 * Created by murrayking on 23/11/2015.
 */
public class ParseSdkTest extends HtmlFragment {
    private static final String TAG = "ParseSdkTest";

    private String objectId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRootView((ViewGroup) inflater.inflate(
                R.layout.parse_test_layout, container, false));

        setHtmlText(R.id.content_page_three, R.string.parse_sdk_content);

        installButtonClickHandler();

        return getRootView();
    }


    private void installButtonClickHandler() {
        final Button button = (Button) getRootView().findViewById(R.id.sendRequest);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendRequest();
            }
        });

        final Button retrieveBut = (Button) getRootView().findViewById(R.id.retrieveRequest);
        retrieveBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                receiveRequest();
            }
        });
    }

    private void sendRequest() {

        final ParseObject po = new ParseObject("Record");
        po.put("field_one", getRecordFieldOneLabel());


        po.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Log.d(TAG, "User update saved!");
                    //temp.setParseObjectId(po.getObjectId());
                    objectId = po.getObjectId();
                    Log.d(TAG, "The object id (from User) is: " + objectId);

                } else {
                    // The save failed.
                    Log.d(TAG, "User update error: " + e);
                }
            }
        });

    }

    private void receiveRequest() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Record");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    String fieldOneContent = object.getString("field_one");
                    new AlertDialog.Builder(ParseSdkTest.this.getContext())
                            .setTitle("Retrieved Object ")
                            .setMessage("fieldOneContent :" + fieldOneContent)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } else {
                    new AlertDialog.Builder(ParseSdkTest.this.getContext())
                            .setTitle("Error Retrieving Object ")
                            .setMessage(" error :" + e.getMessage())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });
    }

    private String getRecordFieldOneLabel() {
        final EditText widget = (EditText) getRootView().findViewById(R.id.edit_text_test);
        return widget.getText().toString();
    }

}
