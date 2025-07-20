package com.example.carbooking.admin.tour;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carbooking.Entity.Category;
import com.example.carbooking.Entity.Tour;
import com.example.carbooking.Entity.Vehicle;
import com.example.carbooking.R;
import com.example.carbooking.helpler.FormatUtils;
import com.example.carbooking.helpler.SaveImageToStorage;
import com.example.carbooking.repository.CategoryRepository;
import com.example.carbooking.repository.TourRepository;
import com.example.carbooking.repository.VehicleRepository;

import java.net.URI;
import java.util.List;

public class EditTourActivity extends AppCompatActivity {
    private final  int GALLERY_REQ_CODE = 1000;
    private TourRepository tourRepository;
    private CategoryRepository categoryRepository;
    private VehicleRepository vehicleRepository;
    private List<Category> categoryList;
    private List<Vehicle> vehicleList = null;
    private Context context;
    private int tourId;
    ImageView imgViewEdit;
    private EditText edtTitleEdit, edtLocationFromEdit,  edtLocationToEdit,
            edtTourTimeEdit, edtDateNumberEdit, edtTourScheduleEdit, edtPricePersonEdit,
            edtContactNumberEdit, edtDescriptionEdit;
    private RadioButton radioActive, radioBan;
    String status = "";
    private Button btnEditTour, btnBack;
    private SaveImageToStorage saveImageToStorage;

