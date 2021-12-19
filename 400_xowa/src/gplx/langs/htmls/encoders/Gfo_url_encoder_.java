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
package gplx.langs.htmls.encoders;
import gplx.langs.htmls.entitys.Gfh_entity_trie;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
public class Gfo_url_encoder_ {
	public static Gfo_url_encoder_mkr New__html_id() { // EX: "<a id='a�b'>" -> "<a id='a.C3.A9b'>"
		return new Gfo_url_encoder_mkr()
			.Init(AsciiByte.Dot)
			.Init__same__rng(0, 255) // clear everything and set to do-not-encode
			.Init__encode_hex(AsciiByte.AngleBgn, AsciiByte.AngleEnd) // NOTE: should not be encoded, but will break existings tests; EX:{{#tag:pre|a|id='<br/>'}}; DATE:2019-05-12
			.Init__decode_mark(AsciiByte.Dot)
			.Init__diff__one(AsciiByte.Space, AsciiByte.Underline)
			.Init__html_ent(AsciiByte.Amp, Gfh_entity_trie.Instance, false);
	}
	public static Gfo_url_encoder_mkr New__html_href_mw(boolean use_anchor_encoder) {		// EX: "<a href='^#^'>" -> "<a href='%5E#.5E'>"; REF.MW: ";:@$!*(),/"
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent).Init_common(BoolUtl.Y)
			.Init__diff__one(AsciiByte.Space, AsciiByte.Underline)
			.Init__same__many
			( AsciiByte.Semic, AsciiByte.Colon, AsciiByte.At, AsciiByte.Dollar, AsciiByte.Bang, AsciiByte.Star
			, AsciiByte.ParenBgn, AsciiByte.ParenEnd, AsciiByte.Comma, AsciiByte.Slash
			, AsciiByte.Hash// NOTE: not part of wfUrlEncode; not sure where this is specified; needed for A#b
			)
			.Init__anchor_encoder(use_anchor_encoder ? New__html_id().Make() : null);
	}
	private static Gfo_url_encoder_mkr New__html_href_qarg() {	// same as regular href encoder, but also do not encode qarg characters "?" and "="
		return New__html_href_mw(BoolUtl.Y).Init__same__many(AsciiByte.Question, AsciiByte.Eq);
	}
	public static Gfo_url_encoder_mkr New__html_href_quotes() {// same as href encoder, but do not encode ?, =, #, +; also, don't encode "%" vals
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent).Init_common(BoolUtl.Y)
			.Init__diff__one(AsciiByte.Space, AsciiByte.Underline)
			.Init__same__many
			( AsciiByte.Semic, AsciiByte.Colon, AsciiByte.At, AsciiByte.Dollar, AsciiByte.Bang, AsciiByte.Star
			, AsciiByte.ParenBgn, AsciiByte.ParenEnd, AsciiByte.Comma, AsciiByte.Slash
			, AsciiByte.Question, AsciiByte.Eq, AsciiByte.Hash, AsciiByte.Plus// NOTE: not part of wfUrlEncode; not sure where this is specified; needed for A#b
			);
	}
	private static Gfo_url_encoder_mkr New__html_href_quotes_v2() {// same as href encoder, but do not encode ?, =, #, +; also, don't encode "%" vals
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent).Init_common(BoolUtl.Y)
			.Init__diff__one(AsciiByte.Space, AsciiByte.Underline)
			.Init__same__many
			( AsciiByte.Semic, AsciiByte.Colon, AsciiByte.At, AsciiByte.Dollar, AsciiByte.Bang, AsciiByte.Star
			, AsciiByte.ParenBgn, AsciiByte.ParenEnd, AsciiByte.Comma, AsciiByte.Slash
			, AsciiByte.Question, AsciiByte.Eq, AsciiByte.Hash, AsciiByte.Plus// NOTE: not part of wfUrlEncode; not sure where this is specified; needed for A#b
			, AsciiByte.Percent	// DATE:2016-07-12
			);
	}
	public static Gfo_url_encoder_mkr New__http_url() {
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent).Init_common(BoolUtl.N)
			.Init__diff__one(AsciiByte.Space, AsciiByte.Plus);
	}
	private static Gfo_url_encoder_mkr New__http_url_ttl() {
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent).Init_common(BoolUtl.Y);
	}
	public static Gfo_url_encoder_mkr New__fsys_lnx() {
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent).Init_common(BoolUtl.Y)
			.Init__same__many(AsciiByte.Slash)
			.Init__diff__one(AsciiByte.Backslash, AsciiByte.Slash);
	}
	public static Gfo_url_encoder_mkr New__fsys_wnt() {
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent)
			.Init__same__rng(AsciiByte.Num0, AsciiByte.Num9)
			.Init__same__rng(AsciiByte.Ltr_A, AsciiByte.Ltr_Z)
			.Init__same__rng(AsciiByte.Ltr_a, AsciiByte.Ltr_z)
			.Init__same__many
			( AsciiByte.Bang, AsciiByte.At, AsciiByte.Hash, AsciiByte.Dollar, AsciiByte.Percent, AsciiByte.Pow, AsciiByte.Amp
			, AsciiByte.Plus, AsciiByte.Eq, AsciiByte.Underline, AsciiByte.Dash
			, AsciiByte.Dot, AsciiByte.Comma
			, AsciiByte.Tick, AsciiByte.Tilde, AsciiByte.BrackBgn, AsciiByte.BrackEnd, AsciiByte.CurlyBgn, AsciiByte.CurlyEnd);
	}
	public static Gfo_url_encoder_mkr New__gfs() {
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent).Init_common(BoolUtl.Y)
			.Init__same__many(AsciiByte.ParenBgn, AsciiByte.ParenEnd, AsciiByte.Apos, AsciiByte.Semic);
	}
	public static Gfo_url_encoder_mkr New__mw_ttl() {
		return new Gfo_url_encoder_mkr()
			.Init(AsciiByte.Percent)
			.Init__same__rng(0, 255)
			.Init__diff__many(AsciiByte.Amp, AsciiByte.Apos, AsciiByte.Eq, AsciiByte.Plus)
			.Init__diff__one(AsciiByte.Space, AsciiByte.Underline)
			;
	}
	public static Gfo_url_encoder_mkr New__php_urlencode() {
		// REF: http://php.net/manual/en/function.urlencode.php;
		// "Returns a String in which all non-alphanumeric characters except -_. have been replaced with a percent (%) sign followed by two hex digits and spaces encoded as plus (+) signs"
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent).Init_common(BoolUtl.Y)
			.Init__diff__one(AsciiByte.Space, AsciiByte.Plus);
	}
	public static Gfo_url_encoder_mkr New__wfUrlencode() {
		// REF: GlobalFunctions.php|wfUrlencode
		// same as php_urlencode, but do not encode ";:@$!*(),/~"
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent).Init_common(BoolUtl.Y)
			.Init__diff__one(AsciiByte.Space, AsciiByte.Plus)
			.Init__same__many
			( AsciiByte.Semic, AsciiByte.At, AsciiByte.Dollar, AsciiByte.Bang, AsciiByte.Star
			, AsciiByte.ParenBgn, AsciiByte.ParenEnd, AsciiByte.Comma, AsciiByte.Slash
			, AsciiByte.Tilde
			, AsciiByte.Colon	// NOTE: MW doesn't unescape colon if IIS. However, all of WMF servers run on non-IIS boxes, so include this;
			);
	}
	public static Gfo_url_encoder_mkr New__php_rawurlencode() {
		// REF: http://php.net/manual/en/function.rawurlencode.php
		// "Returns a String in which all non-alphanumeric characters except -_.~ have been replaced with a percent (%) sign followed by two hex digits. "
		return new Gfo_url_encoder_mkr().Init(AsciiByte.Percent).Init_common(BoolUtl.Y)
			.Init__same__many(AsciiByte.Tilde)
			.Init__diff__one(AsciiByte.Space, AsciiByte.Plus);
	}
	public static final Gfo_url_encoder
	  Id				= Gfo_url_encoder_.New__html_id().Make()
	, Href				= Gfo_url_encoder_.New__html_href_mw(BoolUtl.Y).Make()
	, Href_wo_anchor	= Gfo_url_encoder_.New__html_href_mw(BoolUtl.N).Make()
	, Href_quotes		= Gfo_url_encoder_.New__html_href_quotes().Make()
	, Href_quotes_v2	= Gfo_url_encoder_.New__html_href_quotes_v2().Make()
	, Href_qarg			= Gfo_url_encoder_.New__html_href_qarg().Make()
	, Xourl				= Gfo_url_encoder_.New__html_href_mw(BoolUtl.Y).Init__same__many(AsciiByte.Underline).Make()
	, Http_url			= Gfo_url_encoder_.New__http_url().Make()
	, Http_url_ttl		= Gfo_url_encoder_.New__http_url_ttl().Make()
	, Mw_ttl			= Gfo_url_encoder_.New__mw_ttl().Make()
	, Php_urlencode		= Gfo_url_encoder_.New__php_urlencode().Make()
	, Php_rawurlencode	= Gfo_url_encoder_.New__php_rawurlencode().Make()
	, Mw_wfUrlencode	= Gfo_url_encoder_.New__wfUrlencode().Make()
	;
}
