package domain.utils;

public class SearchResultWrapper {
    private String name;
    private String category;
    private Object entity;

    public SearchResultWrapper(String name, String category, Object entity) {
        this.name = name;
        this.category = category;
        this.entity = entity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "SearchResultWrapper{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", entity=" + entity +
                '}';
    }
}