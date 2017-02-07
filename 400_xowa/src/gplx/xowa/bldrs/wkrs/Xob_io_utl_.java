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
package gplx.xowa.bldrs.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Xob_io_utl_ {
	public static void Delete_sql_files(Io_url wiki_dir, String sql_file_name) {
		Delete_by_wildcard(wiki_dir, sql_file_name + ".sql", ".gz", ".sql");
	}
	public static void Delete_by_wildcard(Io_url dir, String name_pattern, String... ext_ary) {
		List_adp list = Find_by_wildcard(Io_mgr.Instance.QueryDir_args(dir).ExecAsUrlAry(), name_pattern, ext_ary);
		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			Io_url url = (Io_url)list.Get_at(i);
			Io_mgr.Instance.DeleteFil(url);
		}
	}
	public static Io_url Find_nth_by_wildcard_or_null(Io_url dir, String name_pattern, String... ext_ary) {
		return Find_nth_by_wildcard_or_null(Io_mgr.Instance.QueryDir_args(dir).ExecAsUrlAry(), name_pattern, ext_ary);
	}
	public static Io_url Find_nth_by_wildcard_or_null(Io_url[] fil_ary, String name_pattern, String... ext_ary) {
		List_adp list = Find_by_wildcard(fil_ary, name_pattern, ext_ary);
		int list_len = list.Len();
		return list_len == 0 ? null : (Io_url)list.Get_at(list_len - 1);
	}
	public static List_adp Find_by_wildcard(Io_url[] fil_ary, String name_pattern, String... ext_ary) {
		List_adp rv = List_adp_.New();

		// create ext_hash
		Ordered_hash ext_hash = Ordered_hash_.New();
		for (String ext : ext_ary)
			ext_hash.Add(ext, ext);

		// iterate fil_ary
		for (Io_url fil : fil_ary) {
			// file matches pattern
			if (	name_pattern == Pattern__wilcard				// empty String means match anything
				||	String_.Has(fil.NameAndExt(), name_pattern)) {	// name has name_pattern; EX: "enwiki-latest-pages-articles-current.xml" and "pagelinks"
				if (	ext_hash.Len() == 0				// empty hash means match any ext
					||	ext_hash.Has(fil.Ext()))		// ext exists in hash
					rv.Add(fil);
			}
		}
		return rv;
	}
	public static final String Pattern__wilcard = String_.Empty;
}
