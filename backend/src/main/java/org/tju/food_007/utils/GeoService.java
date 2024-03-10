package org.tju.food_007.utils;

/**
 * @author WGY
 * @create 2024-03-10-14:54
 */
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class GeoService {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public double calculateDistanceInKilometer(double lat1, double lon1, double lat2, double lon2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance=EARTH_RADIUS_KM * c;

        return BigDecimal.valueOf(distance)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
}