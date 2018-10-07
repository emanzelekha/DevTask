package emanzelekha.com.devtask.MVP.Model;



public class RecyclerModel {
    private String name, description, login,html_url,html_url_owner;
    private Boolean fork;
    private int width;

    public RecyclerModel(String name, String description, String login, Boolean fork, String html_url, String html_url_owner, int width) {
        this.name = name;
        this.description = description;
        this.login = login;
        this.fork=fork;
        this.html_url=html_url;
        this.html_url_owner=html_url_owner;
        this.width=width;
    }

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

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setFork(Boolean fork) {
        this.fork = fork;
    }

    public Boolean getFork() {
        return fork;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url_owner(String html_url_owner) {
        this.html_url_owner = html_url_owner;
    }

    public String getHtml_url_owner() {
        return html_url_owner;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }
}
