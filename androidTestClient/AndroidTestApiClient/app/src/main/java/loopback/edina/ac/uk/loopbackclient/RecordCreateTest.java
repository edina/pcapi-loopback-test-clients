package loopback.edina.ac.uk.loopbackclient;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loopback.edina.ac.uk.loopbackclient.util.HtmlFragment;

/**
 * Created by murrayking on 12/11/2015.
 */
public class RecordCreateTest extends HtmlFragment {

    private final static String FEATURE = "Feature";

    /**
     * This custom subclass of Model is the closest thing to a "schema" the Note model has.
     *
     * When we save an instance of RecordModel, LoopBack uses the property getters and setters
     * of the subclass to customize the request it makes to the server. The server handles
     * this freeform request appropriately, saving our freeform model to the database just
     * as we expect.
     *
     * Note: in a regular application, this class would be defined as top-level (non-static)
     * class in a file of its own. We are keeping it as a static nested class only to make
     * it easier to follow this guide.
     */

	/*

	{
  "name": "string",
  "properties": {
    "editor": "string",
    "fields": [
      {}
    ],
    "timestamp": "string"
  },
  "type": "string",
  "geometry": {
    "type": "string",
    "coordinates": [
      {}
    ]
  },
  "id": 0
}{
  "name": "RecordProperties",
  "base": "Model",
  "idInjection": false,
  "options": {
    "validateUpsert": true
  },
  "properties": {
    "id": false,
    "editor": {
      "type": "string",
      "required": true
    },
    "fields": {
      "type": "array",
      "required": true
    },
    "timestamp": {
      "type": "string",
      "required": true
    }
  },
  "validations": [],
  "relations": {},
  "acls": [],
  "methods": {}
}


	 */
    public static class RecordModel extends Model {
        private String name;
        private Map properties;
        private String type;
        private Map geometry;
        private String id;

        @Override
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map getProperties() {
            return properties;
        }

        public void setProperties(Map properties) {
            this.properties = properties;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Map getGeometry() {
            return geometry;
        }

        public void setGeometry(Map geometry) {
            this.geometry = geometry;
        }
    }

    public static class Properties{
        private String editor;
        private List<String> fields = new ArrayList<String>();
        private String timestamp;

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public List<String> getFields() {
            return fields;
        }

        public void setFields(List<String> fields) {
            this.fields = fields;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }


    /**
     * The ModelRepository provides an interface to the Model's "type" on the server. For instance,
     * we'll (SPOILER!) see in Lessons Two how the ModelRepository is used for queries;
     * in Lesson Three we'll use it for custom, collection-level behaviour: those locations within
     * the collection closest to the given coordinates.
     *
     * This subclass, however, provides an additional benefit: it acts as glue within the LoopBack
     * interface between a RestAdapter representing the _server_ and a named collection or
     * type of model within it. In this case, that type of model is named "note", and it contains
     * RecordModel instances.
     *
     * Note: in a regular application, this class would be defined as top-level (non-static)
     * class in a file of its own. We are keeping it as a static nested class only to make
     * it easier to follow this guide.
     */
    public static class RecordRepository extends ModelRepository<RecordModel> {
        public RecordRepository() {
            super("record", "records", RecordModel.class);
        }
    }

    /**
     * Saves the desired Note model to the server with all values pulled from the UI.
     */
    private void sendRequest() {
        // 1. Grab the shared RestAdapter instance.
        LoopbackApplication app = (LoopbackApplication)getActivity().getApplication();
        RestAdapter adapter = app.getLoopBackAdapter();

        // 2. Instantiate our RecordRepository. For the intrepid, notice that we could create this
        //    once (say, in onCreateView) and use the same instance for every request.
        //    Additionally, the shared adapter is associated with the prototype, so we'd only
        //    have to do step 1 in onCreateView also. This more verbose version is presented
        //    as an example; making it more efficient is left as a rewarding exercise for the reader.
        RecordRepository repository = adapter.createRepository(RecordRepository.class);

        // 3. From that prototype, create a new RecordModel. We pass in an empty dictionary to defer
        //    setting any values.
        RecordModel model = repository.createObject(new HashMap<String, Object>());

        // 4. Pull model values from the UI.

        model.setId(getRecordId());
        model.setName(getRecordName());
        model.setType(FEATURE);
        Map properties = new HashMap<String,Object>();

        properties.put("editor", getRecordEditor());
        properties.put("fields", new String[]{});

        model.setProperties(properties);
        Map<String,Object> geometry = new HashMap<String,Object>();
        geometry.put("type", "Point");
        geometry.put("coordinates",  new Double[]{-3.186693670396864, 55.93634725525547, 100.0});

        model.setGeometry(geometry);

        // 5. Save!
        model.save(new VoidCallback() {

            @Override
            public void onSuccess() {
                showResult("Saved!");
            }

            @Override
            public void onError(Throwable t) {
                Log.e(getTag(), "Cannot save Note model.", t);
                showResult("Failed.");
            }
        });
    }

    void showResult(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    //
    // GUI glue
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRootView((ViewGroup) inflater.inflate(
                R.layout.record_create_layout, container, false));

        setHtmlText(R.id.content, R.string.save_record_content);

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

    //
    // Properties for accessing form values
    //

    private String getRecordId() {
        final EditText widget = (EditText) getRootView().findViewById(R.id.editId);
        return widget.getText().toString();
    }

    private String getRecordName() {
        final EditText widget = (EditText) getRootView().findViewById(R.id.editName);
        return widget.getText().toString();
    }

    private String getRecordEditor() {
        final EditText widget = (EditText) getRootView().findViewById(R.id.editEditor);
        return widget.getText().toString();
    }


}
