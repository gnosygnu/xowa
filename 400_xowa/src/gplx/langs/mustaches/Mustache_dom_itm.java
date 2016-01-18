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
interface Mustache_elem_itm {
	int Tid();
	byte[] Key();
	Mustache_elem_itm[] Subs();
}
class Mustache_elem_itm_ {// for types, see http://mustache.github.io/mustache.5.html
	public static final int Tid__root = 0, Tid__text = 1, Tid__variable = 2, Tid__escape = 3, Tid__section = 4, Tid__inverted = 5, Tid__comment = 6, Tid__partial = 7, Tid__delimiter = 8;
	public static final Mustache_elem_itm[] Ary_empty = new Mustache_elem_itm[0];
}
abstract class Mustache_elem_base implements Mustache_elem_itm {
	public Mustache_elem_base(int tid, byte[] key) {this.tid = tid; this.key = key;}
	public int Tid() {return tid;} private final int tid;
	public byte[] Key() {return key;} private final byte[] key;
	@gplx.Virtual public Mustache_elem_itm[] Subs() {return Mustache_elem_itm_.Ary_empty;}
}
class Mustache_elem_text extends Mustache_elem_base {		// EX: text -> text
	public Mustache_elem_text(byte[] val) {super(Mustache_elem_itm_.Tid__text, Bry_.Empty);
		this.val = val;
	}
	public byte[] Val() {return val;} private final byte[] val;
}
class Mustache_elem_val extends Mustache_elem_base {		// EX: {{variable}} -> &lt;a&gt;
	public Mustache_elem_val(byte[] key) {super(Mustache_elem_itm_.Tid__variable, key);}
}
class Mustache_elem_escape extends Mustache_elem_base {	// EX: {{{variable}}} -> <a>
	public Mustache_elem_escape(byte[] key) {super(Mustache_elem_itm_.Tid__escape, key);}
}
class Mustache_elem_section extends Mustache_elem_base {	// EX: {{#section}}val{{/section}} -> val (if boolean) or valvalval (if list)
	public Mustache_elem_section(byte[] key) {super(Mustache_elem_itm_.Tid__section, key);}
}
class Mustache_elem_inverted extends Mustache_elem_base {	// EX: {{^section}}missing{{/section}} -> missing
	public Mustache_elem_inverted(byte[] key) {super(Mustache_elem_itm_.Tid__inverted, key);}
}
class Mustache_elem_comment extends Mustache_elem_base {	// EX: {{!section}}commentent{{/section}} -> 
	public Mustache_elem_comment(byte[] key) {super(Mustache_elem_itm_.Tid__inverted, key);}
}
class Mustache_elem_partial extends Mustache_elem_base {	// EX: {{>a}} -> abc (deferred eval)
	public Mustache_elem_partial(byte[] key) {super(Mustache_elem_itm_.Tid__partial, key);}
}
class Mustache_elem_delimiter extends Mustache_elem_base {// EX: {{=<% %>=}} -> <% variable %>
	public Mustache_elem_delimiter(byte[] key) {super(Mustache_elem_itm_.Tid__delimiter, key);}
}
