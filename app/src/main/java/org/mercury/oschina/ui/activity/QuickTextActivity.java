package org.mercury.oschina.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import org.mercury.oschina.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/15.
 */
public class QuickTextActivity extends AppCompatActivity
        implements EmojiconGridFragment.OnEmojiconClickedListener
        , EmojiconsFragment.OnEmojiconBackspaceClickedListener, View.OnClickListener {


    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.iv_send)
    ImageView mIvSend;
    @Bind(R.id.search_bar)
    RelativeLayout mSearchBar;
    @Bind(R.id.et_content)
    EditText mEtContent;
    @Bind(R.id.iv_img)
    ImageView mIvImg;
    @Bind(R.id.iv_clear_img)
    ImageView mIvClearImg;
    @Bind(R.id.rl_img)
    RelativeLayout mRlImg;
    @Bind(R.id.tv_clear)
    TextView mTvClear;
    @Bind(R.id.bottom)
    RelativeLayout mBottom;
    @Bind(R.id.ib_picture)
    ImageButton mIbPicture;
    @Bind(R.id.ib_mention)
    ImageButton mIbMention;
    @Bind(R.id.ib_trend_software)
    ImageButton mIbTrendSoftware;
    @Bind(R.id.ib_emoji_keyboard)
    ImageButton mIbEmojiKeyboard;
    @Bind(R.id.iv_show)
    ImageView mIvShow;

    @Bind(R.id.emoji_keyboard_fragment)
    FrameLayout mEmojiKeyboardFragment;

    private EditText mEt_album;
    private EditText mEt_viode;
    private Button mBt_dismiss;
    private AlertDialog mAlertDialog;
    private View mView1;
    private boolean isShowEmoji = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tweet_pub);
        ButterKnife.bind(this);
        initEmoji();
    }

    private void initEmoji() {
        EmojiconsFragment fragment = new EmojiconsFragment();
        //从sp中拿到数据，填充
        // init();
        initEmoticonsEditText();
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mEtContent.getText().toString();
                text = (160 - text.length()) + "";
                mTvClear.setText(text);
            }
        });
    }

    private void initEmoticonsEditText() {
        //  SimpleCommonUtils.initEmoticonsEditText(mEtContent);

        mEtContent.setFocusable(true);
        mEtContent.setFocusableInTouchMode(true);
        mEtContent.requestFocus();
        mIbEmojiKeyboard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!isShowEmoji) {
                    mEmojiKeyboardFragment.setVisibility(View.VISIBLE);
                } else {
                    mEmojiKeyboardFragment.setVisibility(View.GONE);
                }

                isShowEmoji = !isShowEmoji;

            }
        });
    }

    private void init() {
        //接收从点击相册传过来的photo然后设置到本页面的imageview中
        Bitmap receive = (Bitmap) (getIntent().getParcelableExtra("img_bitmap"));
        mIvShow.setImageBitmap(receive);

        mIvBack.setOnClickListener(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_send, R.id.search_bar, R.id.et_content, R.id.ib_picture, R.id.ib_mention, R.id.ib_trend_software, R.id.ib_emoji_keyboard})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_send:
                break;
            case R.id.search_bar:
                break;
            case R.id.et_content:
                break;
            case R.id.ib_picture:
                AlertDialog.Builder builder = new AlertDialog.Builder(QuickTextActivity.this);
                mView1 = View.inflate(getApplicationContext(), R.layout.activity_text_dialog, null);
                builder.setView(mView1);
                mAlertDialog = builder.show();
                initDialog();
                break;
            case R.id.ib_mention:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.ib_trend_software:
                String str = "#请输入软件名#";
                String text = mEtContent.getText().toString();
                mEtContent.setText(text + str);
                mEtContent.setSelection(text.length() + 1, text.length() + str.length() - 1);
                break;
            case R.id.ib_emoji_keyboard:
                Toast.makeText(getApplicationContext(), "做不来，不服SLOL", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initDialog() {
        mEt_album = (EditText) mView1.findViewById(R.id.et_album);
        mEt_viode = (EditText) mView1.findViewById(R.id.et_viode);
        mBt_dismiss = (Button) mView1.findViewById(R.id.bt_dismiss);
        mEt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
        mEt_viode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });
        mBt_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.iv_show)
    public void onClick() {

    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEtContent, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEtContent);
    }





}
