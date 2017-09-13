package com.example.genesis.galeriafotos;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView mSetImage;
    private Button mOptionButton;
    private ConstraintLayout mCtLay;

    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSetImage=(ImageView) findViewById(R.id.imageView);
        mOptionButton=(Button) findViewById(R.id.button);
        mCtLay=(ConstraintLayout) findViewById(R.id.CLy);

        //if(mayRequestStoragePermission())
            mOptionButton.setEnabled(true);
        //else
            //mOptionButton.setEnabled(false);

        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalery();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private boolean mayRequestStoragePermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                return true;
        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))
            return true;
        if(shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)){
            Snackbar.make(mCtLay, "Los permisos son necesarios para poder usar la app",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {

                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS);
                }
            }).show();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS);
        }
        return false;
    }

    private void openGalery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            Uri path = data.getData();
            mSetImage.setImageURI(path);
        }

    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int [] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 1 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "Permisos acepatdos", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }/*else{
            showExplanation();            }
        }*/

    }

    /*private void showExplanation() {

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
