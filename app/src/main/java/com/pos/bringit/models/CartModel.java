package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartModel implements Parcelable {
    private String id;
    private String cart_id;
    private int position;
    private String type;
    private String name;
    private String price;
    private String object_id;
    private String father_id;
    private String toppingLocation;
    private List<CartFilling> item_filling;
    private List<CartModel> toppings = new ArrayList<>();


    public CartModel(String id, int position, String type, String name, String price, String object_id) {
        this.id = id;
        this.cart_id = "item_" + position;
        this.position = position;
        this.type = type;
        this.name = name;
        this.price = price;
        this.object_id = object_id;
    }

    public CartModel(String id, int position, String type, String name, String price, String object_id, String father_id, String toppingLocation) {
        this.id = id;
        this.cart_id = "item_" + position;
        this.position = position;
        this.type = type;
        this.name = name;
        this.price = price;
        this.object_id = object_id;
        this.father_id = father_id;
        this.toppingLocation = toppingLocation;
    }

    public CartModel(String id, String toppingLocation) {
        this.id = id;
        this.toppingLocation = toppingLocation;
    }

    protected CartModel(Parcel in) {
        id = in.readString();
        cart_id = in.readString();
        position = in.readInt();
        type = in.readString();
        name = in.readString();
        price = in.readString();
        object_id = in.readString();
        father_id = in.readString();
        toppingLocation = in.readString();
        item_filling = in.readParcelable(CartFilling.class.getClassLoader());
        toppings = in.createTypedArrayList(CartModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cart_id);
        dest.writeInt(position);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(object_id);
        dest.writeString(father_id);
        dest.writeString(toppingLocation);
        dest.writeTypedList(item_filling);
        dest.writeTypedList(toppings);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CartModel> CREATOR = new Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel in) {
            return new CartModel(in);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjectId() {
        return object_id;
    }

    public void setObjectId(String objectId) {
        this.object_id = objectId;
    }

    public String getFatherId() {
        return father_id;
    }

    public void setFatherId(String fatherId) {
        this.father_id = fatherId;
    }

    public String getToppingLocation() {
        return toppingLocation;
    }

    public void setToppingLocation(String toppingLocation) {
        this.toppingLocation = toppingLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCartId() {
        return cart_id;
    }

    public void setCartId(int position) {
        this.cart_id = "item_" + position;
    }

    public List<CartFilling> getItem_filling() {
        return item_filling;
    }

    public void setItem_filling(List<CartFilling> item_filling) {
        this.item_filling = item_filling;
    }

    public List<CartModel> getToppings() {
        return toppings;
    }

    public void setToppings(List<CartModel> toppings) {
        this.toppings = toppings;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    class CartFilling implements Parcelable {
        private String name;
        private String price;

        protected CartFilling(Parcel in) {
            name = in.readString();
            price = in.readString();
        }

        public final Creator<CartFilling> CREATOR = new Creator<CartFilling>() {
            @Override
            public CartFilling createFromParcel(Parcel in) {
                return new CartFilling(in);
            }

            @Override
            public CartFilling[] newArray(int size) {
                return new CartFilling[size];
            }
        };

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getPrise() {
            return price;
        }

        public void setPrise(String price) {
            this.price = price;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(price);
        }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartModel cartModel = (CartModel) o;
        return Objects.equals(id, cartModel.id) &&
                Objects.equals(toppingLocation, cartModel.toppingLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, toppingLocation);
    }
}
