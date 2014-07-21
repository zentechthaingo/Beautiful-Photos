package com.lgvalle.photosnearby.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.ListView;
import butterknife.InjectView;
import com.lgvalle.photosnearby.BaseFragment;
import com.lgvalle.photosnearby.R;
import com.lgvalle.photosnearby.event.PhotosAvailableEvent;
import com.lgvalle.photosnearby.model.Photo500Px;
import com.lgvalle.photosnearby.util.BusHelper;
import com.lgvalle.photosnearby.util.RendererAdapter;
import com.squareup.otto.Subscribe;

/**
 * Created by lgvalle on 21/07/14.
 */
public class PhotosFragment extends BaseFragment implements AbsListView.OnScrollListener{
	private static final String TAG = PhotosFragment.class.getSimpleName();
	private RendererAdapter<Photo500Px> adapter;

	@InjectView(R.id.photos_list)
	ListView list;
	private int preLast;


	public static PhotosFragment newInstance() {
		PhotosFragment f = new PhotosFragment();
		Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onResume() {
		super.onResume();
		BusHelper.register(this);
		Log.d(TAG, "[PhotosFragment - onResume] - (line 38): " + "");
	}

	@Override
	public void onPause() {
		super.onPause();
		BusHelper.unregister(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView absListView, int i) {

	}

	@Override
	public void onScroll(AbsListView lw, final int firstVisibleItem,
	                     final int visibleItemCount, final int totalItemCount) {
		switch(lw.getId()) {
			case R.id.photos_list:

				// Make your calculation stuff here. You have all your
				// needed info from the parameters of this function.

				// Sample calculation to determine if the last
				// item is fully visible.
				final int lastItem = firstVisibleItem + visibleItemCount;
				if(lastItem == totalItemCount) {
					if(preLast!=lastItem){ //to avoid multiple calls for last item
						Log.d("Last", "Last");
						preLast = lastItem;
					}
				}
		}

	}

	@Override
	protected void initLayout() {
		PhotosRenderer renderer = new PhotosRenderer();
		adapter = new RendererAdapter<Photo500Px>(LayoutInflater.from(getActivity()), renderer, getActivity());
		list.setAdapter(adapter);
		list.setOnScrollListener(this);
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_photos_list;
	}

	@Subscribe
	public void onNewPhotosEvent(PhotosAvailableEvent event) {
		Log.d(TAG, "[PhotosFragment - onNewPhotosEvent] - (line 58): " + "");
		if (event != null && event.getPhotos() != null) {
			adapter.setElements(event.getPhotos());

		}

	}
}