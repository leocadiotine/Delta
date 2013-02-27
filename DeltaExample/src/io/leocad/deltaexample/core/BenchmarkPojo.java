package io.leocad.deltaexample.core;

import io.leocad.delta.BenchmarkTask;


public class BenchmarkPojo extends BenchmarkTask {
	
	private ModelPojo mModel;
	
	@Override
	protected void onPreExecute() {
		mModel = new ModelPojo();
	}
	
	protected Object task() {
		
		ModelPojo model = mModel;
		model.myString = "Am I slow?";
		return model.myString;
	}
}
