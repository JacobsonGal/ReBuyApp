package com.aviv.rebuy.Model;

import java.util.List;

public class User {

        private List<Product> favorites;
        private List<Product> sell;
        private String id;
        private String name;
        private String phoneNumber;
        private String password;




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

}
