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
package gplx.xowa.langs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*; import gplx.core.strings.*;
import gplx.xowa.langs.*;
public class Xobc_utl_make_lang_tst {		
	@Before public void init() {fxt.Clear();} private Xobc_utl_make_lang_fxt fxt = new Xobc_utl_make_lang_fxt();
	@Test  public void Parse() {
		fxt.Parse_rows(String_.Concat_lines_nl
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
	@Test  public void Trailing_colon() {
		fxt.Kwd_mgr().Parse_keep_trailing_colon(Bry_.new_a7("fr"), Bry_.new_u8(String_.Concat_lines_nl
			(	"if|if:~si:~"
			,	"ifeq|"
			)));
		fxt.Ini_file_mw_core("fr", String_.Concat_lines_nl
			(	"$magicWords = array("
			,	"  'expr' => array(0, 'expr:'),"
			,	"  'if' => array(0, 'if:', 'si:', 'if_unchanged:'),"
			,	"  'ifeq' => array(0, 'ifeq:', 'sieq:'),"
			,	");"
			)
			);
		fxt.Mgr().Bld_all();
		fxt.Tst_file_xo("fr", String_.Concat_lines_nl
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
	@Test  public void Prepend_hash() {
		fxt.Kwd_mgr().Parse_prepend_hash(Bry_.new_a7("fr"), Bry_.new_u8(String_.Concat_lines_nl
			(	"if|if:~si:~"
			,	"ifeq|"
			,	"tag|tag~"
			)));
		fxt.Ini_file_mw_core("fr", String_.Concat_lines_nl
			(	"$magicWords = array("
			,	"  'tag'                   => array( '0', 'etiqueta', 'ETIQUETA', 'tag' ),"
			,	"  'expr' => array(0, 'expr:'),"
			,	"  'if' => array(0, 'if:', 'si:', 'if_unchanged:'),"
			,	"  'ifeq' => array(0, 'ifeq:', 'sieq:'),"
			,	");"
			)
			);
		fxt.Mgr().Bld_all();
		fxt.Tst_file_xo("fr", String_.Concat_lines_nl
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
	@Test  public void Add_words_hash() {
		fxt.Kwd_mgr().Parse_add_words(Bry_.new_a7("fr"), Bry_.new_u8(String_.Concat_lines_nl
			(	"if|if_new:~if~"
			,	"ifeq|"
			)));
		fxt.Ini_file_mw_core("fr", String_.Concat_lines_nl
			(	"$magicWords = array("
			,	"  'if' => array(0, 'if:', 'si:'),"
			,	"  'ifeq' => array(0, 'sieq:'),"
			,	");"
			)
			);
		fxt.Mgr().Bld_all();
		fxt.Tst_file_xo("fr", String_.Concat_lines_nl
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
	@Test  public void Manual_text() {
		fxt.Mgr().Parse_manual_text(Bry_.new_a7("fr"), Bry_.new_u8(String_.Concat_lines_nl
			(	"app;"
			))
			, fxt.Mgr().Manual_text_end_hash());
		fxt.Ini_file_mw_core("fr", String_.Concat_lines_nl
			(	"$magicWords = array("
			,	"  'if' => array(0, 'if:', 'si:'),"
			,	");"
			)
			);
		fxt.Mgr().Bld_all();
		fxt.Tst_file_xo("fr", String_.Concat_lines_nl
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
	public Xobcl_kwd_row row_(String key, String... itms) {return new Xobcl_kwd_row(Bry_.new_a7(key), Bry_.Ary(itms));} 
	public void Parse_rows(String raw, Xobcl_kwd_row... expd) {Tfds.Eq_str_lines(Xto_str(expd), Xto_str(Xobc_utl_make_lang_kwds.Parse(Bry_.new_a7(raw))));}
	public void Ini_file_mw_core(String lang, String raw) {
		Io_url fil = app.Fsys_mgr().Cfg_lang_core_dir().OwnerDir().GenSubFil_nest("mediawiki", "core_php", "Messages" + String_.UpperFirst(lang) + ".php");
		Io_mgr.Instance.SaveFilStr(fil, raw);
	}
	public void Tst_file_xo(String lang, String expd) {
		Io_url fil = Xol_lang_itm_.xo_lang_fil_(app.Fsys_mgr(), lang);
		Tfds.Eq_str_lines(expd, Io_mgr.Instance.LoadFilStr(fil));
	}
	private String Xto_str(Xobcl_kwd_row[] expd) {
		int len = expd.length;
		for (int i = 0; i < len; i++) {
			Xobcl_kwd_row row = expd[i];
			sb.Add(row.Key());
			byte[][] itms = row.Itms();
			int itms_len = itms.length;
			if (itms_len > 0) {
				sb.Add_char_pipe();
				for (int j = 0; j < itms_len; j++) {
					byte[] itm = itms[j];
					sb.Add(itm).Add_char_pipe();
				}
			}
			sb.Add_char_nl();
		}
		return sb.To_str_and_clear();
	}
}
