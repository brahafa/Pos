package com.pos.bringit.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.models.response.AllOrdersResponse;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.response.FolderItemsResponse;
import com.pos.bringit.models.response.ToppingsListResponse;
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
                    JSONObject jsonBusinessModel = json.getJSONObject("message");
                    BusinessModel.getInstance().initData(jsonBusinessModel);

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
        network.sendPostRequest(context, jsonObject, Network.RequestName.WORKER_LOGIN);
    }

    public void getAllOrders(final Context context, final RequestAllOrdersCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getAllOrders", json.toString());
                Gson gson = new Gson();
                AllOrdersResponse response = gson.fromJson(json.toString(), AllOrdersResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                listener.onDataDone(new AllOrdersResponse());
            }
        });
        network.sendRequest(context, Network.RequestName.GET_ALL_ORDERS, Integer.toString(BusinessModel.getInstance().getBusiness_id()));
    }

    public void getItemsInSelectedFolder(Context context, String folderNumber, final RequestFolderItemsCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getSelectedFolder", json.toString());
                Gson gson = new Gson();
                FolderItemsResponse response = gson.fromJson(json.toString(), FolderItemsResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.d("getSelectedFolder", json.toString());
                try {
                    Toast.makeText(context, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        network.sendRequest(context, Network.RequestName.GET_ITEMS_IN_SELECTED_FOLEDER, folderNumber);
    }

    public void getToppings(Context context, RequestToppingItemsCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getToppings", json.toString());
                Gson gson = new Gson();
                ToppingsListResponse response = gson.fromJson(json.toString(), ToppingsListResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {

            }
        });
        network.sendRequest(context, Network.RequestName.LOAD_BUSINES_ITEMS, "topping");
    }

    public void addToCart(final Context context, CartModel cartItem, final RequestCallBackSuccess listener) {
        JSONObject params = new JSONObject();
        try {
            params.put("business_id", BusinessModel.getInstance().getBusiness_id());
            params.put("type", cartItem.getType());
            params.put("o_id", cartItem.getObjectId());

            if (cartItem.getType().equals("Topping")) {
                params.put("f_id", cartItem.getFatherId());
                params.put("t_location", cartItem.getToppingLocation());
            }
            Log.d("addTCart params", params.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("AddToCart", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.d("AddToCart error", json.toString());
            }
        });
        network.sendPostRequest(context, params, Network.RequestName.ADD_TO_CART);
    }

    public void checkToken(Context context, final RequestCallBackSuccess listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                try {
                    JSONObject jsonBusinessModel = json.getJSONObject("user");
                    BusinessModel.getInstance().initData(jsonBusinessModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onDataDone(true);
            }

            @Override
            public void onDataError(JSONObject json) {
                listener.onDataDone(false);
            }
        });
        network.sendRequest(context, Network.RequestName.GET_LOGGED_MANAGER, "");
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

    public interface RequestAllOrdersCallBack {
        void onDataDone(AllOrdersResponse response);
    }

    public interface RequestFolderItemsCallBack {
        void onDataDone(FolderItemsResponse response);
    }

    public interface RequestToppingItemsCallBack {
        void onDataDone(ToppingsListResponse response);
    }

    public interface RequestJsonCallBack {
        void onDataDone(JSONObject jsonObject);
    }

}
