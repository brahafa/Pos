package com.pos.bringit.network;

import android.content.Context;
import android.util.Log;

import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.SharedPrefs;
import com.pos.bringit.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Request {

    private static Request sRequest;

    public static Request getInstance() {
        if (sRequest == null) {
            sRequest = new Request();
        }
        return sRequest;
    }

    public void logIn(final Context context, String password, String email, final RequestCallBackSuccess listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            jsonObject.put("email", email);

            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("response: ", json.toString());
                try {
                    BusinessModel.getInstance().initData(json);
                    SharedPrefs.saveData(Constants.TOKEN_PREF, json.getString("utoken"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (json.has("errorCode") && json.getInt("errorCode") == 1) {
                        listener.onDataDone(false);
                    } else {
                        listener.onDataDone(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDataError(JSONObject json) {
                openAlertMsg(context, json);
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.LOG_IN_MANAGER);
    }

    public void settingsLogin(final Context context, String password, final RequestCallBackSuccess listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            jsonObject.put("phone", "0501112222");
            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                try {
                    if (json.has("status") && json.getBoolean("status")) {
                        if (json.getJSONObject("user").has("name")) {
                            SharedPrefs.saveData(Constants.NAME_PREF, (json.getJSONObject("user")).getString("name"));
                            SharedPrefs.saveData(Constants.ROLE_PREF, (json.getJSONObject("user")).getString("role"));
                        }
                        listener.onDataDone(true);
                    } else {
                        listener.onDataDone(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("GET_ALL_ORDERS", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                openAlertMsg(context, json);
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.SETINGS_LOGIN);
    }

    public void getAllOrders(final Context context, final RequestJsonCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(json);
            }

            @Override
            public void onDataError(JSONObject json) {
                listener.onDataDone(json);
                try {
                    if (json.has("errorCode") && json.getInt("errorCode") == 1) {
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        network.sendRequest(context, Network.RequestName.GET_ALL_ORDERS, Integer.toString(BusinessModel.getInstance().getBusiness_id()));
    }

    public void getItemsInSelectedFolder(Context context, String param, Network.RequestName getItemsInSelectedFoleder, final RequestJsonCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getSelectedFolder", json.toString());
                listener.onDataDone(json);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.d("getSelectedFolder", json.toString());
            }
        });
        network.sendRequest(context, getItemsInSelectedFoleder, param);
    }

    private void openAlertMsg(Context context, JSONObject json) {
        try {
            Utils.openAlertDialog(context, json.getString("message"), "");
            Log.d("response failed: ", json.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface RequestCallBack {
        void onDataDone();
    }

    public interface RequestCallBackSuccess {
        void onDataDone(boolean isDataSuccess);
    }

    public interface RequestJsonCallBack {
        void onDataDone(JSONObject jsonObject);
    }

}
