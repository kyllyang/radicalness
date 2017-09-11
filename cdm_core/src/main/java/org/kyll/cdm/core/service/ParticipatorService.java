package org.kyll.cdm.core.service;

import org.kyll.base.service.DefaultService;
import org.kyll.cdm.core.entity.Participator;
import org.kyll.cdm.core.dao.ParticipatorDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Kyll
 * Date: 2017-08-02 13:52
 */
@Service
@Transactional(readOnly = true)
public class ParticipatorService extends DefaultService<Participator, Long, ParticipatorDao> {
	public Participator getPboc() {
	//	return participatorRepository.find(Const.CLASS_FIELD_CODE, Const.DEFAULT_PARTICIPATOR_PBOC);
		return null;
	}
}
