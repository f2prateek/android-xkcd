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

package com.example.xkcd;

import android.content.res.Resources;
import com.example.xkcd.service.ComicRetrieverService;
import com.example.xkcd.ui.BaseActivity;
import com.example.xkcd.ui.BaseFragment;
import com.example.xkcd.ui.BaseListFragment;
import com.example.xkcd.ui.ComicFragment;
import com.example.xkcd.ui.MainActivity;
import com.example.xkcd.ui.ViewComicActivity;
import com.example.xkcd.ui.XKCDListFragment;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import retrofit.RestAdapter;

@Module(
    injects = {
        BaseActivity.class, MainActivity.class, ViewComicActivity.class, BaseFragment.class,
        BaseListFragment.class, ComicRetrieverService.class, XKCDListFragment.class,
        ComicFragment.class
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