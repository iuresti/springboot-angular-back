package com.iuresti.learning.backendclients.services;


import com.iuresti.learning.backendclients.auth.FirebaseTokenHolder;

public interface FirebaseService {

	FirebaseTokenHolder parseToken(String idToken);

}
