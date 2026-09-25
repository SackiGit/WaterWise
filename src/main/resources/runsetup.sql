-- psql -f runsetup.sql


-- This script deletes everything in your database
\set QUIET true
SET client_min_messages TO WARNING; -- Less talk please.
-- This script deletes everything in your database
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO CURRENT_USER;
-- This line makes psql stop on the first error it encounters
-- You may want to remove this when running tests that are intended to fail
\set ON_ERROR_STOP ON
SET client_min_messages TO NOTICE; -- More talk
\set QUIET false

\ir tables.sql
\ir inserts.sql
\ir views.sql


 --\ir triggers.sql


--Tests
\echo Daily
SELECT * FROM DailyConsumption
WHERE Userid=1 AND Datum='2026-08-10';
\echo Total
SELECT * FROM TotalConsumption
WHERE Userid=1;
\echo TestTrigger
INSERT INTO Users(UserName,UserPassword) VALUES ('adam', '123');
SELECT * FROM Users;
SELECT * FROM ConsMinutes;