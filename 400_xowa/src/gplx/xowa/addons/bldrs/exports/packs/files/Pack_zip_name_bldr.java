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
package gplx.xowa.addons.bldrs.exports.packs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
public class Pack_zip_name_bldr {	// en.wikipedia.org-file-ns.000-db.001.xowa -> Xowa_enwiki_2016-05_file_ns.000_db.001.zip
	private final    Io_url pack_dir;
	private final    byte[] wiki_domain, zip_name_prefix;

	public Pack_zip_name_bldr(Io_url pack_dir, String wiki_domain_str, String wiki_abrv, String wiki_date) {
		this.pack_dir = pack_dir;
		this.wiki_domain = Bry_.new_u8(wiki_domain_str);
		this.zip_name_prefix = Bry_.new_u8("Xowa_" + wiki_abrv + "_" + String_.Replace(wiki_date, ".", "-"));
	}
	public Io_url Bld(Io_url orig_url) {
		String orig_str = orig_url.NameOnly() + ".zip";
		byte[] orig_bry = Bry_.new_u8(orig_str);
		orig_bry = Bry_.Replace(orig_bry, Byte_ascii.Dash, Byte_ascii.Underline);
		orig_bry = Bry_.Replace(orig_bry, wiki_domain, zip_name_prefix);
		return pack_dir.GenSubFil(String_.new_u8(orig_bry));
	}
	public static Io_url To_wiki_url(Io_url wiki_dir, Io_url zip_dir) {	
		// get wiki_url based on wiki_dir and xobc_zip_fil; EX: "/wiki/en.wikipedia.org/", "/wiki/tmp/Xowa_enwiki_2016-09_file_core_deletion_2016-09/" -> "/wiki/en.wikipedia.org-file-core-deletion-2016.09.zip" 
		String name_str = zip_dir.NameOnly() + ".xowa";
		byte[] name_bry = Bry_.new_u8(name_str);
		int pos = Bry_find__Find_fwd_idx(name_bry, Byte_ascii.Underline, 2);
		name_bry = Bry_.Mid(name_bry, pos, name_bry.length);
		name_bry = Bry_.Add(Bry_.new_u8(wiki_dir.NameOnly()), name_bry);
		name_bry = Bry_.Replace(name_bry, Byte_ascii.Underline, Byte_ascii.Dash);
		return wiki_dir.GenSubFil(String_.new_u8(name_bry));
	}
	private static int Bry_find__Find_fwd_idx(byte[] src, byte val, int find_max) {
		int src_len = src.length;
		int find_cur = 0;
		int cur_pos = 0;
		while (true) {
			int new_pos = Bry_find_.Find_fwd(src, val, cur_pos, src_len);
			if (new_pos == -1) throw Err_.New("failed to find value; src={0} val={1}", src, val);
			if (find_cur++ == find_max) return new_pos;
			cur_pos = new_pos + 1;
		}
	}
}
