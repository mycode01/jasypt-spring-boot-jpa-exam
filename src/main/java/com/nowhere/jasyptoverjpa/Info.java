package com.nowhere.jasyptoverjpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Convert(converter = NameEncConverter.class)
    private String encryptedName;

    @Column
    private String text;

    @Column
    @Convert(converter = TextEncConverter.class)
    private String encryptedText;

}
