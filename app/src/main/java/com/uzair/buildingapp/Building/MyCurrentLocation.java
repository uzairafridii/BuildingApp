package com.uzair.buildingapp.Building;

public class MyCurrentLocation
{
    private static double currentLat , currentLng;

    public static void setLocation(double lat , double lng)
    {
       currentLat = lat;
       currentLng = lng;
    }

    public static double getCurrentLat()
    {
        return  currentLat;
    }

    public  static  double getCurrentLng()
    {
        return currentLng;
    }

}
