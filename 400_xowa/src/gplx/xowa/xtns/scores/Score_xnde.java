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
package gplx.xowa.xtns.scores;
import gplx.core.envs.Process_adp;
import gplx.core.security.algos.Hash_algo;
import gplx.core.security.algos.Hash_algo_;
import gplx.core.security.algos.Hash_algo_utl;
import gplx.langs.html.HtmlEntityCodes;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.ByteVal;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.files.Xof_exec_tid;
import gplx.xowa.files.Xof_file_itm;
import gplx.xowa.files.Xof_lnki_page;
import gplx.xowa.files.Xof_lnki_time;
import gplx.xowa.guis.views.Xog_html_itm;
import gplx.xowa.htmls.Xoh_cmd_itm;
import gplx.xowa.htmls.Xoh_consts;
import gplx.xowa.htmls.core.htmls.Xoh_html_wtr;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_root_tkn;
import gplx.xowa.parsers.htmls.Mwh_atr_itm;
import gplx.xowa.parsers.htmls.Mwh_atr_itm_owner1;
import gplx.xowa.parsers.lnkis.Xop_lnki_type;
import gplx.xowa.parsers.logs.Xop_log_basic_wkr;
import gplx.xowa.parsers.xndes.Xop_xnde_tkn;
import gplx.xowa.xtns.Xox_mgr_base;
import gplx.xowa.xtns.Xox_xnde;
import gplx.xowa.xtns.Xox_xnde_;
public class Score_xnde implements Xox_xnde, Mwh_atr_itm_owner1, Xoh_cmd_itm {
	public boolean Lang_is_abc() {return lang_is_abc;} private boolean lang_is_abc;
	public boolean Code_is_raw() {return code_is_raw;} private boolean code_is_raw;
	public boolean Output_midi() {return output_midi;} private boolean output_midi;
	public boolean Output_ogg() {return output_ogg;} private boolean output_ogg;
	public byte[] File_midi() {return file_midi;} private byte[] file_midi;
	public byte[] File_ogg() {return file_ogg;} private byte[] file_ogg;
	public Xop_root_tkn Body() {return body;} public Score_xnde Body_(Xop_root_tkn v) {body = v; return this;} private Xop_root_tkn body;
	public Xop_xnde_tkn Xnde() {return xnde;} private Xop_xnde_tkn xnde = null;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {
		if (xatr_id_obj == null) return;
		ByteVal xatr_id = (ByteVal)xatr_id_obj;
		switch (xatr_id.Val()) {
			case Xatr_id_lang_is_abc:	lang_is_abc = BryLni.Eq(BryUtl.UcaseAll(xatr.Val_as_bry()), Lang_abc); break;
			case Xatr_id_code_is_raw:	code_is_raw = xatr.Val_as_bool_by_int(); break;
			case Xatr_id_output_midi:	output_midi = xatr.Val_as_bool_by_int(); break;
			case Xatr_id_output_ogg:	output_ogg  = xatr.Val_as_bool_by_int(); break;
			case Xatr_id_file_midi:		file_midi = xatr.Val_as_bry(); break;
			case Xatr_id_file_ogg:		file_ogg = xatr.Val_as_bry(); break;
			default:					throw ErrUtl.NewUnhandled(xatr_id.Val());
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Xox_xnde_.Xatr__set(wiki, this, xatrs_hash, src, xnde);
		this.xnde = xnde;
		code = BryLni.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		code = BryUtlByWtr.Replace(code, gplx.xowa.bldrs.xmls.Xob_xml_parser_.Bry_tab_ent, gplx.xowa.bldrs.xmls.Xob_xml_parser_.Bry_tab);
		boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Page(), Xop_log_basic_wkr.Tid_score, src, xnde);
	}	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
	private byte[] code;
	public String Hcmd_id() {return hcmd_id;} private String hcmd_id;
	private void Html_write_code_as_pre(BryWtr bfr, Xoae_app app) {
		bfr.Add(Xoh_consts.Pre_bgn_overflow);
		Xox_mgr_base.Xtn_write_escape(app, bfr, code);
		bfr.Add(Xoh_consts.Pre_end);
	}
	private static final Hash_algo sha1_hash = Hash_algo_.New__sha1();
	public void Xtn_write(BryWtr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		Xowe_wiki wiki = ctx.Wiki(); Xoae_page page = ctx.Page();
		Score_xtn_mgr score_xtn = (Score_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Score_xtn_mgr.XTN_KEY);
		if (!score_xtn.Enabled()) {Html_write_code_as_pre(bfr, app); return;}
		Process_adp ly_process = app.Prog_mgr().App_lilypond();
		if (ly_process.Exe_exists() == BoolUtl.NullByte && ly_process.Exe_url() != null) {	// TEST: ly_process.Exe_url() is null
			boolean exists = Io_mgr.Instance.ExistsFil(ly_process.Exe_url());
			ly_process.Exe_exists_(exists ? BoolUtl.YByte : BoolUtl.NByte);
		}
		if (ly_process.Exe_exists() == BoolUtl.NByte) {Html_write_code_as_pre(bfr, app); return;}
		BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetB128();
		tmp_bfr.Add(code).AddBytePipe().AddIntBool(lang_is_abc).AddBytePipe().AddIntBool(code_is_raw);
		sha1 = Hash_algo_utl.Calc_hash_as_bry(sha1_hash, tmp_bfr.ToBryAndRls()); // NOTE: MW transforms to base32; for now, keep sha1 as raw
		sha1_prefix = StringUtl.NewA7(sha1, 0, 8);
		output_dir = app.Fsys_mgr().File_dir().GenSubDir_nest(wiki.Domain_str(), "lilypond", CharUtl.ToStr(sha1[0]), CharUtl.ToStr(sha1[1]), StringUtl.NewA7(sha1));	// NOTE: MW also adds an extra level for 8-len; EX: /.../sha1_32_len/sha1_8_len/
		png_file = output_dir.GenSubFil(sha1_prefix + ".png");
		aud_file = output_dir.GenSubFil(sha1_prefix + ".midi");
		hcmd_id = "xowa_score_" + IntUtl.ToStr(page.Html_cmd_mgr().Count());

		html_id_pre = hcmd_id + "_pre";
		html_id_img = hcmd_id + "_img";
		html_id_a	= hcmd_id + "_a";
		html_a_href = ""; html_img_src = "";
		html_img_alt = StringUtl.NewU8(BryUtlByWtr.Replace(code, AsciiByte.NlBry, HtmlEntityCodes.NlBry));
		String html_img_alt_tmp = "", html_img_src_tmp = "", html_a_href_tmp = "";
		html_img_src = png_file.To_http_file_str();			
		html_a_href = aud_file.To_http_file_str();
		String html_a_xowa_ttl = "";
		if		(file_midi != null) {
			html_a_href = Fill_xfer(wiki, ctx, page, file_midi);
			html_a_xowa_ttl = StringUtl.NewU8(file_midi);
		}
		else if (file_ogg != null) {
			html_a_href = Fill_xfer(wiki, ctx, page, file_ogg);
		}
		if (Io_mgr.Instance.Exists(png_file)) { // file exists; add html;
			html_img_alt_tmp = html_img_alt;
			html_img_src_tmp = html_img_src;
			html_a_href_tmp = html_a_href;
		}
		else {
			html_img_alt_tmp = html_img_src_tmp = html_a_href_tmp = "";
			score_xtn.Html_txt().BldToBfrMany(bfr, html_id_pre, lang_is_abc ? "xowa-score-abc" : "xowa-score-lilypond", gplx.langs.htmls.Gfh_utl.Escape_html_as_bry(code));
			page.Html_cmd_mgr().Add(this);
		}
		score_xtn.Html_img().BldToBfrMany(bfr, html_id_a, html_a_href_tmp, html_a_xowa_ttl, html_id_img, html_img_src_tmp, html_img_alt_tmp);
	}	private byte[] sha1; private String sha1_prefix; private Io_url output_dir, png_file, aud_file; private String html_id_pre, html_id_img, html_id_a, html_a_href, html_img_src, html_img_alt;
	private String Fill_xfer(Xowe_wiki wiki, Xop_ctx ctx, Xoae_page page, byte[] ttl) {
		Xof_file_itm xfer_itm = wiki.Html_mgr().Html_wtr().Lnki_wtr().File_wtr().Lnki_eval(Xof_exec_tid.Tid_wiki_page, ctx, page, page.File_queue(), ttl, Xop_lnki_type.Id_none, -1, -1, -1, Xof_lnki_time.Null, Xof_lnki_page.Null, false);
		return xfer_itm.Html_orig_url().To_http_file_str();
	}
	public void Hcmd_exec(Xoae_app app, Gfo_usr_dlg usr_dlg, Xoae_page page) {
		fail_msg = "unknown failure";
		usr_dlg.Prog_many(GRP_KEY, "exec.msg", "generating lilypond: ~{0}", StringUtl.NewU8(code));
		usr_dlg.Prog_many(GRP_KEY, "exec.msg", "generating lilypond: ~{0}", StringUtl.NewU8(code));
		Xowe_wiki wiki = page.Wikie();
		Score_xtn_mgr score_xtn = (Score_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Score_xtn_mgr.XTN_KEY);
		Io_url ly_file = output_dir.GenSubFil(sha1_prefix + ".ly");
		byte[] ly_text = null;
		Process_adp ly_process = app.Prog_mgr().App_lilypond();
		if (Score_xtn_mgr.Lilypond_version == null) Score_xtn_mgr.Lilypond_version = Get_lilypond_version(ly_process);
		if	(lang_is_abc) {
			Io_url abc_file = output_dir.GenSubFil(sha1_prefix + ".abc");
			Io_mgr.Instance.SaveFilBry(abc_file, code);
			Process_adp abc2ly_process = app.Prog_mgr().App_abc2ly();
			if (!abc2ly_process.Run(abc_file, ly_file).Exit_code_pass()) {
				fail_msg = abc2ly_process.Rslt_out();
				app.Usr_dlg().Warn_many("", "", "abc2ly failed: ~{0}", fail_msg);
				return;
			}
			ly_text = Io_mgr.Instance.LoadFilBry(ly_file);
			ly_text = BryUtlByWtr.ReplaceBetween(ly_text, Abc_tagline_bgn, Abc_tagline_end, Abc_tagline_repl);	// remove "tagline = Generated By AbcToLy" at bottom of image
			Io_mgr.Instance.SaveFilBry(ly_file, ly_text);
		}	
		else {
			BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetM001();
			try {
				ly_text = code_is_raw ? code : score_xtn.Lilypond_fmtr().BldToBryMany(tmp_bfr, Score_xtn_mgr.Lilypond_version, code);
			} finally {
				tmp_bfr.MkrRls();
			}
			Io_mgr.Instance.SaveFilBry(ly_file, ly_text);
		}
		ly_process.Working_dir_(ly_file.OwnerDir());	// NOTE: must change working_dir, else file will be dumped into same dir as lilypond.exe
		if (!ly_process.Run(ly_file).Exit_code_pass()) {
			fail_msg = ly_process.Rslt_out();
			app.Usr_dlg().Warn_many("", "", "lilypond failed: ~{0}", fail_msg);
			return;
		}
		if (output_ogg) {
			Process_adp timidity_process = app.Prog_mgr().App_convert_midi_to_ogg();
			Io_url ogg_file = ly_file.GenNewExt(".ogg");
			if (!timidity_process.Run(ly_file.GenNewExt(".midi"), ogg_file).Exit_code_pass()) {	// NOTE: do not exit; timidity currently not working for windows
				fail_msg = timidity_process.Rslt_out();
			}
			else
				html_a_href = ogg_file.To_http_file_str();
		}
		Io_mgr.Instance.DeleteFil(ly_file);
		Io_url png_file_untrimmed = png_file.GenNewNameOnly("untrimmed");
		Io_mgr.Instance.MoveFil(png_file, png_file_untrimmed);
		app.Prog_mgr().App_trim_img().Run(png_file_untrimmed, png_file);
		Io_mgr.Instance.DeleteFil(png_file_untrimmed);
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
	byte[] Get_lilypond_version(Process_adp lilypond_process) {
		try {
			Process_adp lilypond_version_proc = new Process_adp().Exe_url_(lilypond_process.Exe_url()).Args_str_("--version").Prog_dlg_(lilypond_process.Prog_dlg()).Run_mode_(Process_adp.Run_mode_sync_block);
			lilypond_version_proc.Run();
			return Get_lilypond_version(lilypond_version_proc.Rslt_out());
		}
		catch (Exception e) {return Version_unknown;}
	}
	public static byte[] Get_lilypond_version(String rslt_str) {
		byte[] rslt = BryUtl.NewU8(rslt_str);	// expect 1st line to be of form "GNU LilyPond 2.16.2"
		int bgn_pos	= BryFind.FindFwd(rslt, Version_find_bgn); if (bgn_pos == BryFind.NotFound) return Version_unknown;
		bgn_pos += Version_find_bgn.length + 1;	// +1 for trailing space
		int end_pos = BryFind.FindFwd(rslt, AsciiByte.Nl, bgn_pos); if (bgn_pos == BryFind.NotFound) return Version_unknown;
		if (rslt[end_pos - 1] == AsciiByte.Cr) end_pos = end_pos - 1;
		return BryLni.Mid(rslt, bgn_pos, end_pos);
	}
	public static final byte Xatr_id_lang_is_abc = 0, Xatr_id_code_is_raw = 1, Xatr_id_output_midi = 2, Xatr_id_output_ogg = 3, Xatr_id_file_midi = 4, Xatr_id_file_ogg = 5;
	private static final Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("lang", Xatr_id_lang_is_abc)
	.Add_str_byte("raw", Xatr_id_code_is_raw)
	.Add_str_byte("midi", Xatr_id_output_midi)
	.Add_str_byte("vorbis", Xatr_id_output_ogg)
	.Add_str_byte("over"+"ride_midi", Xatr_id_file_midi)
	.Add_str_byte("over"+"ride_ogg", Xatr_id_file_ogg)
	;
	private static final byte[]
	  Lang_abc = BryUtl.NewA7("ABC")
	, Abc_tagline_bgn = BryUtl.NewA7("tagline ="), Abc_tagline_end = new byte[] {AsciiByte.Nl}, Abc_tagline_repl = BryUtl.NewA7("tagline = \"\"\n")
	, Version_unknown = BryUtl.NewA7("unknown"), Version_find_bgn = BryUtl.NewA7("GNU LilyPond")
	;
	static final String GRP_KEY = "xowa.xtns.scores.itm";
}
