package uk.co.sequoiasolutions.librarian;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.URLUtil;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Vector;


public class MainActivity extends Activity {

    private EbookAdapter ebookAdapter;
    private AuthorAdapter authorAdapter;
    String jsonResult = null;
    private SharedPreferences sharedPref;
    private String server;
    private String port;
    private String baseUrl;
    private ListView listView;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Just a listView, shown below
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        port = sharedPref.getString("port","");
        if (port.equals(""))
            port = "8282";
        server = sharedPref.getString("server","");
        if (server.equals(""))
            server="192.168.100.102";
        baseUrl = "http://" + server + ":" + port +"/";
        TextView search = (TextView)findViewById(R.id.editText);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                             @Override
                                             public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                 boolean handled = false;
                                                 if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                                                     new JsonReadTask().execute(baseUrl + "?getsearch=" + v.getText(), "Ebook");
                                                     handled = true;
                                                 }
                                                 return handled;
                                             }
                                         }
        );
        Button buttonA = (Button)findViewById(R.id.buttonA);
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=A", "Author");
            }
        });
        Button buttonB = (Button)findViewById(R.id.buttonB);
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=B", "Author");
            }
        });
        Button buttonC = (Button)findViewById(R.id.buttonC);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=C", "Author");
            }
        });
        Button buttonD = (Button)findViewById(R.id.buttonD);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=D", "Author");
            }
        });
        Button buttonE = (Button)findViewById(R.id.buttonE);
        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=E", "Author");
            }
        });
        Button buttonF = (Button)findViewById(R.id.buttonF);
        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=F", "Author");
            }
        });
        Button buttonG = (Button)findViewById(R.id.buttonG);
        buttonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=G", "Author");
            }
        });
        Button buttonH = (Button)findViewById(R.id.buttonH);
        buttonH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=H", "Author");
            }
        });
        Button buttonI = (Button)findViewById(R.id.buttonI);
        buttonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=I", "Author");
            }
        });
        Button buttonJ = (Button)findViewById(R.id.buttonJ);
        buttonJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=J", "Author");
            }
        });
        Button buttonK = (Button)findViewById(R.id.buttonK);
        buttonK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=K", "Author");
            }
        });
        Button buttonL = (Button)findViewById(R.id.buttonL);
        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=L", "Author");
            }
        });
        Button buttonM = (Button)findViewById(R.id.buttonM);
        buttonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=M", "Author");
            }
        });
        Button buttonN = (Button)findViewById(R.id.buttonN);
        buttonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=N", "Author");
            }
        });
        Button buttonO = (Button)findViewById(R.id.buttonO);
        buttonO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=O", "Author");
            }
        });
        Button buttonP = (Button)findViewById(R.id.buttonP);
        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=P", "Author");
            }
        });
        Button buttonQ = (Button)findViewById(R.id.buttonQ);
        buttonQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=Q", "Author");
            }
        });
        Button buttonR = (Button)findViewById(R.id.buttonR);
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=R", "Author");
            }
        });
        Button buttonS = (Button)findViewById(R.id.buttonS);
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=S", "Author");
            }
        });
        Button buttonT = (Button)findViewById(R.id.buttonT);
        buttonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=T", "Author");
            }
        });
        Button buttonU = (Button)findViewById(R.id.buttonU);
        buttonU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=U", "Author");
            }
        });
        Button buttonV = (Button)findViewById(R.id.buttonV);
        buttonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=V", "Author");
            }
        });
        Button buttonW = (Button)findViewById(R.id.buttonW);
        buttonW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=W", "Author");
            }
        });
        Button buttonX = (Button)findViewById(R.id.buttonX);
        buttonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=X", "Author");
            }
        });
        Button buttonY = (Button)findViewById(R.id.buttonY);
        buttonY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=Y", "Author");
            }
        });
        Button buttonZ = (Button)findViewById(R.id.buttonZ);
        buttonZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonReadTask().execute(baseUrl + "?getauthors=Z", "Author");
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parentView, View childView,
                                    int position, long id) {
                if (listView.getItemAtPosition(position) instanceof Ebook) {
                    Ebook ebook = (Ebook) listView.getItemAtPosition(position);
                    new DownloadFileFromURL().execute(baseUrl + ebook.getId(), ebook.getEbookUrl());


                }
                else if (listView.getItemAtPosition(position) instanceof Author) {
                    long authorId=((Author) listView.getItemAtPosition(position)).get_id();
                    new JsonReadTask().execute(baseUrl + "?getauthorbooks=" + authorId, "Ebook");
                }
            }

            public void onNothingSelected(AdapterView parentView) {

            }
        });

        //new JsonReadTask().execute(baseUrl + "?getall=true", "Ebook"); //YOUR URL JSON SERVER, IF IT IS DIFFERENT FROM THAT SUPPLIED ABOVE

    }

    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        private String downloadedFile="";
        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(downloadedFile)), "application/epub+zip");
            startActivity(intent);
        }
        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();


                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                downloadedFile=Environment
                        .getExternalStorageDirectory().toString()
                        + "/" + new File(f_url[1]).getName();
                // Output stream
                OutputStream output = new FileOutputStream(downloadedFile);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                return "sad trombone";
            }

            return "success";
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onStart() {
        super.onStart();

        ebookAdapter = new EbookAdapter(this); //Create a new EbookAdapter
        authorAdapter = new AuthorAdapter(this);
    }

    public static boolean isDownloadManagerAvailable(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
    }

    public static String strFromStream(InputStream in) throws IOException { //Simple function, getting a String from an InputStream
        StringBuilder out = new StringBuilder();
        BufferedReader breader = new BufferedReader(new InputStreamReader(in));
        String cline;
        String newLine = System.getProperty("line.separator");
        while ((cline = breader.readLine()) != null) {
            out.append(cline);
            out.append(newLine);
        }
        return out.toString();
    }

    private class EbookAdapter extends BaseAdapter { //The stocks list adaptor

        class ViewHolder {
            TextView title;
            TextView author;
            ImageView image;
        }

        private LayoutInflater layoutInflater;
        private Ebook[] stocks = null; //Array of stocks
        private ListView stocksListView = null;

        public EbookAdapter(Context context) {
            super();


            layoutInflater = LayoutInflater.from(context);
        }

        public void setStockList(Ebook[] stocksinfo) {
            this.stocks = stocksinfo;// //////////////LITERALLY THIS

        }

        @Override
        public int getCount() {
            return stocks.length;
        }

        @Override
        public Object getItem(int position) {
            return stocks[position];
        }

        public Ebook[] getAll() { //Return the array of stocks
            return stocks;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder; //New holder
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item,
                        null);
                holder = new ViewHolder();
                // Creates the new viewholder define above, storing references to the children
                holder.title = (TextView) convertView.findViewById(R.id.booktitle);
                holder.author = (TextView) convertView.findViewById(R.id.bookauthor);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.title.setText(stocks[position].getTitle());
            holder.author.setText(stocks[position].getAuthor());
            byte[] bytes = Base64.decode(stocks[position].getImageUrl(), Base64.DEFAULT);
            holder.image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            return convertView;
        }
    }

    private class AuthorAdapter extends BaseAdapter { //The stocks list adaptor

        class ViewHolder {
            TextView author;
        }

        private LayoutInflater layoutInflater;
        private Author[] stocks = null; //Array of stocks
        private ListView stocksListView = null;

        public AuthorAdapter(Context context) {
            super();


            layoutInflater = LayoutInflater.from(context);
        }

        public void setStockList(Author[] stocksinfo) {
            this.stocks = stocksinfo;// //////////////LITERALLY THIS

        }

        @Override
        public int getCount() {
            return stocks.length;
        }

        @Override
        public Object getItem(int position) {
            return stocks[position];
        }

        public Author[] getAll() { //Return the array of stocks
            return stocks;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder; //New holder
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.author,
                        null);
                holder = new ViewHolder();
                // Creates the new viewholder define above, storing references to the children

                holder.author = (TextView) convertView.findViewById(R.id.author);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.author.setText(stocks[position].get_name());
            return convertView;
        }
    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {

        private String type;
        @Override
        protected String doInBackground(String... params) {
            if (params[1]!=null)
                type = params[1];
            if (URLUtil.isValidUrl(params[0])) {
                final HttpClient client = new DefaultHttpClient();
                final HttpGet getRequest = new HttpGet(params[0]);
                try {
                    HttpResponse response = client.execute(getRequest);
                    final HttpEntity httpentity = response.getEntity();
                    if (httpentity != null){
                        InputStream inputStream = null;
                        try {
                            inputStream = httpentity.getContent();
                            jsonResult = strFromStream(inputStream);
                            Log.i("", jsonResult);
                            return jsonResult;
                        } catch (IllegalArgumentException e) {
                            //
                        } finally {
                            httpentity.consumeContent();
                        }
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            ListDrawer(type);

        }

    }// end async task

    // build hash set for list view
    public void ListDrawer(String type) {
        if (type.equals("Ebook")) {
            try {
                if (jsonResult != null) {
                    JSONObject jsonResponse = new JSONObject(jsonResult);
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("ebook");
                    Vector<Ebook> vstocks = new Vector<Ebook>();
                    for (int i = 0; i < jsonMainNode.length(); i++) {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        Ebook ebook = new Ebook();
                        ebook.setId(Integer.parseInt(jsonChildNode.optString("id")));
                        ebook.setTitle(jsonChildNode.optString("title"));
                        ebook.setAuthor(jsonChildNode.optString("author"));
                        ebook.setImageUrl(jsonChildNode.getString("imageUrl"));
                        ebook.setDescription(jsonChildNode.getString("description"));
                        ebook.setEbookUrl(jsonChildNode.getString("ebookUrl"));
                        Log.i("StockLog", ebook.getTitle() + ebook.getAuthor() + ebook.getImageUrl());
                        vstocks.add(ebook);
                    }
                    Ebook[] ebooks = new Ebook[jsonMainNode.length()];

                    int stockscount = jsonMainNode.length();
                    for (int n = 0; n < stockscount; n++) {
                        ebooks[n] = vstocks.get(n);
                    }
                    ebookAdapter.setStockList(ebooks);
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(ebookAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Error; jsonResult null",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }
        if (type.equals("Author")) {
            try {
                if (jsonResult != null) {
                    JSONObject jsonResponse = new JSONObject(jsonResult);
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("author");
                    Vector<Author> vstocks = new Vector<Author>();
                    for (int i = 0; i < jsonMainNode.length(); i++) {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        Author author = new Author();
                        author.set_name(jsonChildNode.getString("name"));
                        author.set_id(Integer.parseInt(jsonChildNode.getString("id")));

                        vstocks.add(author);
                    }
                    Author[] authors = new Author[jsonMainNode.length()];

                    int stockscount = jsonMainNode.length();
                    for (int n = 0; n < stockscount; n++) {
                        authors[n] = vstocks.get(n);
                    }
                    authorAdapter.setStockList(authors);
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(authorAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Error; jsonResult null",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
