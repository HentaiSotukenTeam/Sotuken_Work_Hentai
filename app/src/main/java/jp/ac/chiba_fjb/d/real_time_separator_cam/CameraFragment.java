package jp.ac.chiba_fjb.d.real_time_separator_cam;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
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
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment implements View.OnTouchListener {


	static int i = 0;											//フォルダ番号
	private static String foldername = "デフォルト";			//フォルダネーム
	private static CameraPreview mCamera;						//カメラプレビュー
	Permission mPermission;									//パーミッション

	//画像挿入系
	static HashMap<View,Integer> ivX = new HashMap<View,Integer>();
	static HashMap<View,Integer> ivY = new HashMap<View,Integer>();
	static ArrayList<ImageView> ivList = new ArrayList<ImageView>();
	static ArrayList<Bitmap> bmList = new ArrayList<Bitmap>();		//挿入画像リスト
	private FrameLayout fl;									//フレームレイアウト変数
	public final static int REQUEST_GALLERY = 0;			//リクエストギャラリー
	Bitmap bitmap;												//ビットマップ


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
                mCamera.takePicture();	//カメラプレビューのテイクピクチャーに投げる
            }
        });

		//メニューダイアログ表示
        Button menu = (Button)getView().findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ダイアログを表示する
                DialogFragment newFragment = new MenuFragment();
                newFragment.show(getActivity().getSupportFragmentManager(),null);
            }
        });

		//画像挿入ボタン
		Button inp = (Button)getView().findViewById(R.id.inpict);
		inp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Gallery呼出
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_PICK);
				startActivityForResult(intent, REQUEST_GALLERY);
			}
		});


		//挿入画像プレビュー展開
		PrintInsertPict();

	}

	//挿入画像リストの画像をすべてプレビュー
	void PrintInsertPict(){
		fl = (FrameLayout)getView().findViewById(R.id.pictTagLayout);
		for(int i = 0;i<bmList.size();i++){
			ImageView iv = new ImageView(getActivity());	//イメージビュー作成
			ivList.add(iv);
			iv.setImageBitmap(bmList.get(i));				//イメージビューにビットマップを設定
			float width = bmList.get(i).getWidth();			//画像の横サイズを取得
			float height = bmList.get(i).getHeight();		//画像の縦サイズを取得
			iv.setScaleX(height/3000);						//横を1500分の一に
			iv.setScaleY(width/3000);						//縦を1500分の一に
			iv.setOnTouchListener(this);					//タッチされてる間の処理設定
			fl.addView(iv);									//フレームレイアウトに画像を追加
		}
	}


	static Canvas makeCanvas(Canvas can){
		Bitmap bm = null;


		for(int i = 0;i<bmList.size();i++){
			can.drawBitmap(bmList.get(i),ivY.get(ivList.get(i)),ivX.get(ivList.get(i)),null);
		}
		return can;
	}


	//カメラプレビューからの呼び出し
	static String HddSave(){

		//セーブポイント設定
		i++;
		String savept =  Environment.getExternalStorageDirectory() + "/" +foldername+"/"+foldername+String.valueOf(i)+".jpg";
		//セーブポイント返却
		return savept;
	}

	//フォルダーネームを受け取り格納
	void setFolderName(String name){
		i = 0;
		foldername = name;
	}

	//挿入画像ドラッグ処理(オブジェクトがタッチされてる間呼ばれ続ける)
    @Override
    public boolean onTouch(View view, MotionEvent event) {
		//タッチしている位置を取得
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
		//タッチしている指が今どうなっているか
        switch(event.getAction()) {

			//ドラッグ
            case MotionEvent.ACTION_MOVE:
                int diffX = offsetX - x;
                int diffY = offsetY - y;

                currentX -= diffX;
				ivX.put(view,currentX);
                currentY -= diffY;
				ivY.put(view,currentY);
                //画像の移動
                view.layout(currentX, currentY, currentX + view.getWidth(),
                        currentY + view.getHeight());
                offsetX = x;
                offsetY = y;
                break;

			//押されたとき
            case MotionEvent.ACTION_DOWN:
                //x,yセット
                currentX = view.getLeft();
                currentY = view.getTop();
                offsetX = x;
                offsetY = y;
                break;
			//話されたとき
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    //画像挿入
	@Override //ここ単体なら機能する、ボタンと連動させる
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		//外部画像フォルダ呼び出し
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode != REQUEST_GALLERY) {
			return;
		}
		if (resultCode != getActivity().RESULT_OK) {
			return;
		}

		try {//dataからInputStreamを開く処理
			//選択した画像をインプットストリームに
			InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
			//ビットマップに設定
			bitmap = BitmapFactory.decodeStream(inputStream);
			//リストを一度クリア(インプットストリームには過去のものも累積しているため)
			bmList.clear();
			//リストに格納
			bmList.add(bitmap);
			//インプットストリームをクローズ
			inputStream.close();
			//格納した画像を表示
			PrintInsertPict();

		} catch (IOException e) {
			}
	}





}
