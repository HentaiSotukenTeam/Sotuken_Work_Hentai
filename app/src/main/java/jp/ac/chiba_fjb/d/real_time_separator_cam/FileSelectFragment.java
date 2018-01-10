package jp.ac.chiba_fjb.d.real_time_separator_cam;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FileSelectFragment extends Fragment {

    private ArrayList<String> filelist = new ArrayList<String>();
    private File[] files;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.file_select, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        LinearLayout sc = (LinearLayout)view.findViewById(R.id.ScrollChild);
        String sdPath = Environment.getExternalStorageDirectory().getPath();
        files = new File(sdPath).listFiles();

        for(int i = 0; i < files.length; i++){

            TextView tv = new TextView(getActivity());
            tv.setText(files[i].getName());
            tv.setBackgroundColor(Color.rgb(25,135,220));  //背景色の設定
            sc.addView(tv);

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

