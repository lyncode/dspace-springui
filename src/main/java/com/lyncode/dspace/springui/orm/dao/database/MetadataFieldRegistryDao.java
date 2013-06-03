/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.database;

import com.lyncode.dspace.springui.orm.dao.api.IMetadataFieldRegistryDao;

import com.lyncode.dspace.springui.orm.entity.MetadataFieldRegistry;
import com.lyncode.dspace.springui.orm.entity.MetadataSchemaRegistry;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Miguel Pinto <mpinto@lyncode.com>
 * @version $Revision$
 */

@Transactional
@Repository("com.lyncode.dspace.springui.orm.dao.api.IMetadataFieldRegistryDao")
public class MetadataFieldRegistryDao extends DSpaceDao<MetadataFieldRegistry> implements IMetadataFieldRegistryDao {
    
	public MetadataFieldRegistryDao() {
		super(MetadataFieldRegistry.class);
	}

	@Override
	public MetadataFieldRegistry selectByNameAndSchema(
			MetadataSchemaRegistry schema, String fieldName) { //title.alternative ou  title
		int pos = fieldName.indexOf(".");
		String elementName = (pos != -1) ? fieldName.substring(0, pos) : fieldName;
		String qualifierName = (pos == -1) ? null : fieldName.substring(pos+1);
		Criterion qualifierQuery = (qualifierName == null) ? Restrictions.isNull("qualifier") : Restrictions.eq("qualifier", qualifierName);
		return (MetadataFieldRegistry) super.getSession().createCriteria(MetadataFieldRegistry.class)
				.add(Restrictions.and(
						Restrictions.eq("metadataSchema", schema),
						Restrictions.eq("element", elementName),
						qualifierQuery
				))
				.uniqueResult();
	}
}
