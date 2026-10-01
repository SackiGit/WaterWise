INSERT INTO Users (UserName, UserPassword) VALUES ('admin', 'admin');
-- Shower consumes 10 l/min
INSERT INTO WaterData VALUES ('Shower', 1, 10);
-- Faucet consumes 3 l/min
INSERT INTO WaterData VALUES ('Faucet', 1, 3);
-- Washing machine, one load consumes 50 l
INSERT INTO WaterData VALUES ('Washing machine',1,50);
-- Washing machine eco program, one load consumes 45 l
INSERT INTO WaterData VALUES ('Washing machine eco program',1,45);
-- Toilet, a big or regular flush consumes 6 l
INSERT INTO WaterData VALUES ('Toilet regular flush',1,6);
-- Toilet, a small flush consumes 3 l
INSERT INTO WaterData VALUES ('Toilet small flush',1,3);
-- Dishwasher, one load consumes 11 l
INSERT INTO WaterData VALUES ('Dishwasher',1,11);
-- Dishwasher eco program, one load consumes 7 l
INSERT INTO WaterData VALUES ('Dishwasher eco program',1,7);

INSERT INTO ConsMinutes (Userid, Activity, Duration, Datum) VALUES(1,'Shower',5, '2026-08-10');
INSERT INTO ConsMinutes (Userid, Activity, Duration, Datum) VALUES(1,'Faucet',5, '2026-08-10');

INSERT INTO ConsMinutes (Userid, Activity, Duration, Datum) VALUES(1,'Shower',7, '2026-08-10');
INSERT INTO ConsMinutes (Userid, Activity, Duration, Datum) VALUES(1,'Faucet',5, '2026-08-11');


