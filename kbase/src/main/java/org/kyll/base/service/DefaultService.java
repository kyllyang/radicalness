package org.kyll.base.service;

import org.kyll.base.condition.Condition;
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
import java.util.Map;

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
		return dao.findOne(getSpecification(fieldName, fieldValue)).orElse(null);
	}

	public List<E> find(Sort... sorts) {
		return ArrayUtil.isBlank(sorts) ? dao.findAll() : dao.findAll(ValueUtil.toSpringDataDomainSort(sorts));
	}

	public List<E> find(String fieldName, String fieldValue, Sort... sorts) {
		return dao.findAll(getSpecification(fieldName, fieldValue), ValueUtil.toSpringDataDomainSort(sorts));
	}

	public Dataset<E> find(Paginated paginated) {
		Page<E> page = dao.findAll(PageRequest.of(paginated.getNextOnePage(), paginated.getMaxRecord(), ValueUtil.toSpringDataDomainSort(paginated.getSortList())));
		return Dataset.create(paginated, page.getTotalElements(), page.getContent());
	}

	public List<Map<String, Object>> findAsMap(Sort... sorts) {
		return null;
	}

	public List<Map<String, Object>> findAsMap(String fieldName, String fieldValue, Sort... sorts) {
		return null;
	}

	public List<Map<String, Object>> findAsMap(Condition condition, Sort... sorts) {
		return null;
	}

	public Dataset<Map<String, Object>> findAsMap(Condition condition, Paginated paginated) {
		return null;
	}

	public void save(E e) {
		dao.save(e);
	}

	public void delete(PK id) {
		dao.deleteById(id);
	}

	private Specification<E> getSpecification(String fieldName, String fieldValue) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(fieldName), fieldValue);
	}
}
