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

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;
import com.example.xkcd.ComicCountEvent;
import com.example.xkcd.R;
import com.example.xkcd.XKCDApi;
import com.example.xkcd.model.Comic;
import com.example.xkcd.ui.util.BindingAdapter;
import com.example.xkcd.util.Ln;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class XKCDListFragment extends BaseListFragment {

  @Inject XKCDApi xkcdApi;

  public static XKCDListFragment newInstance() {
    XKCDListFragment fragment = new XKCDListFragment();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Subscribe public void onComicCountUpdated(ComicCountEvent comicCountEvent) {
    if (comicCountEvent.comicCount > 0) {
      setListAdapter(new SimpleComicGridAdapter(getActivity(), comicCountEvent.comicCount));
    }
  }

  @Override public void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    Comic comic = (Comic) getListAdapter().getItem(position);
    bus.post(new OnComicClickedEvent(comic));
  }

  public static class OnComicClickedEvent {
    public final Comic comic;

    public OnComicClickedEvent(Comic comic) {
      this.comic = comic;
    }
  }

  class SimpleComicGridAdapter extends BindingAdapter {

    private final int comicCount;
    private final SparseArray<Comic> comicsCache;

    SimpleComicGridAdapter(Context context, int comicCount) {
      super(context);
      this.comicCount = comicCount;
      comicsCache = new SparseArray<Comic>(comicCount);
    }

    @Override public View newView(LayoutInflater inflater, int type, ViewGroup parent) {
      View view = inflater.inflate(R.layout.simple_comic_grid_item, parent, false);
      ViewHolder holder = new ViewHolder(view);
      view.setTag(holder);
      return view;
    }

    @Override public void bindView(int position, int type, View view) {
      final ViewHolder holder = (ViewHolder) view.getTag();
      long comicNumber = getItemId(position);
      xkcdApi.getComic(comicNumber, new Callback<Comic>() {
        @Override public void success(Comic comic, Response response) {
          comicsCache.put(comic.getNum(), comic);
          holder.title.setText(comic.getSafe_title());
        }

        @Override public void failure(RetrofitError retrofitError) {
          Ln.e(retrofitError.getCause());
        }
      });
    }

    @Override public int getCount() {
      return comicCount;
    }

    @Override public Object getItem(int position) {
      return comicsCache.get((int) getItemId(position));
    }

    @Override public long getItemId(int position) {
      return getCount() - position;
    }

    class ViewHolder {
      @InjectView(R.id.comic_title) TextView title;

      public ViewHolder(View view) {
        Views.inject(this, view);
      }
    }
  }
}

