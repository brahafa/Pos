package com.pos.bringit.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.response.AllOrdersResponse;
import com.pos.bringit.models.response.BusinessItemsListResponse;
import com.pos.bringit.models.response.FolderItemsResponse;
import com.pos.bringit.models.response.OrderDetailsResponse;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.SharedPrefs;
import com.pos.bringit.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DRINK;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_TOPPING;

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

    public void getOrderDetailsByID(Context context, String orderId, RequestOrderDetailsCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getOrderDetailsByID", json.toString());
                Gson gson = new Gson();
                OrderDetailsResponse response = gson.fromJson(json.toString(), OrderDetailsResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("getOrderByID error", json.toString());

            }
        });
        network.sendRequest(context, Network.RequestName.GET_ORDER_DETAILS_BY_ID, orderId);
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

    public void getToppings(Context context, RequestBusinessItemsCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getToppings", json.toString());
                Gson gson = new Gson();
                BusinessItemsListResponse response = gson.fromJson(json.toString(), BusinessItemsListResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {

            }
        });
        network.sendRequest(context, Network.RequestName.LOAD_BUSINES_ITEMS, BUSINESS_ITEMS_TYPE_TOPPING);
    }

    public void getDrinks(Context context, RequestBusinessItemsCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getDrinks", json.toString());
                Gson gson = new Gson();
                BusinessItemsListResponse response = gson.fromJson(json.toString(), BusinessItemsListResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {

            }
        });
        network.sendRequest(context, Network.RequestName.LOAD_BUSINES_ITEMS, BUSINESS_ITEMS_TYPE_DRINK);
    }

    public void setDeliveryOption(Context context, String option, final RequestCallBackSuccess listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("setDeliveryOption", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("setDeliveryOption error", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.SET_DELIVERY_OPTION, option);
    }

    public void completeCart(final Context context, JSONObject params, final RequestCallBackSuccess listener) {

        Log.d("CompleteCart params", params.toString());

        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);
                Log.d("CompleteCart", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.d("CompleteCart error", json.toString());
            }
        });
        network.sendPostRequest(context, params, Network.RequestName.MAKE_ORDER);
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

    public interface RequestOrderDetailsCallBack {
        void onDataDone(OrderDetailsResponse response);
    }

    public interface RequestFolderItemsCallBack {
        void onDataDone(FolderItemsResponse response);
    }

    public interface RequestBusinessItemsCallBack {
        void onDataDone(BusinessItemsListResponse response);
    }

    public interface RequestJsonCallBack {
        void onDataDone(JSONObject jsonObject);
    }

}
