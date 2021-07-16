package com.example.notepad.json;

import com.example.notepad.models.NoteModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetNoteResponseJson {
    @Expose
    @SerializedName("catatan")
    private List<NoteModel> data = new ArrayList<>();

    public List<NoteModel> getData() {
        return data;
    }

    public void setData(List<NoteModel> data) {
        this.data = data;
    }
}
