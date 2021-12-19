/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.libs.files;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.core.envs.Op_sys;
import gplx.core.interfaces.ParseAble;
import gplx.core.ios.IoUrlInfo;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import gplx.langs.htmls.Url_encoder_interface;
import gplx.langs.htmls.Url_encoder_interface_same;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.commons.lists.CompareAble;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.utls.StringUtl;
public class Io_url implements CompareAble, ParseAble, Gfo_invk {    //_20101005 URL:doc/Io_url.txt
	public IoUrlInfo Info() {return info;} IoUrlInfo info;
	public String Raw() {return raw;} final String raw;
	public byte[] RawBry() {return BryUtl.NewU8(raw);}
	public String To_http_file_str() {return StringUtl.IsNullOrEmpty(raw) ? StringUtl.Empty : StringUtl.Concat    (Http_file_str, Http_file_str_encoder.Encode_str(raw));}
	public byte[] To_http_file_bry() {return StringUtl.IsNullOrEmpty(raw) ? BryUtl.Empty        : BryUtl.Add        (Http_file_bry, Http_file_str_encoder.Encode_bry(raw));}
	public static Url_encoder_interface Http_file_str_encoder = Url_encoder_interface_same.Instance;
	public static final String Http_file_str = "file:///";
	public static final int Http_file_len = StringUtl.Len(Http_file_str);
	public static final byte[] Http_file_bry = BryUtl.NewA7(Http_file_str);
	public boolean Type_dir() {return info.IsDir(raw);} public boolean Type_fil() {return !info.IsDir(raw);}
	public Io_url OwnerDir() {return Io_url_.new_inf_(info.OwnerDir(raw), info);}
	public Io_url OwnerRoot() {return Io_url_.new_inf_(info.OwnerRoot(raw), info);}
	public String NameAndExt() {return info.NameAndExt(raw);}
	public String NameAndExt_noDirSpr() {return this.Type_dir() ? this.NameOnly() : this.NameAndExt();}
	public String NameOnly() {return info.NameOnly(raw);}
	public String Ext() {return info.Ext(raw);}
	public String Xto_api() {return info.Xto_api(raw);}
	public String XtoCaseNormalized() {return StringUtl.CaseNormalize(info.CaseSensitive(), raw);}
	public Io_url GenSubDir(String subDirName) {return Io_url_.new_inf_(StringUtl.Concat(raw, subDirName, info.DirSpr()), info);}
	public Io_url GenSubDir_nest(String... ary) {return GenSub(false, ary);}
	public Io_url GenSubFil(String val) {return Io_url_.new_inf_(raw + val, info);}
	public Io_url GenSubFil_ary(String... ary)    {return Io_url_.new_inf_(raw + StringUtl.Concat(ary), info);}
	public Io_url GenSubFil_nest(String... ary) {return GenSub(true, ary);}
	public Io_url GenNewNameAndExt(String val)    {return this.OwnerDir().GenSubFil(val);}
	public Io_url GenNewNameOnly(String val)    {return this.OwnerDir().GenSubFil(val + this.Ext());}
	public Io_url GenNewExt(String val) {return this.OwnerDir().GenSubFil(this.NameOnly() + val);}
	public String Gen_sub_path_for_os(String val) {
		if (Op_sys.Cur().Tid_is_wnt()) val = StringUtl.Replace(val, Op_sys.Lnx.Fsys_dir_spr_str(), Op_sys.Wnt.Fsys_dir_spr_str());
		return raw + val;
	}
	public String GenRelUrl_orEmpty(Io_url dir) {
		String dirRaw = dir.Raw();
		return StringUtl.HasAtBgn(raw, dirRaw)
			? StringUtl.DelBgn(raw, StringUtl.Len(dirRaw))
			: StringUtl.Empty;
	}
	public List_adp XtoNames() {
		List_adp list = List_adp_.New();
		Io_url cur = this;
		while (!cur.EqNull()) {
			list.Add(cur.NameAndExt_noDirSpr());
			cur = cur.OwnerDir();
		}
		list.Reverse();
		return list;
	}
	public Io_url GenParallel(Io_url oldRoot, Io_url newRoot) {return newRoot.GenSubFil_ary(GenRelUrl_orEmpty(oldRoot));}
	public boolean Eq(Object obj)        {if (obj == null) return false; return StringUtl.Eq(raw, ((Io_url)obj).raw);}
	public boolean EqNull()            {return this.Eq(Io_url_.Empty);}
	Io_url GenSub(boolean isFil, String[] ary) {
		String_bldr sb = String_bldr_.new_().Add(raw);
		int len = ArrayUtl.Len(ary);
		for (int i = 0; i < len; i++) {
			sb.Add(ary[i]);
			if (isFil && i == len - 1) break;    // do not add closing backslash if last term
			sb.Add(info.DirSpr());
		}
		return Io_url_.new_inf_(sb.ToStr(), info);
	}
	public Object ParseAsObj(String raw) {return Io_url_.new_any_(raw);}
	@Override public String toString()    {return raw;}
	public int compareTo(Object obj)    {return CompareAbleUtl.Compare_obj(raw, ((Io_url)obj).raw);}
	@Override public boolean equals(Object obj) {return StringUtl.Eq(raw, Io_url_.as_(obj).raw);}
	@Override public int hashCode() {return raw.hashCode();}
	public Io_url(String raw, IoUrlInfo info) {this.raw = raw; this.info = info;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if        (ctx.Match(k, Invk_to_http_file))            return To_http_file_str();
		else if    (ctx.Match(k, Invk_gen_sub_path_for_os))    return Gen_sub_path_for_os(m.ReadStr("v"));
		else    return Gfo_invk_.Rv_unhandled;
	}   static final String Invk_to_http_file = "to_http_file", Invk_gen_sub_path_for_os = "gen_sub_path_for_os";
}
