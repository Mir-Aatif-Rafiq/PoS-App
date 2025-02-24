package com.pos.app.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class AbstractPojo {
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @Column(name = "deleted_at", updatable = false)
    private LocalDateTime deleted_at;

    @PrePersist
    protected void onCreate() {
        if (this.created_at == null) {
            this.created_at = LocalDateTime.now();
        }
    }
    @PreUpdate
    protected void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }

}
