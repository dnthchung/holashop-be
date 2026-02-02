package com.chungdt03.holashopbe.services;

import com.chungdt03.holashopbe.models.Token;
import com.chungdt03.holashopbe.models.User;

public interface TokenService {
    Token addTokenEndRefreshToken(User user, String token, boolean isMobile);
}
