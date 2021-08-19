package com.pos.bringit.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.ClocksSendModel;
import com.pos.bringit.models.FinanceItem;
import com.pos.bringit.models.OrderDetailsModel;
import com.pos.bringit.models.PaymentModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.models.SearchFilterModel;
import com.pos.bringit.models.TransactionItem;
import com.pos.bringit.models.UserDetailsModel;
import com.pos.bringit.models.response.AllOrdersResponse;
import com.pos.bringit.models.response.BusinessItemsListResponse;
import com.pos.bringit.models.response.CreateOrderResponse;
import com.pos.bringit.models.response.FinanceSessionResponse;
import com.pos.bringit.models.response.FinanceSessionsResponse;
import com.pos.bringit.models.response.FolderItemsResponse;
import com.pos.bringit.models.response.InvoiceResponse;
import com.pos.bringit.models.response.OrderDetailsResponse;
import com.pos.bringit.models.response.ProductItemResponse;
import com.pos.bringit.models.response.SearchByFiltersResponse;
import com.pos.bringit.models.response.SearchCitiesResponse;
import com.pos.bringit.models.response.SearchStreetsResponse;
import com.pos.bringit.models.response.UserDetailsResponse;
import com.pos.bringit.models.response.WorkerClocksResponse;
import com.pos.bringit.models.response.WorkerResponse;
import com.pos.bringit.models.response.WorkingAreaResponse;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.SharedPrefs;
import com.pos.bringit.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DRINK;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_SPECIAL;
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
                    Gson gson = new Gson();
                    BusinessModel businessModel = gson.fromJson(json.getJSONObject("message").toString(), BusinessModel.class);
                    BusinessModel.getInstance().initData(businessModel);

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
//                openAlertMsg(context, json);
                listener.onDataDone(false);

            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.LOG_IN_MANAGER);
    }

    public void settingsLogin(final Context context, String password, final RequestWorkerCallBack listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Gson gson = new Gson();
                WorkerResponse response = gson.fromJson(json.toString(), WorkerResponse.class);
                listener.onDataDone(response);

                Log.d("settingsLogin", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("settingsLogin error", json.toString());
//                openAlertMsg(context, json);
                listener.onDataDone(new WorkerResponse());
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.WORKER_LOGIN);
    }

    public void otherWorkerLogin(final Context context, String password, final RequestWorkerCallBack listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            jsonObject.put("isOtherWorker", true);
            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Gson gson = new Gson();
                WorkerResponse response = gson.fromJson(json.toString(), WorkerResponse.class);
                listener.onDataDone(response);
                Log.d("otherWorkerLogin", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("otherWorkerLogin error", json.toString());
                listener.onDataDone(new WorkerResponse());
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.WORKER_LOGIN);
    }

    public void saveUserInfoWithNotes(final Context context, UserDetailsModel model, final RequestCallBackSuccess listener) {
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(model));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("user details params", jsonObject.toString());

        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);
                Log.d("user details", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("user details error", json.toString());
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.SAVE_USER_INFO_WITH_NOTES);
    }

    public void getUserByPhone(final Context context, String phone, final RequestUserDetailsCallBack listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phone);
            Log.d("send data: ", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Gson gson = new Gson();
                UserDetailsResponse response = gson.fromJson(json.toString(), UserDetailsResponse.class);
                listener.onDataDone(response);
                Log.d("user details", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("user details error", json.toString());
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.GET_CLIENT_BY_PHONE);
    }

    public void getWorkerClocksByID(Context context, String workerId, String interval, RequestWorkerClocksCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getWorkerClocksByID", json.toString());
                Gson gson = new Gson();
                WorkerClocksResponse response = gson.fromJson(json.toString(), WorkerClocksResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("workerClocksByID error", json.toString());

            }
        });
        network.sendRequest(context, Network.RequestName.GET_WORKER_CLOCKS_BY_ID, workerId + "&interval=" + interval);
    }

    public void startOrEndWorkerClockByID(Context context, String workerId, boolean isEnd, RequestCallBackSuccess listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("startEndWorkerClockByID", json.toString());
                listener.onDataDone(true);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("startEndClock error", json.toString());
                listener.onDataDone(false);

            }
        });
        network.sendRequest(context, isEnd ? Network.RequestName.END_WORKER_CLOCK : Network.RequestName.START_WORKER_CLOCK, workerId);
    }

    public void editWorkerClock(Context context, ClocksSendModel model, RequestCallBackSuccess listener) {

        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(model));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("clocks params", jsonObject.toString());


        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("editWorkerClock", json.toString());
                listener.onDataDone(true);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("editWorkerClock error", json.toString());
                openAlertMsg(context, json);
                listener.onDataDone(false);

            }
        });
        network.sendPostRequest(context, jsonObject, model.getTimeId() == null
                ? Network.RequestName.ADD_NEW_WORKERS_CLOCK : Network.RequestName.EDIT_WORKERS_CLOCK);
    }

    public void getLastFinanceSessions(Context context, RequestFinanceSessionsCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("lastFinSession", json.toString());
                Gson gson = new Gson();
                FinanceSessionsResponse response = gson.fromJson(json.toString(), FinanceSessionsResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.d("lastFinSession Err", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.GET_LAST_FINANCE_SESSIONS, null, true);
    }

    public void openFinanceSession(Context context, FinanceItem financeItem, RequestFinanceSessionCallBack listener) {
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(financeItem));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("validate params: ", jsonObject.toString());

        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("open session", json.toString());
                Gson gson = new Gson();
                FinanceSessionResponse response = gson.fromJson(json.toString(), FinanceSessionResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.d("open session Err", json.toString());
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.OPEN_FINANCE_SESSION, true);
    }

    public void closeFinanceSession(Context context, FinanceItem financeItem, RequestFinanceSessionCallBack listener) {
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(financeItem));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("validate params: ", jsonObject.toString());

        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("close session", json.toString());
                Gson gson = new Gson();
                FinanceSessionResponse response = gson.fromJson(json.toString(), FinanceSessionResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.d("close session Err", json.toString());
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.CLOSE_FINANCE_SESSION, true);
    }

    public void createFinanceTransaction(Context context, TransactionItem transactionItem, RequestCallBackSuccess listener) {
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(transactionItem));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("validate params: ", jsonObject.toString());

        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("create tr", json.toString());
                try {
                    listener.onDataDone(json.getBoolean("status"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.d("create tr", json.toString());
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.CREATE_FINANCE_TRANSACTION, true);
    }

    public void checkBusinessStatus(Context context, RequestCallBackSuccess requestCallBackSuccess) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("checkBusinessStatus ", json.toString());
                try {
                    requestCallBackSuccess.onDataDone(json.getBoolean("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.d("getLoggedManager Err", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.CHECK_BUSINESS_STATUS, null);
    }

    public void changeBusinessStatus(final Context context, boolean isOpen, final RequestCallBackSuccess listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("business_id", BusinessModel.getInstance().getBusiness_id());
            jsonObject.put("status", isOpen ? "open" : "close");

            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);

                Log.d("changeBusinessStatus", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("changeBStatus error", json.toString());
//                openAlertMsg(context, json);
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.CHANGE_BUSINESS_STATUS);
    }

    public void editColor(final Context context, String color, String orderId, final RequestCallBackSuccess listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("business_id", BusinessModel.getInstance().getBusiness_id());
            jsonObject.put("order_id", orderId);
            jsonObject.put("color", color);

            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);

                Log.d("editColor", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("editColor error", json.toString());
//                openAlertMsg(context, json);
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.EDIT_COLOR, true);
    }

    public void openCloseTable(final Context context, String tableId, boolean isClosed, final RequestCallBackSuccess listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("business_id", BusinessModel.getInstance().getBusiness_id());
            jsonObject.put("table_id", tableId);
            jsonObject.put("is_closed", isClosed ? "1" : "0");

            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);

                Log.d("openCloseTable", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("openCloseTable error", json.toString());
//                openAlertMsg(context, json);
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.OPEN_CLOSE_TABLE, true);
    }

    public void getAllOrders(final Context context, final RequestAllOrdersCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                if (json == null) {
                    listener.onDataDone(new AllOrdersResponse());
                } else {
                    Log.d("getAllOrders", json.toString());
                    Gson gson = new Gson();
                    AllOrdersResponse response = gson.fromJson(json.toString(), AllOrdersResponse.class);
                    listener.onDataDone(response);
                }
            }

            @Override
            public void onDataError(JSONObject json) {
                AllOrdersResponse allOrdersResponse = new AllOrdersResponse();
                allOrdersResponse.setOrders(null);
                listener.onDataDone(allOrdersResponse);
            }
        });
        network.sendRequest(context, Network.RequestName.GET_ALL_ORDERS, "2", true);
    }

    public void getOrderDetailsByID(Context context, String orderId, RequestProductsCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getOrderDetailsByID", json.toString());
                Gson gson = new Gson();
                OrderDetailsModel response = null;
                try {
                    response = gson.fromJson(json.getString("order"), OrderDetailsModel.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                listener.onDataDone(null);
                Log.e("getOrderByID error", json.toString());

            }
        });
        network.sendRequest(context, Network.RequestName.GET_ORDER_DETAILS_BY_ID, orderId/*+"?group_toppings=1"*/, true);
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
        network.sendRequest(context, Network.RequestName.GET_ITEMS_IN_SELECTED_FOLDER, folderNumber, true);
    }

    public void getWorkingArea(final Context context, final RequestWorkingAreaCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getWorkingArea", json.toString());
                Gson gson = new Gson();
                WorkingAreaResponse response = gson.fromJson(json.toString(), WorkingAreaResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("getWorkingArea error", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.GET_WORKING_AREA, "");
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
                Log.e("getToppings", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.LOAD_PRODUCTS, BUSINESS_ITEMS_TYPE_TOPPING);
    }

    public void getSpecials(Context context, RequestBusinessItemsCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getSpecials", json.toString());
                Gson gson = new Gson();
                BusinessItemsListResponse response = gson.fromJson(json.toString(), BusinessItemsListResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("getSpecials", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.LOAD_BUSINES_ITEMS, BUSINESS_ITEMS_TYPE_SPECIAL);
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
                Log.e("getDrinks", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.LOAD_PRODUCTS, BUSINESS_ITEMS_TYPE_DRINK, true);
    }

    public void getOneProduct(Context context, String type, String productId, RequestProductItemCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("getOneProduct", json.toString());
                Gson gson = new Gson();
                ProductItemResponse response = gson.fromJson(json.toString(), ProductItemResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("getOneProduct error", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.LOAD_ONE_PRODUCT, type + "/" + productId, true);
    }

    public void searchCities(Context context, String query, RequestSearchCitiesCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("searchCities", json.toString());
                Gson gson = new Gson();
                SearchCitiesResponse response = gson.fromJson(json.toString(), SearchCitiesResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("searchCities error", json.toString());
                listener.onDataDone(new SearchCitiesResponse());
            }
        });
        network.sendRequest(context, Network.RequestName.SEARCH_CITIES, query);
    }

    public void searchStreets(Context context, String query, String cityId, RequestSearchStreetsCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("searchStreets", json.toString());
                Gson gson = new Gson();
                SearchStreetsResponse response = gson.fromJson(json.toString(), SearchStreetsResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("searchStreets error", json.toString());
                listener.onDataDone(new SearchStreetsResponse());
            }
        });
        network.sendRequest(context, Network.RequestName.SEARCH_STREETS, query + "&city_id=" + cityId);
    }

    public void searchProducts(Context context, String query, final RequestFolderItemsCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("searchProducts", json.toString());
                Gson gson = new Gson();
                FolderItemsResponse response = gson.fromJson(json.toString(), FolderItemsResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("searchProducts error", json.toString());
                try {
                    Toast.makeText(context, json.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        network.sendRequest(context, Network.RequestName.SEARCH_PRODUCTS, query, true);
    }

    public void searchByFilters(final Context context, SearchFilterModel filters, final RequestSearchByFiltersCallBack listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("business_id", BusinessModel.getInstance().getBusiness_id());
            jsonObject.put("page", 1);
            Gson gson = new Gson();
            jsonObject.put("filters", new JSONObject(gson.toJson(filters)));

            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {

                Log.d("search orders", json.toString());
                Gson gson = new Gson();
                SearchByFiltersResponse response = gson.fromJson(json.toString(), SearchByFiltersResponse.class);
                listener.onDataDone(response);
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("searchOrders error", json.toString());
//                try {
//                    Toast.makeText(context, json.getString("message"), Toast.LENGTH_SHORT).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.SEARCH_BY_FILTERS, true);
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

    public void validateCartItems(final Context context, List<ProductItemModel> cartItems, final RequestCallBackSuccess listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            Gson gson = new Gson();
            JSONArray cart = new JSONArray(gson.toJson(cartItems));
            jsonObject.put("cart", cart);

            Log.d("validate params: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {

                Log.d("validate params", json.toString());
                listener.onDataDone(true);
            }

            @Override
            public void onDataError(JSONObject json) {
                listener.onDataDone(false);
                Log.d("validate params", json.toString());

            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.VALIDATE_ORDER, true);
    }


    public void completeCart(final Context context, JSONObject params, final RequestCreateOrderCallBack listener) {

        Log.d("CompleteCart params", params.toString());

        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {

                Log.d("make order", json.toString());
                Gson gson = new Gson();
                CreateOrderResponse response = gson.fromJson(json.toString(), CreateOrderResponse.class);
                listener.onDataDone(response);
                Log.d("CompleteCart", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                if (json.toString().equals("{}")) {
                    listener.onDataDone(null);
                } else {
                    listener.onDataDone(new CreateOrderResponse());
                }

                Log.d("CompleteCart error", json.toString());
                FirebaseCrashlytics.getInstance().log("CompleteCart error: " + json.toString());
                FirebaseCrashlytics.getInstance().log("Sent params: " + params.toString());
            }
        });
        network.sendPostRequest(context, params, Network.RequestName.MAKE_ORDER, true);
    }

    public void editCart(final Context context, JSONObject params, final RequestCreateOrderCallBack listener) {

        Log.d("EditCart params", params.toString());

        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Gson gson = new Gson();
                CreateOrderResponse response = gson.fromJson(json.toString(), CreateOrderResponse.class);
                listener.onDataDone(response);
                Log.d("EditCart", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                listener.onDataDone(new CreateOrderResponse());
                Log.d("CompleteCart error", json.toString());
            }
        });
        network.sendPostRequest(context, params, Network.RequestName.EDIT_ORDER_ITEMS, true);
    }

    public void completeOrder(final Context context, String orderId, final RequestCallBackSuccess listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject attributes = new JSONObject();
            attributes.put("status", "finished");

            jsonObject.put("attributes", attributes);
            jsonObject.put("order_id", orderId);

            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);

                Log.d("completeOrder", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("completeOrder error", json.toString());
                listener.onDataDone(false);
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.COMPLETE_ORDER, true);
    }

    public void cancelOrder(Context context, String orderId, final RequestCallBackSuccess listener) {
//        JSONObject params = new JSONObject();
//        try {
//            params.put("business_id", BusinessModel.getInstance().getBusiness_id());
//            params.put("order_id", orderId);
//
//            Log.d("cancel params", params.toString());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);
                Log.d("cancelOrder", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.d("cancelOrder error", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.CANCEL_ORDER, orderId, true);
    }

    public void createNewPayment(final Context context, String orderId, List<PaymentModel> payments, final RequestCallBackSuccess listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("order_id", orderId);
            Gson gson = new Gson();
            jsonObject.put("payments", new JSONArray(gson.toJson(payments)));

            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);

                Log.d("new payment", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("new payment error", json.toString());
                listener.onDataDone(false);
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.CREATE_NEW_PAYMENT, true);
    }

    public void assignToDeliveryMan(final Context context, String orderId, boolean isDeliveryMan, final RequestCallBackSuccess listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("order_id", orderId);
            jsonObject.put("pay_to_delivery_man", isDeliveryMan ? 1 : 0);

            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);

                Log.d("to delivery man", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("to delivery man error", json.toString());
                listener.onDataDone(false);
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.PAY_TO_DELIVERY_MAN, true);
    }

    public void getReceiptByPaymentId(final Context context, String paymentId, final RequestInvoiceCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Gson gson = new Gson();
                InvoiceResponse response = gson.fromJson(json.toString(), InvoiceResponse.class);
                listener.onDataDone(response);

                Log.d("new receipt", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("new receipt error", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.GET_RECEIPT_BY_PAYMENT_ID, paymentId, true);
    }

    public void cancelReceiptByPaymentId(final Context context, String paymentId, final RequestCallBackSuccess listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);

                Log.d("cancel receipt", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("cancel receipt error", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.CANCEL_RECEIPT_BY_PAYMENT_ID, paymentId, true);
    }

    public void markAsPrinted(final Context context, String paymentId, final RequestCallBackSuccess listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("payment_id", paymentId);
            jsonObject.put("is_printed", 1);

            Log.d("send data: ", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                listener.onDataDone(true);

                Log.d("markAsPaid", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("markAsPaid error", json.toString());
                listener.onDataDone(false);
            }
        });
        network.sendPostRequest(context, jsonObject, Network.RequestName.MARK_AS_PRINTED, true);
    }

    public void getInvoiceByOrderId(final Context context, String orderId, final RequestInvoiceCallBack listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                try {

                    Gson gson = new Gson();
                    InvoiceResponse response = gson.fromJson(json.toString(), InvoiceResponse.class);
                    listener.onDataDone(response);

                    Log.d("new invoice", json.toString());
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onDataError(JSONObject json) {
                Log.e("new invoice error", json.toString());
            }
        });
        network.sendRequest(context, Network.RequestName.GET_INVOICE_BY_ORDER_ID, orderId, true);
    }

    public void checkToken(Context context, final RequestCallBackSuccess listener) {
        Network network = new Network(new Network.NetworkCallBack() {
            @Override
            public void onDataDone(JSONObject json) {
                Log.d("checkToken", json.toString());
                try {
                    Gson gson = new Gson();
                    BusinessModel businessModel = gson.fromJson(json.getJSONObject("user").toString(), BusinessModel.class);
                    BusinessModel.getInstance().initData(businessModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onDataDone(true);
                Log.d("checkToken", json.toString());
            }

            @Override
            public void onDataError(JSONObject json) {
                listener.onDataDone(false);
            }
        });
        network.sendRequest(context, Network.RequestName.GET_LOGGED_MANAGER, "");
    }

    public void payWithEMV(Context context, String amount, String xField, final RequestConvertedXmlCallBack listener) {
        Network network = new Network(null);

        SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
        String requestId = s.format(Calendar.getInstance().getTime());

        String body = "<Request>" +
                " <Command>001</Command>" +
                "  <TerminalId>" + BusinessModel.getInstance().getEmv_terminal_id() + "</TerminalId>" +
                "  <TimeoutInSeconds>60</TimeoutInSeconds>" +
                "  <Mti>100</Mti>" +
                "  <TranType>1</TranType>" +
                "  <Amount>" + amount + "</Amount>" +
                "  <Currency>376</Currency>" +
                "  <TermNo>" + BusinessModel.getInstance().getEmv_terminal_number() + "</TermNo>" +
                "  <PanEntryMode>PinPad</PanEntryMode>" +
                "  <XField>" + xField + "</XField>" +
                "  <CreditTerms>1</CreditTerms>" +
                "  <RequestId>" + requestId + "</RequestId>" +
                "  <ParameterJ>4</ParameterJ>" +
                "  </Request>";
        int bodyLength = body.length();
        String bodyLengthHex = Integer.toHexString(bodyLength);

        String params = "^PTL!00#0" + bodyLengthHex + "5202" + body;

        network.sendXMLRequest(context, params, xml -> {
            listener.onDataDone(parseXml(xml));
        });
    }

    private HashMap<String, String> parseXml(String xml) {
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            HashMap<String, String> parsedXml = new HashMap<>();
            String key = "";
            String value = "";

            xpp.setInput(new StringReader(xml)); // pass input whatever xml you have
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("XML", "Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    key = xpp.getName();
                    Log.d("XML", "Start tag " + xpp.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    Log.d("XML", "End tag " + xpp.getName());
                } else if (eventType == XmlPullParser.TEXT) {
                    value = xpp.getText();
                    Log.d("XML", "Text " + xpp.getText()); // here you get the text from xml
                }

                parsedXml.put(key, value);
                eventType = xpp.next();
            }
            Log.d("XML", "End document");
            return parsedXml;

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public interface RequestFinanceSessionsCallBack {
        void onDataDone(FinanceSessionsResponse response);
    }

    public interface RequestFinanceSessionCallBack {
        void onDataDone(FinanceSessionResponse response);
    }

    public interface RequestCreateOrderCallBack {
        void onDataDone(CreateOrderResponse response);
    }

    public interface RequestAllOrdersCallBack {
        void onDataDone(AllOrdersResponse response);
    }

    public interface RequestProductsCallBack {
        void onDataDone(OrderDetailsModel response);
    }

    public interface RequestOrderDetailsCallBack {
        void onDataDone(OrderDetailsResponse response);
    }

    public interface RequestUserDetailsCallBack {
        void onDataDone(UserDetailsResponse response);
    }

    public interface RequestFolderItemsCallBack {
        void onDataDone(FolderItemsResponse response);
    }

    public interface RequestBusinessItemsCallBack {
        void onDataDone(BusinessItemsListResponse response);
    }

    public interface RequestProductItemCallBack {
        void onDataDone(ProductItemResponse response);
    }

    public interface RequestSearchCitiesCallBack {
        void onDataDone(SearchCitiesResponse response);
    }

    public interface RequestSearchStreetsCallBack {
        void onDataDone(SearchStreetsResponse response);
    }

    public interface RequestSearchByFiltersCallBack {
        void onDataDone(SearchByFiltersResponse response);
    }

    public interface RequestWorkingAreaCallBack {
        void onDataDone(WorkingAreaResponse response);
    }

    public interface RequestWorkerCallBack {
        void onDataDone(WorkerResponse response);
    }

    public interface RequestWorkerClocksCallBack {
        void onDataDone(WorkerClocksResponse response);
    }

    public interface RequestInvoiceCallBack {
        void onDataDone(InvoiceResponse response);
    }

    public interface RequestJsonCallBack {
        void onDataDone(JSONObject jsonObject);
    }

    public interface RequestConvertedXmlCallBack {
        void onDataDone(HashMap<String, String> convertedXml);
    }

}
