package com.aviv.rebuy.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

        private String id;
        private List<Product> favorites;
        private List<Product> sell;
        private String imageUrl;


        private String name;
        private String phoneNumber;
        private String password;
        private Long lastUpdated;
        private double longitude = 0;
        private double latitude = 0;


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("phone", phoneNumber);
        result.put("imageUrl", imageUrl);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        result.put("latitude", this.latitude);
        result.put("longitude", this.longitude);
        return result;
    }

    public void fromMap(Map<String, Object> map) {
        this.id = (String) map.get("id");
        this.name = (String) map.get("name");
        this.phoneNumber = (String) map.get("phone");
        this.imageUrl = (String) map.get("imageUrl");
        Timestamp ts = (Timestamp) map.get("lastUpdated");
        this.lastUpdated = ts.getSeconds();
        this.latitude = Double.valueOf(map.get("latitude").toString());
        this.longitude = Double.valueOf(map.get("longitude").toString());
    }

    public List<Product> getFavorites() {
        return favorites;
    }

    public List<Product> getSell() {
        return sell;
    }

    public String getPassword() {
        return password;
    }
        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    public void setFavorites(List<Product> favorites) {
        this.favorites = favorites;
    }

    public void setSell(List<Product> sell) {
        this.sell = sell;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
