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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.InjectView;
import com.f2prateek.xkcd.AppConstansts;
import com.f2prateek.xkcd.R;
import com.f2prateek.xkcd.model.Comic;
import com.f2prateek.xkcd.ui.base.BaseFragment;
import com.squareup.picasso.Picasso;

/** A fragment to display a single comic. */
public class ViewComicFragment extends BaseFragment {

  private static final String COMIC_EXTRA_ARG = "comic";

  @InjectView(R.id.comic_image) ImageView comic_image;
  private Comic comic;

  public static ViewComicFragment newInstance(Comic comic) {
    ViewComicFragment fragment = new ViewComicFragment();
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

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.comic_fragment, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    bindView(comic);
  }

  private void bindView(Comic comic) {
    getActivity().getActionBar().setTitle(comic.getSafe_title());
    Picasso.with(getActivity()).load(comic.getImg()).into(comic_image);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.comic_fragment, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_comic_explanation:
        openUrl(AppConstansts.getExplationUrl(comic.getNum()));
        return true;
      case R.id.action_comic_info:
        showComicInfo();
        return true;
      case R.id.action_comic_link:
        openUrl(AppConstansts.getComicUrl(comic.getNum()));
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  // Show some information about the current comic
  public void showComicInfo() {
    bus.post(new OnShowComicInfoEvent(comic));
  }

  // Opens an activity to view the given url
  public void openUrl(String url) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    startActivity(intent);
  }

  public static class OnShowComicInfoEvent {
    public final Comic comic;

    public OnShowComicInfoEvent(Comic comic) {
      this.comic = comic;
    }
  }
}
