/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.api;

import java.util.List;

import com.lyncode.dspace.springui.orm.dao.content.DSpaceObjectType;
import com.lyncode.dspace.springui.orm.entity.MetadataValue;

/**
 * @author Miguel Pinto <mpinto@lyncode.com>
 * @version $Revision$
 */

public interface IMetadataValueDao extends IDSpaceDao<MetadataValue>{
	List<MetadataValue> selectByResourceId(DSpaceObjectType resourceType, int resourceId);
	List<MetadataValue> selectByResourceAndField(DSpaceObjectType type, int id, String field);
}
