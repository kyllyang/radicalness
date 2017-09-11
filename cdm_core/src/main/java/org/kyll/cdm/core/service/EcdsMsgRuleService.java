package org.kyll.cdm.core.service;

import org.kyll.cdm.core.common.EcdsMsgType;
import org.kyll.cdm.core.entity.EcdsMsgRule;
import org.kyll.cdm.core.dao.EcdsMsgRuleDao;
import org.kyll.common.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2017-08-02 15:59
 */
@Service
@Transactional(readOnly = true)
public class EcdsMsgRuleService {
	@Autowired
	private EcdsMsgRuleDao ecdsMsgRuleDao;

	private static final Map<EcdsMsgType, List<EcdsMsgRule>> RULE_MAP = new HashMap<>();

	public void validate(EcdsMsgType ecdsMsgType, Object... objects) throws BaseException {
		List<EcdsMsgRule> ecdsMsgRuleList = getRuleList(ecdsMsgType);



	}

	private List<EcdsMsgRule> getRuleList(EcdsMsgType ecdsMsgType) {
		List<EcdsMsgRule> ecdsMsgRuleList = RULE_MAP.get(ecdsMsgType);
		if (ecdsMsgRuleList == null) {
		//	ecdsMsgRuleList = ecdsMsgRuleRepository.find(Const.CLASS_FIELD_CODE, ecdsMsgType.getValue(), Sort.asc(Const.CLASS_FIELD_SORT));
			RULE_MAP.put(ecdsMsgType, ecdsMsgRuleList);
		}
		return ecdsMsgRuleList;
	}
}
