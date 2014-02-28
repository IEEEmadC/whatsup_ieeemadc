package pt.up.fe.nuieee.whatsup.api;

import java.net.UnknownHostException;

import android.os.AsyncTask;

public class FetchActivitiesTask<T> extends AsyncTask<Void, Void, Void> {

	private AsyncTaskHandler<T> mHandler;
	private T mResult;
	private Exception mError;

	public FetchActivitiesTask(AsyncTaskHandler<T> handler) {
		this.mHandler = handler;
	}

	@Override
	protected void onPostExecute(Void result) {
		
		if (mError == null)
		{
			mHandler.onSuccess(mResult);
		}
		else
		{
			mHandler.onFailure(mError);
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			mResult = (T) ServerAPI.queryActivitiesList();
		} catch (UnknownHostException e) {
			mError = e;
		}
		return null;
	}

}
