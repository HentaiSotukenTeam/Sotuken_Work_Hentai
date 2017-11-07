package jp.ac.chiba_fjb.d.real_time_separator_cam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by x15g013 on 2017/11/06.
 */

public class OrgaFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.orga, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button orga = (Button) getView().findViewById(R.id.orga_button);

        orga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main,new HomuhomeFragment());
                ft.commit();

            }
        });
    }

}
