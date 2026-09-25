INSERT INTO Users (UserName, UserPassword) VALUES ('admin', 'admin');

INSERT INTO WaterData VALUES ('Shower', 1, 10);
INSERT INTO WaterData VALUES ('Faucet', 1, 3);



INSERT INTO ConsMinutes (Userid, Activity, Duration, Datum) VALUES(1,'Shower',5, '2026-08-10');
INSERT INTO ConsMinutes (Userid, Activity, Duration, Datum) VALUES(1,'Faucet',5, '2026-08-10');

INSERT INTO ConsMinutes (Userid, Activity, Duration, Datum) VALUES(1,'Shower',7, '2026-08-10');
INSERT INTO ConsMinutes (Userid, Activity, Duration, Datum) VALUES(1,'Faucet',5, '2026-08-11');


