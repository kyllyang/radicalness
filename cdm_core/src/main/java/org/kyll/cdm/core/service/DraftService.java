package org.kyll.cdm.core.service;

import org.kyll.base.service.DefaultService;
import org.kyll.cdm.core.dao.DraftDao;
import org.kyll.cdm.core.entity.Draft;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2017-08-02 11:02
 */
@Service
public class DraftService extends DefaultService<Draft, Long, DraftDao> {
}
