package com.lyncode.dspace.springui.services.api.configuration;

public abstract class  PropertyWatcherHandler {
	public abstract void  handleModification (Object object);
	public abstract void  handleCreation (Object object);
	public abstract void  handleDelete ();
}
