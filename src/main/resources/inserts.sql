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

INSERT INTO WaterData VALUES ('Other',1,1);


INSERT INTO MunicipalityCoords VALUES('goteborg', 57.79032135129441, 11.98393584590727);
INSERT INTO MunicipalityCoords VALUES('stockholm', 59.33029923888262, 18.063150590951093);
INSERT INTO MunicipalityCoords VALUES('malmo', 55.60448395641192, 13.002580637272198);
INSERT INTO MunicipalityCoords VALUES('umea', 63.824628939715666, 20.26393241301817);

INSERT INTO Recommendations VALUES ('Turn off the faucet while brushing your teeth or shaving.');
INSERT INTO Recommendations VALUES ('Turn off the shower while applying soap or shampoo.');
INSERT INTO Recommendations VALUES ('Use a dishwasher rather than washing dishes by hand when possible. Modern dishwashers are generally very water-efficient, especially when fully loaded.');
INSERT INTO Recommendations VALUES ('Consider replacing older toilets, faucets, showerheads, dishwashers, and washing machines with more water-efficient alternatives when they need replacing.');
INSERT INTO Recommendations VALUES ('Take shorter showers and choose showers instead of baths when possible.');
INSERT INTO Recommendations VALUES ('Collect rainwater in buckets, barrels, or other containers and use it to water plants and your garden.');
INSERT INTO Recommendations VALUES ('Water your garden in the early morning or evening, when evaporation is lower.');
INSERT INTO Recommendations VALUES ('Keep a pitcher of cold water in the refrigerator instead of letting the faucet run while waiting for the water to become cold.');
INSERT INTO Recommendations VALUES ('Fix leaking faucets and toilets as soon as possible. Even small leaks can waste large amounts of water over time.');
INSERT INTO Recommendations VALUES ('Only run the dishwasher when it is full and use the eco program when available.');
INSERT INTO Recommendations VALUES ('Only run the washing machine with full loads when possible, and use eco programs.');
INSERT INTO Recommendations VALUES ('Scrape leftover food into the compost or food-waste bin instead of rinsing it down the drain.');
INSERT INTO Recommendations VALUES ('Avoid creating unnecessary dishes. For example, use the same coffee mug or water glass throughout the day.');
INSERT INTO Recommendations VALUES ('Reduce the amount of water used when showering by installing a water-efficient showerhead. Reducing the flow rate can substantially lower both water and hot-water consumption.');
INSERT INTO Recommendations VALUES ('When washing your car, use a car wash that treats and reuses its water rather than washing the car at home.');


