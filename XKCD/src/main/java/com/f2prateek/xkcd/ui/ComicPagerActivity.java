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

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.f2prateek.xkcd.ComicCountEvent;
import com.f2prateek.xkcd.ui.base.BaseActivity;
import com.squareup.otto.Subscribe;

public class ComicPagerActivity extends BaseActivity {
  ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    viewPager = new ViewPager(this);
    setContentView(viewPager);
  }

  @Subscribe public void onComicCountUpdated(ComicCountEvent comicCountEvent) {
    if (comicCountEvent.comicCount > 0) {
      viewPager.setAdapter(new ComicPagerAdapter(getFragmentManager(), comicCountEvent.comicCount));
    }
  }

  private class ComicPagerAdapter extends FragmentPagerAdapter {

    final int comicCount;

    public ComicPagerAdapter(FragmentManager fm, int comicCount) {
      super(fm);
      this.comicCount = comicCount;
    }

    @Override public Fragment getItem(int i) {
      return ComicViewFragment.newInstance(i);
    }

    @Override public int getCount() {
      return comicCount;
    }
  }
}
