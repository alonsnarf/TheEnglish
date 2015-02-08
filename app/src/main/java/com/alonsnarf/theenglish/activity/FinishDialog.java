package com.alonsnarf.theenglish.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.alonsnarf.theenglish.R;

public class FinishDialog extends Dialog implements View.OnClickListener
{
  private GameActivity activity;

  public FinishDialog(Context context)
  {
    super(context);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.dialog_finish);
    activity = (GameActivity) context;

    TextView result = (TextView) findViewById(R.id.score_result);
    Button buttonAgain = (Button) findViewById(R.id.button_again);
    Button buttonExit = (Button) findViewById(R.id.button_exit);

    result.setText(((GameActivity) context).getScore().getText().toString());
    buttonAgain.setOnClickListener(this);
    buttonExit.setOnClickListener(this);
  }

  @Override
  public void onClick(View v)
  {
    switch (v.getId())
    {
      case R.id.button_again:
        activity.setValueScore(0);
        activity.getScore().setText("0");
        activity.startTimer();
        activity.newLap();
        dismiss();
        break;
      case R.id.button_exit:
        onBackPressed();
        break;
    }
  }

  @Override
  public void onBackPressed()
  {
    super.onBackPressed();
    activity.finish();
    dismiss();
  }
}
