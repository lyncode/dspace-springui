/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.springui.orm.dao.api;

import java.util.List;

import org.dspace.springui.orm.entity.Bitstream;


public interface IBitstreamDao extends IDSpaceDao<Bitstream> {
	List<Bitstream> selectAllDeleted();
	List<Bitstream> selectDuplicateInternalIdentifier(Bitstream bitstream);
}
