package org.example;

public class Event {
    private String title;
    private String description;
    private String image;

    public Event(String title, String description, String image) {
        this.title = title;
        this.description = description.replace("₸", "т");
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return title + "\n" + description + "\n" + image + "\n";
    }
}
