package webb8.wathub.util;

import webb8.wathub.R;

/**
 * Created by mismayil on 3/25/16.
 */
public enum PostState implements Selectable {
    FAVORITED(0, R.string.title_favorites),
    DONE(1, R.string.title_done),
    DELETED(2, R.string.title_deleted);

    private int id;
    private int nameId;

    PostState(int id, int nameId) {
        this.id = id;
        this.nameId = nameId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getNameId() {
        return nameId;
    }
}
