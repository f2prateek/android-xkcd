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

import android.content.Intent;
import android.os.Bundle;
import com.f2prateek.xkcd.ui.base.BaseActivity;
import com.squareup.otto.Subscribe;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      ListComicFragment fragment = ListComicFragment.newInstance();
      getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
    }
  }

  // TODO : dyanamic for screen size
  @Subscribe public void onComicClicked(ListComicFragment.OnComicClickedEvent onComicClickedEvent) {
    Intent viewComic = new Intent(this, ViewComicActivity.class);
    viewComic.putExtra(ViewComicActivity.COMIC_EXTRA_ARG, onComicClickedEvent.comic);
    startActivity(viewComic);
  }
}
