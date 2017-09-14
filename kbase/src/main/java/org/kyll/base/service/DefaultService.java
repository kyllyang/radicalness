package org.kyll.base.service;

import org.kyll.base.repository.DefaultRepository;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.Sort;
import org.kyll.common.util.ArrayUtil;
import org.kyll.common.util.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-09-06 15:57
 */
public abstract class DefaultService<E, PK extends Serializable, Dao extends DefaultRepository<E, PK>> {
	@Autowired
	protected Dao dao;

	public E get(PK id) {
		return dao.getOne(id);
	}

	public E get(String fieldName, String fieldValue) {
		return dao.findOne(getEqualSpecification(fieldName, fieldValue)).orElse(null);
	}

	public List<E> find(Sort... sorts) {
		return ArrayUtil.isBlank(sorts) ? dao.findAll() : dao.findAll(ValueUtil.toSpringDataDomainSort(sorts));
	}

	public List<E> find(String fieldName, String fieldValue, Sort... sorts) {
		return dao.findAll(getEqualSpecification(fieldName, fieldValue), ValueUtil.toSpringDataDomainSort(sorts));
	}

	public List<E> findLike(String fieldName, String fieldValue, Sort... sorts) {
		return dao.findAll(getLikeSpecification(fieldName, fieldValue), ValueUtil.toSpringDataDomainSort(sorts));
	}

	public Dataset<E> find(Paginated paginated) {
		Page<E> page = dao.findAll(PageRequest.of(paginated.getNextOnePage(), paginated.getMaxRecord(), ValueUtil.toSpringDataDomainSort(paginated.getSortList())));
		return Dataset.create(paginated, page.getTotalElements(), page.getContent());
	}

	public void save(E e) {
		dao.save(e);
	}

	public void delete(PK id) {
		dao.deleteById(id);
	}

	private Specification<E> getEqualSpecification(String fieldName, String fieldValue) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(fieldName), fieldValue);
	}

	private Specification<E> getLikeSpecification(String fieldName, String fieldValue) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(fieldName), ValueUtil.toSqlLike(fieldValue));
	}
}
