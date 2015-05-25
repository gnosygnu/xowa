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
package gplx.xowa.xtns.scores; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.xowa.html.*; import gplx.xowa.files.*;
import gplx.xowa.parsers.logs.*; import gplx.xowa.gui.views.*;
public class Score_xnde implements Xox_xnde, Xop_xnde_atr_parser, Xoh_cmd_itm {
	public boolean Lang_is_abc() {return lang_is_abc;} private boolean lang_is_abc;
	public boolean Code_is_raw() {return code_is_raw;} private boolean code_is_raw;
	public boolean Output_midi() {return output_midi;} private boolean output_midi;
	public boolean Output_ogg() {return output_ogg;} private boolean output_ogg;
	public byte[] File_midi() {return file_midi;} private byte[] file_midi;
	public byte[] File_ogg() {return file_ogg;} private byte[] file_ogg;
	public Xop_root_tkn Body() {return body;} public Score_xnde Body_(Xop_root_tkn v) {body = v; return this;} private Xop_root_tkn body;
	public Xop_xnde_tkn Xnde() {return xnde;} private Xop_xnde_tkn xnde = null;
	public void Xatr_parse(Xowe_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_key_obj) {
		if (xatr_key_obj == null) return;
		Byte_obj_val xatr_key = (Byte_obj_val)xatr_key_obj;
		switch (xatr_key.Val()) {
			case Xatr_id_lang_is_abc:	lang_is_abc = Bry_.Eq(Bry_.Upper_ascii(xatr.Val_as_bry(src)), Lang_abc); break;
			case Xatr_id_code_is_raw:	code_is_raw = xatr.Val_as_bool_by_int(src); break;
			case Xatr_id_output_midi:	output_midi = xatr.Val_as_bool_by_int(src); break;
			case Xatr_id_output_ogg:	output_ogg  = xatr.Val_as_bool_by_int(src); break;
			case Xatr_id_file_midi:		file_midi = xatr.Val_as_bry(src); break;
			case Xatr_id_file_ogg:		file_ogg = xatr.Val_as_bry(src); break;
			default:					throw Err_.unhandled(xatr_key.Val());
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Xop_xatr_itm.Xatr_parse(wiki.Appe(), this, atr_hash, wiki, src, xnde);
		this.xnde = xnde;
		code = Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		code = Bry_.Replace(code, gplx.xowa.bldrs.xmls.Xob_xml_parser_.Bry_tab_ent, gplx.xowa.bldrs.xmls.Xob_xml_parser_.Bry_tab);
		boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Cur_page(), Xop_log_basic_wkr.Tid_score, src, xnde);
	}	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
	private byte[] code;
	public String Hcmd_id() {return hcmd_id;} private String hcmd_id;
	private void Html_write_code_as_pre(Bry_bfr bfr, Xoae_app app) {
		bfr.Add(Xoh_consts.Pre_bgn_overflow);
		Xox_mgr_base.Xtn_write_escape(app, bfr, code);
		bfr.Add(Xoh_consts.Pre_end);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {
		Xowe_wiki wiki = ctx.Wiki(); Xoae_page page = ctx.Cur_page();
		Score_xtn_mgr score_xtn = (Score_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Score_xtn_mgr.XTN_KEY);
		if (!score_xtn.Enabled()) {Html_write_code_as_pre(bfr, app); return;}
		ProcessAdp ly_process = app.Prog_mgr().App_lilypond();
		if (ly_process.Exe_exists() == Bool_.__byte && ly_process.Exe_url() != null) {	// TEST: ly_process.Exe_url() is null
			boolean exists = Io_mgr.I.ExistsFil(ly_process.Exe_url());
			ly_process.Exe_exists_(exists ? Bool_.Y_byte : Bool_.N_byte);
		}
		if (ly_process.Exe_exists() == Bool_.N_byte) {Html_write_code_as_pre(bfr, app); return;}
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b128();
		tmp_bfr.Add(code).Add_byte_pipe().Add_int_bool(lang_is_abc).Add_byte_pipe().Add_int_bool(code_is_raw);
		sha1 = gplx.security.HashAlgo_.Sha1.Calc_hash_bry(tmp_bfr.To_bry_and_rls()); // NOTE: MW transforms to base32; for now, keep sha1 as raw
		sha1_prefix = String_.new_a7(sha1, 0, 8);
		output_dir = app.Fsys_mgr().File_dir().GenSubDir_nest(wiki.Domain_str(), "lilypond", Char_.XtoStr(sha1[0]), Char_.XtoStr(sha1[1]), String_.new_a7(sha1));	// NOTE: MW also adds an extra level for 8-len; EX: /.../sha1_32_len/sha1_8_len/
		png_file = output_dir.GenSubFil(sha1_prefix + ".png");
		aud_file = output_dir.GenSubFil(sha1_prefix + ".midi");
		hcmd_id = "xowa_score_" + Int_.Xto_str(page.Html_cmd_mgr().Count());

		html_id_pre = hcmd_id + "_pre";
		html_id_img = hcmd_id + "_img";
		html_id_a	= hcmd_id + "_a";
		html_a_href = ""; html_img_src = "";
		html_img_alt = String_.new_u8(Bry_.Replace(code, Xoa_consts.Nl_bry, gplx.html.Html_entity_.Nl_bry));
		String html_img_alt_tmp = "", html_img_src_tmp = "", html_a_href_tmp = "";
		html_img_src = png_file.To_http_file_str();			
		html_a_href = aud_file.To_http_file_str();
		String html_a_xowa_ttl = "";
		if		(file_midi != null) {
			html_a_href = Fill_xfer(wiki, ctx, page, file_midi);
			html_a_xowa_ttl = String_.new_u8(file_midi);
		}
		else if (file_ogg != null) {
			html_a_href = Fill_xfer(wiki, ctx, page, file_ogg);
		}
		if (Io_mgr.I.Exists(png_file)) { // file exists; add html;
			html_img_alt_tmp = html_img_alt;
			html_img_src_tmp = html_img_src;
			html_a_href_tmp = html_a_href;
		}
		else {
			html_img_alt_tmp = html_img_src_tmp = html_a_href_tmp = "";
			score_xtn.Html_txt().Bld_bfr_many(bfr, html_id_pre, lang_is_abc ? "xowa-score-abc" : "xowa-score-lilypond", gplx.html.Html_utl.Escape_html_as_bry(code));
			page.Html_cmd_mgr().Add(this);
		}
		score_xtn.Html_img().Bld_bfr_many(bfr, html_id_a, html_a_href_tmp, html_a_xowa_ttl, html_id_img, html_img_src_tmp, html_img_alt_tmp);
	}	private byte[] sha1; private String sha1_prefix; private Io_url output_dir, png_file, aud_file; private String html_id_pre, html_id_img, html_id_a, html_a_href, html_img_src, html_img_alt;
	private String Fill_xfer(Xowe_wiki wiki, Xop_ctx ctx, Xoae_page page, byte[] ttl) {
		Xof_file_itm xfer_itm = wiki.Html_mgr().Html_wtr().Lnki_wtr().File_wtr().Lnki_eval(Xof_exec_tid.Tid_wiki_page, ctx, page, page.File_queue(), ttl, Xop_lnki_type.Id_none, -1, -1, -1, Xof_lnki_time.Null, Xof_lnki_page.Null, false);
		return xfer_itm.Html_orig_url().To_http_file_str();
	}
	public void Hcmd_exec(Xoae_app app, Gfo_usr_dlg usr_dlg, Xoae_page page) {
		fail_msg = "unknown failure";
		usr_dlg.Prog_many(GRP_KEY, "exec.msg", "generating lilypond: ~{0}", String_.new_u8(code));
		usr_dlg.Prog_many(GRP_KEY, "exec.msg", "generating lilypond: ~{0}", String_.new_u8(code));
		Xowe_wiki wiki = page.Wikie();
		Score_xtn_mgr score_xtn = (Score_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Score_xtn_mgr.XTN_KEY);
		Io_url ly_file = output_dir.GenSubFil(sha1_prefix + ".ly");
		byte[] ly_text = null;
		ProcessAdp ly_process = app.Prog_mgr().App_lilypond();
		if (Score_xtn_mgr.Lilypond_version == null) Score_xtn_mgr.Lilypond_version = Get_lilypond_version(ly_process);
		if	(lang_is_abc) {
			Io_url abc_file = output_dir.GenSubFil(sha1_prefix + ".abc");
			Io_mgr.I.SaveFilBry(abc_file, code);
			ProcessAdp abc2ly_process = app.Prog_mgr().App_abc2ly();
			if (!abc2ly_process.Run(abc_file, ly_file).Exit_code_pass()) {
				fail_msg = abc2ly_process.Rslt_out();
				app.Usr_dlg().Warn_many("", "", "abc2ly failed: ~{0}", fail_msg);
				return;
			}
			ly_text = Io_mgr.I.LoadFilBry(ly_file);
			ly_text = Bry_.Replace_between(ly_text, Abc_tagline_bgn, Abc_tagline_end, Abc_tagline_repl);	// remove "tagline = Generated By AbcToLy" at bottom of image
			Io_mgr.I.SaveFilBry(ly_file, ly_text);
		}	
		else {
			Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
			ly_text = code_is_raw ? code : score_xtn.Lilypond_fmtr().Bld_bry_many(tmp_bfr, Score_xtn_mgr.Lilypond_version, code);
			tmp_bfr.Mkr_rls();
			Io_mgr.I.SaveFilBry(ly_file, ly_text);
		}
		ly_process.Working_dir_(ly_file.OwnerDir());	// NOTE: must change working_dir, else file will be dumped into same dir as lilypond.exe
		if (!ly_process.Run(ly_file).Exit_code_pass()) {
			fail_msg = ly_process.Rslt_out();
			app.Usr_dlg().Warn_many("", "", "lilypond failed: ~{0}", fail_msg);
			return;
		}
		if (output_ogg) {
			ProcessAdp timidity_process = app.Prog_mgr().App_convert_midi_to_ogg();
			Io_url ogg_file = ly_file.GenNewExt(".ogg");
			if (!timidity_process.Run(ly_file.GenNewExt(".midi"), ogg_file).Exit_code_pass()) {	// NOTE: do not exit; timidity currently not working for windows
				fail_msg = timidity_process.Rslt_out();
			}
			else
				html_a_href = ogg_file.To_http_file_str();
		}
		Io_mgr.I.DeleteFil(ly_file);
		Io_url png_file_untrimmed = png_file.GenNewNameOnly("untrimmed");
		Io_mgr.I.MoveFil(png_file, png_file_untrimmed);
		app.Prog_mgr().App_trim_img().Run(png_file_untrimmed, png_file);
		Io_mgr.I.DeleteFil(png_file_untrimmed);
		fail_msg = null;		
	}	private String fail_msg = null;
	public void Hcmd_write(Xoae_app app, Gfo_usr_dlg usr_dlg, Xoae_page page) {
		Xog_html_itm html_itm = page.Tab_data().Tab().Html_itm();
		if (fail_msg == null) {	// fill in png/midi;			
			html_itm.Html_atr_set(html_id_a, "href", html_a_href);
			html_itm.Html_atr_set(html_id_img, "src", html_img_src);
			html_itm.Html_atr_set(html_id_img, "alt", html_img_alt);
			html_itm.Html_elem_delete(html_id_pre);
		}
		else {	// write failure message				
			html_itm.Html_atr_set(html_id_pre, "textContent", fail_msg);
		}
	}
	byte[] Get_lilypond_version(ProcessAdp lilypond_process) {
		try {
			ProcessAdp lilypond_version_proc = new ProcessAdp().Exe_url_(lilypond_process.Exe_url()).Args_str_("--version").Prog_dlg_(lilypond_process.Prog_dlg()).Run_mode_(ProcessAdp.Run_mode_sync_block);
			lilypond_version_proc.Run();
			return Get_lilypond_version(lilypond_version_proc.Rslt_out());
		}
		catch (Exception e) {Err_.Noop(e); return Version_unknown;}
	}
	public static byte[] Get_lilypond_version(String rslt_str) {
		byte[] rslt = Bry_.new_u8(rslt_str);	// expect 1st line to be of form "GNU LilyPond 2.16.2"
		int bgn_pos	= Bry_finder.Find_fwd(rslt, Version_find_bgn); if (bgn_pos == Bry_.NotFound) return Version_unknown;
		bgn_pos += Version_find_bgn.length + 1;	// +1 for trailing space
		int end_pos = Bry_finder.Find_fwd(rslt, Byte_ascii.NewLine, bgn_pos); if (bgn_pos == Bry_.NotFound) return Version_unknown;
		if (rslt[end_pos - 1] == Byte_ascii.CarriageReturn) end_pos = end_pos - 1;
		return Bry_.Mid(rslt, bgn_pos, end_pos);
	}
	public static final byte Xatr_id_lang_is_abc = 0, Xatr_id_code_is_raw = 1, Xatr_id_output_midi = 2, Xatr_id_output_ogg = 3, Xatr_id_file_midi = 4, Xatr_id_file_ogg = 5;
	private static final Hash_adp_bry atr_hash = Hash_adp_bry.ci_ascii_()
	.Add_str_byte("lang", Xatr_id_lang_is_abc)
	.Add_str_byte("raw", Xatr_id_code_is_raw)
	.Add_str_byte("midi", Xatr_id_output_midi)
	.Add_str_byte("vorbis", Xatr_id_output_ogg)
	.Add_str_byte("over"+"ride_midi", Xatr_id_file_midi)
	.Add_str_byte("over"+"ride_ogg", Xatr_id_file_ogg)
	;
	private static final byte[] 
	  Lang_abc = Bry_.new_a7("ABC")
	, Abc_tagline_bgn = Bry_.new_a7("tagline ="), Abc_tagline_end = new byte[] {Byte_ascii.NewLine}, Abc_tagline_repl = Bry_.new_a7("tagline = \"\"\n")
	, Version_unknown = Bry_.new_a7("unknown"), Version_find_bgn = Bry_.new_a7("GNU LilyPond")
	;
	static final String GRP_KEY = "xowa.xtns.scores.itm";
}
