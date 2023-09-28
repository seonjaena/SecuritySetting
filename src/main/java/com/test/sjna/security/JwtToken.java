package com.test.sjna.security;

import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtToken {

    private String grantType;
    private String accessToken;
    private String refreshToken;

}
