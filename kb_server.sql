create schema kb_server;
use kb_server;
CREATE TABLE customer (
    customer_id INT PRIMARY KEY auto_increment,
    name VARCHAR(10),
    identity_number VARCHAR(14)
);

ALTER TABLE customer
MODIFY identity_number VARCHAR(255);


insert into customer(name, identity_number) values('김필','$2a$10$/xnMpXhmQyeaM4eWbc0ln.dsT/X1.OehLd5ZIKapRv9Cti8BG2yGC');
select * from customer;

SELECT * FROM customer WHERE identity_number = '$2a$10$/xnMpXhmQyeaM4eWbc0ln.dsT/X1.OehLd5ZIKapRv9Cti8BG2yGC';





SELECT
    z.customer_id,
    z.identity_number,
    COALESCE(SUM(a.total_balance), 0) AS total_balance,
    COALESCE(SUM(c.month_amount), 0) AS monthly_amount,
    COALESCE(SUM(i.monthly_premium), 0) AS monthly_premium,
    COALESCE(SUM(l.remaining_balance), 0) AS remaining_balance
FROM
    customer z
    LEFT JOIN account_info a ON z.customer_id = a.customer_id
    LEFT JOIN card_info c ON a.customer_id = c.customer_id
    LEFT JOIN insurance_info i ON a.customer_id = i.customer_id
    LEFT JOIN loan_info l ON a.customer_id = l.customer_id
WHERE
    z.identity_number = '$2a$10$/xnMpXhmQyeaM4eWbc0ln.dsT/X1.OehLd5ZIKapRv9Cti8BG2yGC'
GROUP BY
    z.customer_id, z.identity_number;







