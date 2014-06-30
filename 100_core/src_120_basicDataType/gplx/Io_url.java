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
package gplx;
import gplx.ios.*; /*IoUrlInfo*/
public class Io_url implements CompareAble, EqAble, ParseAble, GfoInvkAble {	//_20101005 URL:doc/Io_url.txt
	public IoUrlInfo Info() {return info;} IoUrlInfo info;
	public String Raw() {return raw;} final String raw;
	public byte[] RawBry() {return Bry_.new_utf8_(raw);}
//		public byte[] Http_file_bry() {
//			try {return Bry_.new_utf8_(String_.Concat(http_file_str, java.net.URLEncoder.encode(raw, "UTF-8")));}	
//			catch (Exception e) {throw Err_.err_(e, "Http_file_bry");}
//		}
	public String To_http_file_str() {return Http_file_str + Http_file_str_encoder.Encode_str(raw);}
	public byte[] To_http_file_bry() {return Bry_.Add(Http_file_bry, Http_file_str_encoder.Encode_bry(raw));}
	public static Url_encoder_interface Http_file_str_encoder;
	
	public static final String Http_file_str = "file:///";
	public static final int Http_file_len = String_.Len(Http_file_str);
	public static final byte[] Http_file_bry = Bry_.new_ascii_(Http_file_str);
	public boolean Type_dir() {return info.IsDir(raw);} public boolean Type_fil() {return !info.IsDir(raw);}
	public Io_url OwnerDir() {return Io_url_.new_inf_(info.OwnerDir(raw), info);}
	public Io_url OwnerRoot() {return Io_url_.new_inf_(info.OwnerRoot(raw), info);}
	public String NameAndExt() {return info.NameAndExt(raw);}
	public String NameAndExt_noDirSpr() {return this.Type_dir() ? this.NameOnly() : this.NameAndExt();}
	public String NameOnly() {return info.NameOnly(raw);}
	public String Ext() {return info.Ext(raw);}
	public String Xto_api() {return info.Xto_api(raw);}
	public String XtoCaseNormalized() {return String_.CaseNormalize(info.CaseSensitive(), raw);}
	public Io_url GenSubDir(String subDirName) {return Io_url_.new_inf_(String_.Concat(raw, subDirName, info.DirSpr()), info);}
	public Io_url GenSubDir_nest(String... ary) {return GenSub(false, ary);}
	public Io_url GenSubFil(String val) {return Io_url_.new_inf_(raw + val, info);}
	public Io_url GenSubFil_ary(String... ary)	{return Io_url_.new_inf_(raw + String_.Concat(ary), info);}
	public Io_url GenSubFil_nest(String... ary) {return GenSub(true, ary);}
	public Io_url GenNewNameAndExt(String val)	{return this.OwnerDir().GenSubFil(val);}
	public Io_url GenNewNameOnly(String val)	{return this.OwnerDir().GenSubFil(val + this.Ext());}
	public Io_url GenNewExt(String val) {return this.OwnerDir().GenSubFil(this.NameOnly() + val);}
	public String Gen_sub_path_for_os(String val) {
		if (Op_sys.Cur().Tid_is_wnt()) val = String_.Replace(val, Op_sys.Lnx.Fsys_dir_spr_str(), Op_sys.Wnt.Fsys_dir_spr_str());
		return raw + val;
	}
	public String GenRelUrl_orEmpty(Io_url dir) {
		String dirRaw = dir.Raw();
		return String_.HasAtBgn(raw, dirRaw)
			? String_.DelBgn(raw, String_.Len(dirRaw))
			: String_.Empty;
	}
	public ListAdp XtoNames() {
		ListAdp list = ListAdp_.new_();
		Io_url cur = this;
		while (!cur.EqNull()) {
			list.Add(cur.NameAndExt_noDirSpr());
			cur = cur.OwnerDir();
		}
		list.Reverse();
		return list;
	}
	public Io_url GenParallel(Io_url oldRoot, Io_url newRoot) {return newRoot.GenSubFil_ary(GenRelUrl_orEmpty(oldRoot));}
	public boolean Eq(Object obj)		{if (obj == null) return false; return String_.Eq(raw, ((Io_url)obj).raw);}
	public boolean EqNull()			{return this.Eq(Io_url_.Null);}
	Io_url GenSub(boolean isFil, String[] ary) {
		String_bldr sb = String_bldr_.new_().Add(raw);
		int len = Array_.Len(ary);
		for (int i = 0; i < len; i++) {
			sb.Add(ary[i]);
			if (isFil && i == len - 1) break;	// do not add closing backslash if last term
			sb.Add(info.DirSpr());
		}
		return Io_url_.new_inf_(sb.XtoStr(), info);
	}
	public Object ParseAsObj(String raw) {return Io_url_.new_any_(raw);}
	@Override public String toString()	{return raw;}
	public int compareTo(Object obj)	{return CompareAble_.Compare_obj(raw, ((Io_url)obj).raw);}
	@Override public boolean equals(Object obj) {return String_.Eq(raw, Io_url_.as_(obj).raw);}
	@Override public int hashCode() {return raw.hashCode();}
	@gplx.Internal protected Io_url(String raw, IoUrlInfo info) {this.raw = raw; this.info = info;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_to_http_file))			return To_http_file_str();
		else if	(ctx.Match(k, Invk_gen_sub_path_for_os))	return Gen_sub_path_for_os(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
	}	static final String Invk_to_http_file = "to_http_file", Invk_gen_sub_path_for_os = "gen_sub_path_for_os";
}
