package com.example.eshop_v2;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.Manifest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  AddProductsFragment extends Fragment {

    public static FragmentManager fragmentManager;

    EditText EdtTxt1, EdtTxt2, EdtTxt3, EdtTxt4, EdtTxt5;
    Button Btn_save, Btn_picture , Btn_add;
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

        txtview = view.findViewById(R.id.prod);
        EdtTxt1 = view.findViewById(R.id.edit_text_name);
        EdtTxt2 = view.findViewById(R.id.edit_text_id);
        EdtTxt3 = view.findViewById(R.id.edit_text_description);
        EdtTxt4 = view.findViewById(R.id.edit_text_price);
        EdtTxt5 = view.findViewById(R.id.edit_text_quantity);
        Btn_picture = view.findViewById(R.id.btn_picture);
        productImage = view.findViewById(R.id.product_image);
        actionsort = view.findViewById(R.id.action_sort);
        //vagg

        int permissionCheckStorage = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);
        if (permissionCheckStorage == PackageManager.PERMISSION_DENIED) {
            productImage.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            productImage.setEnabled(true);
        }
        dbHelper = new DbHelper(getActivity());

        Btn_add = view.findViewById(R.id.add);
        Btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDb(view);
            }
        });;



        actionsort.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                actionsort();
            }
        });

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImagee(view);
            }
        });

        Btn_picture.setOnTouchListener(new View.OnTouchListener() {


            //GIA EISAGWGH FOTOGRAFIAS (SE PEIRAMATIKO STADIO AKOMA)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new PhotoPickerFragment()).addToBackStack(null).commit();

                        return true;
                }
                return false;
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
                String Var_productdesc = EdtTxt3.getText().toString();


                try {
                    products prods = new products();
                    prods.setId(Var_productid);
                    prods.setName(Var_productname);
                    prods.setPrice(Var_productprice);
                    prods.setDescription(Var_productdesc);
                    prods.setQuantity(Var_productquantity);
                    productImage.setDrawingCacheEnabled(true);
                    productImage.buildDrawingCache();
                    Bitmap bitmap = productImage.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress (Bitmap.CompressFormat.JPEG, 80, baos);
                    byte[] data = baos.toByteArray();
                    dbHelper.addToDb(data);

                    MainActivity.productsDatabase.productsDAOtemp().addProducts(prods);

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
                Toast.makeText(getActivity(), "product added", Toast.LENGTH_LONG).show();

                Toast.makeText(getActivity(), "Image saved to DB successfully", Toast.LENGTH_SHORT).show();
                EdtTxt1.setText("");
                EdtTxt2.setText("");
                EdtTxt3.setText("");
                EdtTxt4.setText("");
                EdtTxt5.setText("");

            }
        });

        return view;
    }

    private void choseImage() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imagePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imagePath);
                productImage.setImageBitmap(imageToStore);
            }
        }catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
        }

    }*/


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

    public void onRequestPermissopnsResult(int requestCode, String[] permission, int grantResult[]) {
        if (requestCode == 0) {
            if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED
                    && grantResult[1] == PackageManager.PERMISSION_GRANTED) {
                productImage.setEnabled(true);
            }
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
                    imagePath = data.getData();
                    imageToStore = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imagePath);
                    setProgressBar();
                    productImage.setImageBitmap(imageToStore);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (requestCode == CAPTURE_PHOTO) {

                    onCaptureImageResult(data);


            }
        }





    public boolean actionsort() {
        Intent intent = new Intent(AddProductsFragment.this.getActivity(), DetailsActivity.class);
        // Create an instance of the DbHelper class
        DbHelper dbHelper = new DbHelper(getContext());

        // Call the deleteTable method on the dbHelper instance
        dbHelper.deleteTable();
        startActivity(intent);
        return true;
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        //set Progress Bar
        setProgressBar();
        //set product picture form camera
        productImage.setMaxWidth(400);
        productImage.setImageBitmap(thumbnail);

    }

    public void addToDb(View view){
        productImage.setDrawingCacheEnabled(true);
        productImage.buildDrawingCache();
        Bitmap bitmap = productImage.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress (Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] data = baos.toByteArray();
        dbHelper.addToDb(data);
        Toast.makeText(getActivity(), "Image saved to DB successfully", Toast.LENGTH_SHORT).show();
    }


}
