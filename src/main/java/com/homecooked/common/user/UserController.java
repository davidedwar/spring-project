package com.homecooked.common.user;

import org.springframework.http.ResponseEntity;

public abstract class UserController<C, R> {

    abstract ResponseEntity<R> update(C userCreateUpdateDto);

    abstract ResponseEntity<Void> delete();

    abstract ResponseEntity<R> me();
}
