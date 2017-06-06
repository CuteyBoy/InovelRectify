package com.tv.inovelrectify.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tv.inovelrectify.R;

/**
 * 功能描述：矫正视图
 * 开发状况：正在开发中
 * 开发作者：黎丝军
 * 开发时间：2017/5/9- 17:28
 */

public class TvRectifyView extends FrameLayout {

    //当前方向
    private Direction mCurrentDir;
    //上左图标
    private ImageView mLeftUpRightIv;
    //上下图标
    private ImageView mLeftUpDownIv;
    //上右图标
    private ImageView mRightUpLeftIv;
    //右下图标
    private ImageView mRightUpDownIv;
    //下右图标
    private ImageView mLeftDownRightIv;
    //下上图标
    private ImageView mLeftDownUpIv;
    //下左图标
    private ImageView mRightDownLeftIv;
    //右下上图标
    private ImageView mRightDownUpIv;
    //中间上下图标
    private ImageView mTopDownIv;
    //中间左右图标
    private ImageView mLeftRightIv;
    //显示左右值
    private TextView mLeftRightTv;
    //显示上下值
    private TextView mUpDownTv;
    //左上方
    private Direction mLeftUpDir;
    //左下方
    private Direction mLeftDownDir;
    //右上方
    private Direction mRightUpDir;
    //右下方
    private Direction mRightDownDir;
    //矫正回调接口
    private IRectifyCallback mCallback;

    public TvRectifyView(Context context) {
        this(context,null);
    }

