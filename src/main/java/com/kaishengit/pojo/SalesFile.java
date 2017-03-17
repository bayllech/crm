package com.kaishengit.pojo;

import java.sql.Timestamp;

public class SalesFile {
  private Integer id;
  private String name;
  private String filename;
  private String contenttype;
  private java.sql.Timestamp createtime;
  private Long size;
  private Integer salesid;

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

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getContenttype() {
    return contenttype;
  }

  public void setContenttype(String contenttype) {
    this.contenttype = contenttype;
  }

  public Timestamp getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Timestamp createtime) {
    this.createtime = createtime;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public Integer getSalesid() {
    return salesid;
  }

  public void setSalesid(Integer salesid) {
    this.salesid = salesid;
  }
}
