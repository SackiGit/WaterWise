CREATE TABLE Users(
    Userid INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    UserName TEXT NOT NULL UNIQUE,
    UserPassword TEXT NOT NULL
    --Kanske kommun så man kan få ut all data på en viss kommun.
);

CREATE TABLE WaterData(
    Activity TEXT PRIMARY KEY,
    Duration INT NOT NULL,
    Consumption INT NOT NULL
    --maybe change waterdata depending on how the actual numbers look.
);

CREATE TABLE ConsMinutes(
    LogNr INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Userid INT,
    Activity TEXT NOT NULL,
    Duration INT NOT NULL,
    Datum DATE NOT NULL,
    FOREIGN KEY (Userid) REFERENCES Users(Userid),
    FOREIGN KEY (Activity) REFERENCES WaterData(Activity)
);

