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
package gplx.langs.htmls.encoders; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.core.btries.*;
import gplx.xowa.parsers.amps.*;
public class Gfo_url_encoder_ {
	public static Gfo_url_encoder New__id() {return Gfo_url_encoder_.New__html_id().Make();}
	public static final    Gfo_url_encoder
	  Id				= Gfo_url_encoder_.New__html_id().Make()
	, Href				= Gfo_url_encoder_.New__html_href_mw(Bool_.Y).Make()
	, Href_wo_anchor	= Gfo_url_encoder_.New__html_href_mw(Bool_.N).Make()
	, Href_quotes		= Gfo_url_encoder_.New__html_href_quotes().Make()
	, Href_qarg			= Gfo_url_encoder_.New__html_href_qarg().Make()
	, Xourl				= Gfo_url_encoder_.New__html_href_mw(Bool_.Y).Init__same__many(Byte_ascii.Underline).Make()
	, Http_url			= Gfo_url_encoder_.New__http_url().Make()
	, Http_url_ttl		= Gfo_url_encoder_.New__http_url_ttl().Make()
	, Fsys_lnx			= Gfo_url_encoder_.New__fsys_lnx().Make()
	, Fsys_wnt			= Gfo_url_encoder_.New__fsys_wnt().Make()
	, Gfs				= Gfo_url_encoder_.New__gfs().Make()
	;
	private static Gfo_url_encoder_mkr New__html_id() {			// EX: "<a id='a�b'>" -> "<a id='a.C3.A9b'>"
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Dot).Init_common(Bool_.Y)
			.Init__decode_mark(Byte_ascii.Dot)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Underline)
			.Init__html_ent(Byte_ascii.Amp, Xop_amp_trie.Instance);
	}
	private static Gfo_url_encoder_mkr New__html_href_mw(boolean use_anchor_encoder) {		// EX: "<a href='^#^'>" -> "<a href='%5E#.5E'>"; REF.MW: ";:@$!*(),/"
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
	private static Gfo_url_encoder_mkr New__html_href_quotes() {// same as href encoder, but do not encode ?, =, #, +; also, don't encode "%" vals
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Underline)
			.Init__same__many
			( Byte_ascii.Semic, Byte_ascii.Colon, Byte_ascii.At, Byte_ascii.Dollar, Byte_ascii.Bang, Byte_ascii.Star
			, Byte_ascii.Paren_bgn, Byte_ascii.Paren_end, Byte_ascii.Comma, Byte_ascii.Slash
			, Byte_ascii.Question, Byte_ascii.Eq, Byte_ascii.Hash, Byte_ascii.Plus// NOTE: not part of wfUrlEncode; not sure where this is specified; needed for A#b
			);
	}
	public static Gfo_url_encoder_mkr New__http_url() {
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.N)
			.Init__diff__one(Byte_ascii.Space, Byte_ascii.Plus);
	}
	private static Gfo_url_encoder_mkr New__http_url_ttl() {
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y);
	}
	private static Gfo_url_encoder_mkr New__fsys_lnx() {
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__same__many(Byte_ascii.Slash)
			.Init__diff__one(Byte_ascii.Backslash, Byte_ascii.Slash);
	}
	private static Gfo_url_encoder_mkr New__fsys_wnt() {
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
	private static Gfo_url_encoder_mkr New__gfs() {
		return new Gfo_url_encoder_mkr().Init(Byte_ascii.Percent).Init_common(Bool_.Y)
			.Init__same__many(Byte_ascii.Paren_bgn, Byte_ascii.Paren_end, Byte_ascii.Apos, Byte_ascii.Semic);
	}
}
