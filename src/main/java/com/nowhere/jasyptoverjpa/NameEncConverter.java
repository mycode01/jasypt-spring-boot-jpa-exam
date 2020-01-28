package com.nowhere.jasyptoverjpa;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NameEncConverter implements AttributeConverter<String, String> {
    @Autowired
    StringEncryptor jasyptStringEncryptor; // case 1, use autoconfigured String Encryptor

    @Override
    public String convertToDatabaseColumn(String raw) {
        return jasyptStringEncryptor.encrypt(raw);
    }

    @Override
    public String convertToEntityAttribute(String encrypted) {
        return jasyptStringEncryptor.decrypt(encrypted);
    }
}
