package com.user.database;

class UserNotFoundException extends RuntimeException {

  UserNotFoundException(Long id) {
    super("Could not find User " + id);
  }
}