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
import android.view.Menu;
import android.view.MenuItem;
import com.f2prateek.xkcd.R;
import com.f2prateek.xkcd.model.Comic;
import com.f2prateek.xkcd.ui.base.BaseActivity;
import com.squareup.otto.Subscribe;

public class ViewComicActivity extends BaseActivity {

  public static final String COMIC_EXTRA_ARG = "com.f2prateek.xkcd.COMIC";
  public static final String COMIC_INFO_FRAGMENT_TAG = "com.f2prateek.xkcd.COMIC_INFO_FRAGMENT";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      Comic comic = getIntent().getExtras().getParcelable(COMIC_EXTRA_ARG);
      ViewComicFragment fragment = ViewComicFragment.newInstance(comic);
      getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
    }

    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setDisplayShowHomeEnabled(false);
  }

  // TODO : dyanamic for screen size
  @Subscribe
  public void onShowComicInfo(ViewComicFragment.OnShowComicInfoEvent onShowComicInfoEvent) {
    ViewComicInfoFragment fragment = ViewComicInfoFragment.newInstance(onShowComicInfoEvent.comic);
    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, fragment, COMIC_INFO_FRAGMENT_TAG)
        .addToBackStack("info")
        .commit();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    if (getFragmentManager().findFragmentByTag(COMIC_INFO_FRAGMENT_TAG) != null) {
      getMenuInflater().inflate(R.menu.activity_comic_info, menu);
    }
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_show_comic:
        getFragmentManager().popBackStackImmediate();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}