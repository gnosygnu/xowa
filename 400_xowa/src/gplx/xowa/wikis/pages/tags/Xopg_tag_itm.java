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
package gplx.xowa.wikis.pages.tags;
import gplx.langs.htmls.*;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.types.commons.KeyVal;
public class Xopg_tag_itm {
	Xopg_tag_itm(byte tid, byte[] node, byte[] href, byte[] body, KeyVal... atrs) {
		this.tid = tid;
		this.node = node;
		this.href = href;
		this.body = body;
		this.atrs = atrs;
	}
	public final String Source = "xowa";
	public byte			Tid()		{return tid;}		private final byte tid;
	public byte[]		Node()		{return node;}		private final byte[] node;
	public byte[]		Href()		{return href;}		private final byte[] href;
	public byte[]		Body()		{return body;}		private final byte[] body;
	public KeyVal[]		Atrs()		{return atrs;}		private final KeyVal[] atrs;
	public void To_html(BryWtr bfr) {
		bfr.AddByte(AsciiByte.AngleBgn);
		bfr.Add(node);
		To_html_atr(bfr, "data-source", "xowa");
		int len = atrs.length;
		for (int i = 0; i < len; ++i) {
			KeyVal atr = atrs[i];
			To_html_atr(bfr, atr.KeyToStr(), atr.ValToStrOrEmpty());
		}
		bfr.AddByte(AsciiByte.AngleEnd);
		if (!BryLni.Eq(node, Gfh_tag_.Bry__link)) {
			if (body != null) {
				bfr.AddByteNl();
				bfr.Add(body);
				bfr.AddByteNl();
			}
			bfr.AddByte(AsciiByte.AngleBgn).AddByte(AsciiByte.Slash);
			bfr.Add(node);
			bfr.AddByte(AsciiByte.AngleEnd);
		}
		bfr.AddByteNl();
	}
	private static void To_html_atr(BryWtr bfr, String key, String val) {
		bfr.AddByteSpace();
		bfr.AddStrA7(key);
		bfr.AddByte(AsciiByte.Eq);
		bfr.AddByte(AsciiByte.Quote);
		bfr.AddStrA7(val);
		bfr.AddByte(AsciiByte.Quote);
	}

	public static final byte Tid__css_file = 0, Tid__css_code = 1, Tid__js_file = 2, Tid__js_code = 3, Tid__htm_frag = 4; 
	private static final KeyVal
	  Tag__type_css = KeyVal.NewStr("type", "text/css"), Tag__type_js = KeyVal.NewStr("type", "text/javascript"), Tag__type_html = KeyVal.NewStr("type", "text/html")
	, Tag__rel_stylesheet = KeyVal.NewStr("rel", "stylesheet");
	public static Xopg_tag_itm New_css_file(Io_url href_url) {
		byte[] href = href_url.To_http_file_bry();
		return new Xopg_tag_itm(Tid__css_file, Gfh_tag_.Bry__link  , href, null, Tag__type_css, Tag__rel_stylesheet, KeyVal.NewStr("href", StringUtl.NewU8(href)));
	}
	public static Xopg_tag_itm New_css_code(byte[] code) {
		return new Xopg_tag_itm(Tid__css_code, Gfh_tag_.Bry__style , null, code, Tag__type_css);
	}
	public static Xopg_tag_itm New_js_file(Io_url src_url) {
		byte[] src = src_url.To_http_file_bry();
		return new Xopg_tag_itm(Tid__js_file , Gfh_tag_.Bry__script, src , null, Tag__type_js, KeyVal.NewStr("src", StringUtl.NewU8(src)));
	}
	public static Xopg_tag_itm New_js_code(String code) {return New_js_code(BryUtl.NewU8(code));}
	public static Xopg_tag_itm New_js_code(byte[] code) {
		return new Xopg_tag_itm(Tid__js_code , Gfh_tag_.Bry__script, null, code, Tag__type_js);
	}
	public static Xopg_tag_itm New_htm_frag(Io_url url, String id) {
		byte[] html = Io_mgr.Instance.LoadFilBry(url);
		return new Xopg_tag_itm(Tid__htm_frag, Gfh_tag_.Bry__script, null, html, Tag__type_html, KeyVal.NewStr("id", id));
	}
}
