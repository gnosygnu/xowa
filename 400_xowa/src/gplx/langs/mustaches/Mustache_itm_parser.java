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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
import gplx.core.btries.*;
class Mustache_itm_parser {
	private byte[] src; private int src_end;
	private final Mustache_tkn_def tkn_def = new Mustache_tkn_def();
	public Mustache_elem_itm Parse(byte[] src, int src_bgn, int src_end) {
		this.src = src; this.src_end = src_end;
		Mustache_elem_root root = new Mustache_elem_root();
		Parse_grp(root, src_bgn);
		return root;
	}
	private void Parse_grp(Mustache_elem_itm owner, int src_bgn) {
		List_adp subs_list = List_adp_.new_();
		int pos = src_bgn;
		boolean loop = true;
		while (loop) {
			int tkn_lhs_bgn = Bry_find_.Find_fwd(src, tkn_def.Variable_lhs, pos, src_end);	// next "{{"
			if (tkn_lhs_bgn == Bry_find_.Not_found) {	// no "{{"; EOS
				loop = false;
				tkn_lhs_bgn = src_end;
			}
			subs_list.Add(new Mustache_elem_text(src, pos, tkn_lhs_bgn));	// add everything between last "}}" and cur "{{"
			if (!loop) break;
			pos = Parse_itm(subs_list, tkn_lhs_bgn + tkn_def.Variable_lhs_len);
		}
		if (subs_list.Count() > 0)
			owner.Subs_ary_((Mustache_elem_itm[])subs_list.To_ary_and_clear(Mustache_elem_itm.class));
	}
	private int Parse_itm(List_adp subs_list, int tkn_lhs_end) {
		if (tkn_lhs_end >= src_end) throw Fail(tkn_lhs_end, "early eos");
		byte b = src[tkn_lhs_end];
		int tkn_rhs_bgn = Bry_find_.Find_fwd(src, tkn_def.Variable_rhs, tkn_lhs_end, src_end);
		if (tkn_rhs_bgn == Bry_find_.Not_found) throw Fail(tkn_lhs_end, "dangling tkn");
		byte[] tkn_val = Bry_.Mid(src, tkn_lhs_end, tkn_rhs_bgn);
		Mustache_elem_itm elem = null;
		byte rhs_chk_byte = Byte_ascii.Null;
		switch (b) {
			default:								elem = new Mustache_elem_variable(tkn_val); break;
			case Mustache_tkn_def.Comment:			elem = new Mustache_elem_comment(tkn_val); break;
			case Mustache_tkn_def.Partial:			elem = new Mustache_elem_partial(tkn_val); break;
			case Mustache_tkn_def.Delimiter_bgn:	elem = new Mustache_elem_delimiter(tkn_val);	rhs_chk_byte = Mustache_tkn_def.Delimiter_end; break;	// TODO: change tkn_def{{=<% %>=}}
			case Mustache_tkn_def.Escape_bgn:		elem = new Mustache_elem_escape(tkn_val);		rhs_chk_byte = Mustache_tkn_def.Escape_end; break;
			case Mustache_tkn_def.Section:			elem = new Mustache_elem_section(tkn_val); break;
			case Mustache_tkn_def.Inverted:			elem = new Mustache_elem_inverted(tkn_val); break;
			case Mustache_tkn_def.Grp_end:			break;
		}
		subs_list.Add(elem);
		if (rhs_chk_byte != Byte_ascii.Null) {
			if (src[tkn_rhs_bgn] != rhs_chk_byte) throw Fail(tkn_lhs_end, "invalid check byte");
			++tkn_rhs_bgn;
		}
		return tkn_rhs_bgn + tkn_def.Variable_rhs_len;
	}
	private Err Fail(int pos, String fmt, Object... args) {
		return Err_.new_("mustache", fmt, "excerpt", Bry_.Mid_by_len_safe(src, pos, 32));
	}
}
class Mustache_tkn_def {
	public byte[] Variable_lhs = Dflt_variable_lhs;
	public byte[] Variable_rhs = Dflt_variable_rhs;
	public int Variable_lhs_len;
	public int Variable_rhs_len;
	public static final byte[]
	  Dflt_variable_lhs = Bry_.new_a7("{{")
	, Dflt_variable_rhs = Bry_.new_a7("}}")
	;
	public static final byte
	  Escape_bgn	= Byte_ascii.Curly_bgn		// {{{escape}}}
	, Escape_end	= Byte_ascii.Curly_end		// {{{escape}}}
	, Section		= Byte_ascii.Hash			// {{#section}}
	, Grp_end		= Byte_ascii.Slash			// {{/section}}
	, Inverted		= Byte_ascii.Pow			// {{^inverted}}
	, Comment		= Byte_ascii.Bang			// {{!comment}}
	, Partial		= Byte_ascii.Angle_bgn		// {{>partial}}
	, Delimiter_bgn	= Byte_ascii.Eq				// {{=<% %>=}}
	, Delimiter_end	= Byte_ascii.Curly_end		// {{=<% %>=}}
	;
	public Mustache_tkn_def() {
		Variable_lhs_len = Variable_lhs.length;
		Variable_rhs_len = Variable_rhs.length;
	}
}
/*
root
txt
key
txt
section
	txt
	key
	txt
txt
*/
