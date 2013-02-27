package io.leocad.deltaexample.core;

import io.leocad.delta.BenchmarkTask;


public class BenchmarkGetSet extends BenchmarkTask {
	
	private ModelGetSet mModel;
	
	public ModelGetSet getModel() {
		return mModel;
	}

	public void setModel(ModelGetSet model) {
		this.mModel = model;
	}

	@Override
	protected void onPreExecute() {
		setModel( new ModelGetSet() );
	}
	
	protected Object task() {
		
		ModelGetSet model = getModel();
		model.setMyString("Am I slow?");
		return model.getMyString();
	}
}
