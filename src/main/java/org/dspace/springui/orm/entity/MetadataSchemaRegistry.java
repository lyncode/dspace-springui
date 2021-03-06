/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.springui.orm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


import org.dspace.springui.orm.dao.content.DSpaceObjectType;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * @author Miguel Pinto <mpinto@lyncode.com>
 * @version $Revision$
 */


@Entity
@Table(name = "metadataschemaregistry")
@SequenceGenerator(name="metadataschemaregistry_gen", sequenceName="metadataschemaregistry_seq")
@Configurable
public class MetadataSchemaRegistry extends DSpaceObject {
    private String namespace;
    private String shortID;
    
    
    @Id
    @Column(name = "metadata_schema_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="metadataschemaregistry_gen")
    public int getID() {
        return id;
    }
    
    @Override
    @Transient
    public DSpaceObjectType getType()
    {
    	return DSpaceObjectType.METADATA;
    }

    @Column(name = "short_id", nullable = true)
	public String getShortID() {
		return shortID;
	}

	public void setShortID(String shortID) {
		this.shortID = shortID;
	}

    @Column(name = "namespace", nullable = true)
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

  
}
