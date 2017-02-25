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
package gplx.langs.htmls.encoders; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.core.btries.*;
import gplx.langs.htmls.entitys.*;
public class Gfo_url_encoder_ {
	public static Gfo_url_encoder New__id() {return Gfo_url_encoder_.New__html_id().Make();}
	public static Gfo_url_encoder_mkr New__html_id() {			// EX: "<a id='a�b'>" -> "<a id='a.C3.A9b'>"
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Dot).Init_common(Bool_.Y)
			.Init__decode_mark(Byte_ascii.Dot)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Underline)
			.Init__html_ent(Byte_ascii.Amp, Gfh_entity_trie.Instance);
	}
	public static Gfo_url_encoder_mkr New__html_href_mw(boolean use_anchor_encoder) {		// EX: "<a href='^#^'>" -> "<a href='%5E#.5E'>"; REF.MW: ";:@$!*(),/"
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Underline)
			.Init__same__many
			( Byte_ascii.Semic, Byte_ascii.Colon, Byte_ascii.At, Byte_ascii.Dollar, Byte_ascii.Bang, Byte_ascii.Star
			, Byte_ascii.Paren_bgn, Byte_ascii.Paren_end, Byte_ascii.Comma, Byte_ascii.Slash
			, Byte_ascii.Hash// NOTE: not part of wfUrlEncode; not sure where this is specified; needed for A#b
			)
			.Init__anchor_encoder(use_anchor_encoder ? New__html_id().Make() : null);
	}
	private static Gfo_url_encoder_mkr New__html_href_qarg() {	// same as regular href encoder, but also do not encode qarg characters "?" and "="
		return New__html_href_mw(Bool_.Y).Init__same__many(Byte_ascii.Question, Byte_ascii.Eq);
	}
	public static Gfo_url_encoder_mkr New__html_href_quotes() {// same as href encoder, but do not encode ?, =, #, +; also, don't encode "%" vals
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Underline)
			.Init__same__many
			( Byte_ascii.Semic, Byte_ascii.Colon, Byte_ascii.At, Byte_ascii.Dollar, Byte_ascii.Bang, Byte_ascii.Star
			, Byte_ascii.Paren_bgn, Byte_ascii.Paren_end, Byte_ascii.Comma, Byte_ascii.Slash
			, Byte_ascii.Question, Byte_ascii.Eq, Byte_ascii.Hash, Byte_ascii.Plus// NOTE: not part of wfUrlEncode; not sure where this is specified; needed for A#b
			);
	}
	private static Gfo_url_encoder_mkr New__html_href_quotes_v2() {// same as href encoder, but do not encode ?, =, #, +; also, don't encode "%" vals
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Underline)
			.Init__same__many
			( Byte_ascii.Semic, Byte_ascii.Colon, Byte_ascii.At, Byte_ascii.Dollar, Byte_ascii.Bang, Byte_ascii.Star
			, Byte_ascii.Paren_bgn, Byte_ascii.Paren_end, Byte_ascii.Comma, Byte_ascii.Slash
			, Byte_ascii.Question, Byte_ascii.Eq, Byte_ascii.Hash, Byte_ascii.Plus// NOTE: not part of wfUrlEncode; not sure where this is specified; needed for A#b
			, Byte_ascii.Percent	// DATE:2016-07-12
			);
	}
	public static Gfo_url_encoder_mkr New__http_url() {
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.N)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Plus);
	}
	private static Gfo_url_encoder_mkr New__http_url_ttl() {
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y);
	}
	public static Gfo_url_encoder_mkr New__fsys_lnx() {
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__same__many(Byte_ascii.Slash)
			.Init__diff__one(Byte_ascii.Backslash, Byte_ascii.Slash);
	}
	public static Gfo_url_encoder_mkr New__fsys_wnt() {
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent)
			.Init__same__rng(Byte_ascii.Num_0, Byte_ascii.Num_9)
			.Init__same__rng(Byte_ascii.Ltr_A, Byte_ascii.Ltr_Z)
			.Init__same__rng(Byte_ascii.Ltr_a, Byte_ascii.Ltr_z)
			.Init__same__many
			( Byte_ascii.Bang, Byte_ascii.At, Byte_ascii.Hash, Byte_ascii.Dollar, Byte_ascii.Percent, Byte_ascii.Pow, Byte_ascii.Amp
			, Byte_ascii.Plus, Byte_ascii.Eq, Byte_ascii.Underline, Byte_ascii.Dash
			, Byte_ascii.Dot, Byte_ascii.Comma
			, Byte_ascii.Tick, Byte_ascii.Tilde, Byte_ascii.Brack_bgn, Byte_ascii.Brack_end, Byte_ascii.Curly_bgn, Byte_ascii.Curly_end);
	}
	public static Gfo_url_encoder_mkr New__gfs() {
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__same__many(Byte_ascii.Paren_bgn, Byte_ascii.Paren_end, Byte_ascii.Apos, Byte_ascii.Semic);
	}
	public static Gfo_url_encoder_mkr New__mw_ttl() {
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent)
			.Init__same__rng(0, 255)
			.Init__diff__many(Byte_ascii.Percent, Byte_ascii.Amp, Byte_ascii.Apos, Byte_ascii.Eq, Byte_ascii.Plus)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Underline)
			;
	}
	public static Gfo_url_encoder_mkr New__php_urlencode() {
		// REF: http://php.net/manual/en/function.urlencode.php;
		// "Returns a String in which all non-alphanumeric characters except -_. have been replaced with a percent (%) sign followed by two hex digits and spaces encoded as plus (+) signs"
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Plus);
	}
	public static Gfo_url_encoder_mkr New__wfUrlencode() {
		// REF: GlobalFunctions.php|wfUrlencode
		// same as php_urlencode, but do not encode ";:@$!*(),/~"
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Plus)
			.Init__same__many
			( Byte_ascii.Semic, Byte_ascii.At, Byte_ascii.Dollar, Byte_ascii.Bang, Byte_ascii.Star
			, Byte_ascii.Paren_bgn, Byte_ascii.Paren_end, Byte_ascii.Comma, Byte_ascii.Slash
			, Byte_ascii.Tilde
			, Byte_ascii.Colon	// NOTE: MW doesn't unescape colon if IIS. However, all of WMF servers run on non-IIS boxes, so include this;
			);
	}
	public static Gfo_url_encoder_mkr New__php_rawurlencode() {
		// REF: http://php.net/manual/en/function.rawurlencode.php
		// "Returns a String in which all non-alphanumeric characters except -_.~ have been replaced with a percent (%) sign followed by two hex digits. "
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__same__many(Byte_ascii.Tilde)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Plus);
	}
	public static final    Gfo_url_encoder
	  Id				= Gfo_url_encoder_.New__html_id().Make()
	, Href				= Gfo_url_encoder_.New__html_href_mw(Bool_.Y).Make()
	, Href_wo_anchor	= Gfo_url_encoder_.New__html_href_mw(Bool_.N).Make()
	, Href_quotes		= Gfo_url_encoder_.New__html_href_quotes().Make()
	, Href_quotes_v2	= Gfo_url_encoder_.New__html_href_quotes_v2().Make()
	, Href_qarg			= Gfo_url_encoder_.New__html_href_qarg().Make()
	, Xourl				= Gfo_url_encoder_.New__html_href_mw(Bool_.Y).Init__same__many(Byte_ascii.Underline).Make()
	, Http_url			= Gfo_url_encoder_.New__http_url().Make()
	, Http_url_ttl		= Gfo_url_encoder_.New__http_url_ttl().Make()
	, Mw_ttl			= Gfo_url_encoder_.New__mw_ttl().Make()
	, Php_urlencode		= Gfo_url_encoder_.New__php_urlencode().Make()
	, Php_rawurlencode	= Gfo_url_encoder_.New__php_rawurlencode().Make()
	, Mw_wfUrlencode	= Gfo_url_encoder_.New__wfUrlencode().Make()
	;
}
