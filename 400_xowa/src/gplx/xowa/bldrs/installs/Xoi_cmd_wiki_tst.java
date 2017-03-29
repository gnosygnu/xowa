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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
import gplx.core.consoles.*;
import gplx.core.brys.args.*; import gplx.core.threads.*; import gplx.xowa.bldrs.setups.maints.*; import gplx.xowa.xtns.wbases.imports.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.bldrs.wms.*; import gplx.xowa.bldrs.wms.dumps.*;
public class Xoi_cmd_wiki_tst {
	@Test  public void Run() {	// MAINT:2017-03-28
//			Bld_import_list(Xow_domain_regy.All);
//			Bld_cfg_files(Xow_domain_regy.All);	// NOTE: remember to carry over the wikisource / page / index commands from the existing xowa_build_cfg.gfs; also, only run the xowa_build_cfg.gfs once; DATE:2013-10-15; last run: DATE:2014-09-09
	}
	public void Bld_import_list(String... ary) {
		int ary_len = ary.length;
		Bry_bfr bfr = Bry_bfr_.Reset(255);
		Wmf_latest_parser parser = new Wmf_latest_parser();
		Bfr_arg__time time_fmtr = new Bfr_arg__time();
		for (int i = 0; i < ary_len; i++)
			Bld_import_list_itm2(bfr, parser, time_fmtr, ary, i);
		Io_mgr.Instance.SaveFilStr("C:\\xowa\\user\\temp.txt", bfr.To_str());
	}
	private void Bld_import_list_itm2(Bry_bfr bfr, Wmf_latest_parser parser, Bfr_arg__time time_fmtr, String[] ary, int i) {
		String domain_str = ary[i];
		byte[] domain_bry = Bry_.new_a7(domain_str);
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(domain_bry);
		byte[] wmf_key_bry = Bry_.Replace(Xow_abrv_wm_.To_abrv(domain_itm), Byte_ascii.Dash, Byte_ascii.Underline);
		String wmf_key = String_.new_u8(wmf_key_bry);
		String url = "https://dumps.wikimedia.org/" + wmf_key + "/latest";
		byte[] latest_html = null;
		for (int j = 0; j < 5; ++j) {
			latest_html = Io_mgr.Instance.DownloadFil_args("", Io_url_.Empty).Exec_as_bry(url);
			if (latest_html != null) break;
			Tfds.Dbg("fail|" + domain_str + "|" + url);
			if (j == 4) return;
		}
		parser.Parse(latest_html);
		Xowm_dump_file dump_file = new Xowm_dump_file(domain_str, "latest", Xowm_dump_type_.Str__pages_articles);
		dump_file.Server_url_(Xowm_dump_file_.Server_wmf_https);
		byte[] pages_articles_key = Bry_.new_a7(wmf_key + "-latest-pages-articles.xml.bz2");
		Wmf_latest_itm latest_itm = parser.Get_by(pages_articles_key);
		if (latest_itm == null) {Tfds.Dbg("missing|" + domain_str + "|" + url); return;} // NOTE: commonswiki missing entry for commonswiki-latest-pages-articles.xml.bz2  DATE:2016-05-01
		Tfds.Dbg("pass|" + domain_str + "|" + url);
		bfr.Add(domain_bry).Add_byte_pipe();
		bfr.Add_str_u8(dump_file.File_url()).Add_byte_pipe();
		bfr.Add(Xow_domain_tid_.Get_type_as_bry(domain_itm.Domain_type_id())).Add_byte_pipe();
		long src_size = latest_itm.Size();
		bfr.Add_long_variable(src_size).Add_byte_pipe();
		bfr.Add_str_a7(gplx.core.ios.Io_size_.To_str(src_size)).Add_byte_pipe();
		time_fmtr.Seconds_(Math_.Div_safe_as_long(src_size, 1000000)).Bfr_arg__add(bfr);
		bfr.Add_byte_pipe();
		bfr.Add_str_a7(latest_itm.Date().XtoStr_fmt_yyyy_MM_dd_HH_mm());
		bfr.Add_byte_pipe();
		bfr.Add_str_a7(dump_file.Dump_date());
		bfr.Add_byte_nl();
	}
	/*
	private void Bld_import_list_itm(Bry_bfr bfr, Xowm_dump_file dump_file, Bry_fmtr_arg_time time_fmtr, String[] ary, int i) {
		String itm = ary[i];
		dump_file.Ctor(itm, "latest", Xowm_dump_type_.Str__pages_articles);
		int count = 0;
		while (count++ < 1) {
			dump_file.Server_url_(Xowm_dump_file_.Server_wmf);
			if (dump_file.Connect()) break;
			Tfds.WriteText(String_.Format("retrying: {0} {1}\n", count, dump_file.File_modified()));
			Thread_adp_.Sleep(15000);	// wait for connection to reset
		}
		if (count == 10) {
			Tfds.WriteText(String_.Format("failed: {0}\n", dump_file.File_url()));
			return;
		}
		else
			Tfds.WriteText(String_.Format("passed: {0}\n", itm));
		bfr.Add_str(itm).Add_byte_pipe();
		bfr.Add_str(dump_file.File_url()).Add_byte_pipe();
		bfr.Add(Xow_domain_tid_.Get_type_as_bry(dump_file.Wiki_type().Wiki_tid())).Add_byte_pipe();
//			Xol_lang_stub lang_itm = Xol_lang_stub_.Get_by_key(wiki_type.Lang_key());
//			if (lang_itm == null) lang_itm = Xol_lang_stub_.Get_by_key(Xol_lang_itm_.Key_en);	// commons, species, meta, etc will have no lang
//			bfr.Add(lang_itm.Local_name()).Add_byte_pipe();
//			bfr.Add(lang_itm.Canonical_name()).Add_byte_pipe();
		long src_size = dump_file.File_len();
		bfr.Add_long_variable(src_size).Add_byte_pipe();
		bfr.Add_str(gplx.core.ios.Io_size_.To_str(src_size)).Add_byte_pipe();
		time_fmtr.Seconds_(Math_.Div_safe_as_long(src_size, 1000000)).XferAry(bfr, 0);
		bfr.Add_byte_pipe();
		bfr.Add_str(dump_file.File_modified().XtoStr_fmt_yyyy_MM_dd_HH_mm());
		bfr.Add_byte_pipe();
//			bfr.Add_str(String_.Concat_with_obj(",", (Object[])dump_file.Dump_available_dates()));
//			bfr.Add_byte_pipe();
		bfr.Add_str(dump_file.Dump_date());
		bfr.Add_byte_nl();
		Thread_adp_.Sleep(1000);
	}
	*/
	public void Bld_cfg_files(String... ary) {
		Bry_bfr bfr = Bry_bfr_.Reset(255);
		gplx.xowa.bldrs.wiki_cfgs.Xoi_wiki_props_api api = new gplx.xowa.bldrs.wiki_cfgs.Xoi_wiki_props_api();
		gplx.xowa.bldrs.wiki_cfgs.Xoi_wiki_props_wiki wiki = new gplx.xowa.bldrs.wiki_cfgs.Xoi_wiki_props_wiki();
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			String wiki_domain = ary[i];
			try {
				byte[] xml = api.Exec_api(api.Api_src(wiki_domain));
				wiki.Wiki_domain_(Bry_.new_a7(wiki_domain));
				api.Parse(wiki, String_.new_u8(xml));
				api.Build_cfg(bfr, wiki);
			}
			catch (Exception e) {
				Console_adp__sys.Instance.Write_str_w_nl(Err_.Message_gplx_full(e));
			}
		}
		bfr.Add_str_a7("app.bldr.wiki_cfg_bldr.run;").Add_byte_nl();
		Io_mgr.Instance.SaveFilStr("C:\\user\\xowa_build_cfg.gfs", bfr.To_str());
	}
}
