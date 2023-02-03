package com.tivizado.tivizado.config;

import com.tivizado.tivizado.model.User;
import java.util.Map;

public interface JwtGeneratorInterface {

    Map<String, String> generateToken(User user);
}