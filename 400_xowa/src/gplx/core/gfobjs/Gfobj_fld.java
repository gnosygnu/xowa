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
package gplx.core.gfobjs; import gplx.*; import gplx.core.*;
public interface Gfobj_fld {
	byte				Fld_tid();
	String				Key();
	Object				As_obj();
}
class Gfobj_fld_ {
	public static final byte
	  Fld_tid__ary		= 0
	, Fld_tid__nde		= 1
	, Fld_tid__bool		= 2
	, Fld_tid__int		= 3
	, Fld_tid__long		= 4
	, Fld_tid__double	= 5
	, Fld_tid__str		= 6
	, Fld_tid__bry		= 7
	;
}
class Gfobj_fld_str implements Gfobj_fld {
	public Gfobj_fld_str(String key, String val) {this.key = key; this.val = val;}
	public String		Key() {return key;} private final    String key;
	public byte			Fld_tid() {return Gfobj_fld_.Fld_tid__str;}
	public Object		As_obj() {return val;}
	public String		As_str() {return val;} private String val;
}
class Gfobj_fld_bry implements Gfobj_fld {
	public Gfobj_fld_bry(String key, byte[] val) {this.key = key; this.val = val;}
	public String		Key() {return key;} private final    String key;
	public byte			Fld_tid() {return Gfobj_fld_.Fld_tid__bry;}
	public Object		As_obj() {return val;}
	public byte[]		As_bry() {return val;} private byte[] val;
}
class Gfobj_fld_bool implements Gfobj_fld {
	public Gfobj_fld_bool(String key, boolean val) {this.key = key; this.val = val;}
	public String		Key() {return key;} private final    String key;
	public byte			Fld_tid() {return Gfobj_fld_.Fld_tid__bool;}
	public Object		As_obj() {return val;}
	public boolean		As_bool() {return val;} private boolean val;
}
class Gfobj_fld_int implements Gfobj_fld {
	public Gfobj_fld_int(String key, int val) {this.key = key; this.val = val;}
	public String		Key() {return key;} private final    String key;
	public byte			Fld_tid() {return Gfobj_fld_.Fld_tid__int;}
	public Object		As_obj() {return val;}
	public int			As_int() {return val;} private int val;
}
class Gfobj_fld_long implements Gfobj_fld {
	public Gfobj_fld_long(String key, long val) {this.key = key; this.val = val;}
	public String		Key() {return key;} private final    String key;
	public byte			Fld_tid() {return Gfobj_fld_.Fld_tid__long;}
	public Object		As_obj() {return val;}
	public long			As_long() {return val;} private long val;
}
class Gfobj_fld_double implements Gfobj_fld {
	public Gfobj_fld_double(String key, double val) {this.key = key; this.val = val;}
	public String		Key() {return key;} private final    String key;
	public byte			Fld_tid() {return Gfobj_fld_.Fld_tid__double;}
	public Object		As_obj() {return val;}
	public double		As_double() {return val;} private double val;
}
class Gfobj_fld_nde implements Gfobj_fld {
	public Gfobj_fld_nde(String key, Gfobj_nde val) {this.key = key; this.val = val;}
	public String		Key() {return key;} private final    String key;
	public byte			Fld_tid() {return Gfobj_fld_.Fld_tid__nde;}
	public Object		As_obj() {return val;}
	public Gfobj_nde	As_nde() {return val;} private Gfobj_nde val;
}
class Gfobj_fld_ary implements Gfobj_fld {
	public Gfobj_fld_ary(String key, Gfobj_ary val) {this.key = key; this.val = val;}
	public String		Key() {return key;} private final    String key;
	public byte			Fld_tid() {return Gfobj_fld_.Fld_tid__ary;}
	public Object		As_obj() {return val;}
	public Gfobj_ary	As_ary() {return val;} private Gfobj_ary val;
}
