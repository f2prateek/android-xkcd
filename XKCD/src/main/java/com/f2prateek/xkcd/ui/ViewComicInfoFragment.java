/*
 * Copyright 2013 Prateek Srivastava (@f2prateek)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f2prateek.xkcd.ui;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.InjectView;
import com.f2prateek.xkcd.R;
import com.f2prateek.xkcd.model.Comic;
import com.f2prateek.xkcd.ui.base.BaseFragment;

public class ViewComicInfoFragment extends BaseFragment {

  private static final String COMIC_EXTRA_ARG = "comic";

  @InjectView(R.id.comic_info_title) TextView comic_info_title;
  @InjectView(R.id.comic_info_date) TextView comic_info_date;
  @InjectView(R.id.comic_info_transcript) TextView comic_info_transcript;

  private Comic comic;

  public static ViewComicInfoFragment newInstance(Comic comic) {
    ViewComicInfoFragment fragment = new ViewComicInfoFragment();
    Bundle args = new Bundle();
    args.putParcelable(COMIC_EXTRA_ARG, comic);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    comic = getArguments().getParcelable(COMIC_EXTRA_ARG);
    setHasOptionsMenu(true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.comic_info_fragment, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    bindView(comic);
  }

  private void bindView(Comic comic) {
    comic_info_title.setText(comic.getTitle());
    comic_info_date.setText(
        DateUtils.getRelativeTimeSpanString(comic.getTimeInMillis(), System.currentTimeMillis(),
            DateUtils.DAY_IN_MILLIS));
    comic_info_transcript.setText(comic.getTranscript());
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.comic_info_fragment, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_show_comic:
        bus.post(new OnShowComicEvent(comic));
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public static class OnShowComicEvent {
    public final Comic comic;

    public OnShowComicEvent(Comic comic) {
      this.comic = comic;
    }
  }
}
