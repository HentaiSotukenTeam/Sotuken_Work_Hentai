package jp.ac.chiba_fjb.d.real_time_separator_cam;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmn on 2017/10/31.
 */

public class FileSelectFragment_takeFllow extends Fragment {

    private ArrayList<String> filelist = new ArrayList<String>();
    private File[] files;
    private String sdPath;
    private LinearLayout sc;
    private String backpath;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.file_select, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sc = (LinearLayout)view.findViewById(R.id.ScrollChild);
        sdPath = Environment.getExternalStorageDirectory().getPath();
        backpath = Environment.getExternalStorageDirectory().getPath();
        printFileName();

        Button back = (Button)view.findViewById(R.id.backButton);
        Button topback = (Button)view.findViewById(R.id.topBackButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sdPath = backpath;
                sc.removeAllViews();
                printFileName();
            }
        });

        topback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sdPath = Environment.getExternalStorageDirectory().getPath();
                sc.removeAllViews();
                printFileName();
            }
        });

        }


        void printFileName(){

            files = new File(sdPath).listFiles();

            for(int i = 0; i < files.length; i++){


                //ファイル名表示
                TextView tv = new TextView(getActivity());
                tv.setText(files[i].getName());
                tv.setBackgroundColor(Color.rgb(25,135,220));  //背景色の設定
                sc.addView(tv);
                tv.setClickable(true);


                //ID設定
                tv.setId(i);

                final int x = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!(files[x].getName().matches(".*jpg.*"))) {
                            backpath = sdPath;
                            sdPath = Environment.getExternalStorageDirectory().getPath() + "/" + files[x].getName() + "/";
                            sc.removeAllViews();
                            printFileName();
                        }else{
                            File file = new File( sdPath + files[x].getName());
                            Bitmap bm = BitmapFactory.decodeFile(file.getPath());
                            CameraFragment.bmList.add(bm);

                            android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.layout_main,new CameraFragment());
                            ft.commit();
                        }
                    }
                });


                //マージン設定
                ViewGroup.LayoutParams lp = tv.getLayoutParams();
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)lp;
                mlp.setMargins(10, 20, 10, 20);
                tv.setLayoutParams(mlp);

                //文字サイズ
                tv.setTextSize(25);


            }

        }




    }

