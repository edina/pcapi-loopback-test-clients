package loopback.edina.ac.uk.loopbackclient;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.loopback.Container;
import com.strongloop.android.loopback.ContainerRepository;
import com.strongloop.android.loopback.File;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loopback.edina.ac.uk.loopbackclient.util.HtmlFragment;

/**
 * Created by murray on 16/11/15.
 */
public class UploadBinaryTest extends HtmlFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRootView((ViewGroup) inflater.inflate(
                R.layout.file_upload, container, false));

        setHtmlText(R.id.content_page_two, R.string.upload_file_content);

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
        // 1. Grab the shared RestAdapter instance.
        LoopbackApplication app = (LoopbackApplication)getActivity().getApplication();
        RestAdapter adapter = app.getLoopBackAdapter();
        ContainerRepository containerRepo = adapter.createRepository(ContainerRepository.class);
        containerRepo.getAll(new ListCallback<Container>() {
            @Override
            public void onSuccess(List<Container> containers) {
                // "containers" hold all items found
                for(Container c : containers) {

                    Log.d("UploadTest", c.getName());
                    Drawable myDrawable = getResources().getDrawable(R.drawable.welcome_to_the_main_library_crop);
                    Bitmap anImage = ((BitmapDrawable) myDrawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    anImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    c.upload("fileName", byteArray, "image/png",
                            new ObjectCallback<File>() {
                                @Override
                                public void onSuccess(File remoteFile) {
                                    // Update GUI - add remoteFile to the list of documents
                                    Log.d("UploadTest", remoteFile.toString());
                                }

                                @Override
                                public void onError(Throwable error) {
                                    // upload failed
                                    Log.d("UploadTest", error.toString());
                                }
                            }
                    );
                }
            }

            @Override
            public void onError(Throwable error) {
                // request failed
                Log.d("UploadTest", error.toString());
            }
        });
    }

    void showResult(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


}
