package com.example.notepad.utils.api.service;

import com.example.notepad.json.GetNoteResponseJson;
import com.example.notepad.json.NotedRequestJson;
import com.example.notepad.json.NotedResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NoteService {
    @GET("catatanapi/kegiatandata")
    Call<GetNoteResponseJson> getNoted();

    @POST("catatanapi/register")
    Call<NotedResponseJson> tambah(@Body NotedRequestJson param);
}
