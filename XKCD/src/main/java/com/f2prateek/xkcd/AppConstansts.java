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

public class AppConstansts {

  // This is the base url of an image, format with String.format
  public static final String KEY_COMIC_COUNT = "key_image_count";

  // This is the base url of an image, format with String.format
  private static final String BASE_COMIC_URL = "http://xkcd.com/%d";

  // This is the base url of an image, format with String.format
  private static final String BASE_EXPLANATION_URL =
      "http://www.explainxkcd.com/wiki/index.php?title=&d";

  public static String getComicUrl(int num) {
    return String.format(BASE_COMIC_URL, num);
  }

  public static String getExplationUrl(int num) {
    return String.format(BASE_EXPLANATION_URL, num);
  }
}