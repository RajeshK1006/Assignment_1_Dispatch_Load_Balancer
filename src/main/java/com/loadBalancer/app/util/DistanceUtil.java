package com.loadBalancer.app.util;

public class DistanceUtil {
    public static final double Earth_Radius_km = 6371.0;

    public static double calculateDistance(double lat1,double lon1,double lat2, double lon2){

        // convert degrees into radians

        double latDistance = Math.toRadians(lat2 - lat1);
        double longDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance/2) * Math.sin(latDistance/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(longDistance/2) * Math.sin(longDistance /2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return Earth_Radius_km * c;
    }
}
