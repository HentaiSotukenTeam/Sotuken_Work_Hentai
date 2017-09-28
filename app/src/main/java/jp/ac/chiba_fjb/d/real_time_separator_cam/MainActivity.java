package jp.ac.chiba_fjb.d.real_time_separator_cam;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CameraPreview mCamera;
    Permission mPermission;

  @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);


      //Android6.0以降用パーミッション設定
      mPermission = new Permission();
      mPermission.setOnResultListener(new Permission.ResultListener() {
          @Override
          public void onResult() {

                    //パーミッション設定完了後の初期化処理を入れる
                    //フラグメントの切り替え
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.layout_main,new CameraFragment());
                    ft.commit();

               }
      });
      mPermission.requestPermissions(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mPermission.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

}
