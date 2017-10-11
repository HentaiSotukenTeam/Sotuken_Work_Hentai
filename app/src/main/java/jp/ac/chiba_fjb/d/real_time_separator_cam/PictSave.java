package jp.ac.chiba_fjb.d.real_time_separator_cam;

/**
 * Created by kmn on 2017/10/11.
 */

public class PictSave  {
    private static String foldername = "デフォルト";
    private CameraPreview Cam;

    void setFolderName(String name){
        foldername = name;
    }

    void save(CameraPreview mC){
        Cam = mC;
        Cam.save(foldername);
    }



}
