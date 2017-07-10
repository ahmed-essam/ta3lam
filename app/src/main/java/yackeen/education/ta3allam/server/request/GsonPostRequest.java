package yackeen.education.ta3allam.server.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import yackeen.education.ta3allam.model.dto.response.LoginResponse;
import yackeen.education.ta3allam.util.UserHelper;

public class GsonPostRequest<T> extends JsonRequest<T> {

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final Object dataIn;

    public GsonPostRequest(String url,
                           Object dataIn,
                           Class<T> clazz,
                           Map<String, String> headers,
                           Response.Listener<T> listener,
                           Response.ErrorListener errorListener) {

        //super(Method.POST, url, errorListener);
        super(Method.POST, url, new Gson().toJson(dataIn), listener,
                errorListener);
        this.dataIn = dataIn;
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        Log.d(dataIn.getClass().getSimpleName(), new Gson().toJson(dataIn));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

//    @Override
//    public byte[] getBody() throws AuthFailureError {
//        return gson.toJson(dataIn).getBytes();
//    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            if (clazz == LoginResponse.class) {
               UserHelper.saveStringInSharedPreferences(UserHelper.security_token,response.headers.get("Token"));
                Log.d("token", ":"+response.headers.get("Token"));
            }
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            JSONObject jsonResponse = new JSONObject(json);
            jsonResponse.put("headers", new JSONObject(response.headers));
            Log.d(clazz.getSimpleName(), json);
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }
}