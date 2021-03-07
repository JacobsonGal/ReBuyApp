package com.aviv.rebuy.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Product {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String description;
    private Double price;
    private String condition;
    private String OwnerId;
    private String imageUrl;
    private Boolean isDeleted=false;
    private Long lastUpdated;


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("description", description);
        result.put("imageUrl", imageUrl);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        result.put("price",price);
        result.put("ownerId", OwnerId);
        result.put("isDeleted", isDeleted);

        return result;
    }



    public void fromMap(Map<String, Object> map){
        id = (String)map.get("id");
        name = (String)map.get("name");
        description = (String)map.get("description");
        imageUrl = (String)map.get("imageUrl");
        price = (Double) map.get("price");
        OwnerId = (String)map.get("ownderId");
        Timestamp ts = (Timestamp)map.get("lastUpdated");
        isDeleted = (Boolean) map.get("isDeleted");
        lastUpdated = ts.getSeconds();
        //long time = ts.toDate().getTime();
    }


    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public String getId() {
        return id;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


}
