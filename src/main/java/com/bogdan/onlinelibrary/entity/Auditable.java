package com.bogdan.onlinelibrary.entity;

import com.bogdan.onlinelibrary.entity.domain.RecordStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.bogdan.onlinelibrary.entity.domain.RecordStatus.ACTIVE;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static jakarta.persistence.EnumType.ORDINAL;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Auditable implements Serializable {
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @Enumerated(ORDINAL)
    private RecordStatus recordStatus = ACTIVE;
}
