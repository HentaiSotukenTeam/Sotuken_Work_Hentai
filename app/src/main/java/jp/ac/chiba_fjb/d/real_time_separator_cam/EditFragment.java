package jp.ac.chiba_fjb.d.real_time_separator_cam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by x15g013 on 2017/11/08.
 */

public class EditFragment extends AppCompatActivity implements View.OnClickListener {

    public final static int REQUEST_GALLERY = 0;
    private ImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.insert_button);
        b1.setOnClickListener(this);

        //ImageView取得
        imageView = (ImageView) findViewById(R.id.imageView);

    }

    @Override //ここ単体なら機能する、ボタンと連動させる
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_GALLERY) {
            return;
        }
        if (resultCode != RESULT_OK) {
            return;
        }
        try {//dataからInputStreamを開く処理
            InputStream inputStream = getContentResolver().openInputStream(data.getData());
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            Toast.makeText(this, "ImageLoad", Toast.LENGTH_SHORT).show();
        }
    }

    @Override //ボタンを押したときの処理
    public void onClick(View v) {
        //Gallery呼出
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, REQUEST_GALLERY);
    }




/*    @Override //ここ単体なら機能する、ボタンと連動させる
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_GALLERY) {
            return;
        }
        if (resultCode != RESULT_OK) {
            return;
        }
        try {//dataからInputStreamを開く処理
            InputStream inputStream = getContentResolver().openInputStream(data.getData());
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            Toast.makeText(this, "ImageLoad", Toast.LENGTH_SHORT).show();
        }
    }
*/
}
