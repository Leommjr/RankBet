package br.com.rankbet.model;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_role")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "role_description")
    private String roleDescription;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "user_enabled")
    private int userEnabled;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "updated_by")
    private String updatedBy;

    public long getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getUserEnabled() {
        return userEnabled;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUserEnabled(int userEnabled) {
        this.userEnabled = userEnabled;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

        public RoleModel(String typeName, String roleDescription, LocalDateTime createAt, LocalDateTime updatedAt, int userEnabled, String createBy, String updatedBy) {
        this.typeName = typeName;
        this.roleDescription = roleDescription;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
        this.userEnabled = userEnabled;
        this.createBy = createBy;
        this.updatedBy = updatedBy;
    }

    public RoleModel() {

    }
}
