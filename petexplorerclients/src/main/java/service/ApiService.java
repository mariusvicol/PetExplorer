package service;
import java.util.List;

import domain.AnimalPierdut;
import domain.CabinetVeterinar;
import domain.Farmacie;
import domain.Magazin;
import domain.Parc;
import domain.PensiuneCanina;
import domain.Salon;
import domain.utils.LocatieFavoritaDTO;
import domain.utils.SearchResultWrapper;
import domain.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

    @GET("api/locatii/favoritesDTO/{userId}")
    Call<List<LocatieFavoritaDTO>> getFavLocationsForUserDTO(@Path("userId") Integer userId);

    @POST("api/locatii/add")
    Call<Void> addFavoritePlace(@Body LocatieFavoritaDTO place);

    @DELETE("api/locatii/delete")
    Call<Void> deleteFavoritePlace(
            @Query("idUser") int userId,
            @Query("idLocation") int locationId,
            @Query("type") String type
    );

    @POST("/api/users/login")
    Call<User> login(@Body User loginRequest);

    @POST("/api/users/register")
    Call<User> register(@Body User registerRequest);

    @GET("/api/users/{id}")
    Call<User> getUserById(@Path("id") int id);

    @PUT("/api/users/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @Multipart
    @POST("/api/animale_pierdute")
    Call<AnimalPierdut> uploadAnimal(
            @Part MultipartBody.Part imagine,
            @Part("nume_animal") RequestBody nume,
            @Part("descriere") RequestBody descriere,
            @Part("latitudine") RequestBody lat,
            @Part("longitudine") RequestBody lng,
            @Part("tip_caz") RequestBody tipCaz,
            @Part("nr_telefon") RequestBody telefon,
            @Part("id_user") RequestBody userId,
            @Part("rezolvat") RequestBody rezolvat
    );

    @PATCH("api/animale_pierdute/{id}/rezolvat")
    Call<Void> markAsResolved(@Path("id") int id);


}