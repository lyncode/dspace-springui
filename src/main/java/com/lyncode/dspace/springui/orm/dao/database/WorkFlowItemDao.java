/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.database;

import com.lyncode.dspace.springui.orm.dao.api.IWorkFlowItemDao;

import com.lyncode.dspace.springui.orm.entity.WorkFlowItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Miguel Pinto <mpinto@lyncode.com>
 * @version $Revision$
 */

@Transactional
@Repository("com.lyncode.dspace.springui.orm.dao.api.IWorkFlowItemDao")
public class WorkFlowItemDao extends DSpaceDao<WorkFlowItem> implements IWorkFlowItemDao {
    
	public WorkFlowItemDao() {
		super(WorkFlowItem.class);
	}
}
