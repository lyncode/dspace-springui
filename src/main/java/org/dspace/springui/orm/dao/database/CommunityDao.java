/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.springui.orm.dao.database;

import java.util.List;

import org.dspace.springui.orm.dao.api.ICommunityDao;
import org.dspace.springui.orm.entity.Community;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author João Melo <jmelo@lyncode.com>
 * @author Miguel Pinto <mpinto@lyncode.com>
 */
@Transactional
@Repository
public class CommunityDao extends DSpaceDao<Community> implements ICommunityDao {

    public CommunityDao () {
    	super(Community.class);
    }

    
    
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @return List of top communities
	 */
	public List<Community> selectTop() {
		return (List<Community>) super.getSession().createCriteria(Community.class)
				.add(Restrictions.eq("top", true))
				.list();
	}



	@Override
	public Integer save(Community c) {
		c.setTop(c.getParents() == null || c.getParents().isEmpty());
		return super.save(c);
	}



	@Override
	public boolean update(Community c) {
		c.setTop(c.getParents() == null || c.getParents().isEmpty());
		return super.update(c);
	}
	
	
}
