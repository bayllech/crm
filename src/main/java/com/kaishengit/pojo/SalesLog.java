package com.kaishengit.pojo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_sales_log")
public class SalesLog {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String context;
  @Column(insertable = false,updatable = false)
  private java.sql.Timestamp createtime;
  private String type;
  private Integer salesid;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public Timestamp getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Timestamp createtime) {
    this.createtime = createtime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getSalesid() {
    return salesid;
  }

  public void setSalesid(Integer salesid) {
    this.salesid = salesid;
  }
}
