package com.vcv.backend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "role")
public class Role {
    @Id
    private Long id;
    private String name;
    private Boolean user;
    private Boolean admin;
    private Boolean staff;

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
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
    public void setName(String name) {
        this.name = name;
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
                name.equals(role.name) &&
                user.equals(role.user) &&
                admin.equals(role.admin) &&
                staff.equals(role.staff);
    }

    public static class Builder {
        private Long id;
        private String name;
        private Boolean user;
        private Boolean admin;
        private Boolean staff;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setUser(Boolean user) {
            this.user = user;
            return this;
        }
        public Builder setAdmin(Boolean admin) {
            this.admin = admin;
            return this;
        }
        public Builder setStaff(Boolean staff) {
            this.staff = staff;
            return this;
        }

        public Role build() {
            Role role = new Role();

            role.setId(this.id);
            role.setName(this.name);
            role.setUser(this.user);
            role.setAdmin(this.admin);
            role.setStaff(this.staff);

            return role;
        }
    }
}
