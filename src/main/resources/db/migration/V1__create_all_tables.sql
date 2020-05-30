CREATE TABLE shopping_platforms (
	id            		 BIGSERIAL NOT NULL,
	name          		 VARCHAR(30) NOT NULL UNIQUE,
	website		   		 VARCHAR(150),
	shipping_method      VARCHAR(150)
);
ALTER TABLE shopping_platforms ADD CONSTRAINT shopping_platform_pk PRIMARY KEY (id);
CREATE TABLE consumers (
	id  					BIGSERIAL NOT NULL,
	name 					VARCHAR(50) NOT NULL UNIQUE,
	address					VARCHAR(100),
	phone					VARCHAR(15),
	shopping_platform_id    	BIGINT
);
ALTER TABLE consumers ADD CONSTRAINT consumer_pk PRIMARY KEY (id);
CREATE TABLE orders (
	id                 		BIGSERIAL NOT NULL,
	total_amount			BIGINT,
	payment_method			VARCHAR(50),
	consumer_id  			BIGINT
);
ALTER TABLE orders ADD CONSTRAINT order_pk PRIMARY KEY (id);
ALTER TABLE orders
    ADD CONSTRAINT	order_consumer_fk FOREIGN KEY ( consumer_id )
        REFERENCES consumers ( id );
ALTER TABLE consumers
    ADD CONSTRAINT consumer_shopping_platform_fk FOREIGN KEY ( shopping_platform_id )
        REFERENCES shopping_platforms ( id );