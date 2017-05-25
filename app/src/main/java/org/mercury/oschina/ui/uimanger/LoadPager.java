package org.mercury.oschina.ui.uimanger;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.mercury.oschina.R;
import org.mercury.oschina.base.AppContext;
import org.mercury.oschina.utils.Utils;

import java.util.List;


/**
 * Created by mercury on 2016/8/2.
 */
public abstract class LoadPager extends FrameLayout
{
    
    private View mLoadingView;
    
    private View mSuccessView;
    
    private View mErrorView;
    
    public LoadPager(Context context)
    {
        this(context, null);
    }
    
    public LoadPager(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public LoadPager(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        
        initLoadPager();
    }
    // 现在我这个类主要功能是实现页面切换
    // 主要思想,把三种不同状态的页面叠加在一起
    // 根据不同的状态去切换不同的页面
    
    private void initLoadPager()
    {
        // 加载中的布局
        if (mLoadingView == null)
        {
            
            mLoadingView = View.inflate(AppContext.context, R.layout.page_loading, null);
        }
        // 添加布局
        addView(mLoadingView);
        // 成功的布局
        if (mSuccessView == null)
        {
            
            // 成功的界面我们这个ui加载框架不能具体实现,谁用谁实现
            mSuccessView = createSuccessView();
        }
        // 添加布局
        addView(mSuccessView);
        // 失败的布局
        
        if (mErrorView == null)
        {
            
            mErrorView = View.inflate(AppContext.context, R.layout.page_error, null);

            Button btn_reload = (Button) mErrorView.findViewById(R.id.btn_reload);
            btn_reload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    currentStaut = staut_loading;

                    showPager();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(300);
                            //重新加载页面
                            loadData();
                        }
                    }).start();



                }
            });
        }
        // 添加布局
        addView(mErrorView);
        
        // 切换不同的页面了
        showPager();

        //调用
        loadData();
    }
    
    // 谁用谁实现
    public abstract View createSuccessView();
    
    // 定义不同的状态
    public static final int staut_loading = 101;// 加载中
    
    public static final int staut_success = 102;// 成功
    
    public static final int staut_error = 103;// 失败
    
    // 记录当前的状态
    
    public  int currentStaut = staut_loading;// 进入第一个状态加载中
    
    // 切换页面的方法
    public void showPager()
    {
        // 爆力
        // 全部隐藏
        // 然后跟不同的状态显示不同的页面
        mLoadingView.setVisibility(View.GONE);
        mSuccessView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        
        switch (currentStaut)
        {
            case staut_loading:
                
                mLoadingView.setVisibility(View.VISIBLE);
                break;
            case staut_success:
                
                mSuccessView.setVisibility(View.VISIBLE);
                break;
            case staut_error:
                
                mErrorView.setVisibility(View.VISIBLE);
                break;
                
            default:
                break;
                
        }
        
    }
    
    // 当前跟服务器交互的数据格式是json
    // 想法是跟据服务器传过来的信息去自动切换当前的页面
    // json其实只有两种格式
    public void loadData()
    {

        // 得到解析后的json对象
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                System.out.println("当前方法加载数据了");



                // 得到解析后的json对象
                Object obj = onSubLoadData();
                
                // 检查当前的json应该赋值什么状态
                currentStaut = checkJson(obj);
                
                // 显示切换的页面
                Utils.runOnUIThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        
                        showPager();
                    }
                });
                
            }
        }).start();
        
    }
    
    // 根据不同的json对象来判断当前的状态是什么
    private int checkJson(Object obj)
    {
        // 如果当前的对象是空的,那么error
        // 如果当前的对象不是空,1,bean,2:集合
        // 判断当前是否是集合
        // 如果是集合,那么个数为0的那么也是error
        
        if (obj != null)
        {
            // 对象不为空
            // 有可能是集合
            if (obj instanceof List)
            {// 当前的对象是不是一个集合
                // 如果是集合
                List list = (List)obj;
                
                if (list.size() == 0)
                {
                    // 说明集合没有数据
                    return staut_error;
                }
                else
                {
                    // 数据大于0
                    return staut_success;
                }
            }
            else
            {
                return staut_success;
            }
            
        }
        else
        {
            // 对象为空
            return staut_error;
        }
        
    }
    
    // 谁想加载,谁去实现
    protected abstract Object onSubLoadData();
    
}
