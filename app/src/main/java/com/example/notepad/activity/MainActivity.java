package com.example.notepad.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notepad.R;
import com.example.notepad.item.NoteItem;
import com.example.notepad.json.GetNoteResponseJson;
import com.example.notepad.models.NoteModel;
import com.example.notepad.utils.api.ServiceGenerator;
import com.example.notepad.utils.api.service.NoteService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView notedList;
    List<NoteModel> noteModels;
    NoteItem noteItem;
    FloatingActionButton tambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notedList = findViewById(R.id.notelist);
        notedList.setHasFixedSize(true);
        notedList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        tambah = findViewById(R.id.tambahdata);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TambahActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        getDataNotepad();
    }

    private void getDataNotepad() {
        NoteService service = ServiceGenerator.createService(NoteService.class,"admin","admin");
        service.getNoted().enqueue(new Callback<GetNoteResponseJson>() {
            @Override
            public void onResponse(Call<GetNoteResponseJson> call, Response<GetNoteResponseJson> response) {
                if (response.isSuccessful()){
                    noteModels = response.body().getData();
                    noteItem = new NoteItem(noteModels,getApplicationContext(),R.layout.item_note);
                    notedList.setAdapter(noteItem);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "gagal menampilkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetNoteResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}