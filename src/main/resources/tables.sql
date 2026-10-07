CREATE TABLE Users(
    Userid INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    UserName TEXT NOT NULL UNIQUE,
    UserPassword TEXT NOT NULL,
    Municipality TEXT
);

CREATE TABLE WaterData(
    Activity TEXT PRIMARY KEY,
    Duration INT NOT NULL,
    Consumption INT NOT NULL
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

