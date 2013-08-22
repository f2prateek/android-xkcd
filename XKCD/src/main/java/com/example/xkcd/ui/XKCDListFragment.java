package com.example.xkcd.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;
import com.example.xkcd.ComicCountEvent;
import com.example.xkcd.R;
import com.example.xkcd.XKCDApi;
import com.example.xkcd.model.XKCDComic;
import com.example.xkcd.ui.util.BindingAdapter;
import com.example.xkcd.util.Ln;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
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
    Ln.d("onCreate XKCDListFragment");
    setRetainInstance(true);
  }

  @Subscribe public void onComicCountUpdated(ComicCountEvent comicCountEvent) {
    Ln.d("onComicCountUpdated %d", comicCountEvent.comicCount);
    if (comicCountEvent.comicCount > 0) {
      setListAdapter(new SimpleComicGridAdapter(getActivity(), comicCountEvent.comicCount));
    }
  }

  class SimpleComicGridAdapter extends BindingAdapter {

    private final int comicCount;

    public SimpleComicGridAdapter(Context context, int comicCount) {
      super(context);
      this.comicCount = comicCount;
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
      xkcdApi.getComic(comicNumber, new Callback<XKCDComic>() {
        @Override public void success(XKCDComic xkcdComic, Response response) {
          Picasso.with(getContext()).load(xkcdComic.getImg()).into(holder.thumbnail);
          holder.title.setText(xkcdComic.getSafe_title());
        }

        @Override public void failure(RetrofitError retrofitError) {

        }
      });
    }

    @Override public int getCount() {
      return comicCount;
    }

    @Override public Object getItem(int position) {
      return null;
    }

    @Override public long getItemId(int position) {
      return comicCount - position;
    }

    class ViewHolder {
      @InjectView(R.id.comic_thumbnail) ImageView thumbnail;
      @InjectView(R.id.comic_title) TextView title;

      public ViewHolder(View view) {
        Views.inject(this, view);
      }
    }
  }
}

