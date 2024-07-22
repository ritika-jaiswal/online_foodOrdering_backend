package com.ritika.service;

import com.ritika.config.JwtProvider;
import com.ritika.model.User;
import com.ritika.repository.UserRepository;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        // Ensure the JWT string is correctly formatted
        if (jwt == null || !jwt.startsWith("Bearer ") || jwt.split("\\.").length != 3) {
            throw new MalformedJwtException("Invalid JWT token");
        }

        // Extract the email from the JWT
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        // Find the user by the extracted email
        User user = findUserByEmail(email);

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new Exception("User not found");
        }
        return user;
    }
}
