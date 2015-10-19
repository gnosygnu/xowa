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
package gplx.xowa.bldrs.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.ios.*;
import gplx.xowa.wikis.ctgs.*; import gplx.xowa.wikis.tdbs.*;
public class Xob_import_cfg {
	public Xob_import_cfg(Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki; private boolean src_fil_is_bz2 = true;
	public byte Category_version() {return category_version;} public Xob_import_cfg Category_version_(byte v) {category_version = v; return this;} private byte category_version = Xoa_ctg_mgr.Version_1;
	public long Src_rdr_len() {return src_rdr_len;} private long src_rdr_len;
	public Io_url Src_fil_xml() {return src_fil_xml;}
	public Io_url Src_fil() {return src_fil;} private Io_url src_fil;
	public Xob_import_cfg Src_fil_xml_(Io_url v) {src_fil_xml = v; src_fil_is_bz2 = Bool_.N; return this;} private Io_url src_fil_xml;
	public Xob_import_cfg Src_fil_bz2_(Io_url v) {src_fil_bz2 = v; src_fil_is_bz2 = Bool_.Y; return this;} private Io_url src_fil_bz2;
	public Io_url Src_dir() {
		if		(src_fil_xml == null && src_fil_bz2 == null)	return wiki.Fsys_mgr().Root_dir();
		else if (src_fil_xml != null)							return src_fil_xml.OwnerDir();
		else if (src_fil_bz2 != null)							return src_fil_bz2.OwnerDir();
		else													throw Err_.new_wo_type("unknown src dir");
	}
	public Io_stream_rdr Src_rdr() {
		if (src_fil_xml == null && src_fil_bz2 == null) {	// will usually be null; non-null when user specifies src through command-line
			Io_url url = Xotdb_fsys_mgr.Find_file_or_fail(wiki.Fsys_mgr().Root_dir(), "*", ".xml", ".bz2");
			if (String_.Eq(url.Ext(), ".xml"))	Src_fil_xml_(url);
			else								Src_fil_bz2_(url);
		}
		if (src_fil_is_bz2) {
			Chk_file_ext(wiki.Appe(), src_fil_bz2, ".bz2", "xml");				
			src_fil = src_fil_bz2; src_rdr_len = Io_mgr.Instance.QueryFil(src_fil_bz2).Size();
			Xoae_app app = wiki.Appe();
			if (app.Setup_mgr().Dump_mgr().Import_bz2_by_stdout()) {
				ProcessAdp process = app.Prog_mgr().App_decompress_bz2_by_stdout();
				return Io_stream_rdr_process.new_(process.Exe_url(), src_fil_bz2, process.Xto_process_bldr_args(src_fil_bz2.Raw()));
			}
			else
				return Io_stream_rdr_.bzip2_(src_fil_bz2);
		}
		else {
			Chk_file_ext(wiki.Appe(), src_fil_xml, ".xml", "bz2");
			src_fil = src_fil_xml; src_rdr_len = Io_mgr.Instance.QueryFil(src_fil_xml).Size();
			return Io_stream_rdr_.file_(src_fil_xml);
		}
	}
	private static void Chk_file_ext(Xoae_app app, Io_url fil, String expd_ext, String alt_ext) {
		if (!String_.Eq(fil.Ext(), expd_ext))
			app.Usr_dlg().Warn_many("", "", "File extension is not " + expd_ext + ". Please use '.src_" + alt_ext + "_fil_' instead; file=~{0}", fil.Raw());
	}
}
