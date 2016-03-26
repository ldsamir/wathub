package webb8.wathub.util;

import webb8.wathub.R;

/**
 * Created by mismayil on 23/02/16.
 */
public enum NavItem implements Selectable {
    PROFILE(0, R.string.title_profile),
    MESSAGES(1, R.string.title_messaging),
    ALL_POSTS(2, R.string.title_all_posts),
    BOOK_EXCHANGE_POSTS(3, R.string.title_book_exchange_posts),
    CARPOOL_POSTS(4, R.string.title_carpool_posts),
    GROUP_STUDY_POSTS(5, R.string.title_group_study_posts),
    FAVORITES(6, R.string.title_favorites),
    DONE(7, R.string.title_done),
    LOG_OUT(8, R.string.title_log_out);

    private int id;
    private int nameId;

    NavItem(int id, int nameId) {
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
