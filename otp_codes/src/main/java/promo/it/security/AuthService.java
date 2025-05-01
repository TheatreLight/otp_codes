package promo.it.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import promo.it.dao.DaoManager;
import promo.it.enums.Roles;
import promo.it.model.User;
import promo.it.settings.SettingsManager;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    DaoManager daoManager;
    private String secretKey;
    private int expirationTime;
    private final String authHeaderPrefix = "Bearer ";

    public AuthService(DaoManager dm, SettingsManager sm) {
        daoManager = dm;
        secretKey = sm.getSecretKey();
        expirationTime = sm.getExpirationTime();
    }

    public Map<String, String> register(User user) {
        var daoUsers = daoManager.getDaoUsers();
        Map<String, String> result = new HashMap<>();

        try {
            if (daoUsers.isExist(user.getLogin())) {
                result.put("message", "Already has user with the same name.");
                return result;
            }

            if (user.getRole() == Roles.ADMIN && daoUsers.isAdminAlreadyRegistered()) {
                result.put("message", "Only one admin is allow to register");
                return result;
            }

            if (daoUsers.insert(user.getRole().ordinal(), user.getLogin(), user.getPwd()) > 0) {
                result.put("message", "Successfully registered!");
                result.put("login", user.getLogin());
                result.put("role", user.getRole().name());
            } else {
                result.put("message", "Can't register user, there are some issues with DB.");
            }
            return result;
        } catch(SQLException e) {
            throw new RuntimeException("Can't register new user: " + e.getMessage());
        }
    }

    public Map<String, String> login(String login, String passwd) {
        var daoUsers = daoManager.getDaoUsers();
        Map<String, String> result = new HashMap<>();
        try {
            if (!daoUsers.isExist(login)) {
                result.put("message", "User '" + login + "' doesn't exist.");
                return result;
            }

            User user = (User)daoUsers.getEntityByName(login);
            if (user == null) {
                result.put("message", "Internal server error");
                return result;
            }

            if (passwd.compareTo(user.getPwd()) != 0) {
                result.put("message", "Wrong password");
                return result;
            }

            SecretKey secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder()
                    .setSubject(String.valueOf(user.getId()))
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(expirationTime))))
                    .signWith(secret)
                    .compact();
            result.put("token", jwt);
            result.put("role", user.getRole().name());
            result.put("login", login);
            result.put("message", "Successfully logged in.");
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    public boolean authenticate(String authHeaders) {
        if (authHeaders == null) {
            return false;
        }

        if (!authHeaders.startsWith(authHeaderPrefix)) {
            return false;
        }

        String jwt = authHeaders.substring(authHeaderPrefix.length());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        var userId = Integer.parseInt(claims.getSubject());
        try {
            User user = (User) daoManager.getDaoUsers().getEntityByID(userId);
            return user.getRole() == Roles.ADMIN;
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }
}
