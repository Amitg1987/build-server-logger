CREATE TABLE if not exists event (
  id                 VARCHAR(200) PRIMARY KEY,
  event_type         VARCHAR(200),
  host               VARCHAR(200),
  start_ts			 VARCHAR(200),
  end_ts			 VARCHAR(200),
  duration           INTEGER,
  alert              BOOLEAN
);
