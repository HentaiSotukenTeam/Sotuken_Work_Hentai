package jp.ac.chiba_fjb.d.real_time_separator_cam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //ホームへmodoru
        Button homef = (Button)getView().findViewById(R.id.HomeFllow);
        homef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main,new HomuhomeFragment());
                CameraFragment.bmList.clear();
                ft.commit();
            }
        });


        //フォルダ切り替えへmodoru
        Button folderchenge = (Button)getView().findViewById(R.id.FolderChenge);
        folderchenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main,new FolderSelectFragment());
                CameraFragment.bmList.clear();
                ft.commit();
            }
        });



        //ダイアログを閉じる
        Button menu = (Button)getView().findViewById(R.id.takeBack);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();

            }
        });


    }



}
