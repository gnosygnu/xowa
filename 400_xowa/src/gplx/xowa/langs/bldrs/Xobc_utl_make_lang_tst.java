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
package gplx.xowa.langs.bldrs;
import gplx.libs.files.Io_mgr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
public class Xobc_utl_make_lang_tst {		
	@Before public void init() {fxt.Clear();} private Xobc_utl_make_lang_fxt fxt = new Xobc_utl_make_lang_fxt();
	@Test public void Parse() {
		fxt.Parse_rows(StringUtl.ConcatLinesNl
			(	""
			,	"if|#if~#si~"
			,	""
			,	"ifeq|#ifeq~#sieq"
			,	""
			,	"expr|"
			)
			, 	fxt.row_("if", "#if", "#si")
			, 	fxt.row_("ifeq", "#ifeq", "#sieq")
			, 	fxt.row_("expr")
			);
	}
	@Test public void Trailing_colon() {
		fxt.Kwd_mgr().Parse_keep_trailing_colon(BryUtl.NewA7("fr"), BryUtl.NewU8(StringUtl.ConcatLinesNl
			(	"if|if:~si:~"
			,	"ifeq|"
			)));
		fxt.Ini_file_mw_core("fr", StringUtl.ConcatLinesNl
			(	"$magicWords = array("
			,	"  'expr' => array(0, 'expr:'),"
			,	"  'if' => array(0, 'if:', 'si:', 'if_unchanged:'),"
			,	"  'ifeq' => array(0, 'ifeq:', 'sieq:'),"
			,	");"
			)
			);
		fxt.Mgr().Bld_all();
		fxt.Tst_file_xo("fr", StringUtl.ConcatLinesNl
			(	"this"
			,	".keywords"
			,	"  .load_text("
			,	"<:['"
			,	"expr|0|expr~"
			,	"if|0|if:~si:~if_unchanged~"
			,	"ifeq|0|ifeq:~sieq:~"
			,	"']:>"
			,	").lang"
			,	";"
			));
	}
	@Test public void Prepend_hash() {
		fxt.Kwd_mgr().Parse_prepend_hash(BryUtl.NewA7("fr"), BryUtl.NewU8(StringUtl.ConcatLinesNl
			(	"if|if:~si:~"
			,	"ifeq|"
			,	"tag|tag~"
			)));
		fxt.Ini_file_mw_core("fr", StringUtl.ConcatLinesNl
			(	"$magicWords = array("
			,	"  'tag'                   => array( '0', 'etiqueta', 'ETIQUETA', 'tag' ),"
			,	"  'expr' => array(0, 'expr:'),"
			,	"  'if' => array(0, 'if:', 'si:', 'if_unchanged:'),"
			,	"  'ifeq' => array(0, 'ifeq:', 'sieq:'),"
			,	");"
			)
			);
		fxt.Mgr().Bld_all();
		fxt.Tst_file_xo("fr", StringUtl.ConcatLinesNl
			(	"this"
			,	".keywords"
			,	"  .load_text("
			,	"<:['"
			,	"tag|0|etiqueta~ETIQUETA~#tag~"
			,	"expr|0|expr~"
			,	"if|0|#if~#si~if_unchanged~"
			,	"ifeq|0|#ifeq~#sieq~"
			,	"']:>"
			,	").lang"
			,	";"
			));
	}
	@Test public void Add_words_hash() {
		fxt.Kwd_mgr().Parse_add_words(BryUtl.NewA7("fr"), BryUtl.NewU8(StringUtl.ConcatLinesNl
			(	"if|if_new:~if~"
			,	"ifeq|"
			)));
		fxt.Ini_file_mw_core("fr", StringUtl.ConcatLinesNl
			(	"$magicWords = array("
			,	"  'if' => array(0, 'if:', 'si:'),"
			,	"  'ifeq' => array(0, 'sieq:'),"
			,	");"
			)
			);
		fxt.Mgr().Bld_all();
		fxt.Tst_file_xo("fr", StringUtl.ConcatLinesNl
			(	"this"
			,	".keywords"
			,	"  .load_text("
			,	"<:['"
			,	"if|0|if~si~if_new:~"
			,	"ifeq|0|sieq~ifeq~"
			,	"']:>"
			,	").lang"
			,	";"
			));
	}
	@Test public void Manual_text() {
		fxt.Mgr().Parse_manual_text(BryUtl.NewA7("fr"), BryUtl.NewU8(StringUtl.ConcatLinesNl
			(	"app;"
			))
			, fxt.Mgr().Manual_text_end_hash());
		fxt.Ini_file_mw_core("fr", StringUtl.ConcatLinesNl
			(	"$magicWords = array("
			,	"  'if' => array(0, 'if:', 'si:'),"
			,	");"
			)
			);
		fxt.Mgr().Bld_all();
		fxt.Tst_file_xo("fr", StringUtl.ConcatLinesNl
			(	"this"
			,	".keywords"
			,	"  .load_text("
			,	"<:['"
			,	"if|0|if~si~"
			,	"']:>"
			,	").lang"
			,	";"
			,	"app;"
			));
	}
}
class Xobc_utl_make_lang_fxt {
	public Xobc_utl_make_lang Mgr() {return mgr;} private Xobc_utl_make_lang mgr;
	public Xobc_utl_make_lang_kwds Kwd_mgr() {return mgr.Kwd_mgr();}
	public Xobc_utl_make_lang_fxt Clear() {
		app = Xoa_app_fxt.Make__app__edit();
		mgr = new Xobc_utl_make_lang(app.Lang_mgr(), app.Fsys_mgr(), app.Msg_log());
		return this;
	}	private String_bldr sb = String_bldr_.new_(); private Xoae_app app;
	public Xobcl_kwd_row row_(String key, String... itms) {return new Xobcl_kwd_row(BryUtl.NewA7(key), BryUtl.Ary(itms));}
	public void Parse_rows(String raw, Xobcl_kwd_row... expd) {GfoTstr.EqLines(Xto_str(expd), Xto_str(Xobc_utl_make_lang_kwds.Parse(BryUtl.NewA7(raw))));}
	public void Ini_file_mw_core(String lang, String raw) {
		Io_url fil = app.Fsys_mgr().Cfg_lang_core_dir().OwnerDir().GenSubFil_nest("mediawiki", "core_php", "Messages" + StringUtl.UpperFirst(lang) + ".php");
		Io_mgr.Instance.SaveFilStr(fil, raw);
	}
	public void Tst_file_xo(String lang, String expd) {
		Io_url fil = Xol_lang_itm_.xo_lang_fil_(app.Fsys_mgr(), lang);
		GfoTstr.EqLines(expd, Io_mgr.Instance.LoadFilStr(fil));
	}
	private String Xto_str(Xobcl_kwd_row[] expd) {
		int len = expd.length;
		for (int i = 0; i < len; i++) {
			Xobcl_kwd_row row = expd[i];
			sb.Add(row.Key());
			byte[][] itms = row.Itms();
			int itms_len = itms.length;
			if (itms_len > 0) {
				sb.AddCharPipe();
				for (int j = 0; j < itms_len; j++) {
					byte[] itm = itms[j];
					sb.Add(itm).AddCharPipe();
				}
			}
			sb.AddCharNl();
		}
		return sb.ToStrAndClear();
	}
}
