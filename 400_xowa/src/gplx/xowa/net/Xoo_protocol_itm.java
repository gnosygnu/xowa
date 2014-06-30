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
package gplx.xowa.net; import gplx.*; import gplx.xowa.*;
public class Xoo_protocol_itm {
	public Xoo_protocol_itm(byte tid, String text) {
		this.tid = tid;
		this.text_bry = Bry_.new_utf8_(text);
		this.text_str = text;
		int text_len = text_bry.length;
		for (int i = 0; i < text_len; i++) {
			if (text_bry[i] == Byte_ascii.Colon) {
				key_wo_colon_bry = Bry_.Mid(text_bry, 0, i);
				key_w_colon_bry_len = i;
				key_wo_colon_str = String_.new_utf8_(key_wo_colon_bry);
				key_w_colon_bry = Bry_.Mid(text_bry, 0, i + 1);
				text_ends_w_colon = i == text_len - 1;
				break;
			}
		}
	}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Key_wo_colon_bry() {return key_wo_colon_bry;} private byte[] key_wo_colon_bry;			// http
	public String Key_wo_colon_str() {return key_wo_colon_str;} private String key_wo_colon_str;
	public byte[] Key_w_colon_bry() {return key_w_colon_bry;} private byte[] key_w_colon_bry;				// http:
	public int Key_w_colon_bry_len() {return key_w_colon_bry_len;} private int key_w_colon_bry_len;
	public byte[] Text_bry() {return text_bry;} private byte[] text_bry;									// http://
	public String Text_str() {return text_str;} private String text_str;
	public boolean Text_ends_w_colon() {return text_ends_w_colon;} private boolean text_ends_w_colon;
	public static final byte // REF.MW:DefaultSettings|$wgUrlProtocols; NOTE: "news:" not included because it breaks alias "wikinews:"
	  Tid_http					=  0
	, Tid_https					=  1
	, Tid_ftp					=  2
	, Tid_ftps					=  3
	, Tid_ssh					=  4
	, Tid_sftp					=  5
	, Tid_irc					=  6
	, Tid_ircs					=  7
	, Tid_xmpp					=  8
	, Tid_sip					=  9
	, Tid_sips					= 10
	, Tid_gopher				= 11
	, Tid_telnet				= 12
	, Tid_nntp					= 13
	, Tid_worldwind				= 14
	, Tid_mailto				= 15
	, Tid_tel					= 16
	, Tid_sms					= 17
	, Tid_svn					= 18
	, Tid_git					= 19
	, Tid_mms					= 20
	, Tid_bitcoin				= 21
	, Tid_magnet				= 22
	, Tid_urn					= 23
	, Tid_geo					= 24
	, Tid_null					= 25
	, Tid_xowa					= 26
	, Tid_file					= 27
	, Tid_relative_1			= 28		// [//a.org]
	, Tid_relative_2			= 29		// [[//a.org]]
	;
	public static final OrderedHash Regy = OrderedHash_.new_bry_();
	public static final Xoo_protocol_itm 
	  Itm_http					= new_(Tid_http			, "http://")
	, Itm_https					= new_(Tid_https		, "https://")
	, Itm_ftp					= new_(Tid_ftp			, "ftp://")
	, Itm_ftps					= new_(Tid_ftps			, "ftps://")
	, Itm_ssh					= new_(Tid_ssh			, "ssh://")
	, Itm_sftp					= new_(Tid_sftp			, "sftp://")
	, Itm_irc					= new_(Tid_irc			, "irc://")
	, Itm_ircs					= new_(Tid_ircs			, "ircs://")
	, Itm_xmpp					= new_(Tid_xmpp			, "xmpp:")
	, Itm_sip					= new_(Tid_sip			, "sip:")
	, Itm_sips					= new_(Tid_sips			, "sips:")
	, Itm_gopher				= new_(Tid_gopher		, "gopher://")
	, Itm_telnet				= new_(Tid_telnet		, "telnet://")
	, Itm_nntp					= new_(Tid_nntp			, "nntp://")
	, Itm_worldwind				= new_(Tid_worldwind	, "worldwind://")
	, Itm_mailto				= new_(Tid_mailto		, "mailto:")
	, Itm_tel					= new_(Tid_tel			, "tel:")
	, Itm_sms					= new_(Tid_sms			, "sms:")
	, Itm_svn					= new_(Tid_svn			, "svn://")
	, Itm_git					= new_(Tid_git			, "git://")
	, Itm_mms					= new_(Tid_mms			, "mms://")
	, Itm_bitcoin				= new_(Tid_bitcoin		, "bicoin:")
	, Itm_magnet				= new_(Tid_magnet		, "magnet:")
	, Itm_urn					= new_(Tid_urn			, "urn:")
	, Itm_geo					= new_(Tid_geo			, "geo:")
	;
	public static final String Str_file = "file:";
	public static final byte[] Bry_file = Bry_.new_ascii_(Str_file);
	public static Xoo_protocol_itm[] Ary() {
		if (protocol_itm_ary == null) {
			int len = Regy.Count();
			protocol_itm_ary = new Xoo_protocol_itm[len];
			for (int i = 0; i < len; i++)
				protocol_itm_ary[i] = (Xoo_protocol_itm)Regy.FetchAt(i);
		}
		return protocol_itm_ary;
	}	private static Xoo_protocol_itm[] protocol_itm_ary;
	public static String[] Protocol_str_ary() {
		if (protocol_str_ary == null) {
			int len = Regy.Count();
			protocol_str_ary = new String[len];
			for (int i = 0; i < len; i++)
				protocol_str_ary[i] = ((Xoo_protocol_itm)Regy.FetchAt(i)).Text_str();
		}
		return protocol_str_ary;
	}	private static String[] protocol_str_ary;
	private static Xoo_protocol_itm new_(byte tid, String text) {
		Xoo_protocol_itm rv = new Xoo_protocol_itm(tid, text);
		Regy.Add(rv.Key_wo_colon_bry(), rv);
		return rv;
	}
}