-- 카드info
CREATE TABLE card_info (
    card_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    card_type VARCHAR(4) NOT NULL,
    card_name VARCHAR(20),
    month_amount VARCHAR(20),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

insert into card_info(customer_id,card_type, card_name,month_amount) values(1,'체크카드','KB나라사랑체크카드','1136778');
select * from card_info;

-- 대출info
CREATE TABLE loan_info (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL, 
    loan_type VARCHAR(10), 
    loan_amount VARCHAR(50),
    loan_start_date DATE,
    loan_end_date DATE,
    interest_rate VARCHAR(10),
    remaining_balance VARCHAR(15),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);
INSERT INTO loan_info (customer_id,loan_type,loan_amount,loan_start_date,loan_end_date,interest_rate,remaining_balance)
VALUES (1,
    '주택담보대출',     -- 대출 종류: 주택 담보 대출
    '30000000',    -- 대출 금액: 3천만 원
    '2023-01-01',   -- 대출 시작일
    '2033-01-01',   -- 대출 만기일 (10년 만기)
    '3.5%',         -- 이자율: 3.5%
    '20000000'      -- 남은 대출 금액: 2천만 원
);
select * from loan_info;

drop table account_info;
-- 예/적금info
CREATE TABLE account_info (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    account_number VARCHAR(30) NOT NULL,
    account_type VARCHAR(15) NOT NULL,
    account_name VARCHAR(15) NOT NULL,
    total_balance VARCHAR(50) NOT NULL,
    available_balance VARCHAR(50),
    savings_start_date DATE default NULL,
    savings_end_date DATE default NULL,
    savings_interest_rate VARCHAR(10) default NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);
-- 예금 
INSERT INTO account_info (customer_id,account_number,account_type,account_name,total_balance,available_balance)
VALUES (1,
    '943202-00-652896',
    '저축예금',
    'KB나라사랑우대통장',
    '281439',
    '281439'
);

-- 적금
INSERT INTO account_info (customer_id,account_number,account_type,account_name,
total_balance,available_balance,savings_start_date,
savings_end_date,savings_interest_rate)
VALUES (1,
    '564778-00-336786', 
    '정액적립식/자유적립식적금',
    'KB내맘대로적금',
    '10000000',           -- 총 잔액: 10,000,000원 (1천만 원)
    '7000000',            -- 출금 가능 금액: 7,000,000원 (7백만 원)
    '2023-01-01',         -- 저축 시작일: 2023년 1월 1일
    '2028-01-01',         -- 저축 만기일: 2028년 1월 1일
    '2.5%'                -- 저축 이자율: 2.5%
);

select * from account_info;

-- 보험info
CREATE TABLE insurance_info (
    insurance_id INT PRIMARY KEY AUTO_INCREMENT,  -- 보험 고유 ID
    customer_id INT NOT NULL,
    insurance_name VARCHAR(100) NOT NULL,         -- 보험 이름
    subscription_start_date DATE NOT NULL,        -- 가입 년월
    monthly_premium VARCHAR(15) NOT NULL,                 -- 월 납입금액
    subscription_months VARCHAR(10) NOT NULL,            -- 가입 개월 차
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);
INSERT INTO insurance_info (
    customer_id,
    insurance_name,
    subscription_start_date, 
    monthly_premium, 
    subscription_months
)
VALUES 
    (1,'(무)KB Yes!365건강보험', '2019-01-01', 110000, 68),
    (1,'(무)KB손보실손의료비보장보험', '2019-01-01', 30000, 68),
    (1,'(무)The드림치아안심보험', '2018-04-01', 30000, 77);
    
    
select * from insurance_info;
use kb_server;
-- 투자)주식info
CREATE TABLE stocks (
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    stock_symbol VARCHAR(10) NOT NULL,
    stock_name VARCHAR(100) NOT NULL,
    shares_owned INT NOT NULL,
    purchase_price DECIMAL(10, 2) NOT NULL,
    current_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

INSERT INTO stocks (customer_id, stock_symbol, stock_name, shares_owned, purchase_price, current_price)
VALUES (1, 'AAPL', 'Apple Inc.', 10, 150.00, 175.00);


-- 투자) 채권info
CREATE TABLE bonds (
    bond_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    bond_name VARCHAR(100) NOT NULL,
    face_value DECIMAL(10, 2) NOT NULL,
    purchase_price DECIMAL(10, 2) NOT NULL,
    maturity_date DATE NOT NULL,
    current_value DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

INSERT INTO bonds (customer_id, bond_name, face_value, purchase_price, maturity_date, current_value)
VALUES (1, 'US Treasury Bond', 1000.00, 950.00, '2030-12-31', 1050.00);

select * from bonds;

-- 펀드
CREATE TABLE funds (
    fund_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    fund_name VARCHAR(100) NOT NULL,
    units_owned INT NOT NULL,
    purchase_price DECIMAL(10, 2) NOT NULL,
    current_value DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);


INSERT INTO funds (customer_id, fund_name, units_owned, purchase_price, current_value)
VALUES (1, 'Global Equity Fund', 50, 20.00, 25.00);


-- 부동산
CREATE TABLE real_estate (
    property_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    property_address VARCHAR(255) NOT NULL,
    purchase_price DECIMAL(10, 2) NOT NULL,
    current_value DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);


INSERT INTO real_estate (customer_id, property_address, purchase_price, current_value)
VALUES (1, '123 Main St, Anytown, USA', 300000.00, 350000.00);




-- 자산 코드
CREATE TABLE asset_type (
    asset_code VARCHAR(15) PRIMARY KEY,
    asset_name VARCHAR(15)
);

insert into asset_type(asset_code,asset_name) values ('A01','예/적금');
insert into asset_type(asset_code,asset_name) values ('A02','대출');
insert into asset_type(asset_code,asset_name) values ('A03','보험');
insert into asset_type(asset_code,asset_name) values ('A04','카드');


select * from asset_type;


-- 사용자 자산

CREATE TABLE user_asset (
    customer_id INT NOT NULL,
    amount VARCHAR(15),
    asset_code VARCHAR(15) NOT NULL,
    asset_name VARCHAR(15),
    PRIMARY KEY (customer_id, asset_code),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (asset_code) REFERENCES asset_type(asset_code)
);

INSERT INTO user_asset (customer_id, amount, asset_code, asset_name)
SELECT 
    ua.customer_id,
    CASE 
        WHEN ua.asset_code = 'A01' THEN (SELECT SUM(total_balance) FROM account_info WHERE customer_id = ua.customer_id)
        WHEN ua.asset_code = 'A02' THEN (SELECT SUM(remaining_balance) FROM loan_info WHERE customer_id = ua.customer_id)
        WHEN ua.asset_code = 'A03' THEN (SELECT SUM(monthly_premium * subscription_months) FROM insurance_info WHERE customer_id = ua.customer_id)
        WHEN ua.asset_code = 'A04' THEN (SELECT SUM(month_amount) FROM card_info WHERE customer_id = ua.customer_id)
        ELSE 0  -- 기본값
    END AS amount,
    ua.asset_code,
    at.asset_name
FROM (
    SELECT 1 AS customer_id, 'A01' AS asset_code UNION ALL
    SELECT 1 AS customer_id, 'A02' AS asset_code UNION ALL
    SELECT 1 AS customer_id, 'A03' AS asset_code UNION ALL
    SELECT 1 AS customer_id, 'A04' AS asset_code
) AS ua
JOIN asset_type at ON ua.asset_code = at.asset_code;

select * from user_asset;


-- -----------------------------------------------상품
use kb_server;
CREATE TABLE product_type (
    product_id VARCHAR(5) PRIMARY KEY,
    product_category VARCHAR(20) NOT NULL
);
-- product_type 더미 데이터 삽입
INSERT INTO product_type (product_id, product_category) VALUES
('P001', '체크카드'),
('P002', '신용카드'),
('P003', '대출'),
('P004', '보험'),
('P005', '예/적금');



CREATE TABLE check_card_products (
    check_card_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id VARCHAR(5),
    check_card_name VARCHAR(50) NOT NULL,
    check_card_content TEXT,
    check_card_detail_url TEXT,
    check_card_image_url TEXT NOT NULL,
    check_card_category VARCHAR(10) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product_type(product_id)
);


-- check_card_products 더미 데이터 삽입
INSERT INTO check_card_products (product_id, check_card_name, check_card_content, check_card_detail_url, check_card_image_url, check_card_category) VALUES
('P001', 'Standard Check Card', 'Basic features with no annual fee.', 'http://example.com/check_card1', 'http://example.com/images/check_card1.jpg', 'Basic'),
('P001', 'Rewards Check Card', 'Earn rewards on purchases.', 'http://example.com/check_card2', 'http://example.com/images/check_card2.jpg', 'Rewards');

select * from check_card_products;


CREATE TABLE credit_card_products (
    credit_card_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id VARCHAR(5),
    credit_card_name VARCHAR(50) NOT NULL,
    credit_card_content TEXT NOT NULL,
    credit_card_detail_url TEXT NOT NULL,
    credit_card_image_url TEXT NOT NULL,
    credit_card_category VARCHAR(10) NOT NULL,
    credit_card_annual_fee INT NOT NULL,
    credit_card_keyword TEXT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product_type(product_id)
);


-- credit_card_products 더미 데이터 삽입
INSERT INTO credit_card_products (product_id, credit_card_name, credit_card_content, credit_card_detail_url, credit_card_image_url, credit_card_category, credit_card_annual_fee, credit_card_keyword) VALUES
('P002', 'Premium Credit Card', 'Exclusive benefits and higher limits.', 'http://example.com/credit_card1', 'http://example.com/images/credit_card1.jpg', 'Premium', 100000, 'premium'),
('P002', 'Cashback Credit Card', 'Get cashback on every purchase.', 'http://example.com/credit_card2', 'http://example.com/images/credit_card2.jpg', 'Cashback', 50000, 'cashback');


CREATE TABLE loan_products (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id VARCHAR(5),
    loan_name VARCHAR(40) NOT NULL,
    loan_url TEXT NOT NULL,
    loan_content TEXT,
    loan_channel VARCHAR(10) NOT NULL,
    loan_amount TEXT NOT NULL,
    loan_keyword TEXT NOT NULL,
    loan_type VARCHAR(30) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product_type(product_id)
);


-- loan_products 더미 데이터 삽입
INSERT INTO loan_products (product_id, loan_name, loan_url, loan_content, loan_channel, loan_amount, loan_keyword, loan_type) VALUES
('P003', 'Personal Loan', 'http://example.com/loan1', 'Flexible personal loan with low interest rates.', 'Online', '5000000', 'personal', 'unsecured'),
('P003', 'Home Loan', 'http://example.com/loan2', 'Loan for purchasing a home.', 'Branch', '20000000', 'home', 'secured');


CREATE TABLE insurance_products (
    insurance_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id VARCHAR(5),
    insurance_name VARCHAR(40) NOT NULL,
    insurance_url TEXT NOT NULL,
    insurance_keyword TEXT,
    insurance_type VARCHAR(30) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product_type(product_id)
);


-- insurance_products 더미 데이터 삽입
INSERT INTO insurance_products (product_id, insurance_name, insurance_url, insurance_keyword, insurance_type) VALUES
('P004', 'Health Insurance', 'http://example.com/insurance1', 'health', 'medical'),
('P004', 'Life Insurance', 'http://example.com/insurance2', 'life', 'life');

use kb_server;

CREATE TABLE deposit_products (
    deposit_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id VARCHAR(5),
    deposit_name VARCHAR(30) NOT NULL,
    deposit_content TEXT NOT NULL,
    deposit_category VARCHAR(2) NOT NULL,
    deposit_url TEXT NOT NULL,
    deposit_interest_rate TEXT,
    deposit_min_amount INT NOT NULL,
    deposit_online BOOLEAN NOT NULL,
    max_period INT,
    deposit_duration INT,
    deposit_prefer_interest_rate BOOLEAN NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product_type(product_id)
);


-- deposit_products 더미 데이터 삽입
INSERT INTO deposit_products (product_id, deposit_name, deposit_content, deposit_category, deposit_url, deposit_interest_rate, deposit_min_amount, deposit_online, max_period, deposit_duration, deposit_prefer_interest_rate) VALUES
('P005', 'Savings Account', 'High interest savings account.', 'SA', 'http://example.com/deposit1', '2.5%', 100000, TRUE, 12, 0, TRUE),
('P005', 'Fixed Deposit', 'Fixed deposit with higher interest rates.', 'FD', 'http://example.com/deposit2', '3.5%', 500000, FALSE, 24, 0, FALSE);



