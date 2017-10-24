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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.envs.*;
public interface IoUrlInfo {
	String Key();
	byte DirSpr_byte();
	String DirSpr();
	boolean CaseSensitive();
	String EngineKey();

	boolean Match(String raw);
	boolean IsDir(String raw);
	String Xto_api(String raw);
	String OwnerDir(String raw);
	String OwnerRoot(String raw);
	String NameAndExt(String raw);
	String NameOnly(String raw);
	String Ext(String raw);
	String XtoRootName(String raw, int rawLen);
}
class IoUrlInfo_nil implements IoUrlInfo {
	public String Key() {return KeyConst;} public static final    String KeyConst = String_.Null_mark;
	public String EngineKey() {return "<<INVALID>>";}
	public String DirSpr() {return "<<INVALID>>";}
	public byte DirSpr_byte() {return Byte_ascii.Slash;}
	public String VolSpr() {return "<<INVALID>>";}
	public boolean CaseSensitive() {return false;}
	public boolean Match(String raw) {return false;}
	public boolean IsDir(String raw) {return false;}
	public String Xto_api(String raw) {return "";}
	public String OwnerDir(String raw) {return IoUrlInfo_base.NullString;}
	public String OwnerRoot(String raw) {return IoUrlInfo_base.NullString;}
	public String NameAndExt(String raw) {return "";}
	public String NameOnly(String raw) {return "";}
	public String Ext(String raw) {return "";}
	public String XtoRootName(String raw, int rawLen) {return "";}
	public static final    IoUrlInfo_nil Instance = new IoUrlInfo_nil(); IoUrlInfo_nil() {}
}
abstract class IoUrlInfo_base implements IoUrlInfo {
	@gplx.Internal protected static final    int DirSprLen = 1;
	@gplx.Internal protected static final    String NullString = "", ExtSeparator = ".";
	public abstract String Key();
	public abstract byte DirSpr_byte();
	public abstract String DirSpr();
	public abstract boolean CaseSensitive();
	public abstract boolean Match(String raw);
	public abstract String EngineKey();
	public boolean IsDir(String raw) {return String_.Has_at_end(raw, DirSpr());}
	public abstract String XtoRootName(String raw, int rawLen);
	@gplx.Virtual public String Xto_api(String raw) {
		return IsDir(raw)
			? String_.DelEnd(raw, IoUrlInfo_base.DirSprLen)	// if Dir, remove trailing DirSpr, since most api will not expect it (ex: .Delete will malfunction)
			: raw;
	}
	public String OwnerDir(String raw) {
		int rawLen = String_.Len(raw);
		int ownerDirSprPos = OwnerDirPos(raw, rawLen);
		if (ownerDirSprPos <= OwnerDirPos_hasNoOwner) return IoUrlInfo_base.NullString;	// no ownerDir found; return Null; only (a) NullUrls (b) RootUrls ("C:\") (c) relative ("fil.txt")
		return String_.MidByLen(raw, 0, ownerDirSprPos + 1);	// +1 to include backslash
	}
	@gplx.Virtual public String OwnerRoot(String raw) {
		String temp = raw, rv = raw;
		while (true) {
			temp = OwnerDir(temp);
			if (String_.Eq(temp, IoUrlInfo_base.NullString)) break;
			rv = temp;
		}
		return rv;
	}
	public String NameAndExt(String raw) {	// if Dir, will return \ as last char
		int rawLen = String_.Len(raw);
		int ownerDirSprPos = OwnerDirPos(raw, rawLen);
		if (ownerDirSprPos == OwnerDirPos_isNull) return IoUrlInfo_base.NullString;	// NullUrl and RootUrl return Null;
		return ownerDirSprPos == OwnerDirPos_hasNoOwner || ownerDirSprPos == OwnerDirPos_isRoot
			? raw											// no PathSeparator b/c (a) RootDir ("C:\"); (b) relative ("fil.txt")
			: String_.DelBgn(raw, ownerDirSprPos + 1);		// +1 to skip backslash
	}
	public String NameOnly(String raw) {
		String nameAndExt = NameAndExt(raw);
		if (IsDir(raw)) {
			String rootName = XtoRootName(raw, String_.Len(raw));	// C:\ -> C; / -> root
			return rootName == null
				? String_.DelEnd(nameAndExt, IoUrlInfo_base.DirSprLen)
				: rootName;
		}
		int pos = String_.FindBwd(nameAndExt, IoUrlInfo_base.ExtSeparator);
		return pos == String_.Find_none
			? nameAndExt									// Ext not found; return entire NameAndExt
			: String_.MidByLen(nameAndExt, 0, pos);
	}
	public String Ext(String raw) {		// if Dir, return DirSpr; if Fil, return . as first char; ex: .txt; .png
		if (IsDir(raw)) return this.DirSpr();
		String nameAndExt = NameAndExt(raw);
		int pos = String_.FindBwd(nameAndExt, IoUrlInfo_base.ExtSeparator);
		return pos == String_.Find_none ? "" : String_.DelBgn(nameAndExt, pos);
	}
	int OwnerDirPos(String raw, int rawLen) {
		if		(rawLen == 0)						return OwnerDirPos_isNull;
		else if (XtoRootName(raw, rawLen) != null)	return OwnerDirPos_isRoot;
		else	{// NullUrls and RootUrls have no owners
			int posAdj = IsDir(raw) ? IoUrlInfo_base.DirSprLen : 0;	// Dir ends with DirSpr, adjust lastIndex by DirSprLen
			return String_.FindBwd(raw, this.DirSpr(), rawLen - 1 - posAdj); // -1 to adjust for LastIdx
		}
	}
	static final    int
		  OwnerDirPos_hasNoOwner		= -1	// List_adp_.Not_found
		, OwnerDirPos_isNull			= -2
		, OwnerDirPos_isRoot			= -3;
}
class IoUrlInfo_wnt extends IoUrlInfo_base {
	@Override public String Key()			{return "wnt";}
	@Override public String EngineKey()		{return IoEngine_.SysKey;}
	@Override public String DirSpr()			{return Op_sys.Wnt.Fsys_dir_spr_str();}
	@Override public byte DirSpr_byte()		{return Byte_ascii.Backslash;}
	@Override public boolean CaseSensitive()	{return Op_sys.Wnt.Fsys_case_match();}
	@Override public boolean Match(String raw)	{return String_.Len(raw) > 1 && String_.CharAt(raw, 1) == ':';} // 2nd char is :; assumes 1 letter drives
	@Override public String XtoRootName(String raw, int rawLen) {
		return rawLen == 3 && String_.CharAt(raw, 1) == ':' // only allow single letter drives; ex: C:\; note, CharAt(raw, 1) to match Match
			? Char_.To_str(String_.CharAt(raw, 0))
			: null;
	}
	public static final    IoUrlInfo_wnt Instance = new IoUrlInfo_wnt(); IoUrlInfo_wnt() {}
}
class IoUrlInfo_lnx extends IoUrlInfo_base {
	@Override public String Key()			{return "lnx";}
	@Override public String EngineKey()		{return IoEngine_.SysKey;}
	@Override public String DirSpr()			{return DirSprStr;} static final    String DirSprStr = Op_sys.Lnx.Fsys_dir_spr_str();
	@Override public byte DirSpr_byte()		{return Byte_ascii.Slash;}
	@Override public boolean CaseSensitive()	{return Op_sys.Lnx.Fsys_case_match();}
	@Override public boolean Match(String raw)	{return String_.Has_at_bgn(raw, DirSprStr);}	// anything that starts with /
	@Override public String XtoRootName(String raw, int rawLen) {
		return rawLen == 1 && String_.Eq(raw, DirSprStr)
			? "root"
			: null;
	}
	@Override public String OwnerRoot(String raw) {return DirSprStr;}	// drive is always /
	@Override public String Xto_api(String raw) {
		return String_.Eq(raw, DirSprStr)			// is root
			? DirSprStr
			: super.Xto_api(raw);					// NOTE: super.Xto_api will strip off last /
	}
	public static final    IoUrlInfo_lnx Instance = new IoUrlInfo_lnx(); IoUrlInfo_lnx() {}
}
class IoUrlInfo_rel extends IoUrlInfo_base {
	@Override public String Key()			{return "rel";}
	@Override public String EngineKey()		{return IoEngine_.SysKey;}
	@Override public String DirSpr()			{return info.DirSpr();}
	@Override public byte DirSpr_byte()		{return info.DirSpr_byte();}
	@Override public boolean CaseSensitive()	{return info.CaseSensitive();}
	@Override public String XtoRootName(String raw, int rawLen) {return info.XtoRootName(raw, rawLen);}
	@Override public boolean Match(String raw)	{return true;} // relPath is always lastResort; return true
	IoUrlInfo info;
        public static IoUrlInfo_rel new_(IoUrlInfo info) {
		IoUrlInfo_rel rv = new IoUrlInfo_rel();
		rv.info = info;
		return rv;
	}	IoUrlInfo_rel() {}
}
class IoUrlInfo_mem extends IoUrlInfo_base {
	@Override public String Key()			{return key;} private String key;
	@Override public String EngineKey()		{return engineKey;} private String engineKey;
	@Override public String DirSpr()			{return "/";}
	@Override public byte DirSpr_byte()		{return Byte_ascii.Slash;}
	@Override public boolean CaseSensitive()	{return false;}
	@Override public String XtoRootName(String raw, int rawLen) {
		return String_.Eq(raw, key) ? String_.DelEnd(key, 1) : null;
	}
	@Override public boolean Match(String raw) {return String_.Has_at_bgn(raw, key);}
	public static IoUrlInfo_mem new_(String key, String engineKey) {
		IoUrlInfo_mem rv = new IoUrlInfo_mem();
		rv.key = key; rv.engineKey = engineKey;
		return rv;
	}	IoUrlInfo_mem() {}
}
class IoUrlInfo_alias extends IoUrlInfo_base {
	@Override public String Key() {return srcDir;}
	@Override public String EngineKey() {return engineKey;} private String engineKey;
	@Override public String DirSpr() {return srcDirSpr;}
	@Override public byte DirSpr_byte()	{return srcDirSpr_byte;} private byte srcDirSpr_byte;
	@Override public boolean CaseSensitive() {return false;}
	@Override public String XtoRootName(String raw, int rawLen) {
		return String_.Eq(raw, srcRootDir) ? srcRootName : null;
	}
	@Override public boolean Match(String raw) {return String_.Has_at_bgn(raw, srcDir);}
	@Override public String Xto_api(String raw) {			
		String rv = String_.Replace(raw, srcDir, trgDir);											// replace src with trg
		if (!String_.Eq(srcDirSpr, trgDirSpr)) rv = String_.Replace(rv, srcDirSpr, trgDirSpr);		// replace dirSprs
		return IsDir(raw)
			? String_.DelEnd(rv, IoUrlInfo_base.DirSprLen)	// remove trailingSeparator, else Directory.Delete will not work properly
			: rv;
	}
	void SrcDir_set(String v) {
		srcDir = v;
		boolean lnx = DirSpr_lnx(v);
		if (srcDirSpr == null) {
			if (lnx) {
				srcDirSpr = Op_sys.Lnx.Fsys_dir_spr_str();
				srcDirSpr_byte = Op_sys.Lnx.Fsys_dir_spr_byte();
			}
			else {
				srcDirSpr = Op_sys.Wnt.Fsys_dir_spr_str();
				srcDirSpr_byte = Op_sys.Wnt.Fsys_dir_spr_byte();
			}
		}
		if (srcRootName == null) srcRootName = lnx ? "root" : String_.Mid(srcDir, 0, String_.FindFwd(srcDir, ":"));
		if (srcRootDir == null) srcRootDir = lnx ? "/" : srcDir;
	}
	void TrgDir_set(String v) {
		trgDir = v;
		boolean lnx = DirSpr_lnx(v);
		if (trgDirSpr == null) trgDirSpr = lnx ? Op_sys.Lnx.Fsys_dir_spr_str() : Op_sys.Wnt.Fsys_dir_spr_str();
	}
	boolean DirSpr_lnx(String s) {return String_.Has(s, Op_sys.Lnx.Fsys_dir_spr_str());}
	void EngineKey_set(String v) {engineKey = v;}
	String srcDir, trgDir, srcDirSpr, trgDirSpr, srcRootDir, srcRootName;
        public static IoUrlInfo_alias new_(String srcDir, String trgDir, String engineKey) {
		IoUrlInfo_alias rv = new IoUrlInfo_alias();
		rv.SrcDir_set(srcDir);
		rv.TrgDir_set(trgDir);
		rv.EngineKey_set(engineKey);
		return rv;
	}
	public static final    IoUrlInfo_alias KEYS = new IoUrlInfo_alias();
	public final    String
		  Data_EngineKey			= "engineKey"
		, Data_SrcDir				= "srcDir"
		, Data_TrgDir				= "trgDir"
		;
}
