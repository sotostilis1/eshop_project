package com.example.eshop_v2;

import static android.app.Activity.RESULT_OK;
import static com.example.eshop_v2.MainActivity.fragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.lights.LightState;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductsFragment extends Fragment {

    public static FragmentManager fragmentManager;

    EditText EdtTxt1, EdtTxt2, EdtTxt3, EdtTxt4, EdtTxt5;
    Button Btn_save, Btn_picture;

    TextView txtview;


    ImageView productImage;
    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private boolean hastmageChanged = false;
    DbHelper dbHelper;
    Bitmap thumbnail;

    ImageButton actionsort;
    // Use your own tag for the fragment:
    private static final String TAG = "AddProductsFragment";


    public static Context contextOfApplication;

    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imagePath;
    private Bitmap imageToStore;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductsFragment newInstance(String param1, String param2) {
        AddProductsFragment fragment = new AddProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_products, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(true);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setSound(null, null); // Disable sound for heads-up notification
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC); // Show heads-up notification on the lock screen
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500}); // Set custom vibration pattern

            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        txtview = view.findViewById(R.id.prod);
        EdtTxt1 = view.findViewById(R.id.edit_text_name);
        EdtTxt2 = view.findViewById(R.id.edit_text_id);
        EdtTxt3 = view.findViewById(R.id.edit_text_description);
        EdtTxt4 = view.findViewById(R.id.edit_text_price);
        EdtTxt5 = view.findViewById(R.id.edit_text_quantity);
        productImage = view.findViewById(R.id.product_image);
        //vagg

        int permissionCheckStorage = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.CAMERA);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            productImage.setEnabled(true);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            productImage.setEnabled(true);
        }
        dbHelper = new DbHelper(getActivity());

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImagee(view);
            }
        });


        Btn_save = view.findViewById(R.id.button_save);
        Btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_productid = 0;
                try {
                    Var_productid = Integer.parseInt(EdtTxt2.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                int Var_productprice = 0;
                try {
                    Var_productprice = Integer.parseInt(EdtTxt4.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                int Var_productquantity = 0;
                try {
                    Var_productquantity = Integer.parseInt(EdtTxt5.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                String Var_productname = EdtTxt1.getText().toString();
                String TempVar_productid = EdtTxt2.getText().toString();
                String Var_productdesc = EdtTxt3.getText().toString();

                if (TempVar_productid.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Var_productid <= 0 )
                {
                    Toast.makeText(getActivity(), "ID must be greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }


                byte[] data = null;
                try {
                    products prods = new products();
                    prods.setId(Var_productid);
                    prods.setName(Var_productname);
                    prods.setPrice(Var_productprice);
                    prods.setDescription(Var_productdesc);
                    prods.setQuantity(Var_productquantity);

                    productImage.buildDrawingCache();
                    Bitmap bitmap = productImage.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    data = baos.toByteArray();
                    dbHelper.addToDb(Var_productid, data, Var_productname);
                    Log.d(TAG, Var_productid + String.valueOf(data) + Var_productname);
                    //set Progress Bar
                    //setProgressBar();

                    MainActivity.productsDatabase.productsDAOtemp().addProducts(prods);

                    Toast.makeText(getActivity(), "Product Added!", Toast.LENGTH_LONG).show();
                    // After adding a product to the database
                    showHeadsUpNotification();
                    productImage.setImageResource(R.drawable.ic_add);
                    EdtTxt1.setText("");
                    EdtTxt2.setText("");
                    EdtTxt3.setText("");
                    EdtTxt4.setText("");
                    EdtTxt5.setText("");

//                    GIA TIN FIREBASE
//                    MainActivity.db.
//                            collection("products").
//                            document(""+Var_productid).
//                            set(prods).
//                            addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(getActivity(),"Product added.",Toast.LENGTH_LONG).show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(getActivity(),"add operation failed.",Toast.LENGTH_LONG).show();
//                                }
//                            });
                } catch (Exception e) {
                    String message = e.getMessage();
                    System.out.println(message);
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }

                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddProductsFragment()).addToBackStack(null).commit();


            }
        });

        return view;
    }

    public void choseImagee(View view) {
        switch (view.getId()) {
            case R.id.product_image:
                new MaterialDialog.Builder(getActivity())
                        .title("Set your image")
                        .items(R.array.uploadImages)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                        photoPickerIntent.setType("image/*");
                                        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                                        break;
                                    case 1:
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, CAPTURE_PHOTO);
                                        break;
                                    case 2:
                                        productImage.setImageResource(R.drawable.ic_add);
                                        break;
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    public void setProgressBar() {
        progressBar = new ProgressDialog(getActivity());
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait");
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < 100) {
                    progressBarStatus += 30;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBarbHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }
            }
        }).start();
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Context applicationContext = AddProductsFragment.getContextOfApplication();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO) {

            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                setProgressBar();
                //set profile picture form gallery
                productImage.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == CAPTURE_PHOTO) {

            onCaptureImageResult(data);
        }
    }


    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        //set Progress Bar
        setProgressBar();
        //set product picture form camera
        productImage.setMaxWidth(400);
        productImage.setImageBitmap(thumbnail);

    }

    private void showHeadsUpNotification() {
        // Create an Intent for the activity to open when the notification is clicked
        //Intent intent = new Intent(getActivity(), MainActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent;
        Intent intent = new Intent(getContext(), AddProductsFragment.class);


        pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "channel_id")
                .setSmallIcon(R.mipmap.ic_stat_notifications_none)
                .setContentTitle("Product Added!")
                .setContentText("Notification Content")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL) // Set category to ensure heads-up behavior
                .setFullScreenIntent(pendingIntent, true);


        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}