package org.mercury.oschina.explorer.ui.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.oschina.common.widget.Loading;

import org.mercury.oschina.R;
import org.mercury.oschina.base.BaseActivity;
import org.mercury.oschina.http.HttpApi;
import org.mercury.oschina.http.RequestHelper;
import org.mercury.oschina.synthesis.bean.News;
import org.mercury.oschina.synthesis.bean.NewsResponse;
import org.mercury.oschina.utils.StringUtils;

import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * created by Mercury at 2017/9/1
 * descript: 摇一摇
 * 受限于openapi可提供的信息，当前仅仅选取随机页数请求得到的新闻列表中随机一条新闻资讯
 */
public class ShakeActivity extends BaseActivity implements SensorEventListener {

    @Bind(R.id.toolbar)
    Toolbar      toolbar;
    @Bind(R.id.iv_shake)
    ImageView    ivShake;
    @Bind(R.id.loading)
    Loading      loading;
    @Bind(R.id.tv_state)
    TextView     tvState;
    @Bind(R.id.cv_shake)
    CardView     cvShake;
    @Bind(R.id.layoutRoot)
    LinearLayout layoutRoot;

    private SensorManager mSensorManager;
    private Vibrator mVibrator;
    private boolean isRegistered;

    public static final int CATALOG = 1;
    public static final int RANDOM_MAX = 3000;

    private News randomItem;

    public static final int UPDATE_INTERVAL = 50;

    private int speedThreshold = 45;    //这个值越大摇的力气需要越大
    private long sensorLastUpdateTime;
    private float sensorLastX;
    private float sensorLastY;
    private float sensorLastZ;

    private int delayTime = 5;
    private boolean isLoading;
    private LayoutInflater mInflater;

    @Override
    protected int getContentView() {
        return R.layout.activity_shake;
    }

    @Override
    protected void initWidget() {
        toolbar.setTitle("摇一摇");
        setSupportActionBar(toolbar);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    protected void initData() {
        registerSensor();
        mInflater = LayoutInflater.from(this);
    }

    private void request() {
        HttpApi retrofitCall = RequestHelper.getInstance().getRetrofitCall(HttpApi.class);
        Random random = new Random();
        int page = random.nextInt(RANDOM_MAX);
        Log.i("param", page + "");
        Call<NewsResponse> newsList = retrofitCall.getNewsList(CATALOG, page);
        newsList.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                Log.i("success", "onSuccess: ");
                NewsResponse body = response.body();
                if (body == null) {
                    return;
                }
                if (body.getNewslist().size() == 0) {
                    tvState.setText("没有获取到数据,再摇一下吧");
                    return;
                }
                int size = body.getNewslist().size();
                int randowPos = new Random().nextInt(size);
                randomItem = body.getNewslist().get(randowPos);
                onSuccess();
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                isLoading = false;
                showToast(getResources().getString(R.string.state_network_error));
            }
        });
    }

    /**
     * 响提示音,并且展示随机获取的新闻条目
     */
    private void onSuccess() {
        isLoading = false;
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.shake);
        mediaPlayer.start();
        View view = mInflater.inflate(R.layout.view_shake_news, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvTitle.setText(randomItem.getTitle());
        tvTime.setText(StringUtils.friendlyTime(randomItem.getPubDate()));

        cvShake.removeAllViews();
        cvShake.addView(view);
        ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(350);
        cvShake.startAnimation(animation);
    }

    /**
     * 该回调差不多50~70Ms执行一次,因此晃动速度阀值和时间间隔的关系也要根据此而定
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentUpdateTime = System.currentTimeMillis();
        long timeInterval = currentUpdateTime - sensorLastUpdateTime;
        //如果距离上次摇一摇时间太近,不处理
        if (timeInterval < UPDATE_INTERVAL) {
            return;
        }
        sensorLastUpdateTime = currentUpdateTime;
        Log.i("时间间隔", timeInterval + "");

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        Log.i("本次坐标", "x: " + x + ",y: " + y + ",z: " + z);
        float deltaX = x - sensorLastX;
        float deltaY = y - sensorLastY;
        float deltaZ = z - sensorLastZ;
        Log.i("上次坐标", "sensorLastX: " + sensorLastX + ",sensorLastY: " + sensorLastY + ",sensorLastZ: " + sensorLastZ);

        sensorLastX = x;
        sensorLastY = y;
        sensorLastZ = z;

        //速度值不能算错了,否则会一直振动
        double speed = (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ + deltaZ) /
                timeInterval) * 100;
        if (speed >= speedThreshold && !isLoading) {
            isLoading = true;
            mVibrator.vibrate(300);
            request();
            Log.i("命中", speed + "");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 注册传感器回调的地方,类似于setOnClickListener
     */
    public void registerSensor() {
        if (mSensorManager != null && !isRegistered) {
            Sensor defaultSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (defaultSensor != null) {
                mSensorManager.registerListener(this, defaultSensor, SensorManager
                        .SENSOR_DELAY_GAME);
                isRegistered = true;
            }
        }
    }

    public void unregisterSensor() {
        if (mSensorManager != null && isRegistered) {
            mSensorManager.unregisterListener(this);
            isRegistered = false;
        }
    }


    @OnClick(R.id.cv_shake)
    public void onClick() {
        // 	新闻类型 [0-链接新闻|1-软件推荐|2-讨论区帖子|3-博客|4-普通新闻|7-翻译文章]
        // TODO: 2017/9/2 根据类型跳转到对应的详情页面
        showToast("cardview");
    }

    /**
     * 在销毁该页面的时候,都应该把传感器注销,否则在其它地方仍然可以震动
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            unregisterSensor();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        unregisterSensor();
        super.onBackPressed();
    }
}
