package com.kaishengit.pojo;

import javax.persistence.*;

@Entity
@Table(name = "t_user_log")
public class UserLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String logintime;
  private String loginip;
  @Transient
  private Integer userid;
  @ManyToOne
  @JoinColumn(name = "userid")
  private User user;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLogintime() {
    return logintime;
  }

  public void setLogintime(String logintime) {
    this.logintime = logintime;
  }

  public String getLoginip() {
    return loginip;
  }

  public void setLoginip(String loginip) {
    this.loginip = loginip;
  }

  public Integer getUserid() {
    return userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
