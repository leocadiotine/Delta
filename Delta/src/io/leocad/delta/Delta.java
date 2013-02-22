package io.leocad.delta;

import android.app.Activity;

public abstract class Delta {

	public abstract void onPostExecute();

	public void benchmark(final Activity activity, final Class<? extends BenchmarkTask> classType, final long numCycles) {

		new Thread() {
			@Override
			public void run() {

				try {
					BenchmarkTask taskInstance = classType.newInstance();
					taskInstance.execute(numCycles);
					
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				
				} finally {
					
					activity.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							onPostExecute();
						}
					});
				}
			};
		}.start();
	}
}
