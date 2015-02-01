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
package gplx.xowa.xtns.scribunto.errs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
class Scrib_err_mgr implements GfoInvkAble {
	private OrderedHash hash = OrderedHash_.new_bry_();
	private int key_id = 0;
	private static final byte[] Key_prefix = Bry_.new_ascii_("scrib_err_");
	private Scrib_err_cmd Set(byte[] key) {
		if (key == null) Bry_.Add(Key_prefix, Bry_.XbyInt(key_id++));
		Scrib_err_cmd rv = new Scrib_err_cmd(key);
		hash.Add_if_new(key, rv);
		return rv;
	}
	public void Clear() {
		hash.Clear();
		key_id = 0;
	}
	public void Process(Scrib_err_data err) {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Scrib_err_cmd itm = (Scrib_err_cmd)hash.FetchAt(i);
			if (itm.Warn_disabled(err)) {
				// log
			}
			else {
				// warn
			}

		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set))	return Set(m.ReadBryOr("v", null));
		else if (ctx.Match(k, Invk_clear))	Clear();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_set = "set", Invk_clear = "clear";
}
class Scrib_err_data {
	public Scrib_err_data(byte[] mod, byte[] fnc, byte[] err, byte[] src) {this.mod = mod; this.fnc = fnc; this.err = err; this.src = src;}
	public byte[] Mod() {return mod;} private byte[] mod;
	public byte[] Fnc() {return fnc;} private byte[] fnc;
	public byte[] Err() {return err;} private byte[] err;
	public byte[] Src() {return src;} private byte[] src;
}
class Scrib_err_cmd implements GfoInvkAble {
	public Scrib_err_cmd(byte[] key) {this.key = key;}
	public byte[] Key() {return key;} private byte[] key;
	public int Fail_after() {return fail_after;} private int fail_after = Int_.MaxValue;
	public int Warn_every() {return warn_every;} private int warn_every = 10000;	// worse case of 400 warnings for 4 million pages
	public int Summary_ttls_len() {return summary_ttls_len;} private int summary_ttls_len = 8;
	public String Warn_disabled_if() {return warn_disabled_if;} private String warn_disabled_if;
	private void Warn_disabled_if_(String v) {
		this.warn_disabled_if = v;
		// compile
	}
	public boolean Warn_disabled(Scrib_err_data err) {
		// gplx.core.criterias.Criteria c;
		// c.Matches(err);
		return false;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_warn_disabled_if_))			Warn_disabled_if_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_fail_after_))				fail_after = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_warn_every_))				fail_after = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_summary_ttls_len_))			summary_ttls_len = m.ReadInt("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_warn_disabled_if_ = "warn_disabled_if_"
	, Invk_warn_every_ = "warn_every_", Invk_fail_after_ = "fail_after_"
	, Invk_summary_ttls_len_ = "summmary_ttls_len_"
	;
}
