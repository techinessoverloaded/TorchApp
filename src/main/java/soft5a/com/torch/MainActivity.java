package soft5a.com.torch;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Button;
import android.content.ContentResolver;
import android.view.Window;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.widget.ToggleButton;
import android.view.WindowManager.LayoutParams;
import android.content.Intent;
import android.view.WindowManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

    private Camera camera;
    ToggleButton flashlightSwitchImg;
    ToggleButton toggleButton2;
    private boolean isFlashlightOn;
    private Window window;
    Parameters params;
    private SeekBar seekBar;
    private int brightness;
    private ContentResolver cResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



            flashlightSwitchImg = (ToggleButton) findViewById(R.id.toggleButton);


            boolean isCameraFlash = getApplicationContext().getPackageManager()
                    .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

            if (!isCameraFlash) {
                showNoCameraAlert();
            } else {
                camera = Camera.open();
                params = camera.getParameters();
            }

            flashlightSwitchImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFlashlightOn) {
                        setFlashlightOff();
                    } else {
                        setFlashlightOn();
                    }

                }
            });}







    private void showNoCameraAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Error: No Camera Flash!")
                .setMessage("Camera flashlight is not present in this Android device! Use Screenlight instead! The app will close now.After that, open the app.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return;
    }

    private void setFlashlightOn() {
        params = camera.getParameters();
        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
        isFlashlightOn = true;
        flashlightSwitchImg.setChecked(true);
    }

    private void setScreen() {
        startActivity(
                new Intent(Settings.ACTION_SETTINGS));

    }






    private void setFlashlightOff() {
        params.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isFlashlightOn = false;
        flashlightSwitchImg.setChecked(false);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;

        }


        cResolver = getContentResolver();
        window = getWindow();
        toggleButton2 = (ToggleButton) findViewById(R.id.toggleButton2);
        float brightness = 0;
        try {
            brightness = android.provider.Settings.System.getInt(cResolver, System.SCREEN_BRIGHTNESS);
        }catch(SettingNotFoundException e){
            e.printStackTrace();}
      final int curBrightnessValue=(int) brightness;
        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton2.isChecked()) {
                android.provider.Settings.System.putInt(cResolver, System.SCREEN_BRIGHTNESS,255);
                    WindowManager.LayoutParams lp=getWindow().getAttributes();
                    lp.screenBrightness=2.55f;
                    getWindow().setAttributes(lp);

                } else {
                    android.provider.Settings.System.putInt(cResolver, System.SCREEN_BRIGHTNESS,curBrightnessValue);
                }

            }
        });
    }

    }