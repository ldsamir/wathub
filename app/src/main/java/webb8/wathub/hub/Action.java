package webb8.wathub.hub;

import webb8.wathub.R;

/**
 * Created by mismayil on 23/02/16.
 */
public enum Action {
    PROFILE(0, R.string.title_profile),
    MESSAGES(1, R.string.title_messaging),
    ALL_POSTS(2, R.string.title_all_posts),
    BOOK_EXCHANGE_POSTS(3, R.string.title_book_exchange_posts),
    CARPOOL_POSTS(4, R.string.title_carpool_posts),
    GROUP_STUDY_POSTS(5, R.string.title_group_study_posts),
    FAVORITES(6, R.string.title_favorites),
    LOG_OUT(7, R.string.title_log_out);

    private int id;
    private int nameId;

    Action(int id, int nameId) {
        this.id = id;
        this.nameId = nameId;
    }

    public int getId() {
        return id;
    }

    public int getNameId() {
        return nameId;
    }
}
