package com.allabo.fyl.fyl_server.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class KakaoUserInfoDTO {

    private Long id;
    private KakaoAccount kakao_account;

    @Getter
    @Setter
    public static class KakaoAccount {
        private KakaoProfile profile;

        @Getter
        @Setter
        public static class KakaoProfile {
            private String email;
        }
    }
}
