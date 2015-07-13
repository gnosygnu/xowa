PRAGMA page_size = 4096;
/*
NOTE:
- smaller columns are generally placed at the start of table: http://www.mail-archive.com/sqlite-users@sqlite.org/msg75750.html
- commented indexes are created within XOWA. They are listed here for reference
- "MW:" comments are comparisons to MediaWiki; "MW:same" means the same
- sqlite only has 4 data types (int, text, numeric, blob); This script uses "standard" SQL types to better describe intent
*/
--core;heap;wikidata
CREATE TABLE xowa_cfg
( cfg_grp             varchar(1024)       NOT NULL
, cfg_key             varchar(1024)       NOT NULL
, cfg_val             blob                NOT NULL
);
CREATE UNIQUE INDEX xowa_cfg__grp_key             ON xowa_cfg (cfg_grp, cfg_key);

CREATE TABLE text
( page_id             integer             NOT NULL        PRIMARY KEY                     -- MW:XOWA
, old_text            mediumblob          NOT NULL                    -- medium blob      -- MW:same; REF: Revision.php!loadText
);

--core;wikidata
CREATE TABLE page
( page_id             integer             NOT NULL        PRIMARY KEY -- int(10) unsigned -- MW:same 
, page_namespace      integer             NOT NULL                    -- int(11)          -- MW:same
, page_title          varchar(255)        NOT NULL                    -- varbinary(255)   -- MW:blob
, page_is_redirect    tinyint             NOT NULL                    -- tinyint(3)       -- MW:same
, page_touched        varchar(14)         NOT NULL                    -- binary(14)       -- MW:blob; NOTE: should technically be saved in revision!rev_timestamp, but not worth another join
, page_len            integer             NOT NULL                    -- int(10) unsigned -- MW:same except NULL REF: WikiPage.php!updateRevisionOn;
, page_random_int     integer             NOT NULL                                        -- MW:XOWA
, page_file_idx       integer             NULL                                            -- MW:XOWA
);
--CREATE UNIQUE INDEX IF NOT EXISTS page__name_title_id           ON page (page_namespace, page_title, page_id);
--CREATE UNIQUE INDEX IF NOT EXISTS page__name_random             ON page (page_namespace, page_random_int);

CREATE TABLE category
( cat_id              integer             NOT NULL        PRIMARY KEY -- int(10) unsigned-- MW:same 
, cat_hidden          tinyint             NOT NULL                                       -- MW:XOWA
, cat_file_idx        integer             NOT NULL                                       -- MW:XOWA
, cat_pages           integer             NOT NULL                    -- int(11)         -- MW:same
, cat_subcats         integer             NOT NULL                    -- int(11)         -- MW:same
, cat_files           integer             NOT NULL                    -- int(11)         -- MW:same
);

CREATE TABLE categorylinks
( cl_from             integer             NOT NULL                    -- int(10)        -- MW:same;
, cl_to_id            integer             NOT NULL                                      -- MW:XOWA
, cl_type_id          tinyint             NOT NULL                    -- enum(page,subcat,file) -- MW:XOWA
, cl_sortkey          varchar(230)        NOT NULL                    -- varbinary(230) -- MW:blob;
, cl_timestamp        varchar(14)         NOT NULL                    -- timestamp      -- MW:date;
);
--CREATE        INDEX IF NOT EXISTS categorylinks__cl_main        ON categorylinks (cl_to_id, cl_type_id, cl_sortkey, cl_from);
--CREATE        INDEX IF NOT EXISTS categorylinks__cl_from        ON categorylinks (cl_from);

CREATE TABLE site_stats
( ss_row_id           integer             NOT NULL        PRIMARY KEY -- int(10) unsigned-- MW:same
, ss_good_articles    bigint              NULL                        -- bigint(20)      -- MW:same
, ss_total_pages      bigint              NULL                        -- bigint(20)      -- MW:same
, ss_images           integer             NULL                        -- int(11)         -- MW:same
);
INSERT INTO site_stats (ss_row_id, ss_good_articles, ss_total_pages, ss_images) VALUES (1, 0, 0 ,0);

CREATE TABLE xowa_db
( db_id               integer             NOT NULL        PRIMARY KEY
, db_type             tinyint             NOT NULL                    -- 1=core;2=wikidata;3=data
, db_url              varchar(512)        NOT NULL
);

CREATE TABLE xowa_ns
( ns_id               integer             NOT NULL        PRIMARY KEY
, ns_name             varchar(255)        NOT NULL
, ns_case             tinyint             NOT NULL
, ns_is_alias         bit                 NOT NULL 
, ns_count            integer             NOT NULL
);

--wikidata
CREATE TABLE wdata_qids
( wq_src_wiki         varchar(255)        NOT NULL
, wq_src_ns           integer             NOT NULL
, wq_src_ttl          varchar(512)        NOT NULL
, wq_trg_ttl          varchar(512)        NOT NULL
);
--CREATE        INDEX IF NOT EXISTS wdata_qids__src               ON wdata_qids (wq_src_wiki, wq_src_ns, wq_src_ttl);

CREATE TABLE wdata_pids
( wp_src_lang         varchar(255)        NOT NULL
, wp_src_ttl          integer             NOT NULL
, wp_trg_ttl          varchar(512)        NOT NULL
);
--CREATE        INDEX IF NOT EXISTS wdata_pids__src               ON wdata_pids (wp_src_lang, wp_src_ttl);
