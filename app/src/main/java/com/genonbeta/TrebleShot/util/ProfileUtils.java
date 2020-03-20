package com.genonbeta.TrebleShot.util;

import com.genonbeta.TrebleShot.model.Customer;

public class ProfileUtils {

    private static Customer customer;

    public void persistProfileDataLocally(){

    }

    public Customer getProfileDataLocally(){
        // call to read and persist details locally
       customer = null;
       return customer;
    }
}
