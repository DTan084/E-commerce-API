package com.ecommerce.api.entity;

import com.ecommerce.api.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Password hash is required")
    @Column(nullable = false)
    private String passwordHash;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    public User() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends BaseBuilder<User, Builder> {
        public Builder() { entity = new User(); }
        @Override protected Builder self() { return this; }
        public Builder email(String email) { entity.setEmail(email); return this; }
        public Builder passwordHash(String passwordHash) { entity.setPasswordHash(passwordHash); return this; }
        public Builder name(String name) { entity.setName(name); return this; }
        public Builder role(Role role) { entity.setRole(role); return this; }
    }
}
