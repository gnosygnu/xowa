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
package gplx.xowa; import gplx.*;
import org.junit.*;
import gplx.brys.*; import gplx.threads.*; import gplx.xowa.wikis.*; import gplx.xowa.setup.maints.*;
public class Xoi_cmd_wiki_tst {
	@Test  public void Run() {
//			Bld_import_list(Wikis);
//			Bld_cfg_files(Wikis);	// NOTE: remember to carry over the wikisource / page / index commands from the existing xowa_build_cfg.gfs; also, only run the xowa_build_cfg.gfs once; DATE:2013-10-15; last run: DATE:2014-09-09
	}
	public void Bld_import_list(String... ary) {
		int ary_len = ary.length;
		Bry_bfr bfr = Bry_bfr.reset_(255);
		Wmf_latest_parser parser = new Wmf_latest_parser();
		Xob_dump_file dump_file = new Xob_dump_file();
		Bry_fmtr_arg_time time_fmtr = new Bry_fmtr_arg_time();
		for (int i = 0; i < ary_len; i++)
			Bld_import_list_itm2(bfr, parser, dump_file, time_fmtr, ary, i);
		Io_mgr._.SaveFilStr("C:\\temp.txt", bfr.Xto_str());
	}
	private void Bld_import_list_itm2(Bry_bfr bfr, Wmf_latest_parser parser, Xob_dump_file dump_file, Bry_fmtr_arg_time time_fmtr, String[] ary, int i) {
		String domain_str = ary[i];
		byte[] domain_bry = Bry_.new_ascii_(domain_str);
		Xow_wiki_domain domain_itm = Xow_wiki_domain_.parse_by_domain(domain_bry);
		byte[] wmf_key_bry = Bry_.Replace(Xow_wiki_alias.Build_alias(domain_itm), Byte_ascii.Dash, Byte_ascii.Underline);
		String wmf_key = String_.new_utf8_(wmf_key_bry);
		String url = "http://dumps.wikimedia.org/" + wmf_key + "/latest";
		byte[] latest_html = null;
		for (int j = 0; j < 5; ++j) {
			latest_html = Io_mgr._.DownloadFil_args("", Io_url_.Null).Exec_as_bry(url);
			if (latest_html != null) break;
			Tfds.Write("fail|" + url);
			if (j == 4) return;
		}
		Tfds.Write("pass|" + url);
		parser.Parse(latest_html);
		dump_file.Ctor(domain_str, "latest", Xow_wiki_alias.Key_pages_articles);
		dump_file.Server_url_(Xob_dump_file_.Server_wmf);
		byte[] pages_articles_key = Bry_.new_ascii_(wmf_key + "-latest-pages-articles.xml.bz2");
		Wmf_latest_itm latest_itm = parser.Get_by(pages_articles_key);
		bfr.Add(domain_bry).Add_byte_pipe();
		bfr.Add_str(dump_file.File_url()).Add_byte_pipe();
		bfr.Add(Xow_wiki_domain_.Key_by_tid(domain_itm.Wiki_tid())).Add_byte_pipe();
		long src_size = latest_itm.Size();
		bfr.Add_long_variable(src_size).Add_byte_pipe();
		bfr.Add_str(gplx.ios.Io_size_.Xto_str(src_size)).Add_byte_pipe();
		time_fmtr.Seconds_(Math_.Div_safe_as_long(src_size, 1000000)).XferAry(bfr, 0);
		bfr.Add_byte_pipe();
		bfr.Add_str(latest_itm.Date().XtoStr_fmt_yyyy_MM_dd_HH_mm());
		bfr.Add_byte_pipe();
		bfr.Add_str(dump_file.Dump_date());
		bfr.Add_byte_nl();
	}
	/*
	private void Bld_import_list_itm(Bry_bfr bfr, Xob_dump_file dump_file, Bry_fmtr_arg_time time_fmtr, String[] ary, int i) {
		String itm = ary[i];
		dump_file.Ctor(itm, "latest", Xow_wiki_alias.Key_pages_articles);
		int count = 0;
		while (count++ < 1) {
			dump_file.Server_url_(Xob_dump_file_.Server_wmf);
			if (dump_file.Connect()) break;
			Tfds.WriteText(String_.Format("retrying: {0} {1}\n", count, dump_file.File_modified()));
			ThreadAdp_.Sleep(15000);	// wait for connection to reset
		}
		if (count == 10) {
			Tfds.WriteText(String_.Format("failed: {0}\n", dump_file.File_url()));
			return;
		}
		else
			Tfds.WriteText(String_.Format("passed: {0}\n", itm));
		bfr.Add_str(itm).Add_byte_pipe();
		bfr.Add_str(dump_file.File_url()).Add_byte_pipe();
		bfr.Add(Xow_wiki_domain_.Key_by_tid(dump_file.Wiki_type().Wiki_tid())).Add_byte_pipe();
//			Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key(wiki_type.Lang_key());
//			if (lang_itm == null) lang_itm = Xol_lang_itm_.Get_by_key(Xol_lang_.Key_en);	// commons, species, meta, etc will have no lang
//			bfr.Add(lang_itm.Local_name()).Add_byte_pipe();
//			bfr.Add(lang_itm.Canonical_name()).Add_byte_pipe();
		long src_size = dump_file.File_len();
		bfr.Add_long_variable(src_size).Add_byte_pipe();
		bfr.Add_str(gplx.ios.Io_size_.Xto_str(src_size)).Add_byte_pipe();
		time_fmtr.Seconds_(Math_.Div_safe_as_long(src_size, 1000000)).XferAry(bfr, 0);
		bfr.Add_byte_pipe();
		bfr.Add_str(dump_file.File_modified().XtoStr_fmt_yyyy_MM_dd_HH_mm());
		bfr.Add_byte_pipe();
//			bfr.Add_str(String_.ConcatWith_any(",", (Object[])dump_file.Dump_available_dates()));
//			bfr.Add_byte_pipe();
		bfr.Add_str(dump_file.Dump_date());
		bfr.Add_byte_nl();
		ThreadAdp_.Sleep(1000);
	}
	*/
	public void Bld_cfg_files(String... ary) {
		Bry_bfr bfr = Bry_bfr.reset_(255);
		gplx.xowa.bldrs.wiki_cfgs.Xoi_wiki_props_api api = new gplx.xowa.bldrs.wiki_cfgs.Xoi_wiki_props_api();
		gplx.xowa.bldrs.wiki_cfgs.Xoi_wiki_props_wiki wiki = new gplx.xowa.bldrs.wiki_cfgs.Xoi_wiki_props_wiki();
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			String wiki_domain = ary[i];
			try {
				byte[] xml = api.Exec_api(api.Api_src(wiki_domain));
				wiki.Wiki_domain_(Bry_.new_ascii_(wiki_domain));
				api.Parse(wiki, String_.new_utf8_(xml));
				api.Build_cfg(bfr, wiki);
			}
			catch (Exception e) {
				ConsoleAdp._.WriteLine(Err_.Message_gplx_brief(e));
			}
		}
		bfr.Add_str("app.bldr.wiki_cfg_bldr.run;").Add_byte_nl();
		Io_mgr._.SaveFilStr("C:\\xowa_build_cfg.gfs", bfr.Xto_str());
	}
	public static String[] Wikis = new String[]
