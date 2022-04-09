package com.notes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @Column(name="created_at", nullable = false, updatable = false)
    @NonNull
    @CreationTimestamp
    private Timestamp CreatedAt;

    @Column(name="updated_at", nullable = false)
    @NonNull
    @UpdateTimestamp
    private Timestamp updatedAt;

}
