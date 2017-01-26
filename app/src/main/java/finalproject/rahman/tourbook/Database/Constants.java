package finalproject.rahman.tourbook.Database;

/**
 * Created by rahma on 1/6/2017.
 */

public class Constants {
    public static final String DATABASE_NAME="tour_book";
    public static final int DATABASE_VERSION=1;

    public static final String TABLE_USER_INFOS="user_infos";
    public static final String USER_ID="id";
    public static final String USER_NAME="user_name";
    public static final String PASSWORD="passwords";
    public static final String EMAIL="email";
    public static final String PHONE="phone";

    public static final String TABLE_EVENT_INFOS="event_infos";
    public static final String EVENT_ID="id";
    public static final String EVENT_NAME="event_name";
    public static final String START_LOCATION="start_location";
    public static final String DESTINATION="destination";
    public static final String START_DATE_DAY="start_date_day";
    public static final String START_DATE_MONTH="start_date_month";
    public static final String START_DATE_YEAR="start_date_year";
    public static final String START_TIME_HOUR="start_time_hour";
    public static final String START_TIME_MIN="start_time_min";
    public static final String BUDGET="budget";
    public static final String BUDGET_LEFT="budget_left";
    public static final String BUDGET_SPENT="budget_spent";
    public static final String CREATED_ON="created_on";
    public static final String STRAT_TIME_MILLIS="start_time_millis";

    public static final String TABLE_EVENT_EXPENSE_HISTORY="event_expense_history";
    public static final String EXPENSE_ID="expense_id";
    public static final String EXPENSE_NAME="event_expense_history";
    public static final String EXPENSE_AMOUNT="event_expense_amount";

    private static final String CREATE_TABLE=" CREATE TABLE ";
    private static final String OPEN_BRACE=" ( ";
    private static final String CLOSE_BRACE=" ) ";
    private static final String INTEGER=" INTEGER ";
    private static final String TEXT=" TEXT ";
    private static final String UNIQUE=" UNIQUE ";
    private static final String COMMA=" , ";
    private static final String WHERE=" WHERE ";
    private static final String PRIMARY_KEY=" PRIMARY KEY ";

    public static final String SHOW_EVENT_DETAIL_BUNDLE="show_event";
    public static final String SHOW_EVENT_UPDATED_DETAIL_BUNDLE="show_updated_event";

    public static final String CREATE_USER_INFO_TABLE=
            CREATE_TABLE+TABLE_USER_INFOS
                    +OPEN_BRACE
                    +USER_ID+INTEGER+PRIMARY_KEY+COMMA
                    +USER_NAME+TEXT+UNIQUE+COMMA
                    +PASSWORD+TEXT+COMMA
                    +EMAIL+TEXT+UNIQUE+COMMA
                    +PHONE+TEXT+UNIQUE
                    +CLOSE_BRACE;
    public static final String CREATE_EVENT_INFO_TABLE=
            CREATE_TABLE+TABLE_EVENT_INFOS
            +OPEN_BRACE
            +EVENT_ID+INTEGER+PRIMARY_KEY+COMMA
            +USER_NAME+TEXT+COMMA
            +EVENT_NAME+TEXT+UNIQUE+COMMA
            +START_LOCATION+TEXT+COMMA
            +DESTINATION+TEXT+COMMA
            +START_DATE_DAY+INTEGER+COMMA
            +START_DATE_MONTH+INTEGER+COMMA
            +START_DATE_YEAR+INTEGER+COMMA
            +START_TIME_HOUR+INTEGER+COMMA
            +START_TIME_MIN+INTEGER+COMMA
            +BUDGET+INTEGER+COMMA
            +BUDGET_LEFT+INTEGER+COMMA
            +BUDGET_SPENT+INTEGER+COMMA
            +CREATED_ON+TEXT+COMMA
            +STRAT_TIME_MILLIS+TEXT
            +CLOSE_BRACE;
    public static final String CREATE_EVENT_EXPENSE_HISTORY_TABLE=
            CREATE_TABLE+TABLE_EVENT_EXPENSE_HISTORY
                    +OPEN_BRACE
                    +EXPENSE_ID+INTEGER+PRIMARY_KEY+COMMA
                    +EVENT_NAME+TEXT+COMMA
                    +EXPENSE_NAME+TEXT+COMMA
                    +EXPENSE_AMOUNT+INTEGER
                    +CLOSE_BRACE;



}
