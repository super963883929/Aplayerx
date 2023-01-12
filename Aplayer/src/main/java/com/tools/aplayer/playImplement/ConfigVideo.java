package com.tools.aplayer.playImplement;



import static com.tools.aplayer.playInterface.IConfigVideo.AspectRatioType.RatioNative;
import static com.tools.aplayer.playInterface.IConfigVideo.AspectRatioType.RationFullScreen;

import android.util.Log;
import android.view.View;

import com.aplayer.APlayerAndroid;
import com.tools.aplayer.model.Content;
import com.tools.aplayer.model.PlayConfig;
import com.tools.aplayer.playInterface.IConfigVideo;
import com.tools.aplayer.utils.APlayerSationUtil;
import com.tools.aplayer.utils.BoolUtil;
import com.tools.aplayer.utils.DisplayAreaSetUtil;
import com.tools.aplayer.utils.UIUtil;




public class ConfigVideo implements IConfigVideo {
    private APlayerAndroid mAPlayerAndroid = null;
    private static final String ERROR_TAGE = Content.APLAYER_DEMO_LOG_PREF_TAG + ConfigVideo.class.getSimpleName();
    private static final String WARE_TAGE = Content.APLAYER_DEMO_LOG_PREF_TAG + ConfigVideo.class.getSimpleName();
    private String mFilePath = null;
    private PlayConfig.PlayerListener mListener;
    private View mPlayDisplayView;
    private AspectRatioType mAspectRatioType;
    //private static VideoSetView.DecodeSet mDecodeSet = null;

    private static final int SPEED_BASE = 100;
    //通过为监听器设置参数的方式，避免重复注册监听器(注册监听器事件时，同一个对象不会重复注册)
    private APlayerAndroid.OnPlayCompleteListener mOnPlayCompleteListener = new PlayerCloseComplate();
    public ConfigVideo(APlayerAndroid aPlayerAndroid, String filePath, PlayConfig.PlayerListener listener, View playDisplayView) {
        mAPlayerAndroid = aPlayerAndroid;
        mFilePath = filePath;
        mListener = listener;
        mPlayDisplayView = playDisplayView;

        init();
    }

    @Override
    public boolean useHardWareDecode(boolean isUse) {
        int retCode = mAPlayerAndroid.setConfig(APlayerAndroid.CONFIGID.HW_DECODER_USE, BoolUtil.BoolToAPlayerConfigBool(isUse));
        boolean bret = BoolUtil.APlayerConfigValToBool(retCode);

        if(!bret)
        {
            Log.e(ERROR_TAGE, "useHardWareDecode() faile, " + "retCode = " + retCode);
        }

        return bret;
    }

