package com.pos.bringit.network;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pos.bringit.activities.LoginActivity;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.SharedPrefs;
import com.pos.bringit.utils.Utils;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class Network {

    private final String SET_COOKIE_KEY = "Set-Cookie";
    private final String COOKIE_KEY = "Cookie";
    private final String SESSION_COOKIE = "Apikey";

    private NetworkCallBack listener;
    private final String BASE_URL = "https://api.bringit.co.il/?apiCtrl=";
    private final String BASE_URL_2 = "https://api2.bringit.co.il/";
    //        private final String BASE_URL = "http://192.168.5.8:80/bringit_backend/?apiCtrl=";
//        private final String BASE_URL_2 = "http://192.168.5.8:80/api2/";
    private final String BUSINESS = "business&do=";
    private final String DALPAK = "dalpak&do=";
    private final String PIZZIRIA = "pizziria&do=";

    private int netErrorCount = 0;

    public enum RequestName {
        SIGN_UP, GET_LOGGED_MANAGER, lOAD_SAVED_USER_DETAILS, SAVE_USER_INFO_WITH_NOTES,
        GET_ITEMS_IN_SELECTED_FOLEDER, WORKER_LOGIN, LOG_IN_MANAGER, GET_ALL_ORDERS,
        GET_ITEMS_SHOTR_CUT_FOLEDER, ADD_TO_CART, EDIT_ORDER_ITEMS, SET_DELIVERY_OPTION, GET_ITEMS_BY_TYPE, GET_ORDER_DETAILS_BY_ID,
        GET_CART, CLEAR_CART, ORDER_CHANGE_POS, UPDATE_ORDER_STATUS, LOAD_BUSINES_ITEMS, UPDATE_ITEM_PRICE, GET_ORDER_CODE,
        CHANGE_BUSINESS_STATUS, CHECK_BUSINESS_STATUS,
        SEARCH_CITIES, SEARCH_STREETS,
        GET_WORKING_AREA,
        GET_WORKER_CLOCKS_BY_ID, START_WORKER_CLOCK, END_WORKER_CLOCK, ADD_NEW_WORKERS_CLOCK, EDIT_WORKERS_CLOCK,
        //        API 2
        LOAD_PRODUCTS, LOAD_ONE_PRODUCT,
        GET_ITEMS_IN_SELECTED_FOLDER,
        MAKE_ORDER,
        SEARCH_PRODUCTS,
        EDIT_COLOR,
        OPEN_CLOSE_TABLE
    }

    Network(NetworkCallBack listener) {
        this.listener = listener;
    }

    public void sendRequest(final Context context, final RequestName requestName, String param1) {
        sendRequest(context, requestName, param1, false);
    }

    public void sendRequest(final Context context, final RequestName requestName, String param1, boolean isApi2) {
        String url = isApi2 ? BASE_URL_2 : BASE_URL;
        switch (requestName) {
            case GET_LOGGED_MANAGER:
                url += BUSINESS + "getLoggedManager";
                break;
//            case GET_ALL_ORDERS:
//                url += BUSINESS + "getAllOrders&business_id=" + BusinessModel.getInstance().getBusiness_id();
//                break;
            case GET_ALL_ORDERS: //api 2
                url += "orders/" + BusinessModel.getInstance().getBusiness_id() + "/period/" + param1;
                break;
            case GET_ITEMS_SHOTR_CUT_FOLEDER:
                url += DALPAK + "getItemsInSelectedFolder&fav=1";
                break;
            case GET_ITEMS_IN_SELECTED_FOLEDER:
                url += DALPAK + "getItemsInSelectedFolder&folder=" + param1;
                break;
            case GET_ITEMS_IN_SELECTED_FOLDER: //api 2
                url += "folders/" + BusinessModel.getInstance().getBusiness_id() + "/" + param1;
                break;
            case SEARCH_PRODUCTS: //api 2
                url += "folders/search/" + BusinessModel.getInstance().getBusiness_id() + "/" + param1;
                break;
            case GET_ITEMS_BY_TYPE:
                url += DALPAK + "getItemsByType&type=" + param1 + "&linked=2";
                break;
            case GET_ORDER_DETAILS_BY_ID://api 2
                url += "orders/" + BusinessModel.getInstance().getBusiness_id() + "/" + param1;
                break;
            case LOAD_BUSINES_ITEMS:
                url += BUSINESS + "loadBusinessItems&type=" + param1 + "&business_id=" + BusinessModel.getInstance().getBusiness_id();
                break;
            case LOAD_PRODUCTS: //api 2
                url += "products/" + param1 + "/" + BusinessModel.getInstance().getBusiness_id();
                break;
            case LOAD_ONE_PRODUCT: //api 2
                url += "products/product/" + BusinessModel.getInstance().getBusiness_id() + "/" + param1;
                break;
            case GET_ORDER_CODE:
                url += BUSINESS + "getOrderCode" + "&order_id=" + param1;
                break;
            case CHECK_BUSINESS_STATUS:
                url += BUSINESS + "checkBusinessStatus" + "&business_id=" + BusinessModel.getInstance().getBusiness_id();
                break;

            case SET_DELIVERY_OPTION:
                url += PIZZIRIA + "setDeliveryOption" + "&option=" + param1;
                break;

            case SEARCH_CITIES:
                url += PIZZIRIA + "searchCities" + "&q=" + param1;
                break;
            case SEARCH_STREETS:
                url += PIZZIRIA + "searchStreets" + "&q=" + param1;
                break;

            case GET_WORKING_AREA:
                url += DALPAK + "getWorkingArea";
                break;

            case GET_WORKER_CLOCKS_BY_ID:
                url += DALPAK + "getClocksForWorkerByID&worker_id=" + param1;
                break;
            case START_WORKER_CLOCK:
                url += DALPAK + "startWorkersClock&worker_id=" + param1;
                break;
            case END_WORKER_CLOCK:
                url += DALPAK + "endWorkersClock&worker_id=" + param1;
                break;


            case EDIT_COLOR: //api 2
                url += "tables/open/" + BusinessModel.getInstance().getBusiness_id() + "/" + param1;
                break;

        }
        sendRequestObject(requestName, url, context, listener);
    }

    public void sendRequest(final Context context, final RequestName requestName) {
        String url = BASE_URL;
        switch (requestName) {
            case SIGN_UP:
                url += BUSINESS + "signup";
                break;
            case GET_CART:
                url += PIZZIRIA + "getCart";
                break;
            case CLEAR_CART:
                url += PIZZIRIA + "clearCart";

        }
        Log.d("Request url  ", url);
        sendRequestObject(requestName, url, context, listener);
    }

    private void sendRequestObject(final RequestName requestName, final String url, final Context context, final NetworkCallBack listener) {
        JsonObjectRequest jsonArrayRequest =
                new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            Log.d("Request url  11  ", url);
                            Log.d(TAG, "onResponse  :   " + response.toString());
                            listener.onDataDone(response);
                        }, error -> {
                    manageErrors(error, context, isRetry -> {
                        if (isRetry) sendRequestObject(requestName, url, context, listener);
                    });
                    try {
                        if (error.networkResponse != null)
                            listener.onDataError(new JSONObject(new String(error.networkResponse.data)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "Connection Error 22" + error.toString());
                }) {

                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        checkSessionCookie(response.headers);
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        if (!SharedPrefs.getData(Constants.TOKEN_PREF).equals("")) {
                            params.put(SESSION_COOKIE, SharedPrefs.getData(Constants.TOKEN_PREF));
                            Log.d(TAG, "token is: " + SharedPrefs.getData(Constants.TOKEN_PREF));

                            addSessionCookie(params);
                        }
                        return params;
                    }
                };
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void sendPostRequest(final Context context, final JSONObject params, final RequestName requestName) {
        sendPostRequest(context, params, requestName, false);
    }

    public void sendPostRequest(final Context context, final JSONObject params, final RequestName requestName, boolean isApi2) {
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        String url = isApi2 ? BASE_URL_2 : BASE_URL;
        switch (requestName) {
            case SIGN_UP:
                url += BUSINESS + "signup";
                break;
            case lOAD_SAVED_USER_DETAILS:
                url += BUSINESS + "loadSavedUserDetails";
                break;
            case SAVE_USER_INFO_WITH_NOTES:
                url += PIZZIRIA + "saveUserInfoWithNotes";
                break;
            case WORKER_LOGIN:
                url += DALPAK + "workerLogin";
                break;
            case LOG_IN_MANAGER:
                url += BUSINESS + "loginManager";
                break;
            case ADD_TO_CART:
                url += PIZZIRIA + "addToCart";
                break;
            case MAKE_ORDER: //api 2
            case EDIT_ORDER_ITEMS:
            case GET_CART:
                url += "orders";
                break;
            case ORDER_CHANGE_POS:
                url += BUSINESS + "orderChangePos";
                break;
            case UPDATE_ORDER_STATUS:
                url += BUSINESS + "updateOrderStatus";
                break;
            case UPDATE_ITEM_PRICE:
                url += BUSINESS + "updateItemPrice";
                break;
            case CHANGE_BUSINESS_STATUS:
                url += BUSINESS + "changeBusinessStatus";
                break;

            case ADD_NEW_WORKERS_CLOCK:
                url += DALPAK + "addNewWorkersClock";
                break;
            case EDIT_WORKERS_CLOCK:
                url += DALPAK + "editWorkersClock";
                break;

            case EDIT_COLOR: //api 2
                url += "orders/color";
                break;
            case OPEN_CLOSE_TABLE: //api 2
                url += "tables/open";
                break;

        }
        Log.d("POST url  ", url);
        JsonObjectRequest req = new JsonObjectRequest(
                requestName.equals(RequestName.EDIT_COLOR) ||
                        requestName.equals(RequestName.OPEN_CLOSE_TABLE) ||
                        requestName.equals(RequestName.EDIT_ORDER_ITEMS)
                        ? Request.Method.PUT : Request.Method.POST,
                url, params,
                response -> {
                    try {
                        listener.onDataDone(response);
                        VolleyLog.v("Response:%n %s", response.toString(4));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("ERROR POST REQUEST", e.toString());
                    }
                }, error -> {
            VolleyLog.e("Error  11: ", error.getMessage());

            manageErrors(error, context, isRetry -> {
                if (isRetry) sendPostRequest(context, params, requestName, isApi2);
            });
            //                try {
            //
            //                   listener.onDataError(new JSONObject(new String(error.networkResponse.data)));
            //                } catch (JSONException e) {
            //                    e.printStackTrace();
            //                }

        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                // since we don't know which of the two underlying network vehicles
                // will Volley use, we have to handle and store session cookies manually
                checkSessionCookie(response.headers);
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                if (!SharedPrefs.getData(Constants.TOKEN_PREF).equals("")) {
                    params.put(SESSION_COOKIE, SharedPrefs.getData(Constants.TOKEN_PREF));
                    Log.d(TAG, "token is: " + SharedPrefs.getData(Constants.TOKEN_PREF));
                    addSessionCookie(params);
                }
                return params;
            }
        };
        RequestQueueSingleton.getInstance(context).addToRequestQueue(req);
    }

    private void manageErrors(VolleyError error, Context context, Utils.DialogListener listener) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            netErrorCount++;
            if (netErrorCount > 3) {
                netErrorCount = 0;
                Utils.openAlertDialogRetry(context, listener);
            } else listener.onRetry(true);

        } else if (error instanceof ParseError) {
            Log.e("parse error", error.toString());
            Toast.makeText(context, ("ParseError"), Toast.LENGTH_SHORT).show();
        } else {
            manageMsg(error, context);
        }
    }

    private void manageMsg(VolleyError error, Context context) {
        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse != null && networkResponse.data != null) {
            try {
                JSONArray jsonArray = null;
                JSONObject jsonError = new JSONObject(new String(networkResponse.data));
                if (networkResponse.statusCode == HttpStatus.SC_FORBIDDEN) {
                    // HTTP Status Code: 403 Unauthorized
                    listener.onDataError(jsonError);
                    Log.e("network error!!!", jsonError.toString());

//                    go to login
                    if (jsonError.toString().contains("לא נמצאו נתוני משתמש, נא להתחבר למערכת")) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPrefs.saveData(SESSION_COOKIE, cookie);
            }
        }
    }

    private void addSessionCookie(Map<String, String> headers) {
        String sessionId = SharedPrefs.getData(Constants.TOKEN_PREF);
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }

    public interface NetworkCallBack {
        void onDataDone(JSONObject json);

        void onDataError(JSONObject json);
    }

}
