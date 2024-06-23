package com.homecooked.common.chef;

import com.homecooked.common.constant.Role;
import com.homecooked.common.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "chef")
public class Chef extends User {

    public Chef() {
        super(Role.CHEF.name());
    }
}
