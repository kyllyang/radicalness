package org.kyll.cdm.core.service;

import org.kyll.cdm.core.entity.Draft;
import org.kyll.cdm.core.dao.DraftDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Kyll
 * Date: 2017-08-02 11:02
 */
@Service
@Transactional(readOnly = true)
public class DraftService {
	@Autowired
	private DraftDao draftDao;

	public List<Draft> findAll() {
		return draftDao.findAll();
	}

	public void save(Draft draft) {
		draftDao.save(draft);
	}
}
