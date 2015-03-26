package jumpers.com.pipervoice.Control;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Objects;

/**
 * Created by Carla on 3/12/2015.
 */
public class Requisition
{
    public interface Callback
    {
        public void requisitionCompleted(int status, String answer, Exception e);
    }

    public static void send(Callback callback, String url, String method, Object objectBodyJSON, Context context)
    {
        Gson gson = new Gson();
        send(callback, url, method, gson.toJson(objectBodyJSON), context);
    }

    public static void send(final Callback callback, final String url, final String method, final String jSONBody, final Context context)
    {
        (new AsyncTask<String, Void, Object>()
        {
            int status = 0;
            @Override
            protected Object doInBackground(String... params)
            {

                try
                {
                    HttpClient client = new DefaultHttpClient();
                    HttpUriRequest methodRequest = null;
                    //só usar post e get

                    if(method.equalsIgnoreCase("GET"))
                    {
                        HttpGet getting = new HttpGet(url);
                        methodRequest = getting;
                    }
                    else if(method.equalsIgnoreCase("POST"))
                    {
                        HttpPost post = new HttpPost(url);
                        post.setEntity(new ByteArrayEntity(jSONBody.getBytes()));
                        methodRequest = post; //recebe post

                        methodRequest.setHeader("Content-type", "application/json; charset=utf-8");
                    }

                    methodRequest.setHeader("Accept", "application/json");
                    methodRequest.setHeader("Accept-Charset", "utf-8");

                    HttpResponse response = client.execute(methodRequest);
                    status = response.getStatusLine().getStatusCode();

                    return EntityUtils.toString(response.getEntity());//entity = byte
                }
                catch (Exception e)
                {
                    return e;
                }
            }

            @Override
            protected void onPreExecute()
            {
                Toast.makeText(context, "Enviando requisição.", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(Object o)
            {
                if(o instanceof Exception)
                {
                    callback.requisitionCompleted(status, null, (Exception)o);
                }
                else if(o != null)
                {
                    callback.requisitionCompleted(status, (String)o, null);
                }
            }
        }).execute(url);
    }
}


