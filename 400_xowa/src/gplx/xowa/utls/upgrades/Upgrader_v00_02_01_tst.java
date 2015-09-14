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
import org.junit.*; import gplx.xowa.bldrs.installs.*;
public class Upgrader_v00_02_01_tst {
	@Test  public void Run() {
		Xoae_app app = Xoa_app_fxt.app_();
		Xowe_wiki wiki = Xoa_app_fxt.wiki_tst_(app);
		Io_url cfg_dir = wiki.Fsys_mgr().Root_dir().GenSubDir("cfg");
		Io_mgr.I.SaveFilStr(cfg_dir.GenSubFil("siteInfo.xml"), Str_siteinfo_xml);
		Io_mgr.I.SaveFilStr(cfg_dir.GenSubFil("wiki.gfs"), Str_wikistats_gfs);
		Upgrader_v00_02_01 mgr = new Upgrader_v00_02_01();
		mgr.Run(wiki);
		Tfds.Eq_str_lines(Xow_cfg_wiki_core_tst.Const_wiki_core_cfg, Io_mgr.I.LoadFilStr(wiki.Tdb_fsys_mgr().Cfg_wiki_core_fil()));
		Tfds.Eq_str_lines("/* assume content ... */", Io_mgr.I.LoadFilStr(wiki.Tdb_fsys_mgr().Cfg_wiki_stats_fil()));
	}
	public static String Str_siteinfo_xml = String_.Concat_lines_nl
	(	"<siteinfo>"
	,	"    <sitename>Wikipedia</sitename>"
	,	"    <base>http://en.wikipedia.org/wiki/Main_Page</base>"
	,	"    <generator>MediaWiki 1.21wmf5</generator>"
	,	"    <case>first-letter</case>"
	,	"    <namespaces>"
	,	"      <namespace key=\"-2\" case=\"first-letter\">Media</namespace>"
	,	"      <namespace key=\"-1\" case=\"first-letter\">Special</namespace>"
	,	"      <namespace key=\"0\" case=\"first-letter\" />"
	,	"      <namespace key=\"1\" case=\"first-letter\">Talk</namespace>"
	,	"      <namespace key=\"2\" case=\"case-sensitive\">User test</namespace>"	// NOTE: intentionally changing this to "0|User test" to differ from existing
	,	"      <namespace key=\"3\" case=\"first-letter\">User talk</namespace>"
	,	"      <namespace key=\"4\" case=\"first-letter\">Wikipedia</namespace>"
	,	"      <namespace key=\"5\" case=\"first-letter\">Wikipedia talk</namespace>"
	,	"      <namespace key=\"6\" case=\"first-letter\">File</namespace>"
	,	"      <namespace key=\"7\" case=\"first-letter\">File talk</namespace>"
	,	"      <namespace key=\"8\" case=\"first-letter\">MediaWiki</namespace>"
	,	"      <namespace key=\"9\" case=\"first-letter\">MediaWiki talk</namespace>"
	,	"      <namespace key=\"10\" case=\"first-letter\">Template</namespace>"
	,	"      <namespace key=\"11\" case=\"first-letter\">Template talk</namespace>"
	,	"      <namespace key=\"12\" case=\"first-letter\">Help</namespace>"
	,	"      <namespace key=\"13\" case=\"first-letter\">Help talk</namespace>"
	,	"      <namespace key=\"14\" case=\"first-letter\">Category</namespace>"
	,	"      <namespace key=\"15\" case=\"first-letter\">Category talk</namespace>"
	,	"      <namespace key=\"100\" case=\"first-letter\">Portal</namespace>"
	,	"      <namespace key=\"101\" case=\"first-letter\">Portal talk</namespace>"
	,	"      <namespace key=\"108\" case=\"first-letter\">Book</namespace>"
	,	"      <namespace key=\"109\" case=\"first-letter\">Book talk</namespace>"
	,	"      <namespace key=\"828\" case=\"first-letter\">Module</namespace>"
	,	"      <namespace key=\"829\" case=\"first-letter\">Module talk</namespace>"
	,	"    </namespaces>"
	,	"  </siteinfo>"
	);
	private static String Str_wikistats_gfs = String_.Concat_lines_nl
	(	"/* assume content ... */"
	,	"props.main_page_('Main Page');"
	,	"props.main_page_('Main Page');"	// sometimes doubled
	);
}
