package jp.ac.chiba_fjb.d.real_time_separator_cam;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by kmn on 2017/10/10.
 */

public class FolderSelectFragment extends Fragment {
    private PictSave ps = new PictSave();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.folder_select, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button tf = (Button) getView().findViewById(R.id.take_fllow);

        tf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final EditText et = (EditText) getView().findViewById(R.id.folder_serect_te);
                if(et.getText().toString().equals("")) {
                    TextView ms = (TextView) getView().findViewById(R.id.areart);
                    ms.setText("フォルダ名を入力してください");
                }else {


                }



            }
        });


    }


}

