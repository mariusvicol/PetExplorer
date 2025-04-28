package service;
import java.util.List;

import domain.CabinetVeterinar;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/cabinete")
    Call<List<CabinetVeterinar>> getCabineteVeterinare();
}