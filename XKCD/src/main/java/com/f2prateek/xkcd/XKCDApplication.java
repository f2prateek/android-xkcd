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

import android.app.Application;
import android.content.Intent;
import com.f2prateek.xkcd.service.ComicRetrieverService;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.picasso.Picasso;
import dagger.ObjectGraph;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

public class XKCDApplication extends Application {

  @Inject @Named("comic_count") int comicCount;
  @Inject Bus bus;

  private ObjectGraph applicationGraph;

  @Override public void onCreate() {
    super.onCreate();
    applicationGraph = ObjectGraph.create(getModules().toArray());
    applicationGraph.inject(this);
    bus.register(this);

    Picasso.with(this).setDebugging(BuildConfig.DEBUG);

    Intent updateComicService = new Intent(this, ComicRetrieverService.class);
    startService(updateComicService);
  }

  protected List<Object> getModules() {
    return Arrays.<Object>asList(new AndroidModule(this), new XKCDModule());
  }

  public void inject(Object object) {
    applicationGraph.inject(object);
  }

  @Produce public ComicCountEvent produceComicCount() {
    return new ComicCountEvent(comicCount);
  }
}