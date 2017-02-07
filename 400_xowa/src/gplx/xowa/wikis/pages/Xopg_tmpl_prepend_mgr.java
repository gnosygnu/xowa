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
package gplx.xowa.wikis.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.btries.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Xopg_tmpl_prepend_mgr {
	private Bry_bfr[] stack = Bry_bfr_.Ary_empty; private int stack_len, stack_max;
	public void Clear() {
		stack = Bry_bfr_.Ary_empty; stack_len = stack_max = 0;
	}
	public boolean Tmpl_args_parsing() {return tmpl_args_parsing;} public void Tmpl_args_parsing_(boolean v) {tmpl_args_parsing = v;} private boolean tmpl_args_parsing;
	public Xopg_tmpl_prepend_mgr Bgn(Bry_bfr bfr) {
		int new_len = stack_len + 1;
		if (new_len > stack_max) {
			stack_max += 8;
			Bry_bfr[] new_stack = new Bry_bfr[stack_max];
			Array_.Copy_to(stack, new_stack, 0);
			stack = new_stack;
		}
		stack[stack_len] = bfr;
		stack_len = new_len;
		return this;
	}
	public void End(Xop_ctx ctx, Bry_bfr bfr, byte[] val, int val_len, boolean called_from_tmpl) {
		if (	val_len > 0														// val is not empty
			&&	tmpl_prepend_nl_trie.Match_bgn(val, 0, val_len) != null			// val starts with {| : ; # *; REF.MW:Parser.php|braceSubstitution
			) {
			boolean add = true;
			if (called_from_tmpl) {												// called from tmpl
				for (int i = stack_len - 1; i > -1; --i) {						// iterate backwards over tmpl_stack;
					Bry_bfr stack_bfr = stack[i];
					switch (stack_bfr.Get_at_last_or_nil_if_empty()) {
						case Byte_ascii.Null:		continue;					// bfr is empty; ignore it
						case Byte_ascii.Nl:	add = false; i = -1; break;	// bfr ends in \n; don't add and stop; PAGE:bn.w:লিওনেল_মেসি |ko.w:도쿄_지하철_히비야_선|DATE:2014-05-27
						default:					i = -1; break;				// bfr has char; stop
					}
				}
			}
			else																// called from func arg; always add \n; EX:vi.w:Friedrich_II_của_Phổ; DATE:2014-04-26
				add = true;
			if (add)
				bfr.Add_byte(Byte_ascii.Nl);
		}
		if (called_from_tmpl) --stack_len;
	}
	private static final Btrie_fast_mgr tmpl_prepend_nl_trie = Xop_curly_bgn_lxr.tmpl_bgn_trie_();
}
