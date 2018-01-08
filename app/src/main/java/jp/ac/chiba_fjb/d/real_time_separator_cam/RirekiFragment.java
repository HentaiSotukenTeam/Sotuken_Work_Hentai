package jp.ac.chiba_fjb.d.real_time_separator_cam;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by kmn on 2017/12/07.
 */

public class RirekiFragment extends Fragment {

    InputStream is = null;
    BufferedReader br = null;
    String text = "";
    LinearLayout lay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.rireki, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lay = (LinearLayout)getActivity().findViewById(R.id.RirekiOut);


        try {
            try {
                // assetsフォルダ内の sample.txt をオープンする
                is = getActivity().getAssets().open("rireki.txt");
                br = new BufferedReader(new InputStreamReader(is));

                // １行ずつ読み込み、改行を付加する
                String str;
                while ((str = br.readLine()) != null) {
                    TextView tx = new TextView(getActivity());
                    tx.setText(str);
                    lay.addView(tx);
                }


            } finally {
                if (is != null) is.close();
                if (br != null) br.close();
            }
        } catch (Exception e){
            // エラー発生時の処理
        }






    }

}
