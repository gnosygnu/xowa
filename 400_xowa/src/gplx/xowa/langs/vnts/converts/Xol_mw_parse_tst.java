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
package gplx.xowa.langs.vnts.converts;
import gplx.libs.files.Io_mgr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import org.junit.*; import gplx.core.log_msgs.*; import gplx.langs.phps.*;
public class Xol_mw_parse_tst {
	private final Xol_mw_parse_fxt fxt = new Xol_mw_parse_fxt();
//		@Test public void Basic() {
//			fxt.Test_convert("$zh2Hant = array('a' => 'A', 'b' => 'B',);", String_.Concat_lines_nl
//			( "// zh_zh-hant"
//			, "app.langs.get('zh').converts.get('zh-hant').add_bulk("
//			, "<:['"
//			, "a|A"
//			, "b|B"
//			, "']:>"
//			, ");"
//			));
//		}
//		@Test public void Run() {
//			Io_url src_dir = Io_url_.new_dir_("C:\\xowa\\bin\\any\\xowa\\cfg\\lang\\mediawiki\\converts\\");
//			Io_url trg_dir = Io_url_.new_dir_("C:\\xowa\\bin\\any\\xowa\\cfg\\lang\\");
//			fxt.Test_run(src_dir, trg_dir);
//		}
	@Test public void Ignore() {
		fxt.toString();
	}
}
class Xol_mw_parse_grp {
	public byte[] Lng() {return lng;} public Xol_mw_parse_grp Lng_(byte[] v) {lng = v; return this;} private byte[] lng;
	public byte[] Vnt() {return vnt;} public Xol_mw_parse_grp Vnt_(byte[] v) {vnt = v; return this;} private byte[] vnt;
	public Xol_mw_parse_itm[] Itms() {return itms;} public Xol_mw_parse_grp Itms_(Xol_mw_parse_itm[] v) {itms = v; return this;} private Xol_mw_parse_itm[] itms;
	public void Write_as_gfs(BryWtr bfr) {
		int itms_len = itms.length;
		Write_bgn(bfr);
		for (int i = 0; i < itms_len; i++) {
			Xol_mw_parse_itm itm = (Xol_mw_parse_itm)itms[i];
			Write_itm(bfr, itm);
		}
		Write_end(bfr);
	}
	private void Write_bgn(BryWtr bfr) {
		bfr.AddStrA7("// ").Add(lng).AddStrA7("_").Add(vnt).AddByteNl();
		bfr.AddStrA7("app.langs.get('");
		bfr.Add(lng);
		bfr.AddStrA7("').converts.get('");
		bfr.Add(vnt);
		bfr.AddStrA7("').add_bulk(");
		bfr.AddByteNl().AddStrA7("<:['").AddByteNl();
	}
	private void Write_itm(BryWtr bfr, Xol_mw_parse_itm itm) {
		bfr.Add(itm.Src());
		bfr.AddBytePipe();
		bfr.Add(itm.Trg());
		bfr.AddByteNl();
	}
	private void Write_end(BryWtr bfr) {
		bfr.AddStrA7("']:>").AddByteNl();
		bfr.AddStrA7(");").AddByteNl();
	}
}
class Xol_mw_parse_itm {
	public Xol_mw_parse_itm(byte[] src, byte[] trg) {this.src = src; this.trg = trg;}
	public byte[] Src() {return src;} private byte[] src;
	public byte[] Trg() {return trg;} private byte[] trg;
}
class Xol_mw_parse_fxt {
	public void Test_convert(String mw, String expd) {
		Xol_mw_parse_grp[] actl_ary = Parse(BryUtl.NewU8(mw));
		BryWtr bfr = BryWtr.New();
		actl_ary[0].Write_as_gfs(bfr);
		GfoTstr.EqLines(expd, bfr.ToStr());
	}
	public void Test_run(Io_url src_dir, Io_url trg_dir) {
		BryWtr bfr = BryWtr.New();
		Io_url[] fils = Io_mgr.Instance.QueryDir_fils(src_dir);
		int fils_len = fils.length;
		for (int i = 0; i < fils_len; i++) {
			Io_url fil = fils[i];
			byte[] src = Io_mgr.Instance.LoadFilBry(fil);
			Xol_mw_parse_grp[] itms = Parse(src);
			int itms_len = itms.length;
			String lang_name = StringUtl.Lower(StringUtl.Mid(fil.NameOnly(), 0, 2));	// ZhConversion.php -> Zh
			for (int j = 0; j < itms_len; j++) {
				Xol_mw_parse_grp itm = itms[j];
				itm.Write_as_gfs(bfr);
			}
			Io_url trg_fil = Xol_convert_regy.Bld_url(trg_dir, lang_name);
			Io_mgr.Instance.SaveFilBry(trg_fil, bfr.ToBryAndClear());
		}
	}
	public Xol_mw_parse_grp[] Parse(byte[] src) {
		List_adp list = List_adp_.New();
		Php_parser parser = new Php_parser();
		Gfo_msg_log msg_log = new Gfo_msg_log("xowa");
		Php_evaluator evaluator = new Php_evaluator(msg_log);
		parser.Parse_tkns(src, evaluator);
		Php_line[] lines = (Php_line[])evaluator.List().ToAry(Php_line.class);
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; i++) {
			Php_line_assign line = (Php_line_assign)lines[i];
			Xol_mw_parse_grp grp = Parse_grp(line);
			list.Add(grp);
		}
		return (Xol_mw_parse_grp[])list.ToAry(Xol_mw_parse_grp.class);
	}
	private List_adp tmp_itm_list = List_adp_.New();
	private Xol_mw_parse_grp Parse_grp(Php_line_assign line) {
		Xol_mw_parse_grp grp = new Xol_mw_parse_grp();
		byte[] key =  line.Key().Val_obj_bry();				// EX: "zh2Hant"
		key = BryUtl.LcaseAll(key);							// EX: "zh2hant"
		byte[][] parts = BrySplit.Split(key, AsciiByte.Num2);	// EX: "zh", "hant"
		byte[] src = parts[0];
		byte[] trg = BryUtl.Add(parts[0], new byte[] {AsciiByte.Dash}, parts[1]);
		grp.Lng_(src).Vnt_(trg);
		Parse_itms(line, grp);
		return grp;
	}
	private void Parse_itms(Php_line_assign line, Xol_mw_parse_grp grp) {
		Php_itm_ary ary = (Php_itm_ary)line.Val();
		tmp_itm_list.Clear();
		int subs_len = ary.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			Xol_mw_parse_itm itm = new Xol_mw_parse_itm(kv.Key().Val_obj_bry(), kv.Val().Val_obj_bry());
			tmp_itm_list.Add(itm);
		}
		grp.Itms_((Xol_mw_parse_itm[])tmp_itm_list.ToAry(Xol_mw_parse_itm.class));
	}
}
