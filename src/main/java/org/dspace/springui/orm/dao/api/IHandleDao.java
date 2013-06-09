/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.springui.orm.dao.api;

import java.util.List;

import org.dspace.springui.orm.dao.content.DSpaceObjectType;
import org.dspace.springui.orm.entity.Handle;


public interface IHandleDao extends IDSpaceDao<Handle> {
    Handle selectByResourceId(DSpaceObjectType resourseType, int id);
    Handle selectByHandle(String handle);
	List<Handle> selectByPrefix(String naHandle);
	long countByPrefix(String oldH);
}
