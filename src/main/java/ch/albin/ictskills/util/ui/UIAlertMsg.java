package ch.albin.ictskills.util.ui;

public enum UIAlertMsg {
    FILE_MISSING("No File selected"),
    ITEM_MISSING("No Item selected"),
    WRONG_USERNAME_OR_PASSWORD("Wrong username or password"),
    INVALID_INPUT("The Data entered is invalid"),
    PERSON_DELETED("Person has successfully been deleted"),
    PERSON_SAVED("Person has successfully been saved"),
    ;

    public final String msg;

    UIAlertMsg(String msg) {
        this.msg = msg;
    }
}
