package petexplorer.petexplorerclients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.ApiService;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.43.39:8080";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}