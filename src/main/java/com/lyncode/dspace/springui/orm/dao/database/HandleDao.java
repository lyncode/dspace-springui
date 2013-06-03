/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.database;

import java.util.List;

import com.lyncode.dspace.springui.orm.dao.api.IHandleDao;
import com.lyncode.dspace.springui.orm.dao.content.DSpaceObjectType;
import com.lyncode.dspace.springui.orm.entity.Handle;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author João Melo <jmelo@lyncode.com>
 * @author Miguel Pinto <mpinto@lyncode.com>
 */
@Transactional
@Repository("com.lyncode.dspace.springui.orm.dao.api.IHandleDao")
public class HandleDao extends DSpaceDao<Handle>  implements IHandleDao {
//    private static Logger log = LogManager.getLogger(HandleDao.class);
    
    public HandleDao () {
    	super(Handle.class);
    }

	@Override
	public Handle selectByResourceId(DSpaceObjectType resourseType, int id) {
		return (Handle) super.getSession().createCriteria(Handle.class)
				.add(Restrictions.and(
						Restrictions.eq("resourceType", resourseType.getId()),
						Restrictions.eq("resourceId", id)
					))
				.uniqueResult();
	}

	@Override
	public Handle selectByHandle(String handle) {
		return (Handle) super.getSession().createCriteria(Handle.class)
				.add(Restrictions.eq("handleString", handle))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Handle> selectByPrefix(String naHandle) {
		return (List<Handle>) super.getSession().createCriteria(Handle.class)
				.add(Restrictions.like("handleString", naHandle+"/%"))
				.list();
	}

	@Override
	public long countByPrefix(String oldH) {
		return (Long) getSession().createCriteria(Handle.class)
				.setProjection(Projections.rowCount())
				.uniqueResult();
	}
	
}
