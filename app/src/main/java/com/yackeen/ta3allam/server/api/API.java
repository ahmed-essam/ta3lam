package com.yackeen.ta3allam.server.api;

public class API {

    /**
     * This class holds all the references of all the APIs
     * It acts as a gate for all the categories (user, notifications, messages etc..)
     *
     * All the methods are static and return singleton references to the related API
     * */
    public static UserAPI getUserAPIs (){
        return UserAPI.getInstance();
    }
}
