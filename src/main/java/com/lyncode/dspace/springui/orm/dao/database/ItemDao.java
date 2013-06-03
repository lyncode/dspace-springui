/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.orm.dao.database;

import com.lyncode.dspace.springui.orm.dao.api.IItemDao;

import com.lyncode.dspace.springui.orm.entity.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author João Melo <jmelo@lyncode.com>
 * @author Miguel Pinto <mpinto@lyncode.com>
 */
@Transactional
@Repository("com.lyncode.dspace.springui.orm.dao.api.IItemDao")
public class ItemDao extends DSpaceDao<Item> implements IItemDao {
    
	public ItemDao() {
		super(Item.class);
	}
}
