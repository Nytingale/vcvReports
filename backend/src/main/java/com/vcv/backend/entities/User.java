package com.vcv.backend.entities;

import com.vcv.backend.enums.CompanyType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    @Id
    private String email;
    private String password;
    private Boolean passwordReset;
    private Long companyId;
    private Long roleId;

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getPasswordReset() {
        return passwordReset;
    }
    public Long getCompanyId() {
        return companyId;
    }
    public Long getRoleId() {
        return roleId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPasswordReset(Boolean passwordReset) {
        this.passwordReset = passwordReset;
    }
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, companyId, roleId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return email.equals(user.email) &&
                password.equals(user.password) &&
                companyId.equals(user.companyId) &&
                roleId.equals(user.roleId);
    }

    public static class Builder {
        private String email;
        private String password;
        private Boolean passwordReset;
        private Long companyId;
        private Long roleId;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
        public Builder setPasswordReset(Boolean passwordReset) {
            this.passwordReset = passwordReset;
            return this;
        }
        public Builder setCompanyId(Long companyId) {
            this.companyId = companyId;
            return this;
        }
        public Builder setRoleId(Long roleId) {
            this.roleId = roleId;
            return this;
        }

        public User build() {
            User user = new User();

            user.setEmail(this.email);
            user.setPassword(this.password);
            user.setPasswordReset(this.passwordReset);
            user.setCompanyId(this.companyId);
            user.setRoleId(this.roleId);

            return user;
        }
    }
}
