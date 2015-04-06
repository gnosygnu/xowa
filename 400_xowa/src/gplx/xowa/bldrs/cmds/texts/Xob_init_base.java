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
package gplx.xowa.bldrs.cmds.texts; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.wikis.*; import gplx.xowa.xtns.wdatas.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.xmls.*;
public abstract class Xob_init_base implements Xob_cmd, GfoInvkAble {
	private Xob_bldr bldr; private Xowe_wiki wiki; private Gfo_usr_dlg usr_dlg;
	private byte wbase_enabled = Bool_.__byte;
	public Xob_init_base Ctor(Xob_bldr bldr, Xowe_wiki wiki) {this.bldr = bldr; this.wiki = wiki; this.usr_dlg = wiki.Appe().Usr_dlg(); return this;}
	public abstract String Cmd_key();
	public abstract void Cmd_ini_wdata(Xob_bldr bldr, Xowe_wiki wiki);
	public abstract void Cmd_run_end(Xowe_wiki wiki);
	public void Cmd_init(Xob_bldr bldr) {		// add other cmds; EX: wikidata
		bldr.Import_marker().Bgn(wiki);
		if (wbase_enabled == Bool_.__byte) wbase_enabled = wiki.Domain_tid() == Xow_domain_.Tid_int_wikidata ? Bool_.Y_byte : Bool_.N_byte;	// if wbase_enabled not explicitly set, set it to y if wiki is "www.wikidata.org"
		if (wbase_enabled == Bool_.Y_byte)		// if wbase_enabled, auto-add wdata_wkrs bldr
			this.Cmd_ini_wdata(bldr, wiki);
	}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {						// parse site_info
		gplx.ios.Io_stream_rdr src_rdr = wiki.Import_cfg().Src_rdr();
		usr_dlg.Plog_many("", "", "reading dump header: ~{0}", src_rdr.Url().Raw());
		byte[] siteinfo_xml = Xob_siteinfo_parser.Siteinfo_extract(src_rdr);
		Xob_siteinfo_parser.Siteinfo_parse(wiki, usr_dlg, String_.new_utf8_(siteinfo_xml));
		this.Cmd_run_end(wiki);					// save site info
	}
	public void Cmd_end() {
		wiki.Appe().Gui_mgr().Html_mgr().Portal_mgr().Wikis().Itms_reset();	// dirty wiki list so that next refresh will load itm
		if (wiki.Appe().Setup_mgr().Dump_mgr().Css_wiki_update()) {
			Io_url url = wiki.Appe().User().Fsys_mgr().Wiki_html_dir(wiki.Domain_str()).GenSubFil(Xoa_css_extractor.Css_wiki_name);
			usr_dlg.Log_many("", "", "deleting css: ~{0}", url.Raw());
			Io_mgr._.DeleteFil_args(url).MissingFails_off().Exec();
		}
	}
	public void Cmd_term() {}
	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_src_xml_fil_))				wiki.Import_cfg().Src_fil_xml_(m.ReadIoUrl("v"));
		else if	(ctx.Match(k, Invk_src_bz2_fil_))				wiki.Import_cfg().Src_fil_bz2_(m.ReadIoUrl("v"));
		else if	(ctx.Match(k, Invk_wdata_enabled_))				wbase_enabled = m.ReadYn("v") ? Bool_.Y_byte : Bool_.N_byte;
		else if	(ctx.Match(k, Invk_owner))						return bldr.Cmd_mgr();
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_src_xml_fil_ = "src_xml_fil_", Invk_src_bz2_fil_ = "src_bz2_fil_", Invk_owner = "owner", Invk_wdata_enabled_ = "wdata_enabled_";
}
