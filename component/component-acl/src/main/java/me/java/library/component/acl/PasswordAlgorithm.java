package me.java.library.component.acl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * File Name             :  PasswordAlgorithm
 *
 * @author :  sylar
 * Create :  2019-08-15
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
@SuppressWarnings("ALL")
public enum PasswordAlgorithm {
    //@formatter:off
    bcrypt  ("bcrypt", new BCryptPasswordEncoder()),
    ldap    ("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder()),
    MD4     ("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder()),
    MD5     ("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5")),
    noop    ("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance()),
    pbkdf2  ("pbkdf2", new Pbkdf2PasswordEncoder()),
    scrypt  ("scrypt", new SCryptPasswordEncoder()),
    SHA1    ("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1")),
    SHA256  ("SHA-256", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256")),
    sha256  ("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder()),
    //@formatter:on
    ;


    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";
    String id;
    PasswordEncoder encoder;

    PasswordAlgorithm(String id, PasswordEncoder encoder) {
        this.id = id;
        this.encoder = encoder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PasswordEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String getPrefixID() {
        return PREFIX + id + SUFFIX;
    }

    @Override
    public String toString() {
        return id;
    }
}
