package com.example.khaledsb.json_pars_endroit;



        import java.util.ArrayList;
        import java.util.HashMap;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import android.app.ListActivity;
        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;

public class List_Endroit extends ListActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://10.0.2.2/android/user.php";
   // hada ouwa mon lien  ki ndirou no results:: remplacih f url li fo9 had la phrase
   //          http://khaledsb1.byethost7.com/
    //http://api.androidhive.info/contacts/

    // JSON Node names
    private static final String TAG_CONTACTS = "endroits";
    private static final String TAG_ID = "id";
    private static final String TAG_NOM = "nom_endroit";
    private static final String TAG_CATEGORIE = "categorie";
    private static final String TAG_ADRESSE = "adresse";
    private static final String TAG_DESCRIPTION = "description";


    // contacts JSONArray
    JSONArray contacts = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_endroit);



        contactList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        // Listview on item click listener
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String costagorie = ((TextView) view.findViewById(R.id.categorie))
                        .getText().toString();
                String description = ((TextView) view.findViewById(R.id.description))
                        .getText().toString();

                // Starting single contact activity
            /*    Intent in = new Intent(getApplicationContext(),
                        SingleContactActivity.class);
                in.putExtra(TAG_NAME, name);
                in.putExtra(TAG_EMAIL, cost);
                startActivity(in);*/

            }
        });

        // Calling async task to get json
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(List_Endroit.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            Recuperer_Endroit sh = new Recuperer_Endroit();

            // Making a request to url and getting response
           // String jsonStr = sh.makeServiceCall(url, Recuperer_Endroit.GET);
            String jsonStr=Get_Data.getInfo();
            Log.d("Res: ", "> " + jsonStr);

            if (jsonStr != null) {

              //  Log.d("Response: ", " ok m  chi null" );
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_CONTACTS);


                    //Log.d("Response: ", " "+contacts.length() );
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NOM);
                        String categorie = c.getString(TAG_CATEGORIE);
                        String address = c.getString(TAG_ADRESSE);
                        String description = c.getString(TAG_DESCRIPTION);
                        Log.d("Res: ", "> " + id+name);




                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        contact.put(TAG_ID, id);
                        contact.put(TAG_NOM, name);
                        contact.put(TAG_CATEGORIE, categorie);
                        contact.put(TAG_DESCRIPTION,description);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    List_Endroit.this, contactList,
                    R.layout.list_item, new String[] { TAG_NOM, TAG_CATEGORIE,TAG_DESCRIPTION}, new int[] { R.id.name,
                    R.id.categorie,R.id.description});

            setListAdapter(adapter);
        }

    }

}