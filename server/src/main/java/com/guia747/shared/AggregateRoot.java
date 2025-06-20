package com.guia747.shared;

import java.io.Serial;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class AggregateRoot<ID extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private ID id;
}
