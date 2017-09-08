package top.trumeet.redirectstorage.simple;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import top.trumeet.redirectstorage.RedirectStorage;

public class MainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        updateText();
        Switch s = findViewById(R.id.switch_widget);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    RedirectStorage.enable(getExternalFilesDir(null)
                    .getAbsolutePath().substring(RedirectStorage.getRealPath().getAbsolutePath()
                            .length()));
                } else {
                    RedirectStorage.disable();
                }
                updateText();
            }
        });
    }

    private void updateText () {
        textView.setText(Html.fromHtml(getString(R.string.text,
                Environment.getExternalStorageDirectory().getAbsolutePath(),
                String.valueOf(RedirectStorage.isEnable()),
                RedirectStorage.getRealPath())));
    }
}
