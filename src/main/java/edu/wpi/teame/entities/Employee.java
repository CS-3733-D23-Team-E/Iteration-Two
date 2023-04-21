package edu.wpi.teame.entities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.Setter;

public class Employee {

  public enum Permission {
    STAFF,
    ADMIN;

    public static String permissionToString(Permission p) {
      switch (p) {
        case STAFF:
          return "STAFF";
        case ADMIN:
          return "ADMIN";
        default:
          throw new NoSuchElementException("No such permission");
      }
    }

    public static Permission stringToPermission(String str) {
      switch (str) {
        case "STAFF":
          return STAFF;
        case "ADMIN":
          return ADMIN;
        default:
          throw new NoSuchElementException("No such permission");
      }
    }
  }

  @Getter @Setter private String fullName;
  @Getter @Setter private String username;
  @Getter @Setter private String password;
  @Getter @Setter private String permission;

  public Employee(String fullName, String username, String password, String permission) {
    this.fullName = fullName;
    this.username = username;
    this.password = hashPassword(password);
    this.permission = permission;
  }

  public Employee(String fullName, String permission) {
    this.fullName = fullName;
    this.permission = permission;
    this.username = null;
    this.password = null;
  }

  public static String hashPassword(String password) {
    try {

      // Static getInstance method is called with hashing MD5
      MessageDigest md = MessageDigest.getInstance("MD5");

      // digest() method is called to calculate message digest
      // of an input digest() return array of byte
      byte[] messageDigest = md.digest(password.getBytes());

      // Convert byte array into signum representation
      BigInteger no = new BigInteger(1, messageDigest);

      // Convert message digest into hex value
      String hashtext = no.toString(16);
      while (hashtext.length() < 32) {
        hashtext = "0" + hashtext;
      }
      return hashtext;
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
