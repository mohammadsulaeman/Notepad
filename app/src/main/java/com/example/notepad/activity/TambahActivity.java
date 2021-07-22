package com.example.notepad.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notepad.R;
import com.example.notepad.json.NotedRequestJson;
import com.example.notepad.json.NotedResponseJson;
import com.example.notepad.utils.NetworkUtils;
import com.example.notepad.utils.api.ServiceGenerator;
import com.example.notepad.utils.api.service.NoteService;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {

    EditText keterangan;
    TextView lokasi,waktu,tanggal;
    Button simpan;
    String latitude, longitude,saveTime,saveDate;
    private final int DESTINATION_ID = 1;
    public final String CHANNEL_ID = "notification_key";
    public final String CHANNEL_NAME ="motifcation_app";
    public final String CHANNEL_DESC = "notifaction_desc";
    private SimpleDateFormat dateFormatter, dateFormatterview;
    String dateview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        keterangan = findViewById(R.id.keterangankegiatan);
        lokasi = findViewById(R.id.lokasikegiatan);
        waktu = findViewById(R.id.waktukegiatan);
        tanggal = findViewById(R.id.tanggalkegiatan);

        waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWaktu();
            }
        });
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTanggal();
            }
        });
        simpan = findViewById(R.id.tambahbutton);
        lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahActivity.this, PicklocationActivity.class);
                intent.putExtra(PicklocationActivity.FORM_VIEW_INDICATOR, DESTINATION_ID);
                startActivityForResult(intent, PicklocationActivity.LOCATION_PICKER_ID);
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (TextUtils.isEmpty(keterangan.getText().toString())){
                    Toast.makeText(TambahActivity.this, "keterangan kegiatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else  if (TextUtils.isEmpty(waktu.getText().toString())){
                    Toast.makeText(TambahActivity.this, "waktu kegiatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else  if (TextUtils.isEmpty(tanggal.getText().toString())){
                    Toast.makeText(TambahActivity.this, "tanggal kegiatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else  if (TextUtils.isEmpty(lokasi.getText().toString())){
                    Toast.makeText(TambahActivity.this, "lokasi kegiatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else
                {
                    if (NetworkUtils.isConnected(TambahActivity.this)) {
                        onSignInClick();
                    } else {
                        Toast.makeText(TambahActivity.this, "tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatterview = new SimpleDateFormat("dd MMM yyyy");
    }

    private void openTanggal() {
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        tanggal.setText(dateFormatterview.format(date_ship_millis));
                        dateview = dateFormatter.format(date_ship_millis);
                    }
                }
        );
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.teal_200));
        datePicker.show(getFragmentManager(), "Tanggal");
    }

    private void openWaktu() {
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                try {

                    String timeString = hourOfDay + ":" + minute;
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date time = sdf.parse(timeString);

                    sdf = new SimpleDateFormat("HH:mm");
                    String formatedTime = sdf.format(Objects.requireNonNull(time));
                    waktu.setText(formatedTime);

                } catch (Exception ignored) {
                }
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.teal_200));
        datePicker.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PicklocationActivity.LOCATION_PICKER_ID) {
                String addressset = data.getStringExtra(PicklocationActivity.LOCATION_NAME);
                LatLng latLng = data.getParcelableExtra(PicklocationActivity.LOCATION_LATLNG);
                lokasi.setText(addressset);
                latitude = String.valueOf(Objects.requireNonNull(latLng).getLatitude());
                longitude = String.valueOf(latLng.getLongitude());
            }
        }

    }

    private void onSignInClick() {
        NotedRequestJson request = new NotedRequestJson();
        request.setKeterangan(keterangan.getText().toString());
        request.setWaktu(waktu.getText().toString());
        request.setTanggal(tanggal.getText().toString());
        request.setLokasi(lokasi.getText().toString());

        NoteService service = ServiceGenerator.createService(NoteService.class,request.getWaktu(),request.getKeterangan());
        service.tambah(request).enqueue(new Callback<NotedResponseJson>() {
            @Override
            public void onResponse(Call<NotedResponseJson> call, Response<NotedResponseJson> response) {
                if (response.isSuccessful()){
                    Intent main = new Intent(TambahActivity.this,MainActivity.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(main);
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(TambahActivity.this,CHANNEL_ID)
                                    .setSmallIcon(R.drawable.ic_baseline_message_24)
                                    .setContentTitle("Notification")
                                    .setContentText(response.body().getData())
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(TambahActivity.this);
                    notificationManagerCompat.notify(1,builder.build());
                }
                else {
                    Toast.makeText(TambahActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotedResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TambahActivity.this, "error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}