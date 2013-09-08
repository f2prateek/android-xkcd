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

package com.f2prateek.xkcd.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.Window;
import butterknife.Views;
import com.f2prateek.xkcd.XKCDApplication;
import com.f2prateek.xkcd.dagger.ActivityModule;
import com.squareup.otto.Bus;
import dagger.ObjectGraph;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

/**
 * A Base {@link android.app.Activity} that injects itself into the {@link
 * dagger.ObjectGraph} and performs view injection
 */
public abstract class BaseActivity extends Activity {

  @Inject Bus bus;
  private ObjectGraph activityGraph;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    activityGraph = ((XKCDApplication) getApplication()).getApplicationGraph().plus(getModules().toArray());

    // Perform injection so that when this call returns all dependencies will be available for use.
    activityGraph.inject(this);

    // All activities will require this
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
  }

  @Override protected void onDestroy() {
    // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
    // soon as possible.
    activityGraph = null;
    super.onDestroy();
  }

  /**
   * A list of modules to use for the individual activity graph. Subclasses can override this
   * method to provide additional modules provided they call and include the modules returned by
   * calling {@code super.getModules()}.
   */
  protected List<Object> getModules() {
    return Arrays.<Object>asList(new ActivityModule(this));
  }

  /** Inject the supplied {@code object} using the activity-specific graph. */
  public void inject(Object object) {
    activityGraph.inject(object);
  }

  @Override public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    // Perform "view injection"
    Views.inject(this);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onResume() {
    super.onResume();
    bus.register(this);
  }

  @Override protected void onPause() {
    bus.unregister(this);
    super.onPause();
  }
}
