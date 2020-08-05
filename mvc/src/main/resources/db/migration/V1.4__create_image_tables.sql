CREATE TABLE images (
    id               BIGSERIAL NOT NULL,
    file_name        VARCHAR(30),
    bucket_name              VARCHAR(200),
    extension        VARCHAR(10),
    s3key            VARCHAR(512),
    user_id          BIGINT
);

ALTER TABLE images ADD CONSTRAINT image_pk PRIMARY KEY ( id );

ALTER TABLE images
    ADD CONSTRAINT image_user_fk FOREIGN KEY ( user_id )
        REFERENCES users ( id );

