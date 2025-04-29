package service;
import java.util.List;

import domain.CabinetVeterinar;
import domain.Parc;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/cabinete")
    Call<List<CabinetVeterinar>> getCabineteVeterinare();

    @GET("api/parcuri")
    Call<List<Parc>> getParcuri();
}