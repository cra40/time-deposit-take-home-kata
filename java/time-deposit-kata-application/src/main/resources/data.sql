insert into timeDeposits(id, planType, balance, days) values(1, 'BASIC', 1000.00, 30);
insert into timeDeposits(id, planType, balance, days) values(2, 'STUDENT', 1000.00, 45);
insert into timeDeposits(id, planType, balance, days) values(3, 'PREMIUM', 1000.00, 30);

insert into withdrawals(id, timeDepositId, amount, date) values(1, 1, 100.00, '2020-01-01');
insert into withdrawals(id, timeDepositId, amount, date) values(2, 1, 100.00, '2020-01-02');
