package emanzelekha.com.devtask.MVP.Model;


public class ResultModle {
    private String name = "", description = "",html_url="";
    private boolean fork;
    private ResultModle2 owner;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setOwner(ResultModle2 owner) {
        this.owner = owner;
    }

    public ResultModle2 getOwner() {
        return owner;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public boolean getFork() {
        return fork;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getHtml_url() {
        return html_url;
    }
}
