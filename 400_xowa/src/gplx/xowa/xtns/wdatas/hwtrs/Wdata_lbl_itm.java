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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.xtns.wdatas.core.*;
public class Wdata_lbl_itm {
	public Wdata_lbl_itm(boolean is_pid, int id, boolean text_en_enabled) {
		this.is_pid = is_pid; this.id = id; this.text_en_enabled = text_en_enabled;
		this.ttl = Make_ttl(is_pid, id);			
	}
	public boolean Is_pid() {return is_pid;} private final boolean is_pid;
	public int Id() {return id;} private final int id;
	public byte[] Ttl() {return ttl;} private final byte[] ttl;
	public byte[] Lang() {return lang;} private byte[] lang;
	public byte[] Text() {return text;} private byte[] text;
	public byte[] Text_en() {return text_en;} public void Text_en_(byte[] v) {text_en = v;} private byte[] text_en = Bry_.Empty;
	public boolean Text_en_enabled() {return text_en_enabled;} private boolean text_en_enabled;
	public void Load_vals(byte[] lang, byte[] text) {this.lang = lang; this.text = text;}
	public static byte[] Make_ttl(boolean is_pid, int id) {
		return is_pid
			? Bry_.Add(Ttl_prefix_pid, Int_.To_bry(id))
			: Bry_.Add(Ttl_prefix_qid, Int_.To_bry(id))
			;
	}
	private static final byte[] Ttl_prefix_pid = Bry_.new_a7("Property:P"), Ttl_prefix_qid = Bry_.new_a7("Q");
	private static final byte[] Extract_ttl_qid = Bry_.new_a7("http://www.wikidata.org/entity/");
	public static byte[] Extract_ttl(byte[] href) {
		if (Bry_.Has_at_bgn(href, Extract_ttl_qid))	// qid
			return Bry_.Mid(href, Extract_ttl_qid.length, href.length);
		else										// possibly support pid in future, but so far, nothing referencing just "Property:P##"
			return null;
	}
}
