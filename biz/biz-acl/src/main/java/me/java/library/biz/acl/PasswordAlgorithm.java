package me.java.library.biz.acl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
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
@SuppressWarnings("deprecation")
public enum PasswordAlgorithm {
    //@formatter:off
    bcrypt("bcrypt", new BCryptPasswordEncoder()),
    ldap("ldap", new LdapShaPasswordEncoder()),
    MD4("MD4", new Md4PasswordEncoder()),
    MD5("MD5", new MessageDigestPasswordEncoder("MD5")),
    noop("noop", NoOpPasswordEncoder.getInstance()),
    pbkdf2("pbkdf2", new Pbkdf2PasswordEncoder()),
    scrypt("scrypt", new SCryptPasswordEncoder()),
    SHA1("SHA-1", new MessageDigestPasswordEncoder("SHA-1")),
    SHA256("SHA-256", new MessageDigestPasswordEncoder("SHA-256")),
    sha256("sha256", new StandardPasswordEncoder()),
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
