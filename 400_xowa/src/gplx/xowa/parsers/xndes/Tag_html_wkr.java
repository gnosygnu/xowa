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
package gplx.xowa.parsers.xndes; import gplx.*;
import gplx.objects.strings.AsciiByte;
import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.langs.htmls.encoders.*;
public interface Tag_html_wkr {
	void Tag__process_name(byte[] name);
	void Tag__process_attr(byte[] key, byte[] val);
	void Tag__process_body(byte[] body);
	byte[] Tag__build(Xowe_wiki wiki, Xop_ctx ctx);
	void Tag__rls();
}
class Tag_html_wkr_noop implements Tag_html_wkr {
	public void Tag__process_name(byte[] name) {}
	public void Tag__process_attr(byte[] key, byte[] val) {}
	public void Tag__process_body(byte[] body) {}
	public byte[] Tag__build(Xowe_wiki wiki, Xop_ctx ctx) {return Bry_.Empty;}
	public void Tag__rls() {}
        public static final Tag_html_wkr_noop Instance = new Tag_html_wkr_noop(); Tag_html_wkr_noop() {}
}
class Tag_html_wkr_basic implements Tag_html_wkr {
	private final boolean atrs_encode;
	private final Bry_bfr tmp_bfr;
	private byte[] tag_name;
	public Tag_html_wkr_basic(Bry_bfr tmp_bfr, boolean atrs_encode) {
		this.tmp_bfr = tmp_bfr;
		this.atrs_encode = atrs_encode;
	}
	public void Tag__process_name(byte[] tag_name) {
		this.tag_name = tag_name;
		tmp_bfr.Add_byte(AsciiByte.Lt).Add(tag_name);	// EX: "<ref"
	}		
	public void Tag__process_attr(byte[] key, byte[] val) {
		int val_len = Bry_.Len(val);
		if (val_len == 0) return;	// ignore atrs with empty vals: EX:{{#tag:ref||group=}} PAGE:ru.w:Колчак,_Александр_Васильевич DATE:2014-07-03

		// NOTE: this behavior emulates /includes/parser/CoreParserFunctions.php|tagObj; REF: $attrText .= ' ' . htmlspecialchars( $name ) . '="' . htmlspecialchars( $value ) . '"';
		// write key
		tmp_bfr.Add_byte(AsciiByte.Space);	// write space between html_args
		int key_len = Bry_.Len(key);
		if (key_len > 0) {
			if (atrs_encode)
				Gfo_url_encoder_.Id.Encode(tmp_bfr, key, 0, key_len);
			else
				tmp_bfr.Add(key);
			tmp_bfr.Add_byte(AsciiByte.Eq);
		}

		// write val
		tmp_bfr.Add_byte(AsciiByte.Quote);
		if (atrs_encode)
			Gfo_url_encoder_.Id.Encode(tmp_bfr, val, 0, val_len);
		else
			tmp_bfr.Add(val);
		tmp_bfr.Add_byte(AsciiByte.Quote);
	}
	public void Tag__process_body(byte[] body) {
		tmp_bfr.Add_byte(AsciiByte.Gt);
		tmp_bfr.Add(body);
		tmp_bfr.Add_byte(AsciiByte.Lt).Add_byte(AsciiByte.Slash).Add(tag_name).Add_byte(AsciiByte.Gt);	// EX: "</ref>"
	}
	public byte[] Tag__build(Xowe_wiki wiki, Xop_ctx ctx) {
		return tmp_bfr.To_bry_and_clear();
	}
	public void Tag__rls() {
		tmp_bfr.Mkr_rls();
	}
}
