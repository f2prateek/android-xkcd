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

package com.example.xkcd.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import com.example.xkcd.AppConstansts;
import com.example.xkcd.ComicCountEvent;
import com.example.xkcd.R;
import com.example.xkcd.service.ComicRetrieverService;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class MainActivity extends BaseActivity {
  @Inject SharedPreferences sharedPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent updateComicService = new Intent(this, ComicRetrieverService.class);
    startService(updateComicService);

    if (savedInstanceState == null) {
      XKCDListFragment gridFragment = XKCDListFragment.newInstance();
      getFragmentManager().beginTransaction().add(android.R.id.content, gridFragment).commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Produce public ComicCountEvent produceComicCount() {
    int comicCount = sharedPreferences.getInt(AppConstansts.KEY_COMIC_COUNT, -1);
    return new ComicCountEvent(comicCount);
  }

  @Subscribe public void onComicClicked(XKCDListFragment.OnComicClickedEvent onComicClickedEvent) {
    Intent viewComic = new Intent(this, XKCDComicActivity.class);
    viewComic.putExtra(XKCDComicActivity.COMIC_EXTRA_ARG, onComicClickedEvent.xkcdComic);
    startActivity(viewComic);
  }
}
