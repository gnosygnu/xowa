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
package gplx.xowa.xtns.wbases.claims.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
import gplx.xowa.xtns.wbases.claims.enums.*;
public class Wbase_claim_monolingualtext extends Wbase_claim_base {
	public Wbase_claim_monolingualtext(int pid, byte snak_tid, byte[] lang, byte[] text) {super(pid, snak_tid);
		this.lang = lang; this.text = text;
	}
	@Override public byte	Val_tid() {return Wbase_claim_type_.Tid__monolingualtext;}
	public byte[]			Lang() {return lang;} private final    byte[] lang;
	public byte[]			Text() {return text;} private final    byte[] text;

	@Override public void Welcome(Wbase_claim_visitor visitor) {visitor.Visit_monolingualtext(this);}
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wbase_claim_value_type_.To_str_or_fail(this.Snak_tid()), Wbase_claim_type_.To_key_or_unknown(this.Val_tid()), String_.new_u8(lang), String_.new_u8(text));
	}
}
