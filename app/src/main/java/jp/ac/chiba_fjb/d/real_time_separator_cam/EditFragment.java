package jp.ac.chiba_fjb.d.real_time_separator_cam;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by x15g013 on 2017/11/08.
 */

public class EditFragment extends Fragment {


    FrameLayout fl;
    public static ArrayList<Bitmap> bmList = new ArrayList<Bitmap>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_pict, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PrintInsertPict();
    }


    public void PrintInsertPict(){
        fl = (FrameLayout)getView().findViewById(R.id.pictTagLayout);
        for(int i = 0;i<bmList.size();i++){
            ImageView iv = new ImageView(getActivity());
            iv.setImageBitmap(bmList.get(i));
            float width = bmList.get(i).getWidth();
            float height = bmList.get(i).getHeight();
            iv.setScaleX(height/1500);
            iv.setScaleY(width/1500);
            fl.addView(iv);
        }
    }



}
