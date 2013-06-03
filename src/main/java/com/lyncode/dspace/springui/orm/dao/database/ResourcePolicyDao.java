/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.database;

import java.util.List;

import com.lyncode.dspace.springui.orm.dao.api.IResourcePolicyDao;

import com.lyncode.dspace.springui.orm.entity.IDSpaceObject;
import com.lyncode.dspace.springui.orm.entity.ResourcePolicy;
import com.lyncode.dspace.springui.services.api.auth.Action;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Miguel Pinto <mpinto@lyncode.com>
 * @version $Revision$
 */

@Transactional
@Repository("com.lyncode.dspace.springui.orm.dao.api.IResourcePolicyDao")
public class ResourcePolicyDao extends DSpaceDao<ResourcePolicy> implements IResourcePolicyDao {
    
	public ResourcePolicyDao() {
		super(ResourcePolicy.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResourcePolicy> selectByObjectAndAction(
			IDSpaceObject dSpaceObject, Action admin) {
		return (List<ResourcePolicy>) super.getSession().createCriteria(ResourcePolicy.class)
				.add(Restrictions.and(
						Restrictions.eq("resourceType", dSpaceObject.getType().getId()),
						Restrictions.eq("resourceId", dSpaceObject.getID()),
						Restrictions.eq("action", admin.getId())
				))
				.list();
	}
}
