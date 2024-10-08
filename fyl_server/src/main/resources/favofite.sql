-- 즐겨찾기 테이블 생성
CREATE TABLE favorites (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id BIGINT NOT NULL,
                           product_id BIGINT NOT NULL,
                           product_type VARCHAR(255) NOT NULL,  -- 상품 종류 (예: 신용카드, 예금 등)
                           UNIQUE (user_id, product_id, product_type)  -- 중복된 즐겨찾기 방지
);
