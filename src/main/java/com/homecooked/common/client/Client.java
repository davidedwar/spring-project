package com.homecooked.common.client;

import com.homecooked.common.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static com.homecooked.common.constant.Role.CLIENT;

@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "client")
public class Client extends User {

    public Client() {
        super(CLIENT.name());
    }
}
