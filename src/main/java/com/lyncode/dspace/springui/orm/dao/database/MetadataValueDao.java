/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.database;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.lyncode.dspace.springui.orm.dao.api.IMetadataFieldRegistryDao;
import com.lyncode.dspace.springui.orm.dao.api.IMetadataSchemaRegistryDao;
import com.lyncode.dspace.springui.orm.dao.api.IMetadataValueDao;
import com.lyncode.dspace.springui.orm.dao.content.DSpaceObjectType;

import com.lyncode.dspace.springui.orm.entity.MetadataFieldRegistry;
import com.lyncode.dspace.springui.orm.entity.MetadataSchemaRegistry;
import com.lyncode.dspace.springui.orm.entity.MetadataValue;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Miguel Pinto <mpinto@lyncode.com>
 * @version $Revision$
 */

@Transactional
@Repository
public class MetadataValueDao extends DSpaceDao<MetadataValue> implements IMetadataValueDao {
	private Map<String, MetadataSchemaRegistry> schemas = null;
	private Map<String, MetadataFieldRegistry> fields = null;
	
	@Autowired IMetadataFieldRegistryDao fieldRegistry;
	@Autowired IMetadataSchemaRegistryDao schemaRegistry;
	
	public MetadataValueDao() {
		super(MetadataValue.class);
	}
	
	
	
	private MetadataSchemaRegistry getSchema (String schema) {
		if (schemas == null) 
			schemas = new TreeMap<String, MetadataSchemaRegistry>();
		
		if (!schemas.containsKey(schema)) {
			schemas.put(schema, schemaRegistry.selectByName(schema));
		}
		
		return schemas.get(schema);
	}
	
	private MetadataFieldRegistry getField (String field) {
		int pos = field.indexOf(".");
		String schemaName = field.substring(0, pos);
		String fieldName = field.substring(pos+1);
		
		if (fields == null)
			fields = new TreeMap<String, MetadataFieldRegistry>();
		
		if (!fields.containsKey(field)) {
			fields.put(field, fieldRegistry.selectByNameAndSchema(this.getSchema(schemaName), fieldName));
		}
			
		return fields.get(field);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MetadataValue> selectByResourceId(DSpaceObjectType resourceType,
			int resourceId) {
		return (List<MetadataValue>) this.getSession().createCriteria(MetadataValue.class)
				.add(Restrictions.and(
						Restrictions.eq("resourceType", resourceType.getId()), 
						Restrictions.eq("resource", resourceId)
				))
				.list();
				
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MetadataValue> selectByResourceAndField (DSpaceObjectType resourceType, int resourceId, String field)
	{
		return (List<MetadataValue>) this.getSession().createCriteria(MetadataValue.class)
				.add(Restrictions.and(
						Restrictions.eq("resourceType", resourceType.getId()), 
						Restrictions.eq("resource", resourceId),  
						Restrictions.eq("metadataField", this.getField(field))
				))
				.list();
	}
}
