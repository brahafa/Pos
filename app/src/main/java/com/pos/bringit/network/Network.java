package com.pos.bringit.network;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.pos.bringit.BuildConfig;
import com.pos.bringit.activities.LoginActivity;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.SharedPrefs;
import com.pos.bringit.utils.Utils;

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

    private String BASE_URL;
    private String BASE_URL_2;

    private final String BASE_URL_DEV = "https://api.dev.bringit.org.il/?apiCtrl=";
    private final String BASE_URL_2_DEV = "https://api2.dev.bringit.org.il/";

    private final String BASE_URL_TEST = "https://api.test.bringit.org.il/?apiCtrl=";
    private final String BASE_URL_2_TEST = "https://api2.test.bringit.org.il/";

    private final String BASE_URL_STAGE = "https://api.stg.bringit.co.il/?apiCtrl=";
    private final String BASE_URL_2_STAGE = "https://api2.stg.bringit.co.il/";

    private final String BASE_URL_PROD = "https://api.bringit.co.il/?apiCtrl=";
    private final String BASE_URL_2_PROD = "https://api2.bringit.co.il/";

    private final String BASE_URL_LOCAL = "http://192.168.5.7:80/bringit_backend/?apiCtrl=";
    private final String BASE_URL_2_LOCAL = "http://192.168.5.7:80/api2/";

    private final String BUSINESS = "business&do=";
    private final String DALPAK = "dalpak&do=";
    private final String PIZZIRIA = "pizziria&do=";

    private int netErrorCount = 0;

    public enum RequestName {
        SIGN_UP, GET_LOGGED_MANAGER, lOAD_SAVED_USER_DETAILS, SAVE_USER_INFO_WITH_NOTES,
        GET_ITEMS_IN_SELECTED_FOLEDER, WORKER_LOGIN, LOG_IN_MANAGER,
        GET_ITEMS_SHOTR_CUT_FOLEDER, ADD_TO_CART, SET_DELIVERY_OPTION, GET_ITEMS_BY_TYPE, GET_ORDER_DETAILS_BY_ID,
        GET_CART, CLEAR_CART, ORDER_CHANGE_POS, UPDATE_ORDER_STATUS, LOAD_BUSINES_ITEMS, UPDATE_ITEM_PRICE, GET_ORDER_CODE,
        CHANGE_BUSINESS_STATUS, CHECK_BUSINESS_STATUS,
        SEARCH_CITIES, SEARCH_STREETS,
        GET_WORKING_AREA,
        GET_WORKER_CLOCKS_BY_ID, START_WORKER_CLOCK, END_WORKER_CLOCK, ADD_NEW_WORKERS_CLOCK, EDIT_WORKERS_CLOCK,
        GET_CLIENT_BY_PHONE,
        //        API 2
        GET_ALL_ORDERS, EDIT_ORDER_ITEMS, CANCEL_ORDER,
        LOAD_PRODUCTS, LOAD_ONE_PRODUCT,
        GET_ITEMS_IN_SELECTED_FOLDER,
        MAKE_ORDER, VALIDATE_ORDER, COMPLETE_ORDER,
        SEARCH_PRODUCTS,
        SEARCH_BY_FILTERS,
        EDIT_COLOR,
        OPEN_CLOSE_TABLE,
        CREATE_NEW_PAYMENT, APPROVE_PAYMENT,
        GET_RECEIPT_BY_PAYMENT_ID, CANCEL_RECEIPT_BY_PAYMENT_ID, GET_INVOICE_BY_ORDER_ID, MARK_AS_PRINTED,
        GET_LAST_FINANCE_SESSIONS, GET_CURRENT_FINANCE_SESSION, OPEN_FINANCE_SESSION, CLOSE_FINANCE_SESSION, CREATE_FINANCE_TRANSACTION,
        PAY_TO_DELIVERY_MAN
    }

    Network(NetworkCallBack listener) {
        switch (BuildConfig.BUILD_TYPE) {
            case "debug":
            default:
                BASE_URL = BASE_URL_DEV;
                BASE_URL_2 = BASE_URL_2_DEV;
                break;
            case "debugTest":
                BASE_URL = BASE_URL_TEST;
                BASE_URL_2 = BASE_URL_2_TEST;
                break;
            case "debugStage":
                BASE_URL = BASE_URL_STAGE;
                BASE_URL_2 = BASE_URL_2_STAGE;
                break;
            case "localHost":
                BASE_URL = BASE_URL_LOCAL;
                BASE_URL_2 = BASE_URL_2_LOCAL;
                break;
            case "release":
            case "debugLive":
                BASE_URL = BASE_URL_PROD;
                BASE_URL_2 = BASE_URL_2_PROD;
                break;
        }
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
            case CANCEL_ORDER://api 2
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

            case GET_RECEIPT_BY_PAYMENT_ID: //api 2
                url += "documents/" + param1;
                break;
            case CANCEL_RECEIPT_BY_PAYMENT_ID: //api 2
                url += "pay/cancel/" + param1;
                break;
            case GET_INVOICE_BY_ORDER_ID: //api 2
                url += "documents/" + BusinessModel.getInstance().getBusiness_id() + "/" + param1;
                break;
            case GET_LAST_FINANCE_SESSIONS: //api 2
                url += "financeSession/latest";
                break;
            case GET_CURRENT_FINANCE_SESSION: //api 2
                url += "financeSession/current";
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

    private void sendRequestObject(final RequestName requestName, final String url, final Context context, final NetworkCallBack listener) {
        JsonObjectRequest jsonArrayRequest =
                new JsonObjectRequest(requestName.equals(RequestName.CANCEL_ORDER)
                        ? Request.Method.DELETE
                        : Request.Method.GET, url, null,
                        response -> {
                            Log.d("Request url  11  ", url);
                            Log.d(TAG, "onResponse  :   " + response.toString());
                            listener.onDataDone(response);
                        },
                        error -> {
                            FirebaseCrashlytics.getInstance().log("GET Request error: " + error.toString());

                            if (error.networkResponse != null) {
                                try {
//                                  check if no orders
                                    if (new JSONObject(new String(error.networkResponse.data))
                                            .getString("message").equals("לא נמצאו הזמנות חדשות")) {
                                        listener.onDataDone(null);
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            manageErrors(requestName, error, context, isRetry -> {
                                if (isRetry)
                                    sendRequestObject(requestName, url, context, listener);
                            });

//                                if (error.networkResponse != null)
//                                    listener.onDataError(new JSONObject(new String(error.networkResponse.data)));
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
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
            case GET_CLIENT_BY_PHONE:
                url += DALPAK + "getClientByPhone";
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
            case COMPLETE_ORDER: //api 2
                url += "orders/editOrderDetails";
                break;
            case VALIDATE_ORDER: //api 2
                url += "orders/cart/validate";
                break;
            case CREATE_NEW_PAYMENT: //api 2
                url += "orders/payments/create";
                break;
            case PAY_TO_DELIVERY_MAN: //api 2
                url += "orders/pay2deliveryman";
                break;

            case SEARCH_BY_FILTERS: //api 2
                url += "search/";
                break;

            case OPEN_FINANCE_SESSION: //api 2
                url += "financeSession/open";
                break;
            case CLOSE_FINANCE_SESSION: //api 2
                url += "financeSession/close";
                break;
            case CREATE_FINANCE_TRANSACTION: //api 2
                url += "financeTransaction";
                break;

            case MARK_AS_PRINTED: //api 2
                url += "pay/markAsPrinted";
                break;
            case APPROVE_PAYMENT: //api 2
                url += "pay/updateByHash";
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
                        requestName.equals(RequestName.EDIT_ORDER_ITEMS) ||
                        requestName.equals(RequestName.COMPLETE_ORDER)
                        ? Request.Method.PUT
                        : Request.Method.POST,
                url, params,
                response -> {
                    try {
                        listener.onDataDone(response);
                        VolleyLog.v("Response:%n %s", response.toString(4));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("ERROR POST REQUEST", e.toString());
                    }
                },
                error -> {
                    VolleyLog.e("Error  11: ", error.getMessage());
                    FirebaseCrashlytics.getInstance().log("POST Request error: " + error.toString());
                    FirebaseCrashlytics.getInstance().log("Sent params: " + params.toString());

                    manageErrors(requestName, error, context, isRetry -> {
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
        req.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(context).addToRequestQueue(req);
    }

    public void sendXMLRequest(final Context context, final String params, NetworkXMLCallBack listener) {
        String url = BusinessModel.getInstance().getEmv_url();
        Log.d("XML url", url);
        CustomBodyStringRequest xmlReq = new CustomBodyStringRequest(
                url, params,
                response -> {
                    listener.onXMLDone(response);
                    VolleyLog.v("Response:%n %s", response);
                },
                error -> {
                    VolleyLog.e("Error  11: ", error.toString());
                    FirebaseCrashlytics.getInstance().log("XML Request error: " + error.toString());
                    FirebaseCrashlytics.getInstance().log("Sent params: " + params);
                }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
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
        xmlReq.setRetryPolicy(new DefaultRetryPolicy(
                60 * 1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(context).addToRequestQueue(xmlReq);
    }

    private void manageErrors(RequestName requestName, VolleyError error, Context context, Utils.DialogListener listener) {
        if (requestName == RequestName.CREATE_NEW_PAYMENT) {
            JSONObject jsonError;
            try {
                jsonError = new JSONObject("{\"message\":\"Payment failed\"}");
                this.listener.onDataError(jsonError);
                Log.e("payment error", error.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (error instanceof NoConnectionError) {
//            Utils.openAlertDialog(context, "You are now offline", "Connection is lost");
            JSONObject jsonError = new JSONObject();
            this.listener.onDataError(jsonError);

        } else if (error instanceof TimeoutError) {
            Log.d("error count", netErrorCount + "");
            netErrorCount++;
//            if (netErrorCount % 10 == 0) {
//                Handler handler = new Handler();
//                handler.postDelayed(() -> listener.onRetry(true), 10 * 1000);
//                return;
//            }
            if (netErrorCount > 100) {
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
                JSONObject jsonError = new JSONObject(new String(networkResponse.data));
//                if (networkResponse.statusCode == HttpStatus.SC_FORBIDDEN) {
                // HTTP Status Code: 403 Unauthorized
                listener.onDataError(jsonError);
                Log.e("network error!!!", jsonError.toString());
                if (!BuildConfig.BUILD_TYPE.equals("release"))
                    Utils.openAlertDialog(context, jsonError.getString("message"), "Error");

//                    go to login

                if (networkResponse.statusCode == 401) {
//                if (jsonError.toString().contains("לא נמצאו נתוני משתמש, נא להתחבר למערכת")) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
//                }

            } catch (JSONException e) {
                Log.e("HTML error", new String(networkResponse.data));
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

    public interface NetworkXMLCallBack {
        void onXMLDone(String xml);
    }

}
