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