    public TvRectifyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TvRectifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_rectify,this);
        mLeftUpRightIv = (ImageView) findViewById(R.id.iv_left_up_right);
        mLeftUpDownIv = (ImageView) findViewById(R.id.iv_up_down);
        mRightUpLeftIv = (ImageView) findViewById(R.id.iv_down_left);
        mRightUpDownIv = (ImageView) findViewById(R.id.iv_right_down);
        mLeftDownRightIv = (ImageView) findViewById(R.id.iv_down_right);
        mLeftDownUpIv = (ImageView) findViewById(R.id.iv_down_up);
        mRightDownLeftIv = (ImageView) findViewById(R.id.iv_up_left);
        mRightDownUpIv = (ImageView) findViewById(R.id.iv_right_up);

        mTopDownIv = (ImageView) findViewById(R.id.iv_top_down);
        mLeftRightIv = (ImageView) findViewById(R.id.iv_left_right);
        mLeftRightTv = (TextView) findViewById(R.id.tv_left_right);
        mUpDownTv = (TextView) findViewById(R.id.tv_top_down);

        mLeftUpDir = new Direction(0,0,DirType.LEFT_UP);
        mLeftDownDir = new Direction(0, 50, DirType.LEFT_DOWN);
        mRightUpDir = new Direction(0,0, DirType.RIGHT_UP);
        mRightDownDir = new Direction(0, 50, DirType.RIGHT_DOWN);

        setDirectionState(mRightDownDir);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                setDirectionState(mCurrentDir);
                break;
            default:
                break;
        }
        mCurrentDir.onKeyDown(keyCode);
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置接口响应
     * @param callback 接口实例
     */
    public void setRectifyCallback(IRectifyCallback callback) {
        mCallback = callback;
    }

    /**
     * 设置左右图标
     * @param resId 资源Id
     */
    private void setLeftRightIcon(int resId) {
        mLeftRightIv.setBackgroundResource(resId);
    }

    /**
     * 设置上下图标
     * @param resId 资源Id
     */
    private void setUpDownIcon(int resId) {
        mTopDownIv.setBackgroundResource(resId);
    }

    /**
     * 矫正改变
     * @param keyCode 按键类型
     */
    private void rectifyChange(int keyCode) {
        mLeftRightTv.setText(String.valueOf(mCurrentDir.getLeftRightCount()));
        mUpDownTv.setText(String.valueOf(mCurrentDir.getUpDownCount()));
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(mCallback != null) {
                    mCallback.onLeftChange(mCurrentDir);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if(mCallback != null) {
                    mCallback.onRightChange(mCurrentDir);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if(mCallback != null) {
                    mCallback.onUpChange(mCurrentDir);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if(mCallback != null) {
                    mCallback.onDownChange(mCurrentDir);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置方向状态
     * @param dir 选择的方向
     */
    private void setDirectionState(Direction dir) {
        switch (dir.getDirType()) {
            case LEFT_UP:
                mLeftUpRightIv.setVisibility(INVISIBLE);
                mLeftUpDownIv.setVisibility(INVISIBLE);
                mRightUpLeftIv.setVisibility(VISIBLE);
                mRightUpDownIv.setVisibility(VISIBLE);
                mCurrentDir = mRightUpDir;
                setLeftRightIcon(R.mipmap.ic_left);
                setUpDownIcon(R.mipmap.ic_down);
                break;
            case LEFT_DOWN:
                mLeftDownRightIv.setVisibility(INVISIBLE);
                mLeftDownUpIv.setVisibility(INVISIBLE);
                mRightDownLeftIv.setVisibility(VISIBLE);
                mRightDownUpIv.setVisibility(VISIBLE);
                mCurrentDir = mRightDownDir;
                setLeftRightIcon(R.mipmap.ic_left);
                setUpDownIcon(R.mipmap.ic_up);
                break;
            case RIGHT_UP:
                mRightUpLeftIv.setVisibility(INVISIBLE);
                mRightUpDownIv.setVisibility(INVISIBLE);
                mLeftDownUpIv.setVisibility(VISIBLE);
                mLeftDownRightIv.setVisibility(VISIBLE);
                mCurrentDir = mLeftDownDir;
                setLeftRightIcon(R.mipmap.ic_right);
                setUpDownIcon(R.mipmap.ic_up);
                break;
            case RIGHT_DOWN:
                mRightDownLeftIv.setVisibility(INVISIBLE);
                mRightDownUpIv.setVisibility(INVISIBLE);
                mLeftUpRightIv.setVisibility(VISIBLE);
                mLeftUpDownIv.setVisibility(VISIBLE);
                mCurrentDir = mLeftUpDir;
                setLeftRightIcon(R.mipmap.ic_right);
                setUpDownIcon(R.mipmap.ic_down);
            default:
                break;
        }
        if(mCallback != null) {
            mCallback.onDirChange(mCurrentDir.getDirType());
        }
    }

    /**
     * 设置矫正接口回调
     */
    public interface IRectifyCallback {

        /**
         * 左改变
         * @param dir 方向
         */
        void onLeftChange(Direction dir);

        /**
         * 右改变
         * @param dir 方向
         */
        void onRightChange(Direction dir);

        /**
         * 上改变
         * @param dir 方向
         */
        void onUpChange(Direction dir);

        /**
         * 下改变
         * @param dir 方向
         */
        void onDownChange(Direction dir);

        /**
         * 方向改变
         * @param dirType 方向类型
         */
        void onDirChange(DirType dirType);
    }

    /**
     * 方向
     */
    public class Direction {
        //方向类型
        private DirType dirType;
        //上下按键计数器
        private int upDownCount;
        //左右按键计数器
        private int leftRightCount;

        public Direction(int leftRCount, int upDCount, DirType dirType) {
            this.dirType = dirType;
            upDownCount = upDCount;
            leftRightCount = leftRCount;
        }

        public DirType getDirType() {
            return dirType;
        }

        public int getUpDownCount() {
            return upDownCount;
        }

        public int getLeftRightCount() {
            return leftRightCount;
        }

        /**
         * 更新计数器状态
         * @param keyCode 按键值
         */
        public boolean onKeyDown(int keyCode) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if(leftRightCount > 0) {
                        leftRightCount --;
                        rectifyChange(keyCode);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if(leftRightCount < 50) {
                        leftRightCount ++;
                        rectifyChange(keyCode);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    if(upDownCount > 0) {
                        upDownCount --;
                        rectifyChange(keyCode);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if(upDownCount < 50) {
                        upDownCount ++;
                        rectifyChange(keyCode);
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    /**
     * 方向
     */
   public enum DirType {
        /**
         * 左上
         */
        LEFT_UP,
        /**
         * 右上
         */
        RIGHT_UP,
        /**
         * 左下
         */
        LEFT_DOWN,
        /**
         * 右下
         */
        RIGHT_DOWN
    }
}
