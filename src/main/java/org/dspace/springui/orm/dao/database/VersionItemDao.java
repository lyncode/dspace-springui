/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.springui.orm.dao.database;


import org.dspace.springui.orm.dao.api.IVersionItemDao;
import org.dspace.springui.orm.entity.VersionItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Miguel Pinto <mpinto@lyncode.com>
 * @version $Revision$
 */
@Transactional
@Repository("org.dspace.springui.orm.dao.api.IVersionItemDao")
public class VersionItemDao extends DSpaceDao<VersionItem> implements IVersionItemDao {
    
	public VersionItemDao() {
		super(VersionItem.class);
	}
}
