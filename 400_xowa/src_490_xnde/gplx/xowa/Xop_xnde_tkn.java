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
import gplx.xowa.xtns.*; import gplx.xowa.parsers.tblws.*;
public class Xop_xnde_tkn extends Xop_tkn_itm_base implements Xop_tblw_tkn {
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_xnde;}
	public int Tblw_tid() {return tag.Id();}	// NOTE: tblw tkns actually return xnde as Tblw_tid
	public boolean Tblw_xml() {return true;}
	public int Tblw_subs_len() {return tblw_subs_len;} public void Tblw_subs_len_add_() {++tblw_subs_len;} private int tblw_subs_len;
	public byte CloseMode() {return closeMode;} public Xop_xnde_tkn CloseMode_(byte v) {closeMode = v; return this;} private byte closeMode = Xop_xnde_tkn.CloseMode_null;
	public boolean Tag_visible() {return tag_visible;} public Xop_xnde_tkn Tag_visible_(boolean v) {tag_visible = v; return this;} private boolean tag_visible = true;
	public int Name_bgn() {return name_bgn;} public Xop_xnde_tkn Name_bgn_(int v) {name_bgn = v; return this;} private int name_bgn = -1;
	public int Name_end() {return name_end;} public Xop_xnde_tkn Name_end_(int v) {name_end = v; return this;} private int name_end = -1;
	public Xop_xnde_tkn Name_rng_(int bgn, int end) {name_bgn = bgn; name_end = end; return this;}
	public int Atrs_bgn() {return atrs_bgn;} public Xop_xnde_tkn Atrs_bgn_(int v) {atrs_bgn = v; return this;} private int atrs_bgn = Xop_tblw_wkr.Atrs_null;
	public int Atrs_end() {return atrs_end;} public Xop_xnde_tkn Atrs_end_(int v) {atrs_end = v; return this;} private int atrs_end = Xop_tblw_wkr.Atrs_null;
	public Xop_xnde_tkn Atrs_rng_(int bgn, int end) {atrs_bgn = bgn; atrs_end = end; return this;}
	public void Atrs_rng_set(int bgn, int end) {Atrs_rng_(bgn, end);}
	public Xop_xatr_itm[] Atrs_ary() {return atrs_ary;}
	public Xop_xnde_tkn Atrs_ary_(Xop_xatr_itm[] v) {atrs_ary = v; return this;} private Xop_xatr_itm[] atrs_ary;
	public Xop_tblw_tkn Atrs_ary_as_tblw_(Xop_xatr_itm[] v) {atrs_ary = v; return this;}
	public Xop_xnde_tag Tag() {return tag;} public Xop_xnde_tkn Tag_(Xop_xnde_tag v) {tag = v; return this;} private Xop_xnde_tag tag;
	public int Tag_open_bgn() {return tag_open_bgn;} private int tag_open_bgn = Int_.Null;
	public int Tag_open_end() {return tag_open_end;} private int tag_open_end = Int_.Null;
	public Xop_xnde_tkn Tag_open_rng_(int bgn, int end) {this.tag_open_bgn = bgn; this.tag_open_end = end; return this;}
	public int Tag_close_bgn() {return tag_close_bgn;} private int tag_close_bgn = Int_.Null;
	public int Tag_close_end() {return tag_close_end;} private int tag_close_end = Int_.Null;
	public Xop_xnde_tkn Tag_close_rng_(int bgn, int end) {this.tag_close_bgn = bgn; this.tag_close_end = end; return this;}
	public Xop_xnde_tkn Subs_add_ary(Xop_tkn_itm... ary) {for (Xop_tkn_itm itm : ary) Subs_add(itm); return this;}
	public Xox_xnde Xnde_xtn() {return xnde_xtn;} public Xop_xnde_tkn Xnde_xtn_(Xox_xnde v) {xnde_xtn = v; return this;} private Xox_xnde xnde_xtn;
	@Override public void Tmpl_compile(Xop_ctx ctx, byte[] src, Xot_compile_data prep_data) {
		switch (tag.Id()) {
			case Xop_xnde_tag_.Tid_noinclude:	// NOTE: prep_mode is false to force recompile; see Ex_Tmpl_noinclude and {{{1<ni>|a</ni>}}}
			case Xop_xnde_tag_.Tid_includeonly:	// NOTE: changed to always ignore <includeonly>; DATE:2014-05-10
				break;	
			case Xop_xnde_tag_.Tid_nowiki: {
				int subs_len = this.Subs_len();
				for (int i = 0; i < subs_len; i++) {
					Xop_tkn_itm sub = this.Subs_get(i);
					sub.Tmpl_compile(ctx, src, prep_data);
				}
				break;
			}
			case Xop_xnde_tag_.Tid_onlyinclude: {
				int subs_len = this.Subs_len();
				for (int i = 0; i < subs_len; i++) {
					Xop_tkn_itm sub = this.Subs_get(i);
					sub.Tmpl_compile(ctx, src, prep_data);
				}
				prep_data.OnlyInclude_exists = true;
				break;
			}
			default: {
				int subs_len = this.Subs_len();
				for (int i = 0; i < subs_len; i++) {
					Xop_tkn_itm sub = this.Subs_get(i);
					sub.Tmpl_compile(ctx, src, prep_data);
				}
				break;	// can happen in compile b/c invks are now being compiled
			}
		}
	}
	@Override public boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {
		int subs_len = this.Subs_len();
		switch (tag.Id()) {
			case Xop_xnde_tag_.Tid_noinclude:		// do not evaluate subs
				break;
			case Xop_xnde_tag_.Tid_includeonly:		// evaluate subs
				if (!ctx.Only_include_evaluate()) {
					for (int i = 0; i < subs_len; i++)
						this.Subs_get(i).Tmpl_evaluate(ctx, src, caller, bfr);
				}
				break;
			case Xop_xnde_tag_.Tid_nowiki:			// evaluate subs; add tags
				bfr.Add_byte(Byte_ascii.Lt).Add(Xop_xnde_tag_.Tag_nowiki.Name_bry()).Add_byte(Byte_ascii.Gt);
				for (int i = 0; i < subs_len; i++)
					this.Subs_get(i).Tmpl_evaluate(ctx, src, caller, bfr);
				bfr.Add_byte(Byte_ascii.Lt).Add_byte(Byte_ascii.Slash).Add(Xop_xnde_tag_.Tag_nowiki.Name_bry()).Add_byte(Byte_ascii.Gt);
				break;
			case Xop_xnde_tag_.Tid_onlyinclude:		// evaluate subs but toggle onlyinclude flag on/off
//					boolean prv_val = ctx.Onlyinclude_enabled;
//					ctx.Onlyinclude_enabled = false;
				for (int i = 0; i < subs_len; i++)
					this.Subs_get(i).Tmpl_evaluate(ctx, src, caller, bfr);
//					ctx.Onlyinclude_enabled = prv_val;
				break;
			default:								// ignore tags except for xtn; NOTE: Xtn tags are part of tagRegy_wiki_tmpl stage
				if (tag.Xtn()) {
					bfr.Add_mid(src, tag_open_bgn, tag_open_end);	// write tag_bgn
					for (int i = 0; i < subs_len; i++)				// always evaluate subs; handle <poem>{{{1}}}</poem>; DATE:2014-03-03
						this.Subs_get(i).Tmpl_evaluate(ctx, src, caller, bfr);
					bfr.Add_mid(src, tag_close_bgn, tag_close_end);	// write tag_end
					if (tag_close_bgn == Int_.Min_value) {// xtn is unclosed; add a </xtn> else rest of page will be gobbled; PAGE:en.w:Provinces_and_territories_of_Canada DATE:2014-11-13
						bfr.Add(tag.XtnEndTag());
						bfr.Add(Byte_ascii.Gt_bry);
					}
				}
				break;
		}
		return true;
	}
	public static Xop_xnde_tkn new_() {return new Xop_xnde_tkn();} private Xop_xnde_tkn() {}
	public static final byte CloseMode_null = 0, CloseMode_inline = 1, CloseMode_pair = 2, CloseMode_open = 3;
}