    private Spinner spinnerCategoryEdit, spinnerVehicleEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_tour);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tourRepository = new TourRepository(this);
        categoryRepository = new CategoryRepository(this);
        vehicleRepository = new VehicleRepository(this);
        saveImageToStorage = new SaveImageToStorage(this);

        tourId = getIntent().getIntExtra("tourId", -1);
        System.out.println("tourId : " + tourId);
        categoryList = categoryRepository.getAllCategory();
        vehicleList = vehicleRepository.getAllVehicle();
        Tour tourById = tourRepository.getTour(tourId);
        if (tourById != null) {
            // Example: Update EditText fields with tour details
            edtTitleEdit = findViewById(R.id.edt_title_tour_edit);
            edtLocationFromEdit = findViewById(R.id.edt_location_from_edit);
            edtLocationToEdit = findViewById(R.id.edt_location_to_edit);
            edtTourTimeEdit = findViewById(R.id.edt_tour_time_edit);
            edtDateNumberEdit = findViewById(R.id.edt_date_number_edit);
            edtTourScheduleEdit = findViewById(R.id.edt_tour_schedule_edit);
            edtPricePersonEdit = findViewById(R.id.edt_price_person_edit);
            edtContactNumberEdit = findViewById(R.id.edt_contact_number_edit);
            spinnerCategoryEdit = findViewById(R.id.spinner_category_edit);
            spinnerVehicleEdit = findViewById(R.id.spinner_vehicle_edit);
            edtDescriptionEdit = findViewById(R.id.edt_description_tour_edit);
            btnEditTour = findViewById(R.id.btn_editTour);
            radioActive = findViewById(R.id.radio_active);
            radioBan = findViewById(R.id.radio_ban);
            imgViewEdit = findViewById(R.id.IVPreviewImageEdit);

            String imageUriString = tourById.getImage();
            Glide.with(EditTourActivity.this)
                    .load(imageUriString)
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(imgViewEdit);


//            imgViewEdit.setImageURI(imageUri);
            edtTitleEdit.setText(tourById.getTile());
            edtLocationFromEdit.setText(tourById.getLocationFrom());
            edtLocationToEdit.setText(tourById.getLocationTo());
            edtTourTimeEdit.setText(tourById.getTourTime());
            int dateNumber = tourById.getDateNumber();
            String strDateNumber = String.valueOf(dateNumber);
            edtDateNumberEdit.setText(strDateNumber);
            edtTourScheduleEdit.setText(tourById.getTourSchdule());
            //ép kiểu double cho price
            double pricePerPerson = tourById.getPricePerPerson();
            String formattedPrice = FormatUtils.formatCurrency(pricePerPerson);
            edtPricePersonEdit.setText(formattedPrice);
            //------------------------------
            edtContactNumberEdit.setText(tourById.getContactNumber());
            edtDescriptionEdit.setText(tourById.getDescription());

            if (tourById.isAvaliable()) {
                radioActive.setChecked(true);
            } else {
                radioBan.setChecked(true);
            }
            int categoryId = tourById.getCategoryId();
            int position = -1;
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == categoryId) {
                    position = i;
                    break;
                }
            }
            ArrayAdapter<Category> arrayAdapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
            arrayAdapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategoryEdit.setAdapter(arrayAdapterCategory);
            if (position != -1) {
                spinnerCategoryEdit.setSelection(position);
            } else {
                spinnerCategoryEdit.setSelection(0);
            }
            //spinner vehicle
             int vehicleId = tourById.getVehicle();;
             int pst = -1;
             for (int i = 0;  i < vehicleList.size(); i++){
                 if(vehicleList.get(i).getId() == vehicleId){
                     pst = i;
                     break;
                 }
             }
            ArrayAdapter<Vehicle> arrayAdapterVehicle = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vehicleList);
            arrayAdapterVehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerVehicleEdit.setAdapter(arrayAdapterVehicle);
            if (pst != -1) {
                spinnerVehicleEdit.setSelection(pst);
            } else {
                spinnerVehicleEdit.setSelection(0);
            }
            radioActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   status = "true";
                }
            });
            radioBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    status = "false";
                }
            });
            btnEditTour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = edtTitleEdit.getText().toString();
                    String locationFrom = edtLocationFromEdit.getText().toString();
                    String locationTo = edtLocationToEdit.getText().toString();
                    String tourTime = edtTourTimeEdit.getText().toString();
                    int dateNumberUpdate = Integer.parseInt(edtDateNumberEdit.getText().toString());
                    String tourSchedule = edtTourScheduleEdit.getText().toString();
                    String pricePerPersonStr = edtPricePersonEdit.getText().toString().replaceAll("[,.]", "");
                    double pricePerPersonUpdate = Double.parseDouble(pricePerPersonStr);
                    String contactNumber = edtContactNumberEdit.getText().toString();
                    String description = edtDescriptionEdit.getText().toString();
                    Category selectedCategory = (Category) spinnerCategoryEdit.getSelectedItem();
                    int categoryIdSelected = selectedCategory.getId();
                    Vehicle selectedVehice = (Vehicle) spinnerVehicleEdit.getSelectedItem();
                    int vehicleIdSelected = selectedVehice.getId();
                    int voteNumber = tourById.getVotedNumber();
                    int voteScore = tourById.getVoteScore();
                    String imgPath = saveImageToStorage.saveImageFromImageView(imgViewEdit);
                    boolean statusUpdate = true;
                    if(status.isEmpty()){
                         statusUpdate =  tourById.isAvaliable();;
                    }else if(status == "true"){
                         statusUpdate = true;
                    }else{
                        statusUpdate = false;
                    }

                    Tour tourById = new Tour(tourId,title,locationFrom,locationTo,tourTime,dateNumberUpdate,description,tourSchedule,pricePerPersonUpdate,vehicleIdSelected,categoryIdSelected,voteNumber,voteScore,statusUpdate,contactNumber,imgPath);
                    tourRepository.updateTour(tourById);
                    Toast.makeText(EditTourActivity.this, "UpdateSuccess", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditTourActivity.this,ListTourActivity.class);
                    startActivity(intent);
                }
            });


        } else {
            Toast.makeText(this, "Tour not found!", Toast.LENGTH_SHORT).show();
            finish(); 
        }
        Button selectedImage = findViewById(R.id.BSelectImageEdit);
        imgViewEdit = findViewById(R.id.IVPreviewImageEdit);
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallrey = new Intent(Intent.ACTION_PICK);
                iGallrey.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallrey, GALLERY_REQ_CODE);
            }
        });
        btnBack = findViewById(R.id.btn_edit_tour_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTourActivity.this,ListTourActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQ_CODE){
                imgViewEdit.setImageURI(data.getData());
                System.out.println("data: " + data.getData());
            }
        }
    }
}