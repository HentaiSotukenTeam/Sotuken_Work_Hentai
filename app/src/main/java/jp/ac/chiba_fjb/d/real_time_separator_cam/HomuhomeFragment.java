package jp.ac.chiba_fjb.d.real_time_separator_cam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kmn on 2017/10/10.
 */

public class HomuhomeFragment extends Fragment{

    public final static int REQUEST_GALLERY = 0;
    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.homuhome, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CameraFragment.bmList.clear();
        ImageButton tf = (ImageButton) getView().findViewById(R.id.take_fllow);
        ImageButton kg = (ImageButton) getView().findViewById(R.id.make_fllow);
        Button quick = (Button) getView().findViewById(R.id.quick);
        Button Auto = (Button) getView().findViewById(R.id.Auto);

        tf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main,new FolderSelectFragment());
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gallery呼出
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, REQUEST_GALLERY);


            }
        });

        final CameraFragment cf = new CameraFragment();

        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cf.setFolderName("クイック");

                String path = Environment.getExternalStorageDirectory().getPath() + "/"+"クイック";
                File root = new File(path);
                if(!root.exists()){
                    root.mkdir();
                }

                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main,new CameraFragment());
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        Auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cf.setFolderName("千葉");
                String path = Environment.getExternalStorageDirectory().getPath() + "/"+"千葉";
                File root = new File(path);
                if(!root.exists()){
                    root.mkdir();
                }

                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main,new CameraFragment());
                ft.addToBackStack(null);
                ft.commit();

            }
        });

            }


    @Override //ここ単体なら機能する、ボタンと連動させる
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_GALLERY) {
            return;
        }
        if (resultCode != getActivity().RESULT_OK) {
            return;
        }
        try {//dataからInputStreamを開く処理
            InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
            bitmap = BitmapFactory.decodeStream(inputStream);
            EditFragment.bmList.add(bitmap);
            inputStream.close();


            android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.layout_main,new EditFragment());
            ft.addToBackStack(null);
            ft.commit();

        } catch (IOException e) {
        }
    }


    }
