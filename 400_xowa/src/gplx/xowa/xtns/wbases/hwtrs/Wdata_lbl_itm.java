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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.xtns.wbases.core.*;
public class Wdata_lbl_itm {
	public Wdata_lbl_itm(boolean is_pid, int id, boolean text_en_enabled) {
		this.is_pid = is_pid; this.id = id; this.text_en_enabled = text_en_enabled;
		this.ttl = Make_ttl(is_pid, id);			
	}
	public boolean Is_pid() {return is_pid;} private final    boolean is_pid;
	public int Id() {return id;} private final    int id;
	public byte[] Ttl() {return ttl;} private final    byte[] ttl;
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
	private static final    byte[] Ttl_prefix_pid = Bry_.new_a7("Property:P"), Ttl_prefix_qid = Bry_.new_a7("Q");
	private static final    byte[] Extract_ttl_qid = Bry_.new_a7("http://www.wikidata.org/entity/");
	public static byte[] Extract_ttl(byte[] href) {
		if (Bry_.Has_at_bgn(href, Extract_ttl_qid))	// qid
			return Bry_.Mid(href, Extract_ttl_qid.length, href.length);
		else										// possibly support pid in future, but so far, nothing referencing just "Property:P##"
			return null;
	}
}
