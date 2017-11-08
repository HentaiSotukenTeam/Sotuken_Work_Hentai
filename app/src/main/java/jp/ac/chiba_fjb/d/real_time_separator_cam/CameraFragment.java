package jp.ac.chiba_fjb.d.real_time_separator_cam;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment implements View.OnTouchListener {

	static int i = 0;
	private static String foldername = "デフォルト";
	private static CameraPreview mCamera;
	Permission mPermission;
	static ArrayList<Bitmap> bmList = new ArrayList<Bitmap>();
	private FrameLayout fl;

    //画像ドラッグ系
    int currentX;   //Viewの左辺座標：X軸
    int currentY;   //Viewの上辺座標：Y軸
    int offsetX;    //画面タッチ位置の座標：X軸
    int offsetY;    //画面タッチ位置の座標：Y軸


	public CameraFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_camera, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);


		//インスタンスの取得
		TextureView textureView = (TextureView) getView().findViewById(R.id.pictTag);
		//カメラプレビュー用クラスの作成
		mCamera = new CameraPreview();
		mCamera.setTextureView(textureView);
		mCamera.open(0);

		//撮影ボタン
		ImageButton photog = (ImageButton)getView().findViewById(R.id.Photo);
        photog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView ms = (TextView) getView().findViewById(R.id.ms);
                ms.setText("撮影しました");
                mCamera.takePicture();


            }
        });

        Button menu = (Button)getView().findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ダイアログを表示する
                DialogFragment newFragment = new MenuFragment();
                newFragment.show(getActivity().getSupportFragmentManager(),null);


            }
        });

		Button inp = (Button)getView().findViewById(R.id.inpict);
		inp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.layout_main,new FileSelectFragment_takeFllow());
				ft.commit();
			}
		});

		fl = (FrameLayout)getView().findViewById(R.id.pictTagLayout);
		for(int i = 0;i<bmList.size();i++){
			ImageView iv = new ImageView(getActivity());
			iv.setImageBitmap(bmList.get(i));
			float width = bmList.get(i).getWidth();
			float height = bmList.get(i).getHeight();
			iv.setScaleX(height/1500);
			iv.setScaleY(width/1500);
			iv.setOnTouchListener(this);

			fl.addView(iv);
		}


	}





	static String HddSave(){
		i++;
		String savept =  Environment.getExternalStorageDirectory() + "/" +foldername+"/"+foldername+String.valueOf(i)+".jpg";
		return savept;
	}


	void setFolderName(String name){
		i = 0;
		foldername = name;
	}

	//ドラッグ系
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch(event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                int diffX = offsetX - x;
                int diffY = offsetY - y;

                currentX -= diffX;
                currentY -= diffY;
                //画像の移動
                view.layout(currentX, currentY, currentX + view.getWidth(),
                        currentY + view.getHeight());
                offsetX = x;
                offsetY = y;
                break;

            case MotionEvent.ACTION_DOWN:
                //x,yセット
                currentX = view.getLeft();
                currentY = view.getTop();
                offsetX = x;
                offsetY = y;
                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }





}
