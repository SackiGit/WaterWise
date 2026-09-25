CREATE VIEW DailyConsumptionByActivity AS 
  SELECT 
  c.Userid,
  c.Datum,
  c.Activity,
  SUM(c.Duration * w.Consumption) AS TotalLitres
    FROM ConsMinutes c JOIN WaterData w
    ON c.Activity = w.Activity
    GROUP BY c.Userid,c.Datum,c.Activity;

CREATE VIEW TotalConsumptionByActivity AS 
  SELECT 
  c.Userid,
  c.Activity,
  SUM(c.Duration * w.Consumption) AS TotalLitres
    FROM ConsMinutes c JOIN WaterData w
    ON c.Activity = w.Activity
    GROUP BY c.Userid,c.Activity;

--Kanske använd ovan två till nästa två

CREATE VIEW DailyConsumption AS 
  SELECT 
  c.Userid,
  c.Datum,
  SUM(c.Duration * w.Consumption) AS TotalLitres
    FROM ConsMinutes c JOIN WaterData w
    ON c.Activity = w.Activity
    GROUP BY c.Userid,c.Datum;

CREATE VIEW TotalConsumption AS 
  SELECT 
  c.Userid,
  SUM(c.Duration * w.Consumption) AS TotalLitres
    FROM ConsMinutes c JOIN WaterData w
    ON c.Activity = w.Activity
    GROUP BY c.Userid;

--Gör en view för total konsumption över alla användare per kommun/totalt