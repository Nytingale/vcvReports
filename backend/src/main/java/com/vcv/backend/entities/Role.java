package com.vcv.backend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "RoleRepository")
public class Role {
    @Id
    private Long id;
    private Boolean user;
    private Boolean admin;
    private Boolean staff;

    public Long getId() {
        return id;
    }
    public Boolean isUser() {
        return user;
    }
    public Boolean isAdmin() {
        return admin;
    }
    public Boolean isStaff() {
        return staff;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setUser(Boolean user) {
        this.user = user;
    }
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    public void setStaff(Boolean staff) {
        this.staff = staff;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, admin, staff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id) &&
                user.equals(role.user) &&
                admin.equals(role.admin) &&
                staff.equals(role.staff);
    }
}
