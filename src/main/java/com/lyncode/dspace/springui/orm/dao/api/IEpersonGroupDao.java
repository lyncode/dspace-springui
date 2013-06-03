/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.api;

import com.lyncode.dspace.springui.orm.dao.content.PredefinedGroup;
import com.lyncode.dspace.springui.orm.entity.EpersonGroup;

/**
 * 
 * 
 * @author João Melo
 */
public interface IEpersonGroupDao extends IDSpaceDao<EpersonGroup>{
	EpersonGroup selectById(PredefinedGroup anonymous);
}
