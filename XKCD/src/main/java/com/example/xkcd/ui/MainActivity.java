package com.example.xkcd.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import com.example.xkcd.AppConstansts;
import com.example.xkcd.ComicCountEvent;
import com.example.xkcd.R;
import com.example.xkcd.service.ComicRetrieverService;
import com.example.xkcd.util.Ln;
import com.squareup.otto.Produce;
import javax.inject.Inject;

public class MainActivity extends BaseActivity {
  @Inject SharedPreferences sharedPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent updateComicService = new Intent(this, ComicRetrieverService.class);
    startService(updateComicService);

    if (savedInstanceState == null) {
      XKCDListFragment gridFragment = XKCDListFragment.newInstance();
      getFragmentManager().beginTransaction().add(android.R.id.content, gridFragment).commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Produce public ComicCountEvent produceComicCount() {
    int comicCount = sharedPreferences.getInt(AppConstansts.KEY_COMIC_COUNT, -1);
    Ln.d("producedComicCount %d", comicCount);
    return new ComicCountEvent(comicCount);
  }
}
