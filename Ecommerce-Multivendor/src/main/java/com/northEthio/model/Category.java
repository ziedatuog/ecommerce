package com.northEthio.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;

    @NonNull
    @Column(unique = true)
    private String catagoryId;

    @ManyToOne
    private Category parentCategory;

    @NotNull
    private Integer level;
}
