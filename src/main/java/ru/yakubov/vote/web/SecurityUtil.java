package ru.yakubov.vote.web;

import ru.yakubov.vote.model.AbstractBaseEntity;

public class SecurityUtil {

    private static int id = AbstractBaseEntity.START_SEQ;

    private static int restaurantId;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

    public static int getRestaurantId() {
        return restaurantId;
    }

    public static void setRestaurantId(int restaurantId) {
        SecurityUtil.restaurantId = restaurantId;
    }
}