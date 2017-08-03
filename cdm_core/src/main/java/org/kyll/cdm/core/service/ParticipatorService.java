package org.kyll.cdm.core.service;

import org.kyll.base.service.DefaultService;
import org.kyll.cdm.core.dao.ParticipatorDao;
import org.kyll.cdm.core.entity.Participator;
import org.kyll.common.Const;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2017-08-02 13:52
 */
@Service
public class ParticipatorService extends DefaultService<Participator, Long, ParticipatorDao> {
	public Participator getPboc() {
		return getDao().get(Const.CLASS_FIELD_CODE, Const.DEFAULT_PARTICIPATOR_PBOC);
	}
}
