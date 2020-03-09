package com.onejane.log;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {

    /**
     * @CreatedDate 创建时间
     *
     * @CreatedBy 创建人
     *
     * @LastModifiedDate 更新时间
     *
     * @LastModifiedBy 更新人
     *
     * @Temporal(TemporalType.DATE) 封装成日期“yyyy-MM-dd”的 Date类型。
     *
     * @Temporal(TemporalType.TIME) 封装成时间“hh-MM-ss”的 Date类型。
     *
     * @Temporal(TemporalType.TIMESTAMP) 封装成完整的时间“yyyy-MM-dd hh:MM:ss”的 Date类型。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    private String path;

    private Integer status;

    @CreatedBy
    @LastModifiedBy
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifyTime;

}
