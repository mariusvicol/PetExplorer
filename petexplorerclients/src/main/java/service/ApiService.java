package service;
import java.util.List;

import domain.AnimalPierdut;
import domain.CabinetVeterinar;
import domain.Farmacie;
import domain.Magazin;
import domain.Parc;
import domain.PensiuneCanina;
import domain.Salon;
import domain.SearchResultWrapper;
import domain.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @GET("api/pensiuni")
    Call<List<PensiuneCanina>> getPensiuniCanine();

    @GET("api/saloane")
    Call<List<Salon>> getSaloane();

    @GET("api/search")
    Call<List<SearchResultWrapper>> getSearchResults(@Query("text") String text);

    @POST("/api/users/login")
    Call<User> login(@Body User loginRequest);

}