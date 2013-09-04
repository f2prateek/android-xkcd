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

package com.f2prateek.xkcd;

import android.content.res.Resources;
import com.f2prateek.xkcd.service.ComicRetrieverService;
import com.f2prateek.xkcd.ui.ComicListFragment;
import com.f2prateek.xkcd.ui.ComicViewFragment;
import com.f2prateek.xkcd.ui.MainActivity;
import com.f2prateek.xkcd.ui.ViewComicActivity;
import com.f2prateek.xkcd.ui.base.BaseActivity;
import com.f2prateek.xkcd.ui.base.BaseFragment;
import com.f2prateek.xkcd.ui.base.BaseListFragment;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import retrofit.RestAdapter;

@Module(
    injects = {
        BaseActivity.class, MainActivity.class, ViewComicActivity.class, BaseFragment.class,
        BaseListFragment.class, ComicRetrieverService.class, ComicListFragment.class,
        ComicViewFragment.class
    },
    complete = false)
public class CouchPotatoModule {

  @Provides @Singleton Bus provideOttoBus() {
    return new Bus();
  }

  @Provides @Named("default_animation_time") int provideDefaultAnimationTime(Resources resources) {
    return resources.getInteger(R.integer.config_defaultAnimationTime);
  }

  @Provides @Singleton XKCDApi provideXKCDApi() {
    return new RestAdapter.Builder().setServer("http://xkcd.com").build().create(XKCDApi.class);
  }
}