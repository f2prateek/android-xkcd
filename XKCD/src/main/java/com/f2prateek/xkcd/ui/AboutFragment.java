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

import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import com.f2prateek.xkcd.R;
import com.f2prateek.xkcd.XKCDApplication;
import javax.inject.Inject;

public class AboutFragment extends DialogFragment {

  @Inject PackageInfo packageInfo;
  @InjectView(R.id.tv_version_number) TextView tv_version_number;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(STYLE_NO_TITLE, getTheme());
    ((XKCDApplication) getActivity().getApplication()).inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_about, container, false);
    Views.inject(this, v);
    tv_version_number.setText(String.valueOf(packageInfo.versionName));
    getDialog().setTitle(R.string.about);
    return v;
  }

  @OnClick({
      R.id.tv_gplus, R.id.tv_twitter
  })
  public void openUrl(View v) {
    switch (v.getId()) {
      case R.id.tv_gplus:
        openUrl("https://plus.google.com/115299591800802520330/about");
        break;
      case R.id.tv_twitter:
        openUrl("https://twitter.com/f2prateek");
        break;
    }
  }

  public void openUrl(String url) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    startActivity(intent);
  }
}
