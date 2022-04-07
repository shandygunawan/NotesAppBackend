package com.notes.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name="note")
@Table(name="notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    // fields
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
    @Column(name="id", updatable = false, nullable = false)
    private UUID id;

    @Column(name="title", nullable = false)
    @NonNull
    private String title;

    @Column(name="content", nullable = false)
    @NonNull
    private String content; // Store the raw string. The content will be rendered as markdown in frontend

}
