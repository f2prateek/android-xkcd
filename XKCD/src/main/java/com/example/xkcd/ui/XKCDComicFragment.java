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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.InjectView;
import com.example.xkcd.R;
import com.example.xkcd.model.XKCDComic;
import com.example.xkcd.util.Ln;
import com.squareup.picasso.Picasso;

/** A fragment to display a single comic. */
public class XKCDComicFragment extends BaseFragment {

  public static final String COMIC_EXTRA_ARG = "com.f2prateek.xkcd.COMIC";

  @InjectView(R.id.comic_image) ImageView comic_image;
  private XKCDComic comic;

  public static XKCDComicFragment newInstance(XKCDComic comic) {
    XKCDComicFragment fragment = new XKCDComicFragment();
    Bundle args = new Bundle();
    args.putParcelable(COMIC_EXTRA_ARG, comic);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    comic = getArguments().getParcelable(COMIC_EXTRA_ARG);
    Ln.d("Comic got %s", comic);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.comic_fragment, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Picasso.with(getActivity()).load(comic.getImg()).into(comic_image);
  }
}
