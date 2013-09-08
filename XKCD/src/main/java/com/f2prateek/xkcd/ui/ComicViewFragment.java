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

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.InjectView;
import com.f2prateek.xkcd.R;
import com.f2prateek.xkcd.dagger.ForActivity;
import com.f2prateek.xkcd.model.Comic;
import com.f2prateek.xkcd.ui.base.BaseFragment;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

/** A fragment to display a single comic. */
public class ComicViewFragment extends BaseFragment {

  public static final String COMIC_EXTRA_ARG = "com.f2prateek.xkcd.COMIC";

  @Inject ActivityTitleController titleController;
  @Inject @ForActivity Context context;
  @InjectView(R.id.comic_image) ImageView comic_image;
  private Comic comic;

  public static ComicViewFragment newInstance(Comic comic) {
    ComicViewFragment fragment = new ComicViewFragment();
    Bundle args = new Bundle();
    args.putParcelable(COMIC_EXTRA_ARG, comic);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    comic = getArguments().getParcelable(COMIC_EXTRA_ARG);
    setHasOptionsMenu(true);
  }

  @Override public void onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.comic_fragment, menu);
  }

  @Override public void onResume() {
    super.onResume();
    titleController.setTitle("Home Fragment");
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.comic_fragment, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Picasso.with(context).load(comic.getImg()).into(comic_image);
  }
}