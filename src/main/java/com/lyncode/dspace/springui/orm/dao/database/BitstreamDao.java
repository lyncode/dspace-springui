/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.database;

import java.util.List;

import com.lyncode.dspace.springui.orm.dao.api.IBitstreamDao;
import com.lyncode.dspace.springui.orm.entity.Bitstream;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author João Melo <jmelo@lyncode.com>
 * @author Miguel Pinto <mpinto@lyncode.com>
 */
@Transactional
@Repository("com.lyncode.dspace.springui.orm.dao.api.IBitstreamDao")
public class BitstreamDao extends DSpaceDao<Bitstream> implements IBitstreamDao {
    // private static Logger log = LogManager.getLogger(BitstreamDao.class);
    public BitstreamDao () {
    	super(Bitstream.class);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Bitstream> selectAllDeleted() {
		return (List<Bitstream>) super.getSession().createCriteria(Bitstream.class)
				.add(Restrictions.eq("deleted", true))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bitstream> selectDuplicateInternalIdentifier(Bitstream bitstream) {
		return (List<Bitstream>) super.getSession().createCriteria(Bitstream.class)
				.add(Restrictions.and(
						Restrictions.eq("internalId", bitstream.getInternalId()),
						Restrictions.ne("ID", bitstream.getID())
				))
				.list();
	}
}
