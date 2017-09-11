package org.kyll.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: Kyll
 * Date: 2017-09-08 15:23
 */
public interface DefaultRepository<E, PK> extends JpaRepository<E, PK>, JpaSpecificationExecutor<E> {
}
