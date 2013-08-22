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

import com.example.xkcd.model.Comic;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface XKCDApi {
  @GET("/info.0.json") void getCurrentComic(Callback<Comic> cb);

  @GET("/{id}/info.0.json") void getComic(@Path("id") long id, Callback<Comic> cb);
}