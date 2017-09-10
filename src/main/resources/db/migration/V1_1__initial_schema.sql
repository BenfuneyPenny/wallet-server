CREATE TABLE payment_request (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  euro2payment_uri VARCHAR(250) NOT NULL,
  requestor_address VARCHAR(50) NOT NULL,
  receiver VARCHAR(50) NOT NULL,
  payer VARCHAR(50),
  PRIMARY KEY (id)
);
