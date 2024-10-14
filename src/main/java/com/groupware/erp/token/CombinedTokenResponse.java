package com.groupware.erp.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinedTokenResponse {
    private String generalToken;
    private JwtTokenDTO jwtTokenDTO;

    @Override
    public String toString() {
        return "CombinedTokenResponse{" +
                "jwtToken=" + jwtTokenDTO +
                ", generalToken='" + generalToken + '\'' +
                '}';
    }
}
