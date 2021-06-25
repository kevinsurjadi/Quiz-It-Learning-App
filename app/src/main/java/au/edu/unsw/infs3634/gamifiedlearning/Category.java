package au.edu.unsw.infs3634.gamifiedlearning;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String category;
    private String description;
    private int img;
    //List<Question> questions;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Category(String category, String description, int img) {
        this.category = category;
        this.description = description;
        this.img = img;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // preset category items
    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<Category>();

        categories.add(new Category("Books", "From Orwell’s 1984 to J. K. Rowling's Harry Potter series, how well do you know your books? Take the test below to find out!", R.drawable.books));
        categories.add(new Category("Film", "Film addict, connoisseur, or just enjoy late film nights? Test your film knowledge below, from iconic film scenes, to characters and much more!", R.drawable.film));
        categories.add(new Category("Music", "Think you’ve got what it takes to be a DJ or Radio Host? Take the test below to find out how well your music knowledge is!", R.drawable.music));
        categories.add(new Category("Musicals & Theatres", "More of the classical, original type? From live action shows based on movies to iconic theatre performances, how well do you know your Musicals & Theatres?", R.drawable.theatre));
        categories.add(new Category("Television", "Did someone say Netflix & Chill! Wonder how well you know your tv shows, take the test below!", R.drawable.television));
        categories.add(new Category("Video Games", "From arcade machines to gaming consoles to mobile games, how well do you know your video games? Take the test below to see if you’re a true gamer!", R.drawable.video_game));
        categories.add(new Category("Board Games", "Are you a Monopoly Master? Think you’ve mastered all the board games? Take the test below to see how well your board game knowledge is!", R.drawable.board_game));
        categories.add(new Category("Japanese Anime & Manga", "Kamehamehaaa!!!!! Have you been called a weeaboo? Love reading and watching Japanese animations? Take the test below to see how you fit on the weeb radar!", R.drawable.anime));
        categories.add(new Category("Cartoon & Animations", "From cartoon characters from Spongebob to classic cartoons such as the Banana in Pyjamas, how well do you know your cartoons? Test your knowledge below!", R.drawable.cartoon));

        return categories;
    }
}
