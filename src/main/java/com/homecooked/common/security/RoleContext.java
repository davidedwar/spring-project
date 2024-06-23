package com.homecooked.common.security;

import com.homecooked.common.constant.Role;

public class RoleContext {

    private static final ThreadLocal<Role> roleHolder = new ThreadLocal<>();

    public static void setRoleHolder(Role role) {
        roleHolder.set(role);
    }

    public static Role getRole() {
        return roleHolder.get();
    }

}
