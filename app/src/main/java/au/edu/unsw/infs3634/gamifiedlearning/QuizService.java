package au.edu.unsw.infs3634.gamifiedlearning;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizService {

    // API seperated by use cases

    @GET("api.php?amount=50&type=multiple")
    Call<Response> getResponse();

    @GET("api.php?amount=1&category=10&difficulty=easy&type=multiple")
    Call<Response> getBookEasy();

    @GET("api.php?amount=1&category=10&difficulty=medium&type=multiple")
    Call<Response> getBookMedium();

    @GET("api.php?amount=1&category=10&difficulty=hard&type=multiple")
    Call<Response> getBookHard();

    @GET("api.php?amount=20&category=10&type=multiple")
    Call<Response> getBook();

    @GET("api.php?amount=1&category=11&difficulty=easy&type=multiple")
    Call<Response> getFilmEasy();

    @GET("api.php?amount=1&category=11&difficulty=medium&type=multiple")
    Call<Response> getFilmMedium();

    @GET("api.php?amount=1&category=11&difficulty=hard&type=multiple")
    Call<Response> getFilmHard();

    @GET("api.php?amount=50&category=11&type=multiple")
    Call<Response> getFilm();

    @GET("api.php?amount=1&category=13&difficulty=easy&type=multiple")
    Call<Response> getTheatreEasy();

    @GET("api.php?amount=1&category=13&difficulty=medium&type=multiple")
    Call<Response> getTheatreMedium();

    @GET("api.php?amount=1&category=13&difficulty=hard&type=multiple")
    Call<Response> getTheatreHard();

    @GET("api.php?amount=20&category=13&type=multiple")
    Call<Response> getTheatre();

    @GET("api.php?amount=1&category=12&difficulty=easy&type=multiple")
    Call<Response> getMusicEasy();

    @GET("api.php?amount=1&category=12&difficulty=medium&type=multiple")
    Call<Response> getMusicMedium();

    @GET("api.php?amount=1&category=12&difficulty=hard&type=multiple")
    Call<Response> getMusicHard();

    @GET("api.php?amount=50&category=12&type=multiple")
    Call<Response> getMusic();

    @GET("api.php?amount=1&category=14&difficulty=easy&type=multiple")
    Call<Response> getTVEasy();

    @GET("api.php?amount=1&category=14&difficulty=medium&type=multiple")
    Call<Response> getTVMedium();

    @GET("api.php?amount=1&category=14&difficulty=hard&type=multiple")
    Call<Response> getTVHard();

    @GET("api.php?amount=50&category=14&type=multiple")
    Call<Response> getTV();

    @GET("api.php?amount=1&category=15&difficulty=easy&type=multiple")
    Call<Response> getGameEasy();

    @GET("api.php?amount=1&category=15&difficulty=medium&type=multiple")
    Call<Response> getGameMedium();

    @GET("api.php?amount=1&category=15&difficulty=hard&type=multiple")
    Call<Response> getGameHard();

    @GET("api.php?amount=50&category=16&type=multiple")
    Call<Response> getGame();

    @GET("api.php?amount=1&category=16&difficulty=easy&type=multiple")
    Call<Response> getBoardEasy();

    @GET("api.php?amount=1&category=16&difficulty=medium&type=multiple")
    Call<Response> getBoardMedium();

    @GET("api.php?amount=1&category=16&difficulty=hard&type=multiple")
    Call<Response> getBoardHard();

    @GET("api.php?amount=50&category=15&type=multiple")
    Call<Response> getBoard();

    @GET("api.php?amount=1&category=31&difficulty=easy&type=multiple")
    Call<Response> getAnimeEasy();

    @GET("api.php?amount=1&category=31&difficulty=medium&type=multiple")
    Call<Response> getAnimeMedium();

    @GET("api.php?amount=1&category=31&difficulty=hard&type=multiple")
    Call<Response> getAnimeHard();

    @GET("api.php?amount=40&category=31&type=multiple")
    Call<Response> getAnime();

    @GET("api.php?amount=1&category=32&difficulty=easy&type=multiple")
    Call<Response> getCartoonEasy();

    @GET("api.php?amount=1&category=32&difficulty=medium&type=multiple")
    Call<Response> getCartoonMedium();

    @GET("api.php?amount=1&category=32&difficulty=hard&type=multiple")
    Call<Response> getCartoonHard();

    @GET("api.php?amount=50&category=32&type=multiple")
    Call<Response> getCartoon();
}
