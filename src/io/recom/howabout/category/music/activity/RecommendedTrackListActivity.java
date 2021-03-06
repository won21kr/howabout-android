package io.recom.howabout.category.music.activity;

import io.recom.howabout.HowaboutApplication;
import io.recom.howabout.R;
import io.recom.howabout.category.music.fragment.RecommendedTrackListFragment;
import io.recom.howabout.category.music.model.Track;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import android.content.res.Configuration;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

@ContentView(R.layout.activity_track_list)
public class RecommendedTrackListActivity extends TrackListActivity {

	@InjectResource(R.string.listen)
	protected String listenString;
	@InjectResource(R.string.add)
	protected String addString;

	protected RecommendedTrackListFragment recommendedTrackListFragment;

	protected String trackId;
	protected String trackTitle;
	protected String artistName;
	protected String thumbnailUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		trackId = bundle.getString("trackId");
		trackTitle = bundle.getString("trackTitle");
		artistName = bundle.getString("artistName");
		thumbnailUrl = bundle.getString("thumbnailUrl");

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(trackTitle);
		actionBar.setSubtitle(artistName);

		recommendedTrackListFragment = new RecommendedTrackListFragment();
		Bundle recommendedTrackListFragmentBundle = new Bundle();
		recommendedTrackListFragmentBundle.putString("trackId", trackId);
		recommendedTrackListFragment
				.setArguments(recommendedTrackListFragmentBundle);

		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.contentView, recommendedTrackListFragment,
						"music_recommend").commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.recommended_track_list, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals(listenString)) {
			HowaboutApplication application = (HowaboutApplication) getApplication();
			Track track = new Track();
			track.setTrackTitle(trackTitle);
			track.setArtistName(artistName);
			track.setThumbnailUrl(thumbnailUrl);
			application.getPlaylistAdapter().play(track);

			return true;
		} else if (item.getTitle().equals(addString)) {
			HowaboutApplication application = (HowaboutApplication) getApplication();
			Track track = new Track();
			track.setTrackTitle(trackTitle);
			track.setArtistName(artistName);
			track.setThumbnailUrl(thumbnailUrl);
			application.getPlaylistAdapter().add(track);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}
