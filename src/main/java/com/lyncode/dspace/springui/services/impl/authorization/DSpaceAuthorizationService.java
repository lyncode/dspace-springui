/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package com.lyncode.dspace.springui.services.impl.authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lyncode.dspace.springui.orm.dao.api.IEpersonDao;
import com.lyncode.dspace.springui.orm.dao.api.IResourcePolicyDao;
import com.lyncode.dspace.springui.orm.entity.Eperson;
import com.lyncode.dspace.springui.orm.entity.IDSpaceObject;
import com.lyncode.dspace.springui.orm.entity.ResourcePolicy;
import com.lyncode.dspace.springui.services.api.configuration.ConfigurationService;
import com.lyncode.dspace.springui.services.api.context.Context;
import com.lyncode.dspace.springui.services.api.context.ContextService;
import com.lyncode.dspace.springui.services.api.security.authorization.Action;
import com.lyncode.dspace.springui.services.api.security.authorization.AuthorizationException;
import com.lyncode.dspace.springui.services.api.security.authorization.AuthorizationService;

/**
 * An implementation of an authorization service based on resource policies.
 * 
 * @author João Melo <jmelo@lyncode.com>
 */
public class DSpaceAuthorizationService implements AuthorizationService {
	@Autowired ContextService contextService;
	@Autowired IResourcePolicyDao resourcePolicyDao;
	@Autowired ConfigurationService configService;
	@Autowired IEpersonDao epersonDao;
	private DSpaceAuthorizeConfiguration config = null;
	
	public void authorizedAnyOf(IDSpaceObject dspaceObject, Action[] actions)
			throws AuthorizationException {

		AuthorizationException ex = null;

        for (int i = 0; i < actions.length; i++)
        {
            try
            {
            	this.authorized(dspaceObject, actions[i]);
                return;
            } 
            catch (AuthorizationException e)
            {
                if (ex == null)
                {
                    ex = e;
                }
            }
        }

        throw new AuthorizationException(actions, contextService.getContext().getCurrentEperson(), dspaceObject);
	}

	public void authorized(IDSpaceObject object, Action action)
			throws AuthorizationException {
		authorized(object, action, true);
	}

	public void authorized(IDSpaceObject object, Action action, boolean inheritance)
			throws AuthorizationException {
		Context c = contextService.getContext();
        Eperson e = c.getCurrentEperson();
		if (object == null)
        {
            throw new AuthorizationException(action, e, object);
        }

        if (!authorize(object, action, e, inheritance))
        {
            throw new AuthorizationException(action, e, object);
        }
	}

	private boolean authorize(IDSpaceObject o, Action action,
			Eperson e, boolean useInheritance) {
		Context c = contextService.getContext();
		
		if (o == null)
        {
            return false;
        }

        // is authorization disabled for this context?
        if (c.ignoreAuthorization())
        {
            return true;
        }

        if (e != null)
        {
            // perform isAdmin check to see
            // if user is an Admin on this object
            IDSpaceObject testObject = useInheritance ? o.getAdminObject(action) : null;
            
            if (c.isAdmin() || testObject.isAdmin(e))
            {
            	// if is admin or if the user has ADMIN permissions on object
                return true;
            }
        } else // is eperson set? if not, userid = 0 (anonymous)
        	e = epersonDao.getAnonymous();
        

        return this.hasPermissions(e, o, action);
	}
	
	private boolean hasPermissions (Eperson e, IDSpaceObject testObject, Action action) {
		List<ResourcePolicy> policies = resourcePolicyDao.selectByObjectAndAction(testObject, action);

        for (ResourcePolicy rp : policies)
        {
            // check policies for date validity
            if (rp.isDateValid())
            {
                if ((rp.getEperson() != null) && (rp.getEperson().getID() == e.getID()))
                {
                    return true; // match
                }

                if ((rp.getEpersonGroup() != null)
                        && e.memberOf(rp.getEpersonGroup()))
                {
                    // group was set, and eperson is a member
                    // of that group
                    return true;
                }
            }
        }
        
        return false;
	}

	
	
	public DSpaceAuthorizeConfiguration getConfiguration() {
		if (this.config == null)
			this.config = new DSpaceAuthorizeConfiguration(configService);
		return this.config;
	}
}
