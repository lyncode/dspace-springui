/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.database;

import com.lyncode.dspace.springui.orm.dao.api.IEpersonGroupDao;
import com.lyncode.dspace.springui.orm.dao.content.PredefinedGroup;

import com.lyncode.dspace.springui.orm.entity.EpersonGroup;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author João Melo <jmelo@lyncode.com>
 * @author Miguel Pinto <mpinto@lyncode.com>
 */
@Transactional
@Repository("com.lyncode.dspace.springui.orm.dao.api.IEpersonGroupDao")
public class EpersonGroupDao extends DSpaceDao<EpersonGroup> implements IEpersonGroupDao {
    
	public EpersonGroupDao() {
		super(EpersonGroup.class);
	}

	@Override
	public EpersonGroup selectById(PredefinedGroup anonymous) {
		return super.selectById(anonymous.getId());
	}
}
