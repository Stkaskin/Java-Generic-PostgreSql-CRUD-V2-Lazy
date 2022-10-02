package com.asstnavi.generic.Manager;

public class CreateTable
{
    public void Create()
    {
        String table = " CREATE TABLE IF NOT EXISTS " +
                "redlineiptv_schema.language ( id Serial NOT NULL  " +
                ", name text COLLATE pg_catalog.\"default\" NOT NULL," +
                " languageculture text COLLATE pg_catalog.\"default\" NOT NULL, " +
                "uniqueseocode text COLLATE pg_catalog.\"default\" NOT NULL," +
                " flagimagefilename text COLLATE pg_catalog.default, published boolean NOT NULL," +
                " displayorder integer NOT NULL, CONSTRAINT language_pkey PRIMARY KEY (id) ) TABLESPACE pg_default; " +
                "ALTER TABLE IF EXISTS redlineiptv_schema.language OWNER to postgres;";

        new PostgreSqlManager().Command(table);

        table = "CREATE TABLE IF NOT EXISTS redlineiptv_schema.languagestring(id Serial NOT NULL," +
                "language_id integer NOT NULL,resource_name text " +
                "COLLATE pg_catalog.\"default\" NOT NULL," +
                "resource_value text COLLATE pg_catalog.\"default\" NOT NULL," +
                "CONSTRAINT \"LanguageString_pkey\" PRIMARY KEY (id))TABLESPACE pg_default;" +
                "ALTER TABLE IF EXISTS redlineiptv_schema.languagestring OWNER to postgres;";

        new PostgreSqlManager().Command(table);
    }
}
