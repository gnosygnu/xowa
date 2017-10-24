/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.bldrs.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*; import gplx.core.envs.*;
import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.wikis.tdbs.*;
import gplx.xowa.bldrs.wkrs.*;
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
			Io_url url = Xob_io_utl_.Find_nth_by_wildcard_or_null(wiki.Fsys_mgr().Root_dir(), Xob_io_utl_.Pattern__wilcard, ".xml", ".bz2");
			if (url == null) throw Err_.new_wo_type("could not find any .xml or .bz2 file", "dir", wiki.Fsys_mgr().Root_dir().Raw());
			if (String_.Eq(url.Ext(), ".xml"))	Src_fil_xml_(url);
			else								Src_fil_bz2_(url);
		}
		if (src_fil_is_bz2) {
			Chk_file_ext(wiki.Appe(), src_fil_bz2, ".bz2", "xml");				
			src_fil = src_fil_bz2; src_rdr_len = Io_mgr.Instance.QueryFil(src_fil_bz2).Size();
			Xoae_app app = wiki.Appe();
			if (gplx.xowa.bldrs.installs.Xoi_dump_mgr.Import_bz2_by_stdout(app)) {
				Process_adp process = app.Prog_mgr().App_decompress_bz2_by_stdout();
				return Io_stream_rdr_process.new_(process.Exe_url(), src_fil_bz2, process.Xto_process_bldr_args(src_fil_bz2.Raw()));
			}
			else
				return Io_stream_rdr_.New__bzip2(src_fil_bz2);
		}
		else {
			Chk_file_ext(wiki.Appe(), src_fil_xml, ".xml", "bz2");
			src_fil = src_fil_xml; src_rdr_len = Io_mgr.Instance.QueryFil(src_fil_xml).Size();
			return Io_stream_rdr_.New__raw(src_fil_xml);
		}
	}
	private static void Chk_file_ext(Xoae_app app, Io_url fil, String expd_ext, String alt_ext) {
		if (!String_.Eq(fil.Ext(), expd_ext))
			app.Usr_dlg().Warn_many("", "", "File extension is not " + expd_ext + ". Please use '.src_" + alt_ext + "_fil_' instead; file=~{0}", fil.Raw());
	}
}
