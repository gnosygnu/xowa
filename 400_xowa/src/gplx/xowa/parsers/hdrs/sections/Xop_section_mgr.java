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
package gplx.xowa.parsers.hdrs.sections; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*;
import gplx.langs.htmls.*;
import gplx.xowa.parsers.mws.*; import gplx.xowa.parsers.mws.wkrs.*; import gplx.xowa.parsers.hdrs.*;
public class Xop_section_mgr implements Gfo_invk {
	public boolean Enabled() {return enabled;} private boolean enabled;
	private final    Bry_fmt section_editable_fmt = Bry_fmt.Auto_nl_apos
	( "<span class='mw-editsection'><span class='mw-editsection-bracket'>[</span><a href='/wiki/~{page_ttl}?action=edit&section_key=~{section_key}' title='Edit section: ~{section_name}' class='xowa-hover-off'>edit</a><span class='mw-editsection-bracket'>]</span></span>"
	);
	private static final    byte[] Qarg__section_key = Bry_.new_u8("section_key");

	public void Init_by_wiki(Xowe_wiki wiki) {
		enabled = wiki.App().Cfg().Bind_bool(wiki, gplx.xowa.htmls.core.wkrs.hdrs.Xoh_section_editable_.Cfg__section_editing__enabled, this);	// SECTION_EDIT
	}
	public byte[] Extract_section(Xoa_url url, Xoa_ttl ttl, byte[] src) {
		// return orig if section_editing not enabled
		if (!enabled) return src;

		// return orig if section_key not in qargs
		byte[] section_key = url.Qargs_mgr().Get_val_bry_or(Qarg__section_key, null);
		if (section_key == null) return src;

		// parse wikitext into list of headers
		Xop_section_list section_list = new Xop_section_list().Parse(src);
		byte[] rv = section_list.Extract_bry_or_null(section_key);
		if (rv == null)
			throw Err_.new_wo_type("section_key not found", "page", ttl.Full_db(), "section_key", section_key);
		return rv;
	}
	public byte[] Merge_section(Xoa_url url, byte[] edit, byte[] orig) {
		// return edit if not enabled
		if (!enabled) return edit;

		// return edit if section_key not in qargs
		byte[] section_key = url.Qargs_mgr().Get_val_bry_or(Qarg__section_key, null);
		if (section_key == null) return edit;

		// parse orig
		Xop_section_list section_list = new Xop_section_list().Parse(orig);
		byte[] rv = section_list.Merge_bry_or_null(section_key, edit);
		if (rv == null)
			throw Err_.new_wo_type("could not merge section_key", "page", url.To_str(), "section_key", section_key);
		return rv;
	}
	public void Write_html(Bry_bfr bfr, byte[] src, byte[] page_ttl, Xop_hdr_tkn hdr, byte[] name) {
		// make key by (a) taking 1st and nth sub; (b) skipping ws at both ends
		Xop_tkn_itm[] subs = hdr.Subs();
		if (subs.length == 0) return;	// GUARD:should not happen, but avoid array-index error
		int key_bgn = subs[0].Src_bgn();
		int key_end = subs[hdr.Subs_len() - 1].Src_end();
		key_bgn = Bry_find_.Find_fwd_while_ws(src, key_bgn, key_end);
		key_end = Bry_find_.Find_bwd__skip_ws(src, key_end, key_bgn);
		byte[] key = Bry_.Mid(src, key_bgn, key_end);

		section_editable_fmt.Bld_many(bfr, page_ttl, key, name);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, gplx.xowa.htmls.core.wkrs.hdrs.Xoh_section_editable_.Cfg__section_editing__enabled)) enabled = m.ReadBool("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}

	public static final    byte[] Bry__meta = Bry_.new_a7("<!--xo_meta|section_edit|");
	public static final    int Len__meta = Bry__meta.length;
}
