package com.example.bazas;

import android.provider.BaseColumns;

public final class GameContract {

    private GameContract() {}

    public static class GameTable implements BaseColumns {

        public static final String TABLE_NAME = "games";
        public static final String COLUMN_GAME_OBJECT_IN_JSON = "game_object_in_json";

    }

}
