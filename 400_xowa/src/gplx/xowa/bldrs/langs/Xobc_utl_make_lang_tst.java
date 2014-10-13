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
package gplx.xowa.bldrs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
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
		fxt.Kwd_mgr().Parse_keep_trailing_colon(Bry_.new_utf8_("fr"), Bry_.new_utf8_(String_.Concat_lines_nl
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
		fxt.Kwd_mgr().Parse_prepend_hash(Bry_.new_utf8_("fr"), Bry_.new_utf8_(String_.Concat_lines_nl
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
		fxt.Kwd_mgr().Parse_add_words(Bry_.new_utf8_("fr"), Bry_.new_utf8_(String_.Concat_lines_nl
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
		fxt.Mgr().Parse_manual_text(Bry_.new_utf8_("fr"), Bry_.new_utf8_(String_.Concat_lines_nl
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
		app = Xoa_app_fxt.app_();
		mgr = new Xobc_utl_make_lang(app);
		return this;
	}	private String_bldr sb = String_bldr_.new_(); private Xoa_app app;
	public Xobcl_kwd_row row_(String key, String... itms) {return new Xobcl_kwd_row(Bry_.new_ascii_(key), Bry_.Ary(itms));} 
	public void Parse_rows(String raw, Xobcl_kwd_row... expd) {Tfds.Eq_str_lines(Xto_str(expd), Xto_str(Xobc_utl_make_lang_kwds.Parse(Bry_.new_ascii_(raw))));}
	public void Ini_file_mw_core(String lang, String raw) {
		Io_url fil = app.Fsys_mgr().Cfg_lang_core_dir().OwnerDir().GenSubFil_nest("mediawiki", "core_php", "Messages" + String_.UpperFirst(lang) + ".php");
		Io_mgr._.SaveFilStr(fil, raw);
	}
	public void Tst_file_xo(String lang, String expd) {
		Io_url fil = Xol_lang_.xo_lang_fil_(app, lang);
		Tfds.Eq_str_lines(expd, Io_mgr._.LoadFilStr(fil));
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
		return sb.Xto_str_and_clear();
	}
}
