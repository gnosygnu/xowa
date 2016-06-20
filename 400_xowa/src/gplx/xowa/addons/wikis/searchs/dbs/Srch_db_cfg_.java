/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.wikis.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.dbs.cfgs.*;
public class Srch_db_cfg_ {
	public static Srch_db_cfg New(Db_cfg_tbl cfg_tbl, long page_count, int version_id) { // NOTE: dflt values are for old search dbs
		int word_count				= cfg_tbl.Assert_int(Grp__search__cfg, Key__word_count, 0);
		int link_count_score_max	= cfg_tbl.Assert_int(Grp__search__cfg, Key__link_count_score_max, 0);
		int link_count_score_cutoff	= cfg_tbl.Assert_int(Grp__search__cfg, Key__link_count_score_cutoff, 0);
		int link_score_max			= cfg_tbl.Assert_int(Grp__search__cfg, Key__link_score_max, 0);
		return new Srch_db_cfg(version_id, page_count, word_count, link_count_score_max, link_count_score_cutoff, link_score_max);
	}
	public static void Update__bldr__link(Db_cfg_tbl cfg_tbl, Srch_db_cfg cfg, int link_score_max) {
		cfg.Update_link(link_score_max);
		cfg_tbl.Upsert_int(Grp__search__cfg, Key__link_score_max, link_score_max);
	}
	public static void Update__bldr__word(Db_cfg_tbl cfg_tbl, Srch_db_cfg cfg, int word_count, int link_count_score_max, int link_count_score_cutoff) {
		cfg.Update_word(word_count, link_count_score_max, link_count_score_cutoff);
		cfg_tbl.Upsert_int(Grp__search__cfg, Key__version_id, Srch_db_upgrade.Version__link_score);
		cfg_tbl.Upsert_int(Grp__search__cfg, Key__word_count, word_count);
		cfg_tbl.Upsert_int(Grp__search__cfg, Key__link_count_score_max, link_count_score_max);
		cfg_tbl.Upsert_int(Grp__search__cfg, Key__link_count_score_cutoff, link_count_score_cutoff);
	}
	public static int Select__version_id(Db_cfg_tbl cfg_tbl, Srch_word_tbl word_tbl) {
		int rv = cfg_tbl.Select_int_or(Grp__search__cfg, Key__version_id, -1);
		if (rv >= Srch_db_upgrade.Version__link_score) return rv;	// version_id exists; return it;
		if (rv == Srch_db_upgrade.Version__link_score_alpha) {		// R.16.03.13 has same schema as later version
			rv = Srch_db_upgrade.Version__link_score;
		} else {
			boolean version_is_page_count = word_tbl.conn.Meta_fld_exists(word_tbl.tbl_name, "word_page_count");
			rv = version_is_page_count ? Srch_db_upgrade.Version__page_count : Srch_db_upgrade.Version__initial;
		}
		cfg_tbl.Upsert_int(Grp__search__cfg, Key__version_id, rv);
		return rv;
	}

	public static final int Link_count_score_cutoff = 300;
	private static final String
	  Grp__search__cfg				= "xowa.search.cfg"
	, Key__version_id				= "version_id"
	, Key__word_count				= "word_count"
	, Key__link_count_score_max		= "link_count_score_max"
	, Key__link_count_score_cutoff	= "link_count_score_cutoff"
	, Key__link_score_max			= "link_score_max"
	;
}
