package au.edu.unsw.infs3634.gamifiedlearning;

public class firebaseModel {

    // these names need to match the createnote titles and content so that our fetch works
    private String title;
    private String content;

    public firebaseModel(){
    }

    public firebaseModel (String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}



