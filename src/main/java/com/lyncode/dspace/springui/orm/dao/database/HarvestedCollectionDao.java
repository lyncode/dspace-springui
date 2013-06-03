/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.database;

import com.lyncode.dspace.springui.orm.dao.api.IHarvestedCollectionDao;

import com.lyncode.dspace.springui.orm.entity.HarvestedCollection;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Miguel Pinto <mpinto@lyncode.com>
 * @version $Revision$
 */

@Transactional
@Repository("com.lyncode.dspace.springui.orm.dao.api.IHarvestedCollectionDao")
public class HarvestedCollectionDao extends DSpaceDao<HarvestedCollection> implements IHarvestedCollectionDao {
    
	public HarvestedCollectionDao() {
		super(HarvestedCollection.class);
	}
}
