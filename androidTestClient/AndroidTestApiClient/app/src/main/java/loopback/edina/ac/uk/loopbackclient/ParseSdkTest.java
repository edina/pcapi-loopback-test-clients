package loopback.edina.ac.uk.loopbackclient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;

import loopback.edina.ac.uk.loopbackclient.util.HtmlFragment;

/**
 * Created by murrayking on 23/11/2015.
 */
public class ParseSdkTest extends HtmlFragment {
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
    }

    private void sendRequest() {
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("test" , getRecordFieldOneLabel());
        testObject.saveInBackground();

    }

    private String getRecordFieldOneLabel() {
        final EditText widget = (EditText) getRootView().findViewById(R.id.edit_text_test);
        return widget.getText().toString();
    }

}
