package com.nowhere.jasyptoverjpa;


import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TextEncConverter implements AttributeConverter<String, String> {
    @Autowired
    StringEncryptor textStringEncryptor; // case 2, use own custom Encryptor, uncomment AppConfig

    @Override
    public String convertToDatabaseColumn(String raw) {
        return textStringEncryptor.encrypt(raw);
    }

    @Override
    public String convertToEntityAttribute(String encrypted) {
        return textStringEncryptor.decrypt(encrypted);
    }
}
