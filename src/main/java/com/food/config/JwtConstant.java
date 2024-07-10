package com.food.config;

public class JwtConstant {
    public static final String SECRET_KEY = "your-secure-secret-key-with-at-least-32-characters";
    public static final String JWT_HEADER = "Authorization";
    public static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
}
