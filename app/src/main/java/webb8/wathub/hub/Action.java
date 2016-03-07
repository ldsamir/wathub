package webb8.wathub.hub;

import webb8.wathub.R;

/**
 * Created by mismayil on 25/02/16.
 */
public enum Action implements Selectable {
    ACTION_POST_GENERAL(8, R.string.action_post),
    ACTION_POST_BOOK_EXCHANGE(9, R.string.action_post),
    ACTION_POST_GROUP_STUDY(10, R.string.action_post),
    ACTION_CARPOOL(11, R.string.action_post);

    private int id;
    private int nameId;

    Action(int id, int nameId) {
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
