package jp.ac.chiba_fjb.d.real_time_separator_cam;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.isseiaoki.simplecropview.CropImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by x15g013 on 2017/11/08.
 */

public class EditFragment extends Fragment implements View.OnTouchListener   {


    FrameLayout fl;
    public static ArrayList<Bitmap> bmList = new ArrayList<Bitmap>();
    Button insertTake;
    CropImageView cm;
   // ArrayList<ImageView> ivList = new ArrayList<ImageView>();
    Button torimu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_pict, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fl = (FrameLayout)getView().findViewById(R.id.pictTagLayout);

        PrintInsertPict();



        torimu = (Button)getActivity().findViewById(R.id.torimu);
        torimu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
              bmList.add(cm.getCroppedBitmap());
                PrintInsertPict();

            }
        });

        insertTake = (Button)getActivity().findViewById(R.id.inTake);
        insertTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fl.setDrawingCacheEnabled(true);
                Bitmap bm = Bitmap.createBitmap(fl.getDrawingCache());
                CameraFragment.bmList_Edit.add(bm);



                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main,new CameraFragment());
                ft.addToBackStack(null);
                ft.commit();

            }

        });

    }


    HashMap<View,Integer> ivH = new HashMap<View,Integer>();

    public void PrintInsertPict(){

        int viewWidth = getView().getWidth();
        int viewHeight = getView().getHeight();
        //cmList.clear();

        for(int i = 0;i<bmList.size();i++){
            /*
            cmList.add(new CropImageView(getActivity()));
            cmList.get(i).setCropMode(CropImageView.CropMode.RATIO_FREE);
            cmList.get(i).setImageBitmap(bmList.get(i));

            //画像を画面の1/3に設定
            FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(viewWidth/3,viewHeight/3);
            */

            ImageView iv = new ImageView(getActivity());
            ivH.put(iv,i);
            iv.setImageBitmap(bmList.get(i));
            FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(viewWidth/3,viewHeight/3);
            fl.addView(iv);
            iv.setOnTouchListener(this);



        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //タッチしている指が今どうなっているか
        switch(event.getAction()) {

            //押されたとき
            case MotionEvent.ACTION_DOWN:




                cm = new CropImageView(getActivity());
                cm.setCropMode(CropImageView.CropMode.RATIO_FREE);
                ImageView iv = (ImageView)v;
                cm.setImageBitmap(  ((BitmapDrawable)iv.getDrawable()).getBitmap());

                bmList.set(ivH.get(v),null);

                fl.addView(cm);



                break;
            //話されたとき
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        return true;
    }
}
