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
public class Err extends RuntimeException {
	@Override public String getMessage() {return Message_gplx();}
	public String Key() {return key;} public Err Key_(String v) {key = v; return this;} private String key = "";
	public String Hdr() {return hdr;} public Err Hdr_(String v) {hdr = v; return this;} private String hdr = "";
	public List_adp Args() {return args;} List_adp args = List_adp_.new_();
	public Err Add(String k, Object o) {args.Add(KeyVal_.new_(k, o)); return this;}
	@gplx.Internal protected ErrProcData Proc()		{return proc;} ErrProcData proc = ErrProcData.Null;
	public Ordered_hash CallStack()	{return callStack;} Ordered_hash callStack = Ordered_hash_.new_();
	public int CallLevel() {return callLevel;} public Err CallLevel_(int val) {callLevel = val; return this;} public Err CallLevel_1_() {return CallLevel_(1);} int callLevel;
	public Err Inner() {return inner;} Err inner;
	@gplx.Internal protected static Err hdr_(String hdr) {
		Err rv = new Err();
		rv.hdr = hdr;
		return rv;
	}	@gplx.Internal protected Err() {}
	@gplx.Internal protected static Err exc_(Exception thrown, String hdr) {
		Err rv = hdr_(hdr + ":" + Err_.Message_lang(thrown));	// add a better error description; DATE:2014-08-15
		rv.inner = convert_(thrown);
		for (int i = 0; i < rv.inner.callStack.Count(); i++) {
			ErrProcData itm = (ErrProcData)rv.inner.callStack.Get_at(i);
			rv.callStack.Add(itm.Raw(), itm);
		}
		return rv;
	}
	@gplx.Internal protected static Err convert_(Exception thrown) {
		Err rv = Err_.as_(thrown);
		if (rv == null)
			rv = Err_.new_key_(ClassAdp_.NameOf_obj(thrown), Err_.Message_lang(thrown));
		CallStack_fill(rv, Err_.StackTrace_lang(rv));
		return rv;
	}
	static void CallStack_fill(Err err, String stackTrace) {
		ErrProcData[] ary = ErrProcData.parse_ary_(stackTrace); if (Array_.Len(ary) == 0) return; // no callStack; shouldn't happen, but don't throw error
		err.proc = ary[0];
		for (ErrProcData itm : ary) {
			String key = itm.Raw();
			if (err.callStack.Has(key)) continue;
			err.callStack.Add(key, itm);
		}
	}
	String Message_gplx() {
		try {return Err_.Message_gplx(this);}
		catch (Exception exc) {Err_.Noop(exc); return super.getMessage();}	
	}
}
