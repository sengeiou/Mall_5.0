package com.qy.zgz.mall.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.qy.zgz.mall.R;
import com.qy.zgz.mall.utils.InputUtils;
import com.qy.zgz.mall.utils.QRBitmapUtils;
import com.zhy.autolayout.utils.AutoUtils;


/**
 * Created by LCB on 2017/11/21 0021.
 *
 */

public class TisGameQRUrlDialog {

    private Dialog dialog;
    private View contentview;
    private Context mcontext;

    private CountDownTimer dialog_countDown;

    private ImageView iv_game_qrurl_qrimg;

    public TisGameQRUrlDialog(Context context)
    {
        if (null==context){
            return;
        }
        mcontext=context;
        dialog=new Dialog(mcontext);
    }

    public TisGameQRUrlDialog create()
    {
        if (null==dialog){
            return this;
        }
        contentview= LayoutInflater.from(mcontext).inflate(R.layout.dialog_game_qrurl,null);
        AutoUtils.auto(contentview);
        iv_game_qrurl_qrimg=contentview.findViewById(R.id.iv_game_qrurl_qrimg);

        try {
            iv_game_qrurl_qrimg.setImageBitmap(QRBitmapUtils.createQRCode("http://hyppmm.com/wap/lottery-list.html",300));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }





    public TisGameQRUrlDialog show()
    {
        if (dialog!=null && contentview!=null)
        {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(contentview);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER;
            params.width= WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (dialog_countDown!=null){
                        dialog_countDown.cancel();
                    }
                    InputUtils.Companion.closeInput(mcontext);

                    //事件回调
                    if (null!=handEventAfterDismiss){
                        handEventAfterDismiss.handEvent();
                    }
                }
            });
            dialog.getWindow().setAttributes(params);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


            dialog_countDown= new CountDownTimer(8000,1000){

                @Override
                public void onTick(long millisUntilFinished) {

                }
                @Override
                public void onFinish() {
                    dismiss();
                }
            }.start();

        }

        return  this;
    }

    public void dismiss()
    {
        if (dialog!=null && dialog.isShowing())
        {
            dialog.dismiss();
        }
    }

    private HandEventAfterDismiss handEventAfterDismiss;

    //弹窗消失后处理事件
    public interface  HandEventAfterDismiss
    {
        void handEvent();
    }

    //设置弹窗消失后处理事件对象
    public TisGameQRUrlDialog setHandEventAfterDismiss(HandEventAfterDismiss handEvent){
        handEventAfterDismiss=handEvent;

        return this;
    }

}
