package org.icday.icds.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "video")
@Data
public class Video {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;
}
