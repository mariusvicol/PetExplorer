package service;
import java.util.List;

import domain.AnimalPierdut;
import domain.CabinetVeterinar;
import domain.Farmacie;
import domain.Magazin;
import domain.Parc;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/cabinete")
    Call<List<CabinetVeterinar>> getCabineteVeterinare();
    @GET("api/farmacii")
    Call<List<Farmacie>> getFarmacii();
    @GET("api/magazine")
    Call<List<Magazin>> getMagazine();
    @GET("api/parcuri")
    Call<List<Parc>> getParcuri();

    @GET("api/animale_pierdute")
    Call<List<AnimalPierdut>> getAnimalePierdute();
}