//{ "simple.wikipedia.org"
//};
{ "aa.wikibooks.org"
, "aa.wikipedia.org"
, "aa.wiktionary.org"
, "ab.wikipedia.org"
, "ab.wiktionary.org"
, "ace.wikipedia.org"
, "af.wikibooks.org"
, "af.wikipedia.org"
, "af.wikiquote.org"
, "af.wiktionary.org"
, "ak.wikibooks.org"
, "ak.wikipedia.org"
, "ak.wiktionary.org"
, "als.wikibooks.org"
, "als.wikipedia.org"
, "als.wikiquote.org"
, "als.wiktionary.org"
, "am.wikipedia.org"
, "am.wikiquote.org"
, "am.wiktionary.org"
, "an.wikipedia.org"
, "an.wiktionary.org"
, "ang.wikibooks.org"
, "ang.wikipedia.org"
, "ang.wikiquote.org"
, "ang.wikisource.org"
, "ang.wiktionary.org"
, "ar.wikibooks.org"
, "ar.wikinews.org"
, "ar.wikipedia.org"
, "ar.wikiquote.org"
, "ar.wikisource.org"
, "ar.wikiversity.org"
, "ar.wiktionary.org"
, "arc.wikipedia.org"
, "arz.wikipedia.org"
, "as.wikibooks.org"
, "as.wikipedia.org"
, "as.wikisource.org"
, "as.wiktionary.org"
, "ast.wikibooks.org"
, "ast.wikipedia.org"
, "ast.wikiquote.org"
, "ast.wiktionary.org"
, "av.wikipedia.org"
, "av.wiktionary.org"
, "ay.wikibooks.org"
, "ay.wikipedia.org"
, "ay.wiktionary.org"
, "az.wikibooks.org"
, "az.wikipedia.org"
, "az.wikiquote.org"
, "az.wikisource.org"
, "az.wiktionary.org"
, "ba.wikibooks.org"
, "ba.wikipedia.org"
, "bar.wikipedia.org"
, "bat-smg.wikipedia.org"
, "bcl.wikipedia.org"
, "be-x-old.wikipedia.org"
, "be.wikibooks.org"
, "be.wikipedia.org"
, "be.wikiquote.org"
, "be.wikisource.org"
, "be.wiktionary.org"
, "bg.wikibooks.org"
, "bg.wikinews.org"
, "bg.wikipedia.org"
, "bg.wikiquote.org"
, "bg.wikisource.org"
, "bg.wiktionary.org"
, "bh.wikipedia.org"
, "bh.wiktionary.org"
, "bi.wikibooks.org"
, "bi.wikipedia.org"
, "bi.wiktionary.org"
, "bjn.wikipedia.org"
, "bm.wikibooks.org"
, "bm.wikipedia.org"
, "bm.wikiquote.org"
, "bm.wiktionary.org"
, "bn.wikibooks.org"
, "bn.wikipedia.org"
, "bn.wikisource.org"
, "bn.wiktionary.org"
, "bo.wikibooks.org"
, "bo.wikipedia.org"
, "bo.wiktionary.org"
, "bpy.wikipedia.org"
, "br.wikipedia.org"
, "br.wikiquote.org"
, "br.wikisource.org"
, "br.wiktionary.org"
, "bs.wikibooks.org"
, "bs.wikinews.org"
, "bs.wikipedia.org"
, "bs.wikiquote.org"
, "bs.wikisource.org"
, "bs.wiktionary.org"
, "bug.wikipedia.org"
, "bxr.wikipedia.org"
, "ca.wikibooks.org"
, "ca.wikinews.org"
, "ca.wikipedia.org"
, "ca.wikiquote.org"
, "ca.wikisource.org"
, "ca.wiktionary.org"
, "cbk-zam.wikipedia.org"
, "cdo.wikipedia.org"
, "ce.wikipedia.org"
, "ceb.wikipedia.org"
, "ch.wikibooks.org"
, "ch.wikipedia.org"
, "ch.wiktionary.org"
, "cho.wikipedia.org"
, "chr.wikipedia.org"
, "chr.wiktionary.org"
, "chy.wikipedia.org"
, "ckb.wikipedia.org"
, "co.wikibooks.org"
, "co.wikipedia.org"
, "co.wikiquote.org"
, "co.wiktionary.org"
, "commons.wikimedia.org"
, "cr.wikipedia.org"
, "cr.wikiquote.org"
, "cr.wiktionary.org"
, "crh.wikipedia.org"
, "cs.wikibooks.org"
, "cs.wikinews.org"
, "cs.wikipedia.org"
, "cs.wikiquote.org"
, "cs.wikisource.org"
, "cs.wikiversity.org"
, "cs.wiktionary.org"
, "csb.wikipedia.org"
, "csb.wiktionary.org"
, "cu.wikipedia.org"
, "cv.wikibooks.org"
, "cv.wikipedia.org"
, "cy.wikibooks.org"
, "cy.wikipedia.org"
, "cy.wikiquote.org"
, "cy.wikisource.org"
, "cy.wiktionary.org"
, "da.wikibooks.org"
, "da.wikipedia.org"
, "da.wikiquote.org"
, "da.wikisource.org"
, "da.wiktionary.org"
, "de.wikibooks.org"
, "de.wikinews.org"
, "de.wikipedia.org"
, "de.wikiquote.org"
, "de.wikisource.org"
, "de.wikiversity.org"
, "de.wikivoyage.org"
, "de.wiktionary.org"
, "diq.wikipedia.org"
, "dsb.wikipedia.org"
, "dv.wikipedia.org"
, "dv.wiktionary.org"
, "dz.wikipedia.org"
, "dz.wiktionary.org"
, "ee.wikipedia.org"
, "el.wikibooks.org"
, "el.wikinews.org"
, "el.wikipedia.org"
, "el.wikiquote.org"
, "el.wikisource.org"
, "el.wikiversity.org"
, "el.wikivoyage.org"
, "el.wiktionary.org"
, "eml.wikipedia.org"
, "en.wikibooks.org"
, "en.wikinews.org"
, "en.wikipedia.org"
, "en.wikiquote.org"
, "en.wikisource.org"
, "en.wikiversity.org"
, "en.wikivoyage.org"
, "en.wiktionary.org"
, "eo.wikibooks.org"
, "eo.wikinews.org"
, "eo.wikipedia.org"
, "eo.wikiquote.org"
, "eo.wikisource.org"
, "eo.wiktionary.org"
, "es.wikibooks.org"
, "es.wikinews.org"
, "es.wikipedia.org"
, "es.wikiquote.org"
, "es.wikisource.org"
, "es.wikiversity.org"
, "es.wikivoyage.org"
, "es.wiktionary.org"
, "et.wikibooks.org"
, "et.wikipedia.org"
, "et.wikiquote.org"
, "et.wikisource.org"
, "et.wiktionary.org"
, "eu.wikibooks.org"
, "eu.wikipedia.org"
, "eu.wikiquote.org"
, "eu.wiktionary.org"
, "ext.wikipedia.org"
, "fa.wikibooks.org"
, "fa.wikinews.org"
, "fa.wikipedia.org"
, "fa.wikiquote.org"
, "fa.wikisource.org"
, "fa.wikivoyage.org"
, "fa.wiktionary.org"
, "ff.wikipedia.org"
, "fi.wikibooks.org"
, "fi.wikinews.org"
, "fi.wikipedia.org"
, "fi.wikiquote.org"
, "fi.wikisource.org"
, "fi.wikiversity.org"
, "fi.wiktionary.org"
, "fiu-vro.wikipedia.org"
, "fj.wikipedia.org"
, "fj.wiktionary.org"
, "fo.wikipedia.org"
, "fo.wikisource.org"
, "fo.wiktionary.org"
, "fr.wikibooks.org"
, "fr.wikinews.org"
, "fr.wikipedia.org"
, "fr.wikiquote.org"
, "fr.wikisource.org"
, "fr.wikiversity.org"
, "fr.wikivoyage.org"
, "fr.wiktionary.org"
, "frp.wikipedia.org"
, "frr.wikipedia.org"
, "fur.wikipedia.org"
, "fy.wikibooks.org"
, "fy.wikipedia.org"
, "fy.wiktionary.org"
, "ga.wikibooks.org"
, "ga.wikipedia.org"
, "ga.wikiquote.org"
, "ga.wiktionary.org"
, "gag.wikipedia.org"
, "gan.wikipedia.org"
, "gd.wikipedia.org"
, "gd.wiktionary.org"
, "gl.wikibooks.org"
, "gl.wikipedia.org"
, "gl.wikiquote.org"
, "gl.wikisource.org"
, "gl.wiktionary.org"
, "glk.wikipedia.org"
, "gn.wikibooks.org"
, "gn.wikipedia.org"
, "gn.wiktionary.org"
, "got.wikibooks.org"
, "got.wikipedia.org"
, "gu.wikibooks.org"
, "gu.wikipedia.org"
, "gu.wikiquote.org"
, "gu.wikisource.org"
, "gu.wiktionary.org"
, "gv.wikipedia.org"
, "gv.wiktionary.org"
, "ha.wikipedia.org"
, "ha.wiktionary.org"
, "hak.wikipedia.org"
, "haw.wikipedia.org"
, "he.wikibooks.org"
, "he.wikinews.org"
, "he.wikipedia.org"
, "he.wikiquote.org"
, "he.wikisource.org"
, "he.wikivoyage.org"
, "he.wiktionary.org"
, "hi.wikibooks.org"
, "hi.wikipedia.org"
, "hi.wikiquote.org"
, "hi.wiktionary.org"
, "hif.wikipedia.org"
, "ho.wikipedia.org"
, "hr.wikibooks.org"
, "hr.wikipedia.org"
, "hr.wikiquote.org"
, "hr.wikisource.org"
, "hr.wiktionary.org"
, "hsb.wikipedia.org"
, "hsb.wiktionary.org"
, "ht.wikipedia.org"
, "ht.wikisource.org"
, "hu.wikibooks.org"
, "hu.wikinews.org"
, "hu.wikipedia.org"
, "hu.wikiquote.org"
, "hu.wikisource.org"
, "hu.wiktionary.org"
, "hy.wikibooks.org"
, "hy.wikipedia.org"
, "hy.wikiquote.org"
, "hy.wikisource.org"
, "hy.wiktionary.org"
, "hz.wikipedia.org"
, "ia.wikibooks.org"
, "ia.wikipedia.org"
, "ia.wiktionary.org"
, "id.wikibooks.org"
, "id.wikipedia.org"
, "id.wikiquote.org"
, "id.wikisource.org"
, "id.wiktionary.org"
, "ie.wikibooks.org"
, "ie.wikipedia.org"
, "ie.wiktionary.org"
, "ig.wikipedia.org"
, "ii.wikipedia.org"
, "ik.wikipedia.org"
, "ik.wiktionary.org"
, "ilo.wikipedia.org"
, "incubator.wikimedia.org"
, "io.wikipedia.org"
, "io.wiktionary.org"
, "is.wikibooks.org"
, "is.wikipedia.org"
, "is.wikiquote.org"
, "is.wikisource.org"
, "is.wiktionary.org"
, "it.wikibooks.org"
, "it.wikinews.org"
, "it.wikipedia.org"
, "it.wikiquote.org"
, "it.wikisource.org"
, "it.wikiversity.org"
, "it.wikivoyage.org"
, "it.wiktionary.org"
, "iu.wikipedia.org"
, "iu.wiktionary.org"
, "ja.wikibooks.org"
, "ja.wikinews.org"
, "ja.wikipedia.org"
, "ja.wikiquote.org"
, "ja.wikisource.org"
, "ja.wikiversity.org"
, "ja.wiktionary.org"
, "jbo.wikipedia.org"
, "jbo.wiktionary.org"
, "jv.wikipedia.org"
, "jv.wiktionary.org"
, "ka.wikibooks.org"
, "ka.wikipedia.org"
, "ka.wikiquote.org"
, "ka.wiktionary.org"
, "kaa.wikipedia.org"
, "kab.wikipedia.org"
, "kbd.wikipedia.org"
, "kg.wikipedia.org"
, "ki.wikipedia.org"
, "kj.wikipedia.org"
, "kk.wikibooks.org"
, "kk.wikipedia.org"
, "kk.wikiquote.org"
, "kk.wiktionary.org"
, "kl.wikipedia.org"
, "kl.wiktionary.org"
, "km.wikibooks.org"
, "km.wikipedia.org"
, "km.wiktionary.org"
, "kn.wikibooks.org"
, "kn.wikipedia.org"
, "kn.wikiquote.org"
, "kn.wikisource.org"
, "kn.wiktionary.org"
, "ko.wikibooks.org"
, "ko.wikinews.org"
, "ko.wikipedia.org"
, "ko.wikiquote.org"
, "ko.wikisource.org"
, "ko.wikiversity.org"
, "ko.wiktionary.org"
, "koi.wikipedia.org"
, "kr.wikipedia.org"
, "kr.wikiquote.org"
, "krc.wikipedia.org"
, "ks.wikibooks.org"
, "ks.wikipedia.org"
, "ks.wikiquote.org"
, "ks.wiktionary.org"
, "ksh.wikipedia.org"
, "ku.wikibooks.org"
, "ku.wikipedia.org"
, "ku.wikiquote.org"
, "ku.wiktionary.org"
, "kv.wikipedia.org"
, "kw.wikipedia.org"
, "kw.wikiquote.org"
, "kw.wiktionary.org"
, "ky.wikibooks.org"
, "ky.wikipedia.org"
, "ky.wikiquote.org"
, "ky.wiktionary.org"
, "la.wikibooks.org"
, "la.wikipedia.org"
, "la.wikiquote.org"
, "la.wikisource.org"
, "la.wiktionary.org"
, "lad.wikipedia.org"
, "lb.wikibooks.org"
, "lb.wikipedia.org"
, "lb.wikiquote.org"
, "lb.wiktionary.org"
, "lbe.wikipedia.org"
, "lez.wikipedia.org"
, "lg.wikipedia.org"
, "li.wikibooks.org"
, "li.wikipedia.org"
, "li.wikiquote.org"
, "li.wikisource.org"
, "li.wiktionary.org"
, "lij.wikipedia.org"
, "lmo.wikipedia.org"
, "ln.wikibooks.org"
, "ln.wikipedia.org"
, "ln.wiktionary.org"
, "lo.wikipedia.org"
, "lo.wiktionary.org"
, "lt.wikibooks.org"
, "lt.wikipedia.org"
, "lt.wikiquote.org"
, "lt.wikisource.org"
, "lt.wiktionary.org"
, "ltg.wikipedia.org"
, "lv.wikibooks.org"
, "lv.wikipedia.org"
, "lv.wiktionary.org"
, "mai.wikipedia.org"
, "map-bms.wikipedia.org"
, "mdf.wikipedia.org"
, "meta.wikimedia.org"
, "mg.wikibooks.org"
, "mg.wikipedia.org"
, "mg.wiktionary.org"
, "mh.wikipedia.org"
, "mh.wiktionary.org"
, "mhr.wikipedia.org"
, "mi.wikibooks.org"
, "mi.wikipedia.org"
, "mi.wiktionary.org"
, "min.wikipedia.org"
, "mk.wikibooks.org"
, "mk.wikipedia.org"
, "mk.wikisource.org"
, "mk.wiktionary.org"
, "ml.wikibooks.org"
, "ml.wikipedia.org"
, "ml.wikiquote.org"
, "ml.wikisource.org"
, "ml.wiktionary.org"
, "mn.wikibooks.org"
, "mn.wikipedia.org"
, "mn.wiktionary.org"
, "mo.wikipedia.org"
, "mo.wiktionary.org"
, "mr.wikibooks.org"
, "mr.wikipedia.org"
, "mr.wikiquote.org"
, "mr.wikisource.org"
, "mr.wiktionary.org"
, "mrj.wikipedia.org"
, "ms.wikibooks.org"
, "ms.wikipedia.org"
, "ms.wiktionary.org"
, "mt.wikipedia.org"
, "mt.wiktionary.org"
, "mus.wikipedia.org"
, "mwl.wikipedia.org"
, "my.wikibooks.org"
, "my.wikipedia.org"
, "my.wiktionary.org"
, "myv.wikipedia.org"
, "mzn.wikipedia.org"
, "na.wikibooks.org"
, "na.wikipedia.org"
, "na.wikiquote.org"
, "na.wiktionary.org"
, "nah.wikibooks.org"
, "nah.wikipedia.org"
, "nah.wiktionary.org"
, "nap.wikipedia.org"
, "nds-nl.wikipedia.org"
, "nds.wikibooks.org"
, "nds.wikipedia.org"
, "nds.wikiquote.org"
, "nds.wiktionary.org"
, "ne.wikibooks.org"
, "ne.wikipedia.org"
, "ne.wiktionary.org"
, "new.wikipedia.org"
, "ng.wikipedia.org"
, "nl.wikibooks.org"
, "nl.wikinews.org"
, "nl.wikipedia.org"
, "nl.wikiquote.org"
, "nl.wikisource.org"
, "nl.wikivoyage.org"
, "nl.wiktionary.org"
, "nn.wikipedia.org"
, "nn.wikiquote.org"
, "nn.wiktionary.org"
, "no.wikibooks.org"
, "no.wikinews.org"
, "no.wikipedia.org"
, "no.wikiquote.org"
, "no.wikisource.org"
, "no.wiktionary.org"
, "nov.wikipedia.org"
, "nrm.wikipedia.org"
, "nso.wikipedia.org"
, "nv.wikipedia.org"
, "ny.wikipedia.org"
, "oc.wikibooks.org"
, "oc.wikipedia.org"
, "oc.wiktionary.org"
, "om.wikipedia.org"
, "om.wiktionary.org"
, "or.wikipedia.org"
, "or.wikisource.org"
, "or.wiktionary.org"
, "os.wikipedia.org"
, "pa.wikibooks.org"
, "pa.wikipedia.org"
, "pa.wiktionary.org"
, "pag.wikipedia.org"
, "pam.wikipedia.org"
, "pap.wikipedia.org"
, "pcd.wikipedia.org"
, "pdc.wikipedia.org"
, "pfl.wikipedia.org"
, "pi.wikipedia.org"
, "pi.wiktionary.org"
, "pih.wikipedia.org"
, "pl.wikibooks.org"
, "pl.wikinews.org"
, "pl.wikipedia.org"
, "pl.wikiquote.org"
, "pl.wikisource.org"
, "pl.wikivoyage.org"
, "pl.wiktionary.org"
, "pms.wikipedia.org"
, "pnb.wikipedia.org"
, "pnb.wiktionary.org"
, "pnt.wikipedia.org"
, "ps.wikibooks.org"
, "ps.wikipedia.org"
, "ps.wiktionary.org"
, "pt.wikibooks.org"
, "pt.wikinews.org"
, "pt.wikipedia.org"
, "pt.wikiquote.org"
, "pt.wikisource.org"
, "pt.wikiversity.org"
, "pt.wikivoyage.org"
, "pt.wiktionary.org"
, "qu.wikibooks.org"
, "qu.wikipedia.org"
, "qu.wikiquote.org"
, "qu.wiktionary.org"
, "rm.wikibooks.org"
, "rm.wikipedia.org"
, "rm.wiktionary.org"
, "rmy.wikipedia.org"
, "rn.wikipedia.org"
, "rn.wiktionary.org"
, "ro.wikibooks.org"
, "ro.wikinews.org"
, "ro.wikipedia.org"
, "ro.wikiquote.org"
, "ro.wikisource.org"
, "ro.wikivoyage.org"
, "ro.wiktionary.org"
, "roa-rup.wikipedia.org"
, "roa-rup.wiktionary.org"
, "roa-tara.wikipedia.org"
, "ru.wikibooks.org"
, "ru.wikinews.org"
, "ru.wikipedia.org"
, "ru.wikiquote.org"
, "ru.wikisource.org"
, "ru.wikiversity.org"
, "ru.wikivoyage.org"
, "ru.wiktionary.org"
, "rue.wikipedia.org"
, "rw.wikipedia.org"
, "rw.wiktionary.org"
, "sa.wikibooks.org"
, "sa.wikipedia.org"
, "sa.wikiquote.org"
, "sa.wikisource.org"
, "sa.wiktionary.org"
, "sah.wikipedia.org"
, "sah.wikisource.org"
, "sc.wikipedia.org"
, "sc.wiktionary.org"
, "scn.wikipedia.org"
, "scn.wiktionary.org"
, "sco.wikipedia.org"
, "sd.wikinews.org"
, "sd.wikipedia.org"
, "sd.wiktionary.org"
, "se.wikibooks.org"
, "se.wikipedia.org"
, "sg.wikipedia.org"
, "sg.wiktionary.org"
, "sh.wikipedia.org"
, "sh.wiktionary.org"
, "si.wikibooks.org"
, "si.wikipedia.org"
, "si.wiktionary.org"
, "simple.wikibooks.org"
, "simple.wikipedia.org"
, "simple.wikiquote.org"
, "simple.wiktionary.org"
, "sk.wikibooks.org"
, "sk.wikipedia.org"
, "sk.wikiquote.org"
, "sk.wikisource.org"
, "sk.wiktionary.org"
, "sl.wikibooks.org"
, "sl.wikipedia.org"
, "sl.wikiquote.org"
, "sl.wikisource.org"
, "sl.wikiversity.org"
, "sl.wiktionary.org"
, "sm.wikipedia.org"
, "sm.wiktionary.org"
, "sn.wikipedia.org"
, "sn.wiktionary.org"
, "so.wikipedia.org"
, "so.wiktionary.org"
, "species.wikimedia.org"
, "sq.wikibooks.org"
, "sq.wikinews.org"
, "sq.wikipedia.org"
, "sq.wikiquote.org"
, "sq.wiktionary.org"
, "sr.wikibooks.org"
, "sr.wikinews.org"
, "sr.wikipedia.org"
, "sr.wikiquote.org"
, "sr.wikisource.org"
, "sr.wiktionary.org"
, "srn.wikipedia.org"
, "ss.wikipedia.org"
, "ss.wiktionary.org"
, "st.wikipedia.org"
, "st.wiktionary.org"
, "stq.wikipedia.org"
, "su.wikibooks.org"
, "su.wikipedia.org"
, "su.wikiquote.org"
, "su.wiktionary.org"
, "sv.wikibooks.org"
, "sv.wikinews.org"
, "sv.wikipedia.org"
, "sv.wikiquote.org"
, "sv.wikisource.org"
, "sv.wikiversity.org"
, "sv.wikivoyage.org"
, "sv.wiktionary.org"
, "sw.wikibooks.org"
, "sw.wikipedia.org"
, "sw.wiktionary.org"
, "szl.wikipedia.org"
, "ta.wikibooks.org"
, "ta.wikinews.org"
, "ta.wikipedia.org"
, "ta.wikiquote.org"
, "ta.wikisource.org"
, "ta.wiktionary.org"
, "te.wikibooks.org"
, "te.wikipedia.org"
, "te.wikiquote.org"
, "te.wikisource.org"
, "te.wiktionary.org"
, "tet.wikipedia.org"
, "tg.wikibooks.org"
, "tg.wikipedia.org"
, "tg.wiktionary.org"
, "th.wikibooks.org"
, "th.wikinews.org"
, "th.wikipedia.org"
, "th.wikiquote.org"
, "th.wikisource.org"
, "th.wiktionary.org"
, "ti.wikipedia.org"
, "ti.wiktionary.org"
, "tk.wikibooks.org"
, "tk.wikipedia.org"
, "tk.wikiquote.org"
, "tk.wiktionary.org"
, "tl.wikibooks.org"
, "tl.wikipedia.org"
, "tl.wiktionary.org"
, "tn.wikipedia.org"
, "tn.wiktionary.org"
, "to.wikipedia.org"
, "to.wiktionary.org"
, "tpi.wikipedia.org"
, "tpi.wiktionary.org"
, "tr.wikibooks.org"
, "tr.wikinews.org"
, "tr.wikipedia.org"
, "tr.wikiquote.org"
, "tr.wikisource.org"
, "tr.wiktionary.org"
, "ts.wikipedia.org"
, "ts.wiktionary.org"
, "tt.wikibooks.org"
, "tt.wikipedia.org"
, "tt.wikiquote.org"
, "tt.wiktionary.org"
, "tum.wikipedia.org"
, "tw.wikipedia.org"
, "tw.wiktionary.org"
, "ty.wikipedia.org"
, "tyv.wikipedia.org"
, "udm.wikipedia.org"
, "ug.wikibooks.org"
, "ug.wikipedia.org"
, "ug.wikiquote.org"
, "ug.wiktionary.org"
, "uk.wikibooks.org"
, "uk.wikinews.org"
, "uk.wikipedia.org"
, "uk.wikiquote.org"
, "uk.wikisource.org"
, "uk.wikivoyage.org"
, "uk.wiktionary.org"
, "ur.wikibooks.org"
, "ur.wikipedia.org"
, "ur.wikiquote.org"
, "ur.wiktionary.org"
, "uz.wikibooks.org"
, "uz.wikipedia.org"
, "uz.wikiquote.org"
, "uz.wiktionary.org"
, "ve.wikipedia.org"
, "vec.wikipedia.org"
, "vec.wikisource.org"
, "vec.wiktionary.org"
, "vep.wikipedia.org"
, "vi.wikibooks.org"
, "vi.wikipedia.org"
, "vi.wikiquote.org"
, "vi.wikisource.org"
, "vi.wikivoyage.org"
, "vi.wiktionary.org"
, "vls.wikipedia.org"
, "vo.wikibooks.org"
, "vo.wikipedia.org"
, "vo.wikiquote.org"
, "vo.wiktionary.org"
, "wa.wikibooks.org"
, "wa.wikipedia.org"
, "wa.wiktionary.org"
, "war.wikipedia.org"
, "wikimediafoundation.org"
, "wo.wikipedia.org"
, "wo.wikiquote.org"
, "wo.wiktionary.org"
, "wuu.wikipedia.org"
, "www.mediawiki.org"
, "www.wikidata.org"
, "xal.wikipedia.org"
, "xh.wikibooks.org"
, "xh.wikipedia.org"
, "xh.wiktionary.org"
, "xmf.wikipedia.org"
, "yi.wikipedia.org"
, "yi.wikisource.org"
, "yi.wiktionary.org"
, "yo.wikibooks.org"
, "yo.wikipedia.org"
, "yo.wiktionary.org"
, "za.wikibooks.org"
, "za.wikipedia.org"
, "za.wikiquote.org"
, "za.wiktionary.org"
, "zea.wikipedia.org"
, "zh-classical.wikipedia.org"
, "zh-min-nan.wikibooks.org"
, "zh-min-nan.wikipedia.org"
, "zh-min-nan.wikiquote.org"
, "zh-min-nan.wikisource.org"
, "zh-min-nan.wiktionary.org"
, "zh-yue.wikipedia.org"
, "zh.wikibooks.org"
, "zh.wikinews.org"
, "zh.wikipedia.org"
, "zh.wikiquote.org"
, "zh.wikisource.org"
, "zh.wikivoyage.org"
, "zh.wiktionary.org"
, "zu.wikibooks.org"
, "zu.wikipedia.org"
, "zu.wiktionary.org"
};
}
//, "als.wikisource.org"
//, "als.wikinews.org"
//, "nds.wikinews.org"
//, "ba.wiktionary.org"
//, "tokipona.wikibooks.org"