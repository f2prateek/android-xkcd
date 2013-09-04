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

import android.os.Bundle;
import com.example.xkcd.model.Comic;
import com.example.xkcd.ui.base.BaseActivity;

public class ViewComicActivity extends BaseActivity {

  public static final String COMIC_EXTRA_ARG = ComicViewFragment.COMIC_EXTRA_ARG;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      Comic comic = getIntent().getExtras().getParcelable(COMIC_EXTRA_ARG);
      ComicViewFragment fragment = ComicViewFragment.newInstance(comic);
      getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
    }
  }
}