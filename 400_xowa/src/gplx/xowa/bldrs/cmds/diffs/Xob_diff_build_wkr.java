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
package gplx.xowa.bldrs.cmds.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*;
import gplx.dbs.*; import gplx.dbs.diffs.*; import gplx.dbs.diffs.builds.*;
class Xob_diff_build_wkr {		
	private final Gfdb_diff_bldr diff_bldr = new Gfdb_diff_bldr();
	private Db_conn prev_conn, curr_conn, diff_conn;
	public Xob_diff_build_wkr(Xob_bldr bldr, Xowe_wiki wiki, String prev_url, String curr_url, String diff_url, int commit_interval) {			
		Bry_fmt url_fmt = Bry_fmt.New("").Args_(New_url_args(wiki));
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		prev_conn = New_conn(wiki, url_fmt, prev_url, tmp_bfr);
		curr_conn = New_conn(wiki, url_fmt, curr_url, tmp_bfr);
		diff_conn = New_conn(wiki, url_fmt, diff_url, tmp_bfr);
		// get Gfdb_diff_tbl; format urls
            Tfds.Write(prev_conn, curr_conn, diff_conn);
	}
	public void Exec() {
		diff_bldr.Init(null);			// diff_db_wkr
		diff_bldr.Compare(null, null);	// lhs_tbl, rhs_tbl
	}
	public static Db_conn New_conn(Xow_wiki wiki, Bry_fmt fmtr, String url_fmt, Bry_bfr tmp_bfr) {
		fmtr.Fmt_(url_fmt).Bld_bfr_many(tmp_bfr);
		Db_conn conn = Db_conn_pool.Instance.Get_or_new(tmp_bfr.To_str_and_clear());
		return conn;
	}
	private static Bfr_fmt_arg[] New_url_args(Xow_wiki wiki) {
		return null;
	}
	//prev_url='~{.dump_dir}-prev/~{.dump_core}';
	//curr_url='~{.dump_dir}/~{.dump_core}';
	//diff_url='~{.dump_dir}/~{.dump_domain}-diff.xowa';
}
//	class Bfr_arg__dump_dir : Bfr_arg {
//		public void Bfr_arg__add(Bry_bfr bfr) {
//			// dump_dir = bfr.Add("C:\xowa\wiki\en.wikipedia.org");
//			// dump_core = en.wikipedia.org-core.xowa
//			// dump_domain = en.wikipedia.org
//		}
//	}
