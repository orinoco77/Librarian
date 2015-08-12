package uk.co.sequoiasolutions.librarian;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.Vector;

public class MainActivity extends Activity {

    ProgressDialog pDialog;

    private EbookAdapter ebookAdapter;
    String jsonResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Just a listView, shown below
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parentView, View childView,
                                    int position, long id) {
                Ebook ebook = (Ebook)listView.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, ebook.getDescription(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView parentView) {

            }
        });
        new JsonReadTask().execute("http://192.168.100.102:8282/?getall"); //YOUR URL JSON SERVER, IF IT IS DIFFERENT FROM THAT SUPPLIED ABOVE
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true; //No options
    }

    public void onStart() {
        super.onStart();

        ebookAdapter = new EbookAdapter(this); //Create a new EbookAdapter
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

                if (holder.image != null) {
                    if (holder.image.getDrawable() == null) {
                        new ImageDownloaderTask(holder.image, null).execute(stocks[position].getImageUrl()); //Download the image using the imageurl

                    }
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.title.setText(stocks[position].getTitle());
            holder.author.setText(stocks[position].getAuthor());

            return convertView;
        }
    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... params) {
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

            ListDrawer();

        }

    }// end async task

    // build hash set for list view
    public void ListDrawer() {

        try {
            if (jsonResult!=null) {
                JSONObject jsonResponse = new JSONObject(jsonResult);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("ebook");
                Vector<Ebook> vstocks = new Vector<Ebook>();
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    Ebook ebook = new Ebook();
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
                for (int n = 0; n < stockscount; n++)
                {
                    ebooks[n] = vstocks.get(n);
                }
                ebookAdapter.setStockList(ebooks);
                ListView listView = (ListView)findViewById(R.id.listView);
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

    private class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public ImageDownloaderTask(ImageView imageView, View view) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        // Actual download method, run in the task thread
        protected Bitmap doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the url.
            return downloadBitmap(params[0]);
        }

        @Override
        // Once the image is downloaded, associates it to the imageView
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {

                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        //
                    }
                }

            }

        }

        Bitmap downloadBitmap(String url) {
            if(URLUtil.isValidUrl(url)){

                final HttpClient client = new DefaultHttpClient();
                final HttpGet getRequest = new HttpGet(url);
                try {
                    HttpResponse response = client.execute(getRequest);
                    final int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode != HttpStatus.SC_OK) {
                        Log.w("ImageDownloader", "Error " + statusCode
                                + " while retrieving bitmap from " + url);
                        return null;
                    }

                    final HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        InputStream inputStream = null;
                        try {
                            inputStream = entity.getContent();
                            try {
                                byte[] buffer = new byte[8192];
                                int bytesRead;
                                ByteArrayOutputStream output = new ByteArrayOutputStream();
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    output.write(buffer, 0, bytesRead);
                                }
                                return BitmapFactory.decodeByteArray(output.toByteArray(), 0, output.toByteArray().length);
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                                Log.i("IAE", "in ebooks");
                                return null;
                            }
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            entity.consumeContent();
                        }
                    }
                } catch (Exception e) {
                    getRequest.abort();
                    Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
                }
                return null;

            }
            return null;
        }

    }
}
