package org.liying.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
@Entity
@Table (name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "file_name") private String fileName;
    @Column(name = "bucket_name") private String bucketName;
    @Column(name = "extension") private String extension;
    @Column(name = "s3key") private String s3key;
//    @Column(name = "user_id") private Long user_id;
//    ForeignKey
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String file_name) {
        this.fileName = file_name;
    }
    public String getBucketName() {
        return bucketName;
    }
    public void setBucketName(String url) {
        this.bucketName = url;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getS3key() {
        return s3key;
    }
    public void setS3key(String s3key) {
        this.s3key = s3key;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String toString(){
        return "{ "+"id:"+getId()+", FileName:"+getFileName()+", S3Key:"+getS3key()+" }";
    }
}
