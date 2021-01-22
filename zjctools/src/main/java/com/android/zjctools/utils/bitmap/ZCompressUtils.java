package com.android.zjctools.utils.bitmap;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;


import com.android.zjctools.pick.bean.ZPictureBean;
import com.android.zjctools.utils.ZLog;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ZCompressUtils {


    int needCompressCounts = 0;  //需要压缩的个数
    int compressedCounts = 0;  //已经压缩的个数
    CompressImageListener compressImageListener;

    public interface CompressImageListener {
        void onComplete();
        void onError(String errorMsg);
    }


    /**
     * 压缩单张图片，裁剪之后的图片不压缩
     * @param pictureBean
     * @param compressImageListener
     */
    public void compressSinglePicture(ZPictureBean pictureBean, CompressImageListener compressImageListener){
        compressSinglePicture(pictureBean,false,null,compressImageListener);
    }


    /**
     * 压缩单张图片，裁剪之后的图片不压缩
     * @param pictureBean
     * @param compressImageListener
     */
    public void compressSinglePicture(ZPictureBean pictureBean,String catalogueName, CompressImageListener compressImageListener){
        compressSinglePicture(pictureBean,false,catalogueName,compressImageListener);
    }


    /**
     * 压缩单张图片，
     * @param pictureBean
     * @param compressImageListener
     * @param corpIsCompress    经过裁剪之后的图片是否继续压缩
     * @param  catalogueName     目录
     */
    public void compressSinglePicture(ZPictureBean pictureBean,boolean corpIsCompress,String catalogueName, CompressImageListener compressImageListener){
        List<ZPictureBean> pictureBeans=new ArrayList<>();
        pictureBeans.add(pictureBean);
        compressPictures(pictureBeans,corpIsCompress,catalogueName,compressImageListener);
    }


    /**
     * 压缩图片，裁剪之后的图片不压缩
     * @param pictureBeans
     * @param compressImageListener
     */
    public void compressPictures(List<ZPictureBean> pictureBeans, CompressImageListener compressImageListener){
        compressPictures(pictureBeans,false,null,compressImageListener);
    }


    /**
     * 压缩图片，裁剪之后的图片不压缩
     * @param pictureBeans
     * @param compressImageListener
     */
    public void compressPictures(List<ZPictureBean> pictureBeans,String catalogueName, CompressImageListener compressImageListener){
        compressPictures(pictureBeans,false,catalogueName,compressImageListener);
    }


    /**
     *
     * @param pictureBeans
     * @param compressImageListener
     * @param corpIsCompress    经过裁剪之后的图片是否继续压缩
     */
    public void compressPictures(List<ZPictureBean> pictureBeans ,boolean corpIsCompress,String catalogueName,CompressImageListener compressImageListener){
        this.compressImageListener = compressImageListener;
        compressPictures(pictureBeans,corpIsCompress,catalogueName);
    }


    /**
     *
     * @param pictureBeans
     * @param corpIsCompress    裁剪的图片是否压缩
     */
    private void compressPictures(List<ZPictureBean> pictureBeans ,boolean corpIsCompress,String catalogueName) {
        boolean isNeedCompress = false;

        for (int i = 0; i < pictureBeans.size(); i++) {
            if (TextUtils.isEmpty(pictureBeans.get(i).url) && TextUtils.isEmpty(pictureBeans.get(i).compressPath)
                    && !TextUtils.isEmpty(pictureBeans.get(i).path)) {
                if(!corpIsCompress&&!TextUtils.isEmpty(pictureBeans.get(i).cropPath)){ //剪切图片，且剪切图片不需要压缩
                    pictureBeans.get(i).compressPath=pictureBeans.get(i).cropPath;//将剪切图片地址复制给压缩图片
                }else{
                    needCompressCounts += 1;  //需要压缩处理的个数
                    isNeedCompress = true;
                }

            }
        }
        if (!isNeedCompress && compressImageListener != null) {
            ZLog.e("无需压缩");
            compressImageListener.onComplete();
        }
        compressedCounts = 0; //当前压缩个数
        for (int i = 0; i < pictureBeans.size(); i++) {
            if(!TextUtils.isEmpty(pictureBeans.get(i).url)){//网络图片不需要压缩
                continue;
            }
            if(!corpIsCompress&&!TextUtils.isEmpty(pictureBeans.get(i).cropPath)){  //剪切的图片不压缩,
                pictureBeans.get(i).compressPath=pictureBeans.get(i).cropPath;
                continue;
            }
            if (TextUtils.isEmpty(pictureBeans.get(i).compressPath) && !TextUtils.isEmpty(pictureBeans.get(i).path)) {
                compressPicture(pictureBeans.get(i),catalogueName);
            }
        }
    }
    private void compressPicture(final ZPictureBean pictureBean,String catalogueName) {
        try {
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                String compressPath = ZBitmap.compressTempImage(pictureBean.path,catalogueName);
                emitter.onNext(compressPath);
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String compressPath) {
                            ZLog.e("compressPath:" + compressPath);
                            pictureBean.compressPath = compressPath;
                            compressedCounts += 1;
                            if (needCompressCounts == compressedCounts && compressImageListener != null) {
                                compressImageListener.onComplete();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (compressImageListener != null) {
                                compressImageListener.onError(e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception ex) {

        }

    }




    /**
     * 获取选择的图片
     * @param imagePaths     选择图片返回的url
     * @param selectedPictures  已经选择的图片
     * @return
     */
    public  static ArrayList<ZPictureBean> getSelectPictureBeans(List<String> imagePaths,ArrayList<ZPictureBean> selectedPictures){
        ArrayList<ZPictureBean> pictures=new ArrayList<>();
        for (int i = 0; i <selectedPictures.size() ; i++) {  //之前的网路图片对象
            if(!TextUtils.isEmpty(selectedPictures.get(i).url)&&TextUtils.isEmpty(selectedPictures.get(i).path)){
                pictures.add(selectedPictures.get(i));
            }
        }
        for (int i = 0; i <imagePaths.size() ; i++) {
            ZPictureBean pictureBean=new ZPictureBean();
            pictureBean.path=imagePaths.get(i);
            pictures.add(pictureBean);
        }
        return  pictures;
    }
}
