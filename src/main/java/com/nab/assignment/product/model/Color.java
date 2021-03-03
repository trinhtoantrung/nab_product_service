package com.nab.assignment.product.model;

import com.nab.assignment.product.util.GsonUtils;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "color")
public class Color extends AbstractAuditingEntity {
    private static final Logger log = LoggerFactory.getLogger(Color.class);

    @Id
    private String code;

    @NotNull
    private String name;

    public Color(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @PrePersist
    public void onPrePersist() {
        log.debug("onPrePersist: {}", GsonUtils.toJsonPretty(this));
    }
}
