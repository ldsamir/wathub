package webb8.wathub.models;

/**
 * Created by mismayil on 25/02/16.
 */
public enum PostTypes {
    BOOK_EXCHANGE(BookExchange.KEY_CLASSNAME),
    GROUP_STUDY(GroupStudy.KEY_CLASSNAME),
    CARPOOL(Carpool.KEY_CLASSNAME);

    private String typeName;

    PostTypes(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