    @Override
    public boolean isUseHardWareDecode() {
        String retStr = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.HW_DECODER_USE);
        return BoolUtil.APlayerConfigValToBool(retStr);
    }

    @Override
    public boolean isEnableHardWareDecode() {
        String retStr = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.HW_DECODER_ENABLE);
        Log.e("HW_DECODER_ENABLE", "retStr = " + retStr);
        return BoolUtil.APlayerConfigValToBool(retStr);
    }

    @Override
    public boolean stopPlayer(IStopCompletCallback stopCallback, boolean isOnlyCallOnStopSuccess) {
        PlayerCloseComplate listener = (PlayerCloseComplate)mOnPlayCompleteListener;
        listener.setStopCallback(stopCallback);
        listener.setIsOnlyCallOnSuccess(isOnlyCallOnStopSuccess);

        //First Call Pause，Ensure OnPlayCompleteListener be call, then recover listener
        if(Content.APLYER_SUCCESS != mAPlayerAndroid.pause())
        {
            return false;
        }

        //may over OnPlayComplete listener, will recover listener in PlayerCloseComplate
        mAPlayerAndroid.setOnPlayCompleteListener(mOnPlayCompleteListener);

        boolean bret = true;
        if(!isPlayerStop()) {
            bret = (Content.APLYER_SUCCESS == mAPlayerAndroid.close());
        }
        return bret;
    }

    @Override
    public boolean isPlayerStop() {
        return (mAPlayerAndroid.getState() == APlayerAndroid.PlayerState.APLAYER_READ);
    }

    @Override
    public boolean play() {
        return (Content.APLYER_SUCCESS == mAPlayerAndroid.play());
    }

    @Override
    public int getPlayPostion() {
        return mAPlayerAndroid.getPosition();
    }

    @Override
    public boolean setPlayPostion(int mesc) {
        return (Content.APLYER_SUCCESS == mAPlayerAndroid.setPosition(mesc));
    }

    @Override
    public boolean open() {

       //if use HardWareDecode, may change view size, recover size
       // In SoftWare Decode,must set view full displayArea, aplayer engine will control  display area size
		DisplayAreaSetUtil.adjustMatchParentDisplaySize(mPlayDisplayView);
       return(Content.APLYER_SUCCESS == mAPlayerAndroid.open(mFilePath));
    }

    @Override
    public void setAspectRatioType(AspectRatioType aspectRatioType, String param) {
        if(isUseVR()) {
            //when use VR, aplayer engine will adjust size automatic
            DisplayAreaSetUtil.adjustMatchParentDisplaySize(mPlayDisplayView);
        }
        else if(IsHardWareDecode())
        {
            // if use HardWare Decode, and not use vr,shoud set view size manual
            //SetAspectRatioHardWareDecode(aspectRatioType, param);
        }
        if(IsHardWareDecode() && !isUseVR()) {
        }

        //Sync Settings Ratio to aplayer engine
        SetAspectRatioSoftDecoder(aspectRatioType, param);
        // save Ratio mode, aplayer engine internal not saved ratin type
        mAspectRatioType = aspectRatioType;
    }

    @Override
    public AspectRatioType getAspectRatioType() {
        return mAspectRatioType;
    }

    @Override
    public String getAspectRatioNative() {
        return mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.ASPECT_RATIO_NATIVE);
    }

    @Override
    public String getAspectRatioCustom() {
        return mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.ASPECT_RATIO_CUSTOM);
    }

    @Override
    public View getDisplayView() {
        return mPlayDisplayView;

    }

    @Override
    public float getPlaySpeed() {
        float playSpeed = 0.0f;
        String strPlaySpeed = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.PLAY_SPEED);
        if(null != strPlaySpeed && !strPlaySpeed.isEmpty())
        {
            playSpeed = Float.parseFloat(strPlaySpeed);
        }

        return playSpeed / SPEED_BASE;
    }

    @Override
    public boolean setPlaySpeed(float playSpeed) {
        int iPlaySpeed = Math.round(playSpeed * SPEED_BASE); //四舍五入取整
        int retCode = mAPlayerAndroid.setConfig(APlayerAndroid.CONFIGID.PLAY_SPEED,Integer.toString(iPlaySpeed));
        boolean bret = BoolUtil.APlayerConfigValToBool(retCode);
        return bret;
    }

    @Override
    public boolean isUseVR() {

        return BoolUtil.APlayerConfigValToBool(mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.VR_ENABLE));
    }

    public void init()
    {
        //deafult use RatioNative
        mAspectRatioType = RatioNative;
    }

    public void SetAspectRatioSoftDecoder(AspectRatioType aspectRatioType, String param) {
        int configID = APlayerAndroid.CONFIGID.ASPECT_RATIO_CUSTOM;
        switch (aspectRatioType)
        {
            case RationFullScreen:
            {
                UIUtil.Size fullScreenSize = UIUtil.getScrrenResolutionSize(mPlayDisplayView.getContext());
                param = DisplayAreaSetUtil.makeAspectRatioString(fullScreenSize.width, fullScreenSize.height);
                break;
            }
            case RatioNative:
                String aspectRationNative = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.ASPECT_RATIO_NATIVE);
                param = aspectRationNative;
                break;
            case RatioCustom:
                break;
            default:
                Log.e(ERROR_TAGE, "UnKnow Aspect Ratio Type!");
                assert (false);
                return;
        }

        mAPlayerAndroid.setConfig(configID, param);
    }

    public void SetAspectRatioHardWareDecode(AspectRatioType aspectRatioType, String param)
    {
        if(RationFullScreen == aspectRatioType)
        {
            DisplayAreaSetUtil.adjustMatchParentDisplaySize(mPlayDisplayView);
            return;
        }

        if(RatioNative == aspectRatioType)
        {
            param = getAspectRatioNative();
        }

        double aspectRation = DisplayAreaSetUtil.parseAspectRatio(param);
        DisplayAreaSetUtil.adjustCustomRatioDisplaySize(mPlayDisplayView, aspectRation);
    }

    private boolean IsHardWareDecode()
    {
        boolean isHardWareDecode = isEnableHardWareDecode() && isUseHardWareDecode();
        return isHardWareDecode;
    }

    public class PlayerCloseComplate implements APlayerAndroid.OnPlayCompleteListener
    {
        private IStopCompletCallback mStopCallback;
        private boolean mIsOnlyCallOnSuccess;

        @Override
        public void onPlayComplete(String playRet) {
            boolean isUseCallStop = APlayerSationUtil.isStopByUserCall(playRet);
            if( (null != mStopCallback) &&
                (isUseCallStop || !mIsOnlyCallOnSuccess) ){
                assert (mAPlayerAndroid.getState() == APlayerAndroid.PlayerState.APLAYER_READ);
                mStopCallback.onStopComplet();
            }

            //recover listener
            if(null != mListener){
                mAPlayerAndroid.setOnPlayCompleteListener(mListener.playCompleteListener);
            }
        }


        public void setIsOnlyCallOnSuccess(boolean isOnlyCallOnSuccess) {
            mIsOnlyCallOnSuccess = isOnlyCallOnSuccess;
        }

        public void setStopCallback(IStopCompletCallback stopCallback) {
            mStopCallback = stopCallback;
        }
    }
}
