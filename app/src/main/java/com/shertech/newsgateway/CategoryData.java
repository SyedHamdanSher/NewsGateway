package com.shertech.newsgateway;

import java.io.Serializable;

/**
 * Created by lastwalker on 4/24/17.
 */

public class CategoryData implements Serializable {
    private String id;
    private String name;
    private String url;
    private String category;

    public CategoryData(String id, String name, String url, String category) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.category = category;

    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    public String getCategory() {
        return category;
    }

}
