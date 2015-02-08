package com.alonsnarf.theenglish;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.WindowFeature;

import static android.view.Window.FEATURE_INDETERMINATE_PROGRESS;
import static android.view.Window.FEATURE_NO_TITLE;

@Fullscreen
@EActivity(R.layout.activity_main)
@WindowFeature({FEATURE_NO_TITLE, FEATURE_INDETERMINATE_PROGRESS })
public class MainActivity extends ActionBarActivity {

}
