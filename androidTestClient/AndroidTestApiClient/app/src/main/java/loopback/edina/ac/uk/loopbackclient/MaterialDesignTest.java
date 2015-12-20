package loopback.edina.ac.uk.loopbackclient;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.remoting.BeanUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loopback.edina.ac.uk.loopbackclient.util.HtmlFragment;

/**
 * Created by murrayking on 12/11/2015.
 */
public class MaterialDesignTest extends Fragment {




    //
    // GUI glue
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.material_design_test, container, false);

        TextInputLayout TLUserName = (TextInputLayout)rootView.findViewById(R.id.userName);
        TextInputLayout TLPassword =(TextInputLayout)rootView.findViewById(R.id.id_password);
        Button submitButton = (Button)rootView.findViewById(R.id.id_submit_button);

        EditText usrName = (EditText)rootView.findViewById(R.id.txtUserName);
        EditText Pwd = (EditText)rootView.findViewById(R.id.txtPassword);

        TLUserName.setHint("Enter User Name"); //setting hint.
        TLPassword.setHint("Enter password");
        TextInputLayout dynamic = new TextInputLayout(this.getContext());
        LinearLayout viewToAddTo = (LinearLayout)rootView.findViewById(R.id.viewToAddTo);
        dynamic.setHint("Test Dynamic");
        EditText dynamicEditText = new EditText(this.getContext());

        dynamic.addView(dynamicEditText, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        viewToAddTo.addView(dynamic, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return rootView;
    }



}
