package advisor.model.api.categories;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoriesRoot {
    private Categories categories;
}

@Getter
class Categories {
    private List<Item> items;
}

@Getter
class Item {
    private String id;
    private String name;
}
