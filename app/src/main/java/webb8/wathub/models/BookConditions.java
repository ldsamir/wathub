package webb8.wathub.models;

import webb8.wathub.R;
import webb8.wathub.util.Selectable;

/**
 * Created by mismayil on 25/02/16.
 */
public enum BookConditions implements Selectable {
    BAD(0, R.string.book_condition_bad),
    MODERATE(1, R.string.book_condition_moderate),
    GOOD(2, R.string.book_condition_good),
    EXCELLENT(3, R.string.book_condition_excellent);

    private int id;
    private int nameId;

    BookConditions(int id, int nameId) {
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
