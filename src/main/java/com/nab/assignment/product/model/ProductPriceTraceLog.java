package com.nab.assignment.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "product_price_trace_log")
public class ProductPriceTraceLog extends AbstractTraceAuditingEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.StandardRandomStrategy"
                    )
            }
    )
    @Column(name = "id")
    private UUID id;

    @NotNull
    private UUID productId;

    @NotNull
    private Long price;

    public ProductPriceTraceLog(@NotNull UUID productId, @NotNull Long price) {
        this.productId = productId;
        this.price = price;
    }
}
