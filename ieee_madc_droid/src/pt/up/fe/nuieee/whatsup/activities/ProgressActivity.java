package pt.up.fe.nuieee.whatsup.activities;

import pt.up.fe.nuieee.whatsup.R;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public abstract class ProgressActivity extends FragmentActivity {
    private ProgressBar mProgressBar;

    @Override
    public void setContentView(View view) {
        init().addView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID,init(),true);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        init().addView(view,params);
    }

    private ViewGroup init(){
        super.setContentView(R.layout.progress);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_bar);
        return (ViewGroup) findViewById(R.id.activity_frame);
    }

    protected ProgressBar getProgressBar() {
        return mProgressBar;
    }
    
    private AsyncTask<Integer, Integer, Void> progressAnimationTask;

    protected void setProgress(int progress, boolean animated) {
    	
    	if (animated == false) {
    		mProgressBar.setProgress(progress);
    	}
    	else {
    		if (progressAnimationTask != null)
    		{
    			progressAnimationTask.cancel(true);
    		}
    		progressAnimationTask = new AsyncTask<Integer, Integer, Void>()
    		{
				@Override
				protected Void doInBackground(Integer... args) {
	    			int currentProgress = args[0];
	    			int finalProgress = args[1];
	    			int stepValue = currentProgress < finalProgress ? 1 : -1;
					try {
						while (currentProgress != finalProgress && isCancelled() == false)
						{
							Thread.sleep(5);
							currentProgress += stepValue;
							publishProgress(currentProgress);
						}
					} catch (InterruptedException e) {
			    		mProgressBar.setProgress(finalProgress);
					}
					return null;
				}

				@Override
				protected void onProgressUpdate(Integer... values) {
		    		mProgressBar.setProgress(values[0]);
				}
    		}.execute(mProgressBar.getProgress(), progress);
    	}
    }
}