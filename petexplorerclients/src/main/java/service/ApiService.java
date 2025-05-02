package service;
import java.util.List;

import domain.CabinetVeterinar;
import domain.Farmacie;
import domain.Magazin;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/cabinete")
    Call<List<CabinetVeterinar>> getCabineteVeterinare();
    @GET("api/farmacii")
    Call<List<Farmacie>> getFarmacii();
    @GET("api/magazine")
    Call<List<Magazin>> getMagazine();


}