SELECT * FROM fyl_server.user_financials;

alter table user_financials
rename column total_expenses to month_expenses;

alter table user_financials
rename column user_email to id;

SELECT * FROM fyl_server.user_financials_ratio;