package com.fun.driven.development.fun.unified.payments.api.web.rest.mapper;

import java.util.List;

/**
 * Contract for a generic dto to vm mapper.
 *
 * @param <V> - VM type parameter.
 * @param <E> - Entity type parameter.
 */
public interface VMMapper<V, E> {

    V toVm(E entity);

    E toEntity(V vm);

    List <V> toVm(List<E> entities);

    List <E> toEntity(List<V> vms);
}
