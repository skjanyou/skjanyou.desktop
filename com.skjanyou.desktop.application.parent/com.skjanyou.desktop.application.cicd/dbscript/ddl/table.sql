CREATE TABLE parameter ( 
    oprid VARCHAR REFERENCES operation ( oprid ),
    pid   VARCHAR PRIMARY KEY,
    [key] VARCHAR,
    value VARCHAR 
);
CREATE TABLE operation ( 
    oprid  VARCHAR PRIMARY KEY,
    cmd    VARCHAR,
    [desc] VARCHAR 
);
CREATE TABLE computer ( 
    ip       VARCHAR,
    port     NUMERIC,
    hostname VARCHAR,
    username VARCHAR,
    password VARCHAR 
);
CREATE TABLE command ( 
    commandid VARCHAR PRIMARY KEY,
    cmd       VARCHAR,
    [desc]    VARCHAR 
);
