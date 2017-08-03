package org.kyll.cdm.core.service;

import org.kyll.base.service.DefaultService;
import org.kyll.cdm.core.common.EcdsMsgType;
import org.kyll.cdm.core.dao.EcdsMsgRuleDao;
import org.kyll.cdm.core.entity.EcdsMsgRule;
import org.kyll.common.Const;
import org.kyll.common.exception.BaseException;
import org.kyll.common.paginated.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2017-08-02 15:59
 */
@Service
public class EcdsMsgRuleService extends DefaultService<EcdsMsgRule, Long, EcdsMsgRuleDao> {
	private static final Map<EcdsMsgType, List<EcdsMsgRule>> RULE_MAP = new HashMap<>();

	public void validate(EcdsMsgType ecdsMsgType, Object... objects) throws BaseException {
		List<EcdsMsgRule> ecdsMsgRuleList = getRuleList(ecdsMsgType);



	}

	private List<EcdsMsgRule> getRuleList(EcdsMsgType ecdsMsgType) {
		List<EcdsMsgRule> ecdsMsgRuleList = RULE_MAP.get(ecdsMsgType);
		if (ecdsMsgRuleList == null) {
			ecdsMsgRuleList = getDao().find(Const.CLASS_FIELD_CODE, ecdsMsgType.getValue(), Sort.asc(Const.CLASS_FIELD_SORT));
			RULE_MAP.put(ecdsMsgType, ecdsMsgRuleList);
		}
		return ecdsMsgRuleList;
	}
}
