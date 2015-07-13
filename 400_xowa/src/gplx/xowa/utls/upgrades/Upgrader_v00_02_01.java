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
package gplx.xowa.utls.upgrades; import gplx.*; import gplx.xowa.*; import gplx.xowa.utls.*;
import gplx.xowa.bldrs.*;
class Upgrader_v00_02_01 {
	public void Run(Xowe_wiki wiki) {
		Io_url cfg_dir = wiki.Fsys_mgr().Root_dir().GenSubDir("cfg");
		if (!Io_mgr.I.ExistsDir(cfg_dir)) return;	// brand-new wiki; nothing to migrate
		Gfo_usr_dlg usr_dlg = wiki.Appe().Usr_dlg();
		usr_dlg.Note_many(GRP_KEY, "run.bgn", "migrate.bgn for ~{0}", wiki.Domain_str());
		Io_url siteinfo_url = cfg_dir.GenSubFil_nest("siteInfo.xml");
		usr_dlg.Note_many(GRP_KEY, "siteinfo.bgn", "siteinfo.bgn for ~{0}", siteinfo_url.Raw());
		String siteinfo_str = Io_mgr.I.LoadFilStr_args(siteinfo_url).MissingIgnored_(true).Exec(); if (String_.Len_eq_0(siteinfo_str)) throw Exc_.new_("could not find siteinfo.xml", "url", siteinfo_url.Raw());
		usr_dlg.Note_many(GRP_KEY, "siteinfo.parse", "parsing siteinfo");
		gplx.xowa.bldrs.xmls.Xob_siteinfo_parser.Siteinfo_parse(wiki, usr_dlg, siteinfo_str);	// NOTE: this also resets the namespaces on the wiki; not necessary, but is benign
		usr_dlg.Note_many(GRP_KEY, "siteinfo.save", "saving siteinfo");
		byte[] wiki_core_bry = wiki.Cfg_wiki_core().Build_gfs();
		Io_mgr.I.SaveFilBry(wiki.Tdb_fsys_mgr().Cfg_wiki_core_fil(), wiki_core_bry);
		usr_dlg.Note_many(GRP_KEY, "siteinfo.end", "siteinfo.end for ~{0}", wiki.Domain_str());

		Io_url old_wikistats_url = wiki.Fsys_mgr().Root_dir().GenSubFil_nest("cfg", "wiki.gfs");
		Io_url new_wikistats_url = wiki.Tdb_fsys_mgr().Cfg_wiki_stats_fil();
		if		(Io_mgr.I.ExistsFil(new_wikistats_url))		// noop; should not happen, but perhaps results from merging directories; 
			usr_dlg.Note_many(GRP_KEY, "wiki_stats.new_exists", "new wiki stats already exists for ~{0}", new_wikistats_url.Raw());
		else if (!Io_mgr.I.ExistsFil(old_wikistats_url))	// noop; should not happen;
			usr_dlg.Note_many(GRP_KEY, "wiki_stats.old_missing", "old wiki stats missing ~{0}", old_wikistats_url.Raw());
		else {												// rename "wiki.gfs" to "wiki_stats.gfs"
			usr_dlg.Note_many(GRP_KEY, "wiki_stats.rename.bgn", "copying: src=~{0} trg=~{1}", old_wikistats_url.Raw(), new_wikistats_url.Raw());
			Io_mgr.I.CopyFil(old_wikistats_url, new_wikistats_url, false);
		}
		byte[] old_wikistats_bry = Io_mgr.I.LoadFilBry(new_wikistats_url);
		byte[] new_wikistats_bry = Bry_.Replace_between(old_wikistats_bry, Bry_.new_a7("props.main_page_('"), Bry_.new_a7("');\n"), Bry_.Empty);
		if (!Bry_.Eq(old_wikistats_bry, new_wikistats_bry)) {
			usr_dlg.Note_many(GRP_KEY, "wiki_stats.remove_mainpage", "removing mainpages");
			Io_mgr.I.SaveFilBry(new_wikistats_url, new_wikistats_bry);
		}
		usr_dlg.Note_many(GRP_KEY, "run.end", "migrate.end for ~{0}", wiki.Domain_str());
		usr_dlg.Note_many("", "", "");
	}
	static final String GRP_KEY = "xowa.wiki.upgrades.v00_02_01";
}
