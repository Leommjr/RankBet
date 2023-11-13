package br.com.rankbet.model;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_subscription")
public class SubscriptionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "updated_by")
    private String updateBy;

    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @Column(name = "price")
    private Float price;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "role_id")
    private long roleId;

    @Column(name = "user_id")
    private long userId;

    public long getId() {
        return id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public Float getPrice() {
        return price;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public long getRoleId() {
        return roleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    public SubscriptionModel(long id, String createBy, LocalDateTime createAt, String updateBy, LocalDateTime updateAt, Float price, LocalDateTime expiresAt, long roleId, long userId) {
        this.id = id;
        this.createBy = createBy;
        this.createAt = createAt;
        this.updateBy = updateBy;
        this.updateAt = updateAt;
        this.price = price;
        this.expiresAt = expiresAt;
        this.roleId = roleId;
        this.userId = userId;
    }
    public SubscriptionModel(){

    }

}
