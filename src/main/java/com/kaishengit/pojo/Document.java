package com.kaishengit.pojo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_document")
public class Document {

    public static final String TYPE_DIR = "dir";
    public static final String TYPE_DOC = "doc";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String size;
    @Column(insertable = false,updatable = false)
    private Timestamp createtime;
    private String createuser;
    private String type;
    private String filename;
    private String md5;
    private Integer fid;
    private String contexttype;

    public Integer getId() {
    return id;
    }

    public void setId(Integer id) {
    this.id = id;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public String getSize() {
    return size;
    }

    public void setSize(String size) {
    this.size = size;
    }

    public Timestamp getCreatetime() {
    return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
    this.createtime = createtime;
    }

    public String getCreateuser() {
    return createuser;
    }

    public void setCreateuser(String createuser) {
    this.createuser = createuser;
    }

    public String getType() {
    return type;
    }

    public void setType(String type) {
    this.type = type;
    }

    public String getFilename() {
    return filename;
    }

    public void setFilename(String filename) {
    this.filename = filename;
    }

    public String getMd5() {
    return md5;
    }

    public void setMd5(String md5) {
    this.md5 = md5;
    }

    public Integer getFid() {
    return fid;
    }

    public void setFid(Integer fid) {
    this.fid = fid;
    }

    public String getContexttype() {
    return contexttype;
    }

    public void setContexttype(String contexttype) {
    this.contexttype = contexttype;
  }
}
