package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartModel implements Parcelable, Cloneable {
    private String cart_id;
    private String object_id;
    private String object_type;
    private String father_id;
    private String toppingLocation;
    private double price;
    private List<CartFillingModel> item_filling;
    private String changeType = "";
    private String folder_id;

    private transient String id;
    private transient int position;
    private transient String name;
    private transient List<CartModel> toppings = new ArrayList<>();
    private transient List<CartModel> dealItems = new ArrayList<>();

    private transient List<CategoryModel> categoryItems = new ArrayList<>();
    private transient List<DealItemModel> dealChooseItems = new ArrayList<>();
    private transient List<ProductItemModel> productChooseItems = new ArrayList<>();

    private transient boolean selected;
    private transient String pizzaType = "circle";
    private transient int oneSliceToppingPrice;

    private String category = "";

    //empty
    public CartModel() {
    }

    //general
    public CartModel(String id, int position, String object_type, String name, double price, String object_id) {
        this.id = id;
        this.cart_id = "item_" + position;
        this.position = position;
        this.object_type = object_type;
        this.name = name;
        this.price = price;
        this.object_id = object_id;
    }

    //topping
    public CartModel(String id, int position, String object_type, String name, double price, String object_id, String father_id, String toppingLocation) {
        this.id = id;
        this.cart_id = "item_" + position;
        this.position = position;
        this.object_type = object_type;
        this.name = name;
        this.price = price;
        this.object_id = object_id;
        this.father_id = father_id;
        this.toppingLocation = toppingLocation;
    }

    //deal
    public CartModel(String id, int position, String object_type, String name, double price, String object_id, String father_id) {
        this.id = id;
        this.cart_id = "item_" + position;
        this.position = position;
        this.object_type = object_type;
        this.name = name;
        this.price = price;
        this.object_id = object_id;
        this.father_id = father_id;
    }

    //remove topping
    public CartModel(String object_id, String toppingLocation) {
        this.object_id = object_id;
        this.toppingLocation = toppingLocation;
    }

    protected CartModel(Parcel in) {
        cart_id = in.readString();
        object_id = in.readString();
        object_type = in.readString();
        father_id = in.readString();
        toppingLocation = in.readString();
        price = in.readDouble();
        item_filling = in.createTypedArrayList(CartFillingModel.CREATOR);
        changeType = in.readString();
        folder_id = in.readString();
        id = in.readString();
        position = in.readInt();
        name = in.readString();
        toppings = in.createTypedArrayList(CartModel.CREATOR);
        dealItems = in.createTypedArrayList(CartModel.CREATOR);
        categoryItems = in.createTypedArrayList(CategoryModel.CREATOR);
        dealChooseItems = in.createTypedArrayList(DealItemModel.CREATOR);
        productChooseItems = in.createTypedArrayList(ProductItemModel.CREATOR);
        selected = in.readByte() != 0;
        pizzaType = in.readString();
        oneSliceToppingPrice = in.readInt();
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cart_id);
        dest.writeString(object_id);
        dest.writeString(object_type);
        dest.writeString(father_id);
        dest.writeString(toppingLocation);
        dest.writeDouble(price);
        dest.writeTypedList(item_filling);
        dest.writeString(changeType);
        dest.writeString(folder_id);
        dest.writeString(id);
        dest.writeInt(position);
        dest.writeString(name);
        dest.writeTypedList(toppings);
        dest.writeTypedList(dealItems);
        dest.writeTypedList(categoryItems);
        dest.writeTypedList(dealChooseItems);
        dest.writeTypedList(productChooseItems);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeString(pizzaType);
        dest.writeInt(oneSliceToppingPrice);
        dest.writeString(category);
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

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCartId() {
        return cart_id;
    }

    public void setCartId(int position) {
        this.cart_id = "item_" + position;
    }

    public List<CartFillingModel> getItem_filling() {
        return item_filling;
    }

    public void setItem_filling(List<CartFillingModel> item_filling) {
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
        setCartId(position);
    }

    public List<CartModel> getDealItems() {
        return dealItems;
    }

    public void setDealItems(List<CartModel> dealItems) {
        this.dealItems = dealItems;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType == null ? "" : changeType;
    }

    public String getFolderId() {
        return folder_id;
    }

    public void setFolderId(String folderId) {
        this.folder_id = folderId;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public int getOneSliceToppingPrice() {
        return oneSliceToppingPrice;
    }

    public void setOneSliceToppingPrice(int oneSliceToppingPrice) {
        this.oneSliceToppingPrice = oneSliceToppingPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<CategoryModel> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(List<CategoryModel> categoryItems) {
        this.categoryItems = categoryItems;
    }

    public CartModel clone() {
        CartModel newModel = null;
        try {
            newModel = (CartModel) super.clone();

            if (this.item_filling != null) {
                newModel.item_filling = new ArrayList<>();
                for (CartFillingModel temp : this.item_filling) {
                    newModel.item_filling.add(temp.clone());
                }
            }

            newModel.toppings = new ArrayList<>();
            for (CartModel temp : this.toppings) {
                newModel.toppings.add(temp.clone());
            }
            newModel.dealItems = new ArrayList<>();
            for (CartModel temp : this.dealItems) {
                newModel.dealItems.add(temp.clone());
            }

            newModel.categoryItems = new ArrayList<>();
            for (CategoryModel temp : this.categoryItems) {
                newModel.categoryItems.add(temp.clone());
            }

            newModel.dealChooseItems = new ArrayList<>();
            for (DealItemModel temp : this.dealChooseItems) {
                newModel.dealChooseItems.add(temp.clone());
            }

            newModel.productChooseItems = new ArrayList<>();
            for (ProductItemModel temp : this.productChooseItems) {
                newModel.productChooseItems.add(temp.clone());
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return newModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartModel cartModel = (CartModel) o;
        return Objects.equals(object_id, cartModel.object_id) &&
                Objects.equals(toppingLocation, cartModel.toppingLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object_id, toppingLocation);
    }

    public List<DealItemModel> getDealChooseItems() {
        return dealChooseItems;
    }

    public void setDealChooseItems(List<DealItemModel> dealChooseItems) {
        this.dealChooseItems = dealChooseItems;
    }

    public List<ProductItemModel> getProductChooseItems() {
        return productChooseItems;
    }

    public void setProductChooseItems(List<ProductItemModel> productChooseItems) {
        this.productChooseItems = productChooseItems;
    }
}


