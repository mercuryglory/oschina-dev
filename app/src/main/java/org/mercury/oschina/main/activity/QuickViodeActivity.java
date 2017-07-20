package org.mercury.oschina.main.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.mercury.oschina.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuickViodeActivity extends AppCompatActivity {

    @Bind(R.id.recordview_start)
    ImageView mRecordviewStart;
    @Bind(R.id.recordview_delete)
    ImageView mRecordviewDelete;
    @Bind(R.id.recordview_listen)
    ImageView mRecordviewListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_viode);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.recordview_start, R.id.recordview_delete, R.id.recordview_listen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recordview_start:
                Toast.makeText(getApplicationContext(), "哎哟，没有做", Toast.LENGTH_SHORT).show();
                break;
            case R.id.recordview_delete:
                Toast.makeText(getApplicationContext(), "哎哟，真没有做", Toast.LENGTH_SHORT).show();

                break;
            case R.id.recordview_listen:
                Toast.makeText(getApplicationContext(), "哎哟，还是没有做", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
