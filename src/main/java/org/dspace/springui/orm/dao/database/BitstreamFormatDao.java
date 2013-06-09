/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.springui.orm.dao.database;

import org.dspace.springui.orm.dao.api.IBitstreamFormatDao;
import org.dspace.springui.orm.entity.BitstreamFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author João Melo <jmelo@lyncode.com>
 * @author Miguel Pinto <mpinto@lyncode.com>
 */
@Transactional
@Repository("org.dspace.springui.orm.dao.api.IBitstreamFormatDao")
public class BitstreamFormatDao extends DSpaceDao<BitstreamFormat> implements IBitstreamFormatDao {
//	private static Logger log = LogManager.getLogger(BitstreamFormatDao.class);
	
    public BitstreamFormatDao() {
		super(BitstreamFormat.class);
	}
}
