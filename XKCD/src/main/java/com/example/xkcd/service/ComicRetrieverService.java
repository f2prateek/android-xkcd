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

package com.example.xkcd.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import com.example.xkcd.AppConstansts;
import com.example.xkcd.ComicCountEvent;
import com.example.xkcd.XKCDApi;
import com.example.xkcd.XKCDApplication;
import com.example.xkcd.model.XKCDComic;
import com.example.xkcd.util.Ln;
import com.squareup.otto.Bus;
import javax.inject.Inject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ComicRetrieverService extends Service implements Callback<XKCDComic> {

  @Inject Bus bus;
  @Inject XKCDApi xkcdApi;
  @Inject SharedPreferences sharedPreferences;

  @Override
  public void onCreate() {
    super.onCreate();
    ((XKCDApplication) getApplication()).inject(this);
    bus.register(this);
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    xkcdApi.getCurrentComic(this);
    return START_STICKY;
  }

  @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public void success(XKCDComic xkcdComic, Response response) {
    int old_count = sharedPreferences.getInt(AppConstansts.KEY_COMIC_COUNT, -1);
    int retrieved_count = xkcdComic.getNum();
    if (old_count < retrieved_count) {
      sharedPreferences.edit().putInt(AppConstansts.KEY_COMIC_COUNT, retrieved_count).commit();
      bus.post(new ComicCountEvent(retrieved_count));
    }
  }

  @Override public void failure(RetrofitError retrofitError) {
    Ln.e(retrofitError.getCause());
    Ln.e("ComicRetrieverService failure");
  }

  @Override public void onDestroy() {
    bus.unregister(this);
    super.onDestroy();
  }
}