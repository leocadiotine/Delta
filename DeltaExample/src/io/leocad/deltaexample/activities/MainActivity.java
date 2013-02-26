package io.leocad.deltaexample.activities;

import io.leocad.delta.BenchmarkResult;
import io.leocad.delta.Delta;
import io.leocad.deltaexample.R;
import io.leocad.deltaexample.core.BenchmarkGetSet;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final DecimalFormat NUM_CYCLES_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
	private Dialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		new Delta() {
			
			@Override
			public void onPreExecute() {
				mDialog = ProgressDialog.show(MainActivity.this, null, "Benchmarking…");
			}

			@Override
			public void onPostExecute(BenchmarkResult result) {
				showResult(result);
				mDialog.dismiss();
			}
			
		}.benchmark(this, BenchmarkGetSet.class, 1000000000L);
	}

	private void showResult(BenchmarkResult result) {
		
		((TextView) findViewById(R.id.tv_class_name)).setText(result.className);
		
		((TextView) findViewById(R.id.tv_warmup_duration)).setText( String.valueOf(result.warmupDurationSecs) );
		((TextView) findViewById(R.id.tv_warmup_cycles)).setText( NUM_CYCLES_FORMATTER.format(result.warmupCycles) );
		((TextView) findViewById(R.id.tv_warmup_avg_time)).setText( String.valueOf(result.warmupAvgTaskTimeNs) );
		
		((TextView) findViewById(R.id.tv_benchmarking_duration)).setText( String.valueOf(result.benchmarkDurationSecs) );
		((TextView) findViewById(R.id.tv_benchmarking_cycles)).setText( NUM_CYCLES_FORMATTER.format(result.benchmarkCycles) );
		((TextView) findViewById(R.id.tv_benchmarking_avg_time)).setText( String.valueOf(result.benchmarkAvgTaskTimeNs) );
	}
}
