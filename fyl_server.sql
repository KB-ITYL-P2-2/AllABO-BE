create schema fyl_server;
use fyl_server;
drop table customer;
CREATE TABLE customer (
    id VARCHAR(50) PRIMARY KEY,  -- 아이디, 기본 키
    pwd VARCHAR(255) NOT NULL,            -- 비밀번호, NOT NULL
    name VARCHAR(50) NOT NULL,           -- 이름, NOT NULL
    identity_number VARCHAR(255) NOT NULL, -- 주민번호, NOT NULL
    total_income INT,
    sns VARCHAR(20) default 'normal'
);
select * from customer;

CREATE TABLE favorites (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id BIGINT NOT NULL,
                           product_id BIGINT NOT NULL,
                           product_type VARCHAR(255) NOT NULL,  -- 상품 종류 (예: 신용카드, 예금 등)
                           UNIQUE (user_id, product_id, product_type)  -- 중복된 즐겨찾기 방지
);

select * from favorites;

INSERT INTO customer (id, pwd, name, identity_number,total_income)
VALUES
    ('john.doe@example.com', '1111', '김필', '000729-3456789','45000000');
select * from customer;
drop table customer_auth;
CREATE TABLE customer_auth (
    id VARCHAR(50),
    auth VARCHAR(50),
    PRIMARY KEY (id, auth)
);
INSERT INTO customer_auth (id, auth) VALUES ('john.doe@example.com', 'ROLE_ADMIN');
-- INSERT INTO customer_auth (id, auth) VALUES ('user2', 'ROLE_MANAGER');
-- INSERT INTO customer_auth (id, auth) VALUES ('user3', 'ROLE_MEMBER');

select * from customer_auth;


select c.*, a.auth
        from
            customer c left outer join customer_auth a
                                       on c.id = a.id
        where c.id = "john.doe@example.com";



-- 사용자 자산
drop table user_financials;
 CREATE TABLE user_financials (
    user_email VARCHAR(50) PRIMARY KEY,               -- 사용자 이메일, 기본 키
    monthly_income VARCHAR(20) NOT NULL,           -- 월 소득
    total_assets VARCHAR(30) NOT NULL,             -- 총 자산 (저축 + 투자)
    total_savings VARCHAR(30) NOT NULL,            -- 총 저축 금액
    total_investment VARCHAR(30) NOT NULL,         -- 총 투자 금액
    total_expenses VARCHAR(20) NOT NULL,            -- 총 지출 금액
    total_loan VARCHAR(20) NOT NULL
);
INSERT INTO user_financials (
    user_email, 
    monthly_income, 
    total_assets, 
    total_savings, 
    total_investment, 
    total_expenses,
    total_loan
)
VALUES 
    ('john.doe@example.com', '5830000', '42000000', '12000000', '30000000', '2300000','20000000');

select * from user_financials; -- 처음엔없는게맞음


    
drop table user_financials_ratio;
-- 사용자 자산분석 비율
CREATE TABLE user_financials_ratio (
    user_email VARCHAR(50) PRIMARY KEY,                -- 사용자 이메일, 기본 키
    asset_investment_ratio VARCHAR(10) NOT NULL,       -- 자산 대비 투자 비율
    income_investment_ratio VARCHAR(10) NOT NULL,      -- 연 소득 대비 투자 비율
    income_savings_ratio VARCHAR(10) NOT NULL,         -- 연 소득 대비 저축 비율
    income_debt_ratio VARCHAR(10) NOT NULL,            -- 연 소득 대비 부채 비율
    asset_savings_ratio VARCHAR(10) NOT NULL,          -- 자산 대비 저축 비율
    asset_debt_ratio VARCHAR(10) NOT NULL,             -- 자산 대비 부채 비율
    income_expenditure_ratio VARCHAR(10) NOT NULL      -- 월 소득 대비 지출 비율
);
select * from user_financials_ratio; -- 비어있어야 정상




use kb_serve
