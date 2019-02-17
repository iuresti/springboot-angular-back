package com.iuresti.learning.backendclients.services.impl;

import org.springframework.stereotype.Service;

import com.iuresti.learning.backendclients.auth.FirebaseParser;
import com.iuresti.learning.backendclients.auth.FirebaseTokenHolder;
import com.iuresti.learning.backendclients.services.FirebaseService;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public FirebaseTokenHolder parseToken(String firebaseToken) {
        return new FirebaseParser().parseToken(firebaseToken);
    }
}
