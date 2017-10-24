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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.lnkis.files.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.xtns.imaps.itms.*;
public class Imap_parser {
	private Imap_xtn_mgr xtn_mgr; private Xoa_url page_url; private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Instance;
	private byte[] imap_img_src;
	private Imap_part_img imap_img;
	private Imap_part_dflt imap_dflt;
	private Imap_part_desc imap_desc;
	private List_adp shapes = List_adp_.New(), pts = List_adp_.New(), errs = List_adp_.New();
	private byte[] src;
	private int itm_idx; private int itm_bgn, itm_end;
	private Xoae_app app; private Xowe_wiki wiki; private Xop_ctx wiki_ctx, imap_ctx; private Xop_root_tkn imap_root;
	private final    Btrie_rv trv = new Btrie_rv();
	public Imap_parser(Imap_xtn_mgr xtn_mgr) {this.xtn_mgr = xtn_mgr;}
	public void Init(Xowe_wiki wiki, Xoae_page page, Gfo_usr_dlg usr_dlg) {// SCOPE.PAGE
		this.app = wiki.Appe(); this.wiki = wiki; this.page_url = page.Url(); this.usr_dlg = usr_dlg;
		this.wiki_ctx = wiki.Parser_mgr().Ctx();
		imap_ctx = Xop_ctx.New__top(wiki, page.Ttl().Raw());	// NOTE: must update page ttl for Modules; PAGE:it.s:Patria_Esercito_Re/Indice_generale; DATE:2015-12-02
		imap_root = app.Parser_mgr().Tkn_mkr().Root(Bry_.Empty);
	}
	public void Clear() {
		this.itm_idx = 0;
		imap_img = null; imap_img_src = null; imap_desc = null; imap_dflt = null;
		shapes.Clear(); pts.Clear(); errs.Clear();
	}
	public Imap_map Parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Imap_map rv = new Imap_map(ctx.Page().Html_data().Xtn_imap_next_id());
		Init(wiki, ctx.Page(), wiki.Appe().Usr_dlg());
		this.Parse(rv, src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		return rv;
	}
	public void Parse(Imap_map rv, byte[] src, int src_bgn, int src_end) {
		this.Clear();
		this.src = src;
		itm_bgn = src_bgn; itm_end = src_bgn - 1;
		while (true) {
			if (itm_end == src_end) break;
			itm_bgn = Bry_find_.Trim_fwd_space_tab(src, itm_end + 1, src_end);					// trim ws at start, and look for first char
			if (itm_bgn == src_end) break;														// line is entirely ws and terminated by eos; EX: "\n  EOS"
			itm_end = Bry_find_.Find_fwd_until(src, itm_bgn, src_end, Byte_ascii.Nl);			// look for \n
			if (itm_end == Bry_find_.Not_found) itm_end = src_end;								// no \n; make EOS = \n
			itm_end = Bry_find_.Trim_bwd_space_tab(src, itm_end, itm_bgn);						// trim any ws at end
			if (itm_end - itm_bgn == 0) continue;												// line is entirely ws; continue;
			byte b = src[itm_bgn];
			if (b == Byte_ascii.Hash) {
				Parse_comment(itm_bgn, itm_end);
				continue;
			}
			try {
				if (itm_idx == 0)
					itm_end = Parse_img(rv, itm_bgn, itm_end, src_end);
				else {
					Object tid_obj = tid_trie.Match_at_w_b0(trv, b, src, itm_bgn, itm_end);
					byte tid_val = tid_obj == null ? Imap_part_.Tid_invalid : ((Byte_obj_val)tid_obj).Val();
					int tid_end_pos = trv.Pos();
					switch (tid_val) {
						case Imap_part_.Tid_desc:			Parse_desc(tid_end_pos, itm_end); break;
						case Imap_part_.Tid_dflt:			Parse_dflt(tid_end_pos, itm_end); break;
						case Imap_part_.Tid_shape_rect:		Parse_shape(tid_val, tid_end_pos, itm_bgn, itm_end, 4); break;
						case Imap_part_.Tid_shape_poly:		Parse_shape(tid_val, tid_end_pos, itm_bgn, itm_end, Reqd_poly); break;
						case Imap_part_.Tid_shape_circle:	Parse_shape(tid_val, tid_end_pos, itm_bgn, itm_end, 3); break;
						default:
						case Imap_part_.Tid_invalid:			Parse_invalid(itm_bgn, itm_end); break;
					}
				}
			} catch (Exception e) {usr_dlg.Warn_many("", "", "imap.parse:skipping line; page=~{0} line=~{1} err=~{2}", page_url.To_str(), Bry_.Mid_safe(src, itm_bgn, itm_end), Err_.Message_gplx_log(e));}
			++itm_idx;
		}
		rv.Init(xtn_mgr, imap_img_src, imap_img, imap_dflt, imap_desc, (Imap_part_shape[])shapes.To_ary_and_clear(Imap_part_shape.class), (Imap_err[])errs.To_ary_and_clear(Imap_err.class));
	}
	private void Parse_comment(int itm_bgn, int itm_end) {}	// noop comments; EX: "# comment\n"
	private void Parse_invalid(int itm_bgn, int itm_end) {usr_dlg.Warn_many("", "", "imap has invalid line: page=~{0} line=~{1}", page_url.To_str(), String_.new_u8(src, itm_bgn, itm_end));}
	private boolean Parse_desc(int itm_bgn, int itm_end) {
		Btrie_slim_mgr trie = xtn_mgr.Desc_trie();
		byte tid_desc = Imap_desc_tid.Parse_to_tid(trie, src, Bry_find_.Trim_fwd_space_tab(src, itm_bgn, itm_end), Bry_find_.Trim_bwd_space_tab(src, itm_end, itm_bgn));
		switch (tid_desc) {
			case Imap_desc_tid.Tid_null: return Add_err(Bool_.N, itm_bgn, itm_end, "imagemap_invalid_coord");
			case Imap_desc_tid.Tid_none: return true;
		}
		if (imap_img == null || imap_img.Img_link().Lnki_type() == Xop_lnki_type.Id_thumb) return true;	// thumbs don't get desc
		imap_desc = new Imap_part_desc(tid_desc);
		return true;
	}
	private void Parse_dflt(int itm_bgn, int itm_end) {
		imap_dflt = new Imap_part_dflt();
		Init_link_owner(imap_dflt, src, itm_bgn, itm_end);
	}
	private boolean Parse_shape(byte shape_tid, int tid_end_pos, int itm_bgn, int itm_end, int reqd_pts) {
		boolean shape_is_poly = shape_tid == Imap_part_.Tid_shape_poly;
		int pos = Bry_find_.Trim_fwd_space_tab(src, tid_end_pos, itm_end);				// gobble any leading spaces
		int grp_end = Bry_find_.Find_fwd(src, Byte_ascii.Brack_bgn, pos, itm_end);		// find first "["; note that this is a lazy way of detecting start of lnki / lnke; MW has complicated regex, but hopefully this will be enough; DATE:2014-10-22
		if (grp_end == -1) {return Add_err(Bool_.Y, itm_bgn, itm_end, "imap.parse:No valid link was found");}
		int num_bgn = -1, comma_pos = -1, pts_len = 0;
		while (true) {
			boolean last = pos == grp_end;
			byte b = last ? Byte_ascii.Space : src[pos];
			switch (b) {
				case Byte_ascii.Comma:	if (comma_pos == -1) comma_pos = pos; break;
				default:				if (num_bgn == -1) num_bgn = pos; break;
				case Byte_ascii.Space: case Byte_ascii.Tab:
					if (num_bgn != -1) {
						byte[] num_bry 
							=		comma_pos == -1			// if commas exist, treat first as decimal; echo(intval(round('1,2,3,4' * 1))) -> 1; PAGE:fr.w:Gouesnou; DATE:2014-08-12
								||	comma_pos < num_bgn		// if comma is at start of number, ignore; EX: "poly ,1 2"; PAGE:en.w:Area_codes_281,_346,_713,_and_832; DATE:2015-07-31
							? Bry_.Mid(src, num_bgn, pos)
							: Bry_.Mid(src, num_bgn, comma_pos)
							;
						double num = Bry_.To_double_or(num_bry, Double_.NaN);
						if (Double_.IsNaN(num)) { 
							if (shape_is_poly)	// poly code in ImageMap_body.php accepts invalid words and converts to 0; EX:"word1"; PAGE:uk.w:Стратосфера; DATE:2014-07-26
								num = 0;
							else
								return Add_err(Bool_.Y, itm_bgn, itm_end, "imagemap_invalid_coord");								
						}
						num_bgn = -1; comma_pos = -1;
						pts.Add(Double_obj_val.new_(num));
						++pts_len;
						if (pts_len == reqd_pts) // NOTE: MW allows more points, but doesn't show them; EX: rect 1 2 3 4 5 -> rect 1 2 3 4; PAGE:en.w:Kilauea DATE:2014-07-28; EX:1 2 3 4 <!-- --> de.w:Wilhelm_Angele DATE:2014-10-30
							last = true;
					}
					break;
			}
			if (last) break;
			++pos;
		}
		if (reqd_pts == Reqd_poly) {
			if		(pts_len == 0)			return Add_err(Bool_.Y, itm_bgn, itm_end, "imagemap_missing_coord");
			else if (pts_len % 2 != 0)		return Add_err(Bool_.Y, itm_bgn, itm_end, "imagemap_poly_odd");
		}
		else {
			if		(pts_len < reqd_pts)	return Add_err(Bool_.Y, itm_bgn, itm_end, "imagemap_missing_coord");
		}
		pos = Bry_find_.Trim_fwd_space_tab(src, pos, itm_end);
		Imap_part_shape shape_itm = new Imap_part_shape(shape_tid, (Double_obj_val[])pts.To_ary_and_clear(Double_obj_val.class));
		Init_link_owner(shape_itm, src, pos, itm_end);
		shapes.Add(shape_itm);
		return true;
	}
	private boolean Add_err(boolean clear_pts, int bgn, int end, String err_key) {
		usr_dlg.Warn_many("", "", err_key + ": page=~{0} line=~{1}", page_url.To_str(), String_.new_u8(src, bgn, end));
		errs.Add(new Imap_err(itm_idx, err_key));
		if (clear_pts) pts.Clear();
		return false;
	}
	private void Init_link_owner(Imap_link_owner link_owner, byte[] src, int bgn, int end) {
		byte[] link_tkn_src = Bry_.Mid(src, bgn, end);
		Xop_tkn_itm link_tkn = Parse_link(link_tkn_src);
		if (link_tkn == null) {Add_err(Bool_.N, bgn, end, "imap.invalid link_owner"); return;}	// exit early if invalid; PAGE:de.u:PPA/Raster/TK25/51/18/12/20; DATE:2015-02-02
		link_tkn_src = imap_root.Data_mid();	// NOTE:must re-set link_tkn_src since link_tkn is expanded wikitext; i.e.: not "{{A}}" but "expanded"; PAGE:fr.w:Arrondissements_de_Lyon DATE:2014-08-12
		Imap_link_owner_.Init(link_owner, app, wiki, link_tkn_src, link_tkn);
	}
	private Xop_tkn_itm Parse_link(byte[] raw) {
		imap_root.Clear();
		imap_ctx.Clear(false);	// NOTE: imap should not reset scrib; PAGE:it.s:La_guerra_del_vespro_siciliano/Indice DATE:2015-12-02
		wiki.Parser_mgr().Main().Parse_text_to_wdom(imap_root, imap_ctx, wiki.Appe().Parser_mgr().Tkn_mkr(), raw, 0);
		int subs_len = imap_root.Subs_len();
		for (int i = 0; i < subs_len; ++i) {
			Xop_tkn_itm sub = imap_root.Subs_get(i);
			switch (sub.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_lnki:
				case Xop_tkn_itm_.Tid_lnke:
					return sub;
			}
		}
		return null;
	}
	private int Parse_img(Imap_map imap, int itm_bgn, int itm_end, int src_end) {
		int img_bgn = Bry_find_.Trim_fwd_space_tab(src, itm_bgn, itm_end);	// trim ws
		int img_end = Parse_img__get_img_end(itm_end, src_end);
		imap_img_src = Bry_.Add(Xop_tkn_.Lnki_bgn, Bry_.Mid(src, img_bgn, img_end), Xop_tkn_.Lnki_end);
		Xop_tkn_itm tkn_itm = Parse_link(imap_img_src);			// NOTE: need to parse before imap_root.Data_mid() below
		imap_img_src = imap_root.Data_mid();					// need to re-set src to pick up templates; EX: <imagemap>File:A.png|thumb|{{Test_template}}\n</imagemap>; PAGE:en.w:Kilauea; DATE:2014-07-27
		if (	tkn_itm == null									// no lnki or lnke
			||	tkn_itm.Tkn_tid() != Xop_tkn_itm_.Tid_lnki		// no lnki; occurs with badly constructed maps; PAGE:en.w:Demography_of_the_United_Kingdom DATE:2015-01-22
			)
			Xoa_app_.Usr_dlg().Note_many("", "", "image_map failed to find lnki; page=~{0} imageMap=~{1}", page_url.To_str(), imap_img_src);
		else {
			Xop_lnki_tkn lnki_tkn = (Xop_lnki_tkn)tkn_itm;
			imap_img = new Imap_part_img(lnki_tkn);
			lnki_tkn.Lnki_file_wkr_(imap);
			wiki_ctx.Page().Lnki_list().Add(lnki_tkn);
			wiki_ctx.Lnki().File_logger().Log_file(wiki_ctx, lnki_tkn, Xop_file_logger_.Tid__imap);	// NOTE: do not do imap_ctx.Lnki(); imap_ctx is brand new
		}
		return img_end;
	}
	private int Parse_img__get_img_end(int line_end, int src_end) {	// heuristic to handle images that span more than one line via ref; en.w:Archaea DATE:2014-08-22
		int rv = line_end;
		int pos = line_end + 1;
		while (pos < src_end) {
			pos = Bry_find_.Trim_fwd_space_tab(src, pos, src_end);	// trim ws
			if (pos == src_end) break;
			byte b = src[pos];
			if (b == Byte_ascii.Nl)	// new-line; end
				break;
			else {
				Object tid_obj = tid_trie.Match_bgn_w_byte(b, src, pos, src_end);
				if (tid_obj == null) {		// not a known imap line; assume continuation of img line and skip to next line
					Xoa_app_.Usr_dlg().Log_many("", "", "image_map extending image over multiple lines; page=~{0} imageMap=~{1}", page_url.To_str(), imap_img_src);
					int next_line = Bry_find_.Find_fwd(src, Byte_ascii.Nl, pos);
					if (next_line == Bry_find_.Not_found) next_line = src_end;
					rv = next_line;
					pos = rv + 1;
				}
				else						// known imap line; stop
					break;
			}
		}
		return rv;
	}
	private static Btrie_slim_mgr tid_trie = Btrie_slim_mgr.ci_a7()	// names are not i18n'd; // NOTE:ci.ascii:MW_const.en
	.Add_str_byte("desc"						, Imap_part_.Tid_desc)
	.Add_str_byte("#"							, Imap_part_.Tid_comment)
	.Add_bry_byte(Imap_part_.Key_dflt			, Imap_part_.Tid_dflt)
	.Add_bry_byte(Imap_part_.Key_shape_rect		, Imap_part_.Tid_shape_rect)
	.Add_bry_byte(Imap_part_.Key_shape_circle	, Imap_part_.Tid_shape_circle)
	.Add_bry_byte(Imap_part_.Key_shape_poly		, Imap_part_.Tid_shape_poly)
	; 
	private static final int Reqd_poly = -1;
}
