/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.api;

import com.lyncode.dspace.springui.orm.entity.MetadataFieldRegistry;
import com.lyncode.dspace.springui.orm.entity.MetadataSchemaRegistry;

/**
 * @author Miguel Pinto <mpinto@lyncode.com>
 * @version $Revision$
 */

public interface IMetadataFieldRegistryDao extends IDSpaceDao<MetadataFieldRegistry>{

	MetadataFieldRegistry selectByNameAndSchema(MetadataSchemaRegistry schema, String fieldName);
    
}
