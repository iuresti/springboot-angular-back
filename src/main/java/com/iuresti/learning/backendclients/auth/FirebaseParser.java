package com.iuresti.learning.backendclients.auth;

import org.springframework.util.StringUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.iuresti.learning.backendclients.exception.FirebaseTokenInvalidException;

public class FirebaseParser {

    public FirebaseTokenHolder parseToken(String idToken) {
        if (StringUtils.isEmpty(idToken)) {
            throw new IllegalArgumentException("FirebaseTokenBlank");
        }
        try {
            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);

            return new FirebaseTokenHolder(token);
        } catch (Exception e) {
            throw new FirebaseTokenInvalidException(e.getMessage());
        }
    }
}
