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
package gplx.xowa.bldrs.cmds.texts.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.data.site_stats.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.tdbs.*; import gplx.xowa.wikis.tdbs.hives.*; import gplx.xowa.wikis.tdbs.xdats.*;
public class Xob_calc_stats_cmd extends Xob_itm_basic_base implements Xob_cmd {
	public Xob_calc_stats_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_tdb_calc_stats;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {Exec();}
	public void Cmd_end() {}
	public void Cmd_term() {}
	private void Exec() {
		int ns_len = wiki.Ns_mgr().Ords_len();
		int total = 0;
		for (int i = 0; i < ns_len; i++) {
			Xow_ns ns = wiki.Ns_mgr().Ords_ary()[i];
			int ns_count = Calc_counts(ns);
			ns.Count_(ns_count);
			total += ns_count;
		}
		int count_main = Calc_count_articles(wiki.Ns_mgr().Ns_main());
		int count_file = Calc_count_articles(wiki.Ns_mgr().Ns_file());
		Bry_bfr bfr = Bry_bfr_.New();
		Gen_call(Bool_.Y, bfr, Xowe_wiki.Invk_stats);
		Gen_call(Bool_.N, bfr, Xowd_site_stats_mgr.Invk_number_of_articles_, count_main);
		Gen_call(Bool_.N, bfr, Xowd_site_stats_mgr.Invk_number_of_files_, count_file);
		Gen_call(Bool_.N, bfr, Xowd_site_stats_mgr.Invk_number_of_pages_, total);
		for (int i = 0; i < ns_len; i++) {
			Xow_ns ns = wiki.Ns_mgr().Ords_ary()[i];
			if (ns.Id() < 0) continue;
			bfr.Add_byte_nl();
			Gen_call(Bool_.N, bfr, Xowd_site_stats_mgr.Invk_number_of_articles_in_ns_, ns.Num_str(), Int_.To_str_pad_bgn_zero(ns.Count(), 10));
		}
		bfr.Add_byte_nl().Add_byte(Byte_ascii.Semic).Add_byte_nl();
		Io_url wiki_gfs = Wiki_gfs_url(wiki);
		Io_mgr.Instance.SaveFilBfr(wiki_gfs, bfr);
	}
	private void Gen_call(boolean first, Bry_bfr bfr, String key, Object... vals) {
		if (!first) bfr.Add_byte(Byte_ascii.Dot);
		bfr.Add_str_u8(key);
		int len = vals.length;
		if (len > 0) {
			bfr.Add_byte(Byte_ascii.Paren_bgn);
			for (int i = 0; i < len; i++) {
				if (i != 0) bfr.Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Space);
				Object val = vals[i];
				bfr.Add_str_u8(Object_.Xto_str_strict_or_null_mark(val));
			}
			bfr.Add_byte(Byte_ascii.Paren_end);
		}
	}
	int Calc_counts(Xow_ns ns) {
		Io_url reg_url = wiki.Tdb_fsys_mgr().Url_ns_reg(ns.Num_str(), Xotdb_dir_info_.Tid_ttl);
		Xowd_regy_mgr reg_mgr = new Xowd_regy_mgr(reg_url);
		int files_ary_len = reg_mgr.Files_ary().length;
		int count = 0;
		for (int i = 0; i < files_ary_len; i++) {
			count += reg_mgr.Files_ary()[i].Count();
		}
		return count;
	}
	int Calc_count_articles(Xow_ns ns) {
		Io_url hive_dir = wiki.Fsys_mgr().Root_dir().GenSubDir_nest(Xotdb_dir_info_.Name_ns, ns.Num_str(), Xotdb_dir_info_.Name_title);
		return Calc_count_articles_dir(ns, hive_dir);
	}
	int Calc_count_articles_dir(Xow_ns ns, Io_url dir) {
		Io_url[] subs = Io_mgr.Instance.QueryDir_args(dir).DirInclude_().ExecAsUrlAry();
		int count = 0;
		int subs_len = subs.length;
		bldr.Usr_dlg().Prog_one(GRP_KEY, "count", "calculating: ~{0}", dir.Raw());
		for (int i = 0; i < subs_len; i++) {
			Io_url sub = subs[i];
			if (sub.Type_dir())
				count += Calc_count_articles_dir(ns, sub);
			else
				count += Calc_count_articles_fil(ns, sub);
		}
		return count;
	}
	int Calc_count_articles_fil(Xow_ns ns, Io_url fil) {
		if (String_.Eq(fil.NameAndExt(), Xotdb_dir_info_.Name_reg_fil)) return 0;
		int rv = 0;
		byte[] bry = Io_mgr.Instance.LoadFilBry(fil);
		Xob_xdat_file xdat_file = new Xob_xdat_file().Parse(bry, bry.length, fil);
		Xowd_page_itm page = Xowd_page_itm.new_tmp();
		int count = xdat_file.Count();
		for (int i = 0; i < count; i++) {
			byte[] ttl_bry = xdat_file.Get_bry(i);
			Xotdb_page_itm_.Txt_ttl_load(page, ttl_bry);
			rv += page.Redirected() ? 0 : 1;
		}
		return rv;
	}
	static final String GRP_KEY = "xowa.bldr.calc_stats";
	public static Io_url Wiki_gfs_url(Xowe_wiki wiki) {return wiki.Fsys_mgr().Root_dir().GenSubFil_nest("cfg", "wiki_stats.gfs");}
}
