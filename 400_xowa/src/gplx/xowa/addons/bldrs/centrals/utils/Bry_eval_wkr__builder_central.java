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
package gplx.xowa.addons.bldrs.centrals.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.core.brys.evals.*;
public class Bry_eval_wkr__builder_central implements Bry_eval_wkr {
	private final    byte[] wiki_dir;
	public Bry_eval_wkr__builder_central(Io_url wiki_dir) {this.wiki_dir = wiki_dir.RawBry();}
	public String Key() {return "builder_central";}
	public void Resolve(Bry_bfr rv, byte[] src, int args_bgn, int args_end) {
		// EX: "~{builder_central|download_fil|en.wikipedia.org|Xowa_enwiki_2016-05_html_ns.000_db.001.zip}" -> "/xowa/wiki/en.wikipedia.org/tmp/bldr/Xowa_enwiki_2016-05_html_ns.000_db.001.zip/download.zip"
		byte[][] args = Bry_split_.Split(src, args_bgn, args_end, Byte_ascii.Pipe, Bool_.N);
		int type = hash.Get_as_byte_or(args[0], Byte_.Max_value_127);
		if (type == Byte_.Max_value_127) throw Err_.new_wo_type("unknown eval type", "src", src);
		byte dir_spr = gplx.core.envs.Op_sys.Cur().Fsys_dir_spr_byte();
		rv.Add(wiki_dir);								// "/xowa/wiki/"
		rv.Add(args[1]).Add_byte(dir_spr);				// "en.wikipedia.org/"
		if (type == Type__wiki_dir) return;
		rv.Add_str_a7("tmp").Add_byte(dir_spr);			// "tmp/"
		rv.Add_str_a7("bldr").Add_byte(dir_spr);		// "bldr/"
		rv.Add(args[2]).Add_byte(dir_spr);				// "Xowa_enwiki_2016-05_html_ns.000_db.001.zip/"
		switch (type) {
			case Type__download_fil:	rv.Add_str_a7("download.zip"); break;
			case Type__unzip_dir:		rv.Add_str_a7("unzip").Add_byte(dir_spr); break;
			default:					throw Err_.new_unhandled_default(type);
		}
	}

	public static final byte Type__download_fil = 0, Type__unzip_dir = 1, Type__wiki_dir = 2;
	private static final    Hash_adp_bry hash = Hash_adp_bry.cs()
	.Add_str_byte("download_fil", Type__download_fil)
	.Add_str_byte("unzip_dir", Type__unzip_dir)
	.Add_str_byte("wiki_dir", Type__wiki_dir)
	;
	public static String Make_str(byte type, String domain, String file_name) {
		String type_name = null;
		switch (type) {
			case Type__download_fil:	type_name = "download_fil"; break;
			case Type__unzip_dir:		type_name = "unzip_dir"; break;
			case Type__wiki_dir:		type_name = "wiki_dir"; break;
			default:					throw Err_.new_unhandled_default(type);
		}
		return "~{" + String_.Format("builder_central|{0}|{1}|{2}", type_name, domain, file_name) + "}";
	}
	public static String Make_str(byte type, String domain) {
		String type_name = null;
		switch (type) {
			case Type__wiki_dir:		type_name = "wiki_dir"; break;
			default:					throw Err_.new_unhandled_default(type);
		}
		return "~{" + String_.Format("builder_central|{0}|{1}", type_name, domain) + "}";
	}
}